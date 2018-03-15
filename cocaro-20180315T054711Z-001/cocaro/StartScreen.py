from tkinter import *
import time
import random
import winsound


class StartScreen:


	def __init__(self,width,height,data):
		winsound.PlaySound('01 Once Upon a Time.wav', winsound.SND_ALIAS | winsound.SND_ASYNC | winsound.SND_LOOP)
		self.canvas = data.canvas
		self.width = width
		self.height = height
		self.player1 = "Người"
		self.isStart = False
		self.chooseOppo = False
		self.chooseColor = False
		self.fontSize = 300	
		self.isOver = False
		self.info = []
		self.textcolor = True
		self.isChooseDiffcult = False
		self.mode = 0
		
	def keyPressedWrapper(self,event):
		if event.keysym == "Return" and self.isStart == False:
			self.chooseOppo = True
			self.isStart = True		

	def mousePressedWrapper(self,event):
		if self.chooseOppo == True:
			if event.x<self.width/2:
				self.player2 = "Người"
			elif event.x>self.width/2:
				self.player2 = "Máy"
			self.chooseOppo = False
			self.chooseColor = True
			return
		if self.chooseColor == True:
			if event.x<self.width/2:
				self.player2,self.player1 = self.player1,self.player2
			self.info = [(self.player1,"black"),(self.player2,"white")]
			if self.player1 == 'Máy' or self.player2 == 'Máy':
				self.isChooseDiffcult = True
				self.chooseColor = False
			else:
				self.isChooseDiffcult = False
				self.isOver = True
			return
		if self.isChooseDiffcult:
			if event.x<self.width/2:
				self.mode = 1
			else:self.mode = 2
			self.info.append(self.mode)
			self.isOver = True


			#self.canvas.delete(ALL)

	def drawEverything(self):
		if self.isStart == False:
			self.drawStartScreening()
		elif self.chooseOppo:
			self.drawChoicePage()
		elif self.chooseColor:
			self.drawChoiceColorPage()
		elif self.isChooseDiffcult:
			self.drawDiffcultPage()


	def drawChoicePage(self):

		self.canvas.create_rectangle(0,0,self.width/2,self.height,fill = "white")
		self.canvas.create_rectangle(self.width/2,0,self.width,self.height,fill = "black")
		
		self.canvas.create_text(50,self.height/3,text = "Người", fill = "black",anchor = W,font = ("Fixedsys", 40))
		self.canvas.create_text(self.width/4,self.height/2.5+35,text = """Tôi chơi cùng bạn""", fill = "black",font = ("Fixedsys", 8))		
		self.canvas.create_text(self.width/5*4,self.height/3,text = "Máy", fill = "white",font = ("Fixedsys", 40))
		self.canvas.create_text(self.width/5*4,self.height/2.5+35,text = """ Tôi sẽ đánh với máy""", fill = "white",font = ("Fixedsys", 8))
		


	def drawStartScreening(self):
		self.canvas.create_rectangle(0,0,self.width,self.height,fill = "black")	

		self.canvas.create_text(self.width/2,self.height/3,text = 'Cờ Caro', font = ('Courier',self.fontSize,'bold'),fill = "white")
		if self.textcolor:
			fill = "white"
		else: fill = "black"
		self.canvas.create_text(self.width/2,self.height*2/3,text = 'Nhấn Enter để bắt đầu', font = ('Fixedsys',15), fill = fill)
		

	def	drawChoiceColorPage(self):
		self.canvas.create_rectangle(0,0,self.width/2,self.height,fill = "black")
		self.canvas.create_rectangle(self.width/2,0,self.width,self.height,fill = "white")
		
		self.canvas.create_text(50,self.height/3,text = "White", fill = "white",anchor = W,font = ("Fixedsys", 40))
		self.canvas.create_text(self.width/4,self.height/2.5+35,text = """Tôi rất giỏi về phòng thủ,\nmáy đi trước""", fill = "white",font = ("Fixedsys", 8))		
		self.canvas.create_text(self.width/5*4,self.height/3,text = "Black ", fill = "Black",font = ("Fixedsys", 40))
		self.canvas.create_text(self.width/5*4,self.height/2.5+35,text = """Tấn công tui rất giỏi ,\ntui đi trước""", fill = "black",font = ("Fixedsys", 8))
		

	def drawDiffcultPage(self):
		self.canvas.create_rectangle(0,0,self.width/2,self.height,fill = "white")
		self.canvas.create_rectangle(self.width/2,0,self.width,self.height,fill = "black")
		
		self.canvas.create_text(50,self.height/2.5,text = " Easy", fill = "black",anchor = W,font = ("Fixedsys", 40))
		self.canvas.create_text(self.width/4,self.height/2+35,text = """Bạn có thể đánh mà không cần \nsuy nghĩ trong vấn cờ này""", fill = "black",font = ("Fixedsys", 8))		
		self.canvas.create_text(self.width/5*4,self.height/2.5,text = "Hard", fill = "white",font = ("Fixedsys", 40))
		self.canvas.create_text(self.width/5*4,self.height/2+35,text = """Bạn sẽ thốn nhiều nhiều \nthời gian trong ván cờ này""", fill = "white",font = ("Fixedsys", 8))
		
