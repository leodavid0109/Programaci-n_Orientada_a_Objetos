from tkinter import Tk
from tkinter import Button
from tkinter import Label

class Tablas():

    @staticmethod
    def impresionCuentasCorriente(cuentas, frame, row, column = 0):

        i = row
        j = column
        k = 1

        encabezados = ["#", "ID", "NOMBRE", "TITULAR", "CUPO", "DISPONIBLE", "PLAZO PAGO", "INTERESES", "PRIMER MENSUALIDAD", "BANCO"]

        llenado = []
        for cuenta in cuentas:
            c_ordenada = []
            c_ordenada.append(cuenta.getId())
            c_ordenada.append(cuenta.getNombre())
            c_ordenada.append(cuenta.getTitular().getNombre())
            c_ordenada.append(str(round(cuenta.getCupo(), 2)) + " " + cuenta.getDivisa().value)
            c_ordenada.append(str(round(cuenta.getDisponible(), 2)) + " " + cuenta.getDivisa().value)
            c_ordenada.append(cuenta.getPlazo_Pago().value)
            c_ordenada.append(str(round(cuenta.getIntereses(), 2)) + " %")
            if cuenta.getPrimerMensualidad() == 0:
                c_ordenada.append("False")
            else:
                c_ordenada.append("True")
            c_ordenada.append(cuenta.getBanco().getNombre())
            llenado.append(c_ordenada)

        for col, encabezado in enumerate(encabezados):
            label_encabezado = Label(frame, text = encabezado, relief= "ridge", padx = 10, pady = 5)
            label_encabezado.grid(row=i, column= j + col, sticky="nswe")

        i += 1

        for row, cuenta in enumerate(llenado, start=1):
            num_el = Label(frame, text=str(k), relief= "ridge", padx = 10, pady = 5)
            num_el.grid(row= i + row, column=j, sticky="nswe")

            for col, valor in enumerate(cuenta):
                label_cuentas = Label(frame, text=valor, relief= "ridge", padx = 10, pady = 5)
                label_cuentas.grid(row= i + row, column= 1 + j + col, sticky="nswe")

            i += 1
            k += 1

    @staticmethod
    def impresionCuentasCorrienteInteres(cuentas, intereses, frame, row, column = 0):
        i = row
        j = column
        k = 1

        encabezados = ["#", "ID", "NOMBRE", "TITULAR", "CUPO", "DISPONIBLE", "PLAZO PAGO", "INTERESES", "PRIMER MENSUALIDAD", "BANCO", "INTERÉS NUEVO"]

        m = 0
        llenado = []
        for cuenta in cuentas:
            c_ordenada = []
            c_ordenada.append(cuenta.getId())
            c_ordenada.append(cuenta.getNombre())
            c_ordenada.append(cuenta.getTitular().getNombre())
            c_ordenada.append(str(round(cuenta.getCupo(), 2)) + " " + cuenta.getDivisa().value)
            c_ordenada.append(str(round(cuenta.getDisponible(), 2)) + " " + cuenta.getDivisa().value)
            c_ordenada.append(cuenta.getPlazo_Pago().value)
            c_ordenada.append(str(round(cuenta.getIntereses(), 2)) + " %")
            if cuenta.getPrimerMensualidad() == 0:
                c_ordenada.append("False")
            else:
                c_ordenada.append("True")
            c_ordenada.append(cuenta.getBanco().getNombre())
            c_ordenada.append(str(round(intereses[m], 2)) + " %")
            llenado.append(c_ordenada)
            m += 1

        for col, encabezado in enumerate(encabezados):
            label_encabezado = Label(frame, text = encabezado, relief= "ridge", padx = 10, pady = 5)
            label_encabezado.grid(row=i, column= j + col, sticky="nswe")

        i += 1

        for row, cuenta in enumerate(llenado, start=1):
            num_el = Label(frame, text=str(k), relief= "ridge", padx = 10, pady = 5)
            num_el.grid(row= i + row, column=j, sticky="nswe")

            for col, valor in enumerate(cuenta):
                label_cuentas = Label(frame, text=valor, relief= "ridge", padx = 10, pady = 5)
                label_cuentas.grid(row= i + row, column= 1 + j + col, sticky="nswe")

            i += 1
            k += 1

    @staticmethod
    def impresionCotizaciones(movimientos, frame, row, column=0):
        i = row
        j = column
        k = 1

        encabezados = ["#", "CUENTA", "BANCO", "TASA", "CUOTA DE MANEJO"]

        llenado = []
        for movimiento in movimientos:
            m_ordenada = []
            m_ordenada.append((str(movimiento.getOrigen().getId()) + ": " + movimiento.getOrigen().getNombre()))
            m_ordenada.append(cuenta.getBanco().getNombre())
            m_ordenada.append(str(round(movimiento.getCantidad(), 4)))
            m_ordenada.append(str(round(movimiento.getCuotaManejo, 4)))
            llenado.append(m_ordenada)

        for col, encabezado in enumerate(encabezados):
            label_encabezado = Label(frame, text = encabezado, relief= "ridge", padx = 10, pady = 5)
            label_encabezado.grid(row=i, column= j + col, sticky="nswe")

        i += 1

        for row, cuenta in enumerate(llenado, start=1):
            num_el = Label(frame, text=str(k), relief= "ridge", padx = 10, pady = 5)
            num_el.grid(row= i + row, column=j, sticky="nswe")

            for col, valor in enumerate(cuenta):
                label_cuentas = Label(frame, text=valor, relief= "ridge", padx = 10, pady = 5)
                label_cuentas.grid(row= i + row, column= 1 + j + col, sticky="nswe")

            i += 1
            k += 1