from SerVivo import SerVivo
class Animal(SerVivo):
    _totalCreados = 0

    def __init__(self, nombre, edad, raza):
        self._nombre = nombre
        self._edad = edad
        self._raza = raza
        Animal._totalCreados += 1

    def setRaza(self, raza):
        self._raza = raza
    
    def getRaza(self):
        return self._raza
    
    def setNombre(self, nombre):
        self._nombre = nombre
    
    def getNombre(self):
        return self._nombre
    def caminar():
        pass

    def correr():
        pass

    def ruido():
        pass

    @classmethod
    def getTotalCreados(cls):
        return cls._totalCreados