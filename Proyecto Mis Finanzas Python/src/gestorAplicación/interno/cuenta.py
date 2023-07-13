from abc import ABC, abstractmethod
from datetime import date, datetime
from gestorAplicación.externo.banco import Banco
import math


class Cuenta(ABC):
    #Atributos de clase
    _cuentasTotales = []

    #Constructor
    def __init__(self, _banco = Banco(), **kwargs):
        self._clave = None
        self._nombre = ""
        self._divisa = None
        self._titular = None
        self._banco = None
        #Atributos de instancia
        Cuenta._cuentasTotales.append(self)
        self._id = len(Cuenta._cuentasTotales)
        for key in kwargs:
            if key == "banco":
                self._banco = kwargs[key]
            if key == "clave":
                self._clave = kwargs[key]
            if key == "nombre":
                self._nombre = kwargs[key]
            if key == "divisa":
                self._divisa = kwargs[key]

    @abstractmethod
    def crearCuenta(self, banco, clave, nombre, **kwargs):
        pass
    #El kwargs hace referencia a la sobrecarga de la divisa

    def __str__(self):
        return "Cuenta: " + self._nombre + "\n# " + self._id + "\nTitular: " + self.getTitular().getNombre() + "\nBanco: " + self._banco.getNombre() + "\nDivisa: " + self._divisa

    # Métodos para la funcionalidad asesoramiento de inversiones
    def gota_gota(cantidadPrestamo, user, gota):
        from .movimientos import Movimientos
        from .categoria import Categoria
        from .ahorros import Ahorros
        mayor = 0
        contador = 0
        for i in range(len(user.getCuentasAsociadas())):
            if isinstance(user.getCuentasAsociadas()[i], Ahorros):
                if user.getCuentasAsociadas()[i].getSaldo() > mayor:
                    mayor = user.getCuentasAsociadas()[i].getSaldo()
                    contador = i
                else:
                    continue
            else:
                if user.getCuentasAsociadas()[i].getDisponible() > mayor:
                    mayor = user.getCuentasAsociadas()[i].getDisponible()
                    contador = i
            
            movimiento = Movimientos(destino = user.getCuentasAsociadas()[contador], cantidad = cantidadPrestamo, categoria = Categoria.OTROS, fecha = datetime.now())
            Movimientos.getMovimientosTotales().remove(movimiento)
            return user.getCuentasAsociadas()[contador]
        
    def vaciar_cuenta(gota):
        pass

    #Métodos para funcionalidad de cambio de divisa
    @staticmethod
    def hacer_cambio(escogencia, monto, destino, user, exacto=False):
        from .movimientos import Movimientos
        origen = escogencia.get_origen()
        if exacto:
            pagar = monto / (1 - escogencia.get_banco().get_estado_asociado().get_tasa_impuestos())
            pagar /= (1 - escogencia.get_couta_manejo())
            pagar /= escogencia.get_cantidad()
            m = Movimientos(escogencia.get_banco(), origen, destino, escogencia.get_divisa(), escogencia.get_divisa_aux(), escogencia.get_couta_manejo(), pagar, datetime.datetime.now())
            origen.set_saldo(origen.get_saldo() - pagar)
            destino.set_saldo(destino.get_saldo() + monto)
        else:
            cambiado = monto * (1 - escogencia.get_banco().get_estado_asociado().get_tasa_impuestos())
            cambiado *= (1 - escogencia.get_couta_manejo())
            cambiado *= escogencia.get_cantidad()
            m = Movimientos(escogencia.get_banco(), origen, destino, escogencia.get_divisa(), escogencia.get_divisa_aux(), escogencia.get_couta_manejo(), monto, datetime.datetime.now())
            origen.set_saldo(origen.get_saldo() - monto)
            destino.set_saldo(destino.get_saldo() + cambiado)

        user.asociar_movimiento(m)
        for i in range(len(user.get_bancos_asociados())):
            user.get_bancos_asociados()[i].set_asociado(False)

    @staticmethod
    def comprobar_saldo(cuenta, monto=None):
        ahorro = cuenta
        if monto is None:
            return ahorro.get_saldo() >= monto
        else:
            pagar = monto / (1 - cuenta.get_banco().get_estado_asociado().get_tasa_impuestos())
            pagar /= (1 - cuenta.get_couta_manejo())
            pagar /= cuenta.get_cantidad()
            return ahorro.get_saldo() >= pagar

    @staticmethod
    def obtener_cuentas_divisa(usuario, divisa):
        cuentas_b = []
        for ahorro in usuario.get_cuentas_ahorros_asociadas():
            if ahorro.get_divisa() == divisa:
                cuentas_b.append(ahorro)
        return cuentas_b


    #Realizar el método del compare to
    def compareTo(self, cuenta):
        if self.getId() > cuenta.getId():
            return 1
        elif self.getId() < cuenta.getId():
            return -1
        else:
            return 0

    #¿Se maneja ligadura dinámica?
    def invertirSaldo(self):
        pass

    def retornoCuotaMensual(self, deudaActual):
        banco = self.getBanco()
        cuotaMensual = []
        if (banco.getComision() + banco.getEstadoAsociado().getInteres_bancario_corriente()) < 1:
            #Cuota del estado y del banco
            cuota1 = deudaActual*banco.getComision() + banco.getEstadoAsociado().getIntereses_bancario_corriente()*deudaActual
            cuota2 = (deudaActual - cuota1) / 2
            cuotaMensual[0] = deudaActual*banco.getComision() + banco.getEstadoAsociado().getInteres_bancario_corriente()*deudaActual
            cuotaMensual[1] = cuota2
            cuotaMensual[2] = cuota2
        else:
            cuotaMensual[0] = deudaActual / 3
            cuotaMensual[1] = deudaActual / 3
            cuotaMensual[2] = deudaActual / 3
        return cuotaMensual

    #Realizar el método equals
    def equals(self, cuenta):
        if self.getId() == cuenta.getId():
            return True
        else:
            return False

    @staticmethod
    def limpiarPropiedades(arreglo):
        arreglo.remove("cuentasTotales")
        #Verificar que otras variables se crean
    #Verificar su uso

    @staticmethod
    def dineroATenerDisponible(cuenta, divisaB):
        from .movimientos import Movimientos
        deuda = cuenta.getCupo() - cuenta.getDisponible()
        if cuenta.getDivisa() == divisaB:
            print(1)
            return cuenta.getCupo() - cuenta.getDisponible()
        cambio_div = Movimientos(divisa=cuenta.getDivisa(), divisaAux=divisaB, owner = cuenta.getTitular())
        existe_cambio = Movimientos.facilitar_informacion(cambio_div)
        if len(existe_cambio) == 0:
            return 0
        
        cuentas_posibles = []
        for conta in cuenta.getTitular().getCuentasCorrienteAsociadas():
            if conta.getDivisa() == cuenta.getDivisa():
                cuentas_posibles.append(conta)
        
        cadena = cuenta.getDivisa().name + divisaB.name
        imprimir = Banco.cotizar_taza_aux(cuenta.getTitular(), existe_cambio, cadena, cuentas_posibles)
        cambio_max = 0
        valor = 999999999
        for cotizacion in imprimir:
            valor = cotizacion.getCantidad() / ((1-cotizacion.getCuotaManejo()) * (1 - cotizacion.getBanco().getEstadoAsociado().getTasa_impuestos()))
            if valor > cambio_max:
                cambio_max = valor

        return cambio_max * deuda

    def __str__(self):
        return "Cuenta: " + self._nombre + "\n# " + self._id + "\nTitular: " + self.getTitular.getNombre() + "\nBanco: " + self._banco.getNombre() + "\nDivisa: " + self._divisa

    #Métodos Get & Set
    @classmethod
    def getCuentasTotales(cls):
        return cls._cuentasTotales
    @classmethod
    def setCuentasTotales(cls, cuentasTotales):
        cls._cuentasTotales = cuentasTotales

    def getTitular(self):
        return self._titular
    def setTitular(self, titular):
        self._titular = titular

    def getDivisa(self):
        return self._divisa
    def setDivisa(self, divisa):
        self._divisa = divisa

    def getNombre(self):
        return self._nombre
    def setNombre(self, nombre):
        self._nombre = nombre

    def getId(self):
        return self._id
    def setId(self, id):
        self._id = id

    def getBanco(self):
        return self._banco
    def setBanco(self, banco):
        self._banco = banco

    def getClave(self):
        return self._clave
    def setClave(self, clave):
        self._clave = clave