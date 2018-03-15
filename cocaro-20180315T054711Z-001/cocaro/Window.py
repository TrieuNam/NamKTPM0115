from tkinter import * 

class Window(object):
	def __init__(self,width,height):
		self.root = Tk()
		self.root.title("Co ca ro")

	def quit(self):
		self.root.destroy
