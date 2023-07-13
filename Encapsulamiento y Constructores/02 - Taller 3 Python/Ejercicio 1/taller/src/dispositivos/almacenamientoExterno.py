from almacenamientos.almacenamiento import Almacenamiento


class AlmacenamientoExterno:
    _cantidad = 0

    def __init__(self, nombre):
        self._nombre = nombre
        self._almacenamiento = Almacenamiento(1000, "HDD")
        AlmacenamientoExterno._cantidad += 1

    def setNombre(self, nombre):
        self._nombre = nombre
    
    def getNombre(self):
        return self._nombre

    def setAlmacenamiento(self, almacenamiento):
        self._almacenamiento = almacenamiento

    def getAlmacenamiento(self):
        return self._almacenamiento
    
    @classmethod
    def getCantidad(cls):
        return cls._cantidad