from tkinter import *
import time
import random
from StartScreen import *
from Window import *
from Bang import *
from Game import *
from Ve import *
import winsound

def init(data):
	data.s = StartScreen(data.width, data.height,data)
	data.isStart = False
	data.anitime = 0
	data.drawWin = DrawDia(data)
	data.drawIns = DrawDia(data)
	data.drawDiff = DrawDia(data)
	data.drawColor = DrawDia(data)


def mousePressed(event, data):
	if not data.s.isOver:
		data.s.mousePressedWrapper(event)
	elif data.isStart and data.g.board.isWin == False and data.g.inComputerTurn == False and not data.g.inSelectHuman:
		data.g.takeTurn(event)
		
def keyPressed(event, data):
	if event.keysym == "r":
		init(data)
	if not data.s.isOver:
		data.s.keyPressedWrapper(event)
		redrawAll(data.canvas, data)

def timerFired(data):
	if not data.s.isOver and data.s.fontSize > 80:
		data.s.fontSize -= 2

	if not data.s.isOver and data.anitime%600 == 0:
		data.s.textcolor = not data.s.textcolor
		data.anitime+=10
	else:
		data.anitime+=10
	if data.s.isOver and data.isStart == False:
		data.g = Game(data.s.info,data)

		data.isStart = True
	if data.isStart	== True and data.g.currentPlayer[0]=='Máy' and not data.g.board.isWin:
		data.g.inComputerTurn = True
		
		data.g.computerTurn()

	if data.isStart	== True and data.g.board.isWin:

		data.drawWin.onTimerFired()
		
	if not data.s.isOver and data.s.chooseOppo:
		data.drawIns.onTimerFired()
	if not data.s.isOver and data.s.isChooseDiffcult:
		data.drawDiff.onTimerFired()
	if not data.s.isOver and data.s.chooseColor:
		data.drawColor.onTimerFired()
def redrawAll(canvas, data):
	

	if not data.s.isOver:
		data.s.drawEverything()
	if not data.s.isOver and data.s.chooseOppo:
		data.drawIns.drawChooseOppo(canvas)
	if not data.s.isOver and data.s.isChooseDiffcult:
		data.drawDiff.drawChooseDiff(canvas)
	if not data.s.isOver and data.s.chooseColor:
		data.drawColor.drawChooseColor(canvas)
	elif data.isStart:
		data.g.board.drawBoard(data.canvas)
		if data.g.board.isWin:
			data.drawWin.drawWin(canvas, data.g.notCurrentPlayer[1].upper(),data.g.notCurrentPlayer[0],data.g.currentPlayer[0])
			
			
def run(width=300, height=300):
	def redrawAllWrapper(canvas, data):
		canvas.delete(ALL)
		redrawAll(canvas, data)
		canvas.update()    

	def mousePressedWrapper(event, canvas, data):
		mousePressed(event, data)
		redrawAllWrapper(canvas, data)

	def keyPressedWrapper(event, canvas, data):
		keyPressed(event, data)
		redrawAllWrapper(canvas, data)

	def timerFiredWrapper(canvas, data):
		timerFired(data)
		redrawAllWrapper(canvas, data)
		canvas.after(data.timerDelay, timerFiredWrapper, canvas, data)
		
	class Struct(object): pass
	data = Struct()
	data.width = width
	data.height = height
	data.timerDelay = 1 # milliseconds
	
	
	data.root = Tk()
	
	data.root.title("cờ caro")
	data.canvas = Canvas(data.root, width=data.width, height=data.height)
	data.canvas.pack()
	init(data)
	
	# set up events
	data.canvas.bind("<Button-1>", lambda event:
							mousePressedWrapper(event, data.canvas, data))
	data.root.bind("<Key>", lambda event:
							keyPressedWrapper(event, data.canvas, data))
	timerFiredWrapper(data.canvas, data)
	data.root.mainloop()  
