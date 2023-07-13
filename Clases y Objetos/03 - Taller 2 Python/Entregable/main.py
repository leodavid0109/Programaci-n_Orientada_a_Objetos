class Asiento:
    def __init__(self, color, precio, registro):
        self.color = color
        self.precio = precio
        self.registro = registro

    def cambiarColor(self, color):
        if color == "rojo":
            self.color = color
        elif color == "verde":
            self.color = color
        elif color == "amarillo":
            self.color = color
        elif color == "negro":
            self.color = color
        elif color == "blanco":
            self.color = color
    

class Auto:
    cantidadCreados = 0
    def __init__(self, modelo, precio, asientos, marca, motor, registro):
        self.modelo = modelo
        self.precio = precio
        self.asientos = asientos
        self.marca = marca
        self.motor = motor
        self.registro = registro
    
    def cantidadAsientos(self):
        cantidad_asientos = 0
        for asiento in self.asientos:
            if isinstance(asiento, Asiento):
                cantidad_asientos += 1
        return cantidad_asientos

    def verificarIntegridad(self):
        for asiento in self.asientos:
            if asiento == None:
                continue
            elif self.registro != asiento.registro:
                return "Las piezas no son originales"
        if self.registro != self.motor.registro:
            return "Las piezas no son originales"
        return "Auto original"


class Motor:
    def __init__(self, numeroCilindros, tipo, registro):
        self.numeroCilindros = numeroCilindros
        self.tipo = tipo
        self.registro = registro

    def cambiarRegistro(self, registro):
        self.registro = registro
    
    def asignarTipo(self, tipo):
        if tipo == "electrico":
            self.tipo = tipo
        elif tipo == "gasolina":
            self.tipo = tipo