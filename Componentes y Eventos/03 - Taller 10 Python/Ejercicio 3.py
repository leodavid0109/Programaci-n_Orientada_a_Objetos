import tkinter as tk

def agregar_nombre():
    nombre = entrada.get()
    if nombre != "":
        lista.insert(tk.END, nombre)
        entrada.delete(0, tk.END)

ventana = tk.Tk()
ventana.title("Agregar Nombres")
ventana.config(padx=10, pady=10)

label_nombre = tk.Label(ventana, text="Nombre:")
label_nombre.grid(row=0, column=0)

entrada = tk.Entry(ventana)
entrada.grid(row=0, column=1, padx=10, pady=10)

boton_agregar = tk.Button(ventana, text="Agregar", command=agregar_nombre)
boton_agregar.grid(row=0, column=2)

lista = tk.Listbox(ventana)
lista.grid(row=1, column=1)

ventana.mainloop()