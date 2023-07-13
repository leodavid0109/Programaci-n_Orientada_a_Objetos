from enum import Enum
class Cuotas(Enum):
    #Constantes
    C1 = (1)
    C6 = (6)
    C12 = (12)
    C18 = (18)
    C24 = (24)
    C36 = (36)
    C48 = (48)

    #Constructor
    def __init__(self, cantidad_Cuotas):
        #Atributos de instancia
        self._CANTIDAD_CUOTAS = cantidad_Cuotas

    #Métodos de instancia
    def getCantidad_Cuotas(self):
        return self._CANTIDAD_CUOTAS
    
    def __str__(self):
        if (self._CANTIDAD_CUOTAS == 1):
            return str(self._CANTIDAD_CUOTAS) + " cuota"
        else:
            return str(self._CANTIDAD_CUOTAS) + " cuotas"