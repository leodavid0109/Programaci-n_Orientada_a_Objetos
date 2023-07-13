class NoBanksException(Exception):
    def __init__(self, _nameUser, *args: object) -> None:
        super().__init__(*args)
        self._nameUser = _nameUser
    
    def show_message(self):
        return("Error. El usuario " + self._nameUser.getNombre() + " no tiene bancos asociados. Inténtelo más tarde")

class NoBankSelectedException(Exception):
    def __init__(self, *args: object) -> None:
        super().__init__(*args)

    @staticmethod
    def show_message():
        return ("Debes seleccionar un banco para continuar. \n¿Desea intentarlo de nuevo? ")