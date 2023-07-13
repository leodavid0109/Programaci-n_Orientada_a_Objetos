from SerVivo import SerVivo
class Persona(SerVivo):
    _totalCreados = 0

    def __init__(self, nombre, edad):
        super.__init__(nombre, edad)
        self._animalAcargo = None
        Persona._totalCreados += 1

    def aduenarAnimal(self, x):
        self._animalAcargo = x
        x.ruido()
    
    @classmethod
    def getTotalCreados(cls):
        return cls._totalCreados