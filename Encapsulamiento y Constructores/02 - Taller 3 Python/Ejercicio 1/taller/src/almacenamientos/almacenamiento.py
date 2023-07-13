class Almacenamiento:
    def __init__(self, tamano, tipo):
        self._TAMANO = tamano
        self.tipo = tipo

    def getTamano(self):
        return self._TAMANO

    def setTamano(self, tamano):
        self._TAMANO = tamano