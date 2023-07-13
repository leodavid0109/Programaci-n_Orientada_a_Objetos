class Ram:
    def __init__(self, tamano, tipoModulo):
        self._TAMANO = tamano
        self._tipoModulo = tipoModulo

    def setTipoModulo(self, tipoModulo):
        self._tipoModulo = tipoModulo

    def getTipoModulo(self):
        return self._tipoModulo
    
    def getTamano(self):
        return self._TAMANO