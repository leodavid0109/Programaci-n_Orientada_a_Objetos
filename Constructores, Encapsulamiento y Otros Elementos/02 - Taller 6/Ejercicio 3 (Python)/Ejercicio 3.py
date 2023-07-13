from enum import Enum


class Nombre(Enum):
    PEPE = "Pepe"
    JAIME = "Jaime"
    HERNAN = "Hernan"


class Persona:
    numPersonas = 0

    def __init__(self):
        Persona.numPersonas += 1


class Padre(Persona):
    pass


class Abuelo(Padre):
    def __init__(self, nombre, hijo):
        self._nombre = nombre
        self._hijo = hijo
        #super().__init__() Punto 2

    def getNombre(self):
        return self._nombre
    

class Bisabuelo(Abuelo):
    def __init__(self, nombre, hijo, edad):
        self._edad = edad
        self._nombre = nombre
        super().__init__(Nombre.PEPE.value, hijo)


if __name__ == "__main__":

    p1 = Persona()
    p2 = Padre()
    p3 = Abuelo(Nombre.JAIME.value, p2)
    p4 = Bisabuelo(Nombre.HERNAN.value, p3, 89)

    print(Persona.numPersonas)
    print(p4.getNombre())