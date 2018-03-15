from tkinter import *
class DrawDia(object):
	def __init__(self,data):
		self.y = data.height
		self.x = data.width-50
		self.height = 200
		self.text = ""
		self.textPos = 0
		self.textCount = 0

	def onTimerFired(self):
		if self.y > 600-self.height:
			self.y -= 1
		if self.y < 600-self.height +2 and self.textPos<=len(self.text):
			if self.textCount%80 == 0:
				self.textPos+=1
				self.textCount+=10
			else:
				self.textCount+=10

	def drawWin(self,canvas,winner,player,another):
		self.height = 200
		self.text ="* " + winner + " Win"
		if player == "Máy":
			self.text+="\n* Đợi Bạn đã bị đánh bại bởi một ...\n  Máy??"
			self.text+="\n* Hãy thử sức lần sau hoặc..."
		self.text+="\n* Nhấn r để khởi động\n   lại trò chơi"
		if player == "Người" and another == "Người":
			self.text+="\n* Chơi với con người là...\n* Hãy thử một ...\n* Máy!"
		self.text = self.text.upper()
		self.drawBox(canvas)
		self.drawText(canvas)

	def drawBox(self,canvas):
		canvas.create_rectangle(44,self.y-2,self.x+5,self.y+self.height,fill = 'white') 
		canvas.create_rectangle(48,self.y+2,self.x+1,self.y+self.height-3,fill = 'black')

	def drawText(self,canvas):
		text = self.text[:self.textPos] + "_"
		canvas.create_text(56,self.y+10,anchor = NW, text = text, fill = "white", font = ("Fixedsys",22))
		
	def drawChooseOppo(self,canvas):## Chon che do tro choi
		self.height = 180
		self.text = "*Để cài đặt trò chơi...\n*Bạn hãy chọn người hoặc máy..!\n "
		self.drawBox(canvas)
		self.drawText(canvas)

	def drawChooseDiff(self,canvas):## huong dan choi voi may
		self.height = 200
		self.text = "* Bạn hãy thử sức với chế độ \n  Easy hoặc Hard...\n   "
		self.drawBox(canvas)
		self.drawText(canvas)

	def drawChooseColor(self,canvas):## huong dan tro choi
		self.height = 200
		self.text = "*Bạn chọn tấn công hay phòng\n  thủ...!\n*Bạn phải tìm cách tích đủ 5 ô \ntheo chiều dọc hoặc chiều ngang \nhoặc đường chéo mà không bị chặn \n2 đầu thì sẽ thắng..!"
		self.drawBox(canvas)
		self.drawText(canvas)
