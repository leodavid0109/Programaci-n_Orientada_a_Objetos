from Animal import Animal
class Pajaro(Animal):
    _totalCreados = 0

    def __init__(self, nombre, edad, raza, colorAlas):
        super.__init__(nombre, edad, raza)
        self._colorAlas = colorAlas
        Pajaro._totalCreados += 1

    def setColorAlas(self, colorAlas):
        self._colorAlas = colorAlas

    def getColorAlas(self):
        return self._colorAlas
    
    def caminar():
        return "se mueve poco"
    
    def correr():
        return "no corre"
    
    def volar():
        return "vuela al punto"
    
    def ruido():
        print("cantar y silbar")
    
    @classmethod
    def getTotalCreados(cls):
        return cls._totalCreados