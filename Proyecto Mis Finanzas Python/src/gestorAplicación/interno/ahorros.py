from .cuenta import Cuenta
from excepciones import accountsException
from datetime import datetime
import random

class Ahorros(Cuenta):
    #Atributos de clase
    _cuentasAhorroTotales = []

    #Constructor
    def __init__(self, **kwargs):
        #Atributos de instancia
        Ahorros._cuentasAhorroTotales.append(self)
        if "saldo" in kwargs:
            self._saldo = kwargs.pop("saldo")
        super().__init__(**kwargs)

#REVISAR SOBRECARGA

    def crearCuenta(self, banco, clave, nombre, **kwargs):
        if "divisa" in kwargs:
            return Ahorros(banco = banco, clave = clave, nombre = nombre, divisa = kwargs["divisa"])
        else:
            return Ahorros(banco = banco, clave = clave, nombre = nombre)
    
    def __str__(self):
        return "Cuenta: " + self._nombre + "\nCuentas de Ahorros # " + str(self._id) + "\nTitular: " + self.getTitular().getNombre() + "\nBanco: " + self._banco.getNombre() + "\nDivisa: " + str(self._divisa.value) + "\nSaldo: " + str(self._saldo) + " " + str(self._divisa.value)
    
    def __contains__(self, elementos):
        for elemento in elementos:
            if self.getId() == elemento.getId():
                return elementos.index(elemento)
        else:
            return None
        
    # Método de la funcionalidad asesoramiento de inversiones
    def vaciar_cuenta(self, gota):
        from .movimientos import Movimientos
        from .categoria import Categoria
        movimiento = Movimientos(origen = self, destino = gota, cantidad = self.getSaldo(), categoria = Categoria.OTROS, fecha = datetime.now())
        Movimientos.getMovimientosTotales().remove(movimiento)
        self.setSaldo(0)

    # Funcionalidad de Suscripciones de Usuarios
    def invertirSaldo(self):
        from .movimientos import Movimientos
        from .categoria import Categoria
        probabilidad = self.getTitular().getSuscripcion().getProbabilidadInversion()
        rand = random.random()+probabilidad
        print()
        if(rand >= 1 and self.getSaldo() != 0):
            return(Movimientos.crearMovimiento(self, self.getSaldo() + self.getSaldo() * probabilidad, Categoria.FINANZAS, datetime.now()))
        else:
            raise accountsException.FailedInvestmentException(self.getTitular())


    # Funcionalidad Prestamos
    @classmethod
    def comprobarPrestamo(cls,_cuentas):
        cuentasPrestamos = []
        bancos = []

        for cuenta in _cuentas:
            prestamos = cuenta.getBanco().getPrestamo()
            if(prestamos>0):
                cuentasPrestamos.append(cuenta)
            else:
                bancos.append(cuenta.getBanco())
        if len(cuentasPrestamos)!=0:
            return cuentasPrestamos
        else:
            return bancos

    @staticmethod
    def limpiarPropiedades(cls, arreglo):
        arreglo.remove("cuentasTotales")
        #Verificar que otras variables se crean
    #Verificar su uso

    #Realizar el método del compare to
    def compareTo(self, cuenta):
        if self.getSaldo() > cuenta.getSaldo():
            return 1
        elif self.getSaldo() < cuenta.getSaldo():
            return -1
        else:
            return 0

    #Métodos Get & Set
    def getSaldo(self):
        return self._saldo
    def setSaldo(self, saldo):
        self._saldo = saldo

    @classmethod
    def getCuentasAhorrosTotales(cls):
        return cls._cuentasAhorroTotales
    @classmethod
    def setCuentasAhorrosTotales(cls, cuentasAhorrosTotales):
        cls._cuentasAhorroTotales = cuentasAhorrosTotales