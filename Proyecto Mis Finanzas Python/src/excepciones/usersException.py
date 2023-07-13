class NoUserFoundException(Exception):
    def __init__(self,*args: object) -> None:
        super().__init__(*args)

    @staticmethod
    def show_message():
        return("Error, no se ha encontrado un usuario con estos datos. \n¿Desea intentarlo de nuevo? ")