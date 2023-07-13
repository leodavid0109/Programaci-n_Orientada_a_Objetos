from tkinter import Tk, Label, Button

root = Tk()

def myClick():
    myLabel = Label(root, text = "Llegó la mazamorra")
    myLabel.pack()

myButton = Button(root, text="Click me", command = myClick, fg="gray", bg="white", padx=50, pady=20)
myButton.pack()


root.mainloop()