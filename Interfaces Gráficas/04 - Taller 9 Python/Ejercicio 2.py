from tkinter import Tk, Label

root = Tk()

myLabel_1 = Label(root, text="Hola chicos de Pepino")
myLabel_2 = Label(root, text = "Hola chicos de Samara")
myLabel_3 = Label(root, text="Hola chicos de Jaime")

myLabel_1.grid(row=0, column=0)
myLabel_2.grid(row = 1, column = 1)
myLabel_3.grid(row=2, column = 2)


root.mainloop()