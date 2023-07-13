import tkinter as tk

def cambioPantalla():
    def evento():
        pass

    def retornoPantalla():
        ventana2.destroy()
        ventana.deiconify()

    ventana.withdraw()

    ventana2 = tk.Tk()
    ventana2.title("Ventana Principal")

    menu = tk.Menu(ventana2)
    ventana2.config(menu=menu)

    menu1 = tk.Menu(menu)
    menu.add_cascade(label="Archivo", menu=menu1, command=evento)

    menu1.add_command(label="Salir", command=retornoPantalla)

    ventana2.protocol("WM_DELETE_WINDOW", retornoPantalla)

    ventana2.mainloop()

ventana = tk.Tk()
ventana.title("Ventana Inicio")
ventana.geometry("400x200")

boton1 = tk.Button(ventana, text="Ventana Principal", command=cambioPantalla)
boton1.grid(row=1, column=1)

#Forma de centrar el grid a la fuerza, se aumentan las medidas de estas celdas a más no poder
ventana.grid_rowconfigure(0, weight=1)
ventana.grid_columnconfigure(0, weight=1)
ventana.grid_rowconfigure(2, weight=1)
ventana.grid_columnconfigure(2, weight=1)

ventana.mainloop()