class UnderAccountsLimitException(Exception):
    def __init__(self, _suscriptionLevel, _nameUser,*args: object) -> None:
        super().__init__(*args)
        self._suscriptionLevel = _suscriptionLevel
        self._nameUser = _nameUser

    def show_message(self):
        return("Error. El nivel de suscripción que escogiste tiene un limite de cuentas para asociar de " + str(self._suscriptionLevel.getLimiteCuentas()) + " y el número de cuentas que tienes asociadas actualmente es de " + str(len(self._nameUser.getCuentasAsociadas())) + ".\nVolviendo al menú anterior.")
    
class NoSuscriptionSelectedException(Exception):
    def __init__(self, *args: object) -> None:
        super().__init__(*args)

    @staticmethod
    def show_message():
        return ("Debes seleccionar un nivel de suscripción para continuar. \n¿Desea intentarlo de nuevo? ")