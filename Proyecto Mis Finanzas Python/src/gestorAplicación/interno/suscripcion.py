from enum import Enum
class Suscripcion(Enum):
    #Constantes
    DIAMANTE = (13, 0.80, 4, 2.0)
    ORO = (10, 0.60, 3, 1.5)
    PLATA = (7, 0.40, 2, 1)
    BRONCE = (4, 0.20, 2, 0.5)

    #Constructor
    def __init__(self, _LIMITECUENTAS, _PROBABILIDADINVERSION, _MAXDEUDAS, _PORCENTAJEPRESTAMO) -> None:
        #Atributos de instancia
        self._LIMITECUENTAS = _LIMITECUENTAS
        self._PROBABILIDADINVERSION = _PROBABILIDADINVERSION
        self._MAXDEUDAS = _MAXDEUDAS
        self._PORCENTAJEPRESTAMO = _PORCENTAJEPRESTAMO
    
    #Métodos de instancia
    def getLimiteCuentas(self) -> int:
        return self._LIMITECUENTAS
    def getProbabilidadInversion(self) -> float:
        return self._PROBABILIDADINVERSION
    def getMaxDeudas(self) -> int:
        return self._MAXDEUDAS
    def getPorcentajePrestamo(self) -> float:
        return self._PORCENTAJEPRESTAMO

    #Métodos estáticos
    @staticmethod
    def getNivelesSuscripcion() -> list:
        return([Suscripcion.DIAMANTE, Suscripcion.ORO, Suscripcion.PLATA, Suscripcion.BRONCE])
