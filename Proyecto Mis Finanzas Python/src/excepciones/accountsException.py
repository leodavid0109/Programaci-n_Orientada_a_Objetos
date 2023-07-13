class NoSavingAccountsAssociatedException(Exception):
    def __init__(self, _nameUser, *args: object) -> None:
        super().__init__(*args)
        self._nameUser = _nameUser
    
    def show_message(self):
        return("Error. El usuario " + self._nameUser.getNombre() + " no tiene cuentas de ahorro asociadas. Inténtelo más tarde.")
    
class NoBalanceinSavingAccountException(Exception):
    def __init__(self, _savingAccount, *args: object) -> None:
        super().__init__(*args)
        self._savingAccount = _savingAccount
    
    def show_message(self):
        return("Error. La cuenta {} de ahorros selecciona no tiene saldo. \n¿Desea consignarle saldo? ". format(self._savingAccount.getNombre()))
    
class FailedInvestmentException(Exception):
    def __init__(self, _User, *args: object) -> None:
        super().__init__(*args)
        self._User = _User
    
    def show_message(self):
        return("Su inversion ha fallado, inténtelo de nuevo. Considere subir de nivel para aumentar la probabilidad de tener inversiones exitosas. Su nivel de suscripción actual es de {}".format(self._User.getSuscripcion().name))
    
class NotEnoughSavingAccountsException(Exception):
    def __init__(self, _User, *args: object) -> None:
        super().__init__(*args)
        self._User = _User
    
    def show_message(self):
        return("Error. {} debes tener más de una cuenta de ahorros asociada. \n¿Deseas crear una cuenta de ahorros? ".format(self._User.getNombre()))

class NotEnoughTotalSavingAccountsException(Exception):
    def __init__(self, *args: object) -> None:
        super().__init__(*args)
    
    @staticmethod
    def show_message():
        return("Error. En el sistema debe existir más de una cuenta de ahorros.")
    
class NoAccountSelectedException(Exception):
    def __init__(self, *args: object) -> None:
        super().__init__(*args)

    @staticmethod
    def show_message():
        return ("Debes seleccionar una cuenta para continuar. \n¿Desea intentarlo de nuevo? ")
    
class MaxLimitAccountsReached(Exception):
    def __init__(self, _user, *args: object) -> None:
        super().__init__(*args)
        self._user = _user

    def show_message(self):
        return ("Debes verificar que no hayas alcanzado el máximo de cuentas que puede crear el usuario. El máximo de cuentas que puede asociar el usuario " + self._user.getNombre()  + " es " + str(self._user.getLimiteCuentas()) + " y la cantidad de cuentas asociadas es " + str(len(self._user.getCuentasAsociadas())) + " \n¿Desea cambiar su nivel de suscripción?")