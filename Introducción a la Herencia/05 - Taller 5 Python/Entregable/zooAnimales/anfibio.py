from zooAnimales.animal import Animal
class Anfibio(Animal):
    _listado = list()
    ranas = 0
    salamandras = 0

    def __init__(self, nombre="", edad=0, habitat="", genero="", colorPiel = "", venenoso = False):
        super().__init__(nombre, edad, habitat, genero)
        self._colorPiel = colorPiel
        self._venenoso = venenoso
        Anfibio._listado.append(self)
    
    def movimiento(self):
        return "saltas"
    
    @classmethod
    def cantidadAnfibios(cls):
        return len(cls._listado)
    
    @classmethod
    def crearRana(cls, nombre, edad, genero):
        cls.ranas += 1
        return Anfibio(nombre, edad, "selva", genero, "rojo", True)
    
    @classmethod
    def crearSalamandra(cls, nombre, edad, genero):
        cls.salamandras += 1
        return Anfibio(nombre, edad, "selva", genero, "negro y amarillo", False)
    
    @classmethod
    def getListado(cls):
        return cls._listado
    
    @classmethod
    def setListado(cls, listado):
        cls._listado = listado

    def getColorPiel(self):
        return self._colorPiel
    
    def setColorPiel(self, colorPiel):
        self._colorPiel = colorPiel
    
    def isVenenoso(self):
        return self._venenoso
    
    def setVenenoso(self, venenoso):
        self._venenoso = venenoso
    
