class SerVivo:
    _totalCreados = 0

    def __init__(self, nombre, edad):
        self._nombre = nombre
        self._edad = edad
        SerVivo._totalCreados += 1

    def setNombre(self, nombre):
        self._nombre = nombre

    def getNombre(self):
        return self._nombre
    
    def setEdad(self, edad):
        self._edad = edad

    def getEdad(self):
        return self._edad
    
    @classmethod
    def getTotalCreados(cls):
        return cls._totalCreados