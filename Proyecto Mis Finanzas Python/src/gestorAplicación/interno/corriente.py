from .cuenta import Cuenta
from gestorAplicación.externo.banco import Banco
from datetime import datetime
from gestorAplicación.externo.cuotas import Cuotas
import math

class Corriente(Cuenta):
    #Atributos de clase
    _cuentasCorrienteTotales = []

    #Constructor
    def __init__(self, plazo_pago = Cuotas.C1, intereses = 28, **kwargs):
        #Atributos de instancia
        Corriente._cuentasCorrienteTotales.append(self)
        super().__init__(**kwargs)
        self._plazo_Pago = plazo_pago
        self._intereses = intereses
        self._cupo = 0.0
        self._disponible = 0.0
        self._primerMensualidad = False

#REVISAR SOBRECARGA

    def crearCuenta(self, banco, clave, nombre, **kwargs):
        if "divisa" in kwargs:
            return Corriente(banco = banco, clave = clave, nombre = nombre, divisa = kwargs["divisa"])
        else:
            return Corriente(banco =  banco, clave = clave, nombre = nombre)
    
    def __str__(self):
        return "Cuenta: " + self._nombre + "\nCuenta Corriente # " + str(self._id) + "\nTitular: " + self.getTitular().getNombre() + "\nBanco: " + self._banco.getNombre() + "\nDivisa: " + self._divisa.name + "\nCupo: " + str(self._cupo) + " " + self._divisa.name + "\nCupo disponible: " + str(self._disponible) + " " + self._divisa.name + "\nCuotas: " + str(self._plazo_Pago) + "\nIntereses: " + str(self._intereses)
    
    # Método para la funcionalidad asesoramiento de inversiones
    def vaciar_cuenta(self, gota):
        from .movimientos import Movimientos
        from .categoria import Categoria
        movimiento = Movimientos(origen = self, destino = gota, cantidad = self.getDisponible(), categoria = Categoria.OTROS, fecha = datetime.now())
        Movimientos.getMovimientosTotales().remove(movimiento)
        self.setDisponible(0)

    def retornoCuotaMensual(self, deudaActual, mes = 0):
        cuotaMensual = []
        interes_nominal_mensual = Corriente.calculoInteresNominalMensual(self.getIntereses())
        if mes == 0:
            interes = deudaActual * (interes_nominal_mensual / 100)
            cuotaMensual.append(interes)
            abono_capital = (self.getCupo() - self.getDisponible()) / self.getPlazo_Pago().getCantidad_Cuotas()
            cuotaMensual.append(abono_capital)
            cuotaMensualFinal = interes + abono_capital
            cuotaMensual.append(cuotaMensualFinal)
        elif mes == 1:
            cuotaMensual.append(0)
            abono_capital = (self.getCupo() - self.getDisponible()) / self.getPlazo_Pago().getCantidad_Cuotas()
            cuotaMensual.append(abono_capital)
            cuotaMensualFinal = abono_capital
            cuotaMensual.append(cuotaMensualFinal)
        elif mes == 2:
            abono_capital = (self.getCupo() - self.getDisponible()) / self.getPlazo_Pago().getCantidad_Cuotas()
            interes_mes1 = (interes_nominal_mensual / 100) * (abono_capital + deudaActual)
            interes_mes2 = deudaActual * (interes_nominal_mensual / 100)
            interes = interes_mes1 + interes_mes2
            cuotaMensual.append(interes)
            cuotaMensual.append(abono_capital)
            cuotaMensualFinal = interes + abono_capital
            cuotaMensual.append(cuotaMensualFinal)
        else:
            interes = deudaActual * (interes_nominal_mensual / 100)
            cuotaMensual.append(interes)
            abono_capital = (self.getCupo() - self.getDisponible()) / self.getPlazo_Pago().getCantidad_Cuotas()
            cuotaMensual.append(abono_capital)
            cuotaMensualFinal = interes + abono_capital
            cuotaMensual.append(cuotaMensualFinal)
        return cuotaMensual

    @staticmethod
    def imprimirCuotaMensual(cuotaMensual):
        return "Cuota: " + str(round(cuotaMensual[2], 2)) + "\nIntereses: " + str(round(cuotaMensual[0], 2)) + "\nAbono a capital: " + str(round(cuotaMensual[1], 2))

    @staticmethod
    def calculoInteresNominalMensual(interesEfectivoAnual):
        interes = math.pow((1 + (interesEfectivoAnual / 100)), (30.0 / 360.0)) - 1
        interes_porcentaje = interes * 100
        interes_porcentaje_redondeado = round(interes_porcentaje, 2)
        return interes_porcentaje_redondeado

    @staticmethod
    def calculadoraCuotas(cuotas, deuda, intereses, auxiliar = False):
        cuotasTotales = cuotas.getCantidad_Cuotas()
        cuota = []
        interesMensual = Corriente.calculoInteresNominalMensual(intereses)
        deudaActual = deuda

        abono_capital = deuda / cuotasTotales

        if not auxiliar:
            for i in range(0, cuotasTotales, 1):
                cuotaMes = []
                interes = deudaActual * (interesMensual / 100)
                cuotaMes.append(interes)
                cuota_pagar = interes + abono_capital
                cuotaMes.append(cuota_pagar)
                deudaTotal = deudaActual - (cuota_pagar - interes)
                cuotaMes.append(deudaTotal)
                cuota.append(cuotaMes)

                deudaActual = deudaTotal
        else:
            interesMes1 = deudaActual * (interesMensual / 100)
            cuotaMes1 = []
            cuotaMes1.append(0)
            cuotaMes1.append(abono_capital)
            cuotaMes1.append(deudaActual - abono_capital)
            cuota.append(cuotaMes1)

            deudaActual = deudaActual - abono_capital

            for i in range(1, cuotasTotales, 1):
                cuotaMes = []
                interes = deudaActual * (interesMensual / 100)
                cuotaMes.append(interes + interesMes1)
                cuota_pagar = interes + abono_capital + interesMes1
                cuotaMes.append(cuota_pagar)
                deudaTotal = deudaActual - (cuota_pagar - (interes + interesMes1))
                cuotaMes.append(deudaTotal)
                cuota.append(cuotaMes)

                interesMes1 = 0
                deudaActual = deudaTotal
            
        return cuota
            
        

    @staticmethod
    def informacionAdicionalCalculadora(cuota, deuda):
        infoAdicional = []
        totalPagado = 0

        for i in range(0, len(cuota), 1):
            totalPagado += cuota[i][1]
        
        interesesPagados = totalPagado - deuda

        totalPagado = round(totalPagado, 2)
        interesesPagados = round(interesesPagados, 2)
        deuda = round(deuda, 2)

        infoAdicional.append(totalPagado)
        infoAdicional.append(interesesPagados)
        infoAdicional.append(deuda)

        return infoAdicional

    @staticmethod
    def inicializarCupo(cuenta):
        banco = cuenta.getBanco()
        suscripcion = cuenta.getTitular().getSuscripcion()
        cupo = banco.decisionCupo(suscripcion)
        cuenta.setCupo(cupo)
        cuenta.setDisponible(cupo)

    def capacidadDeuda(self, cantidad):
        if self.getDisponible().compareTo(cantidad) >= 0:
            return True
        else:
            return False

    @staticmethod
    def limpiarPropiedades(cls, arreglo):
        arreglo.remove("cuentasTotales")
        #Verificar que otras variables se crean
    #Verificar su uso

    #Realizar el método del compare to
    def compareTo(self, cuenta):
        if self.getDisponible() > cuenta.getDisponible():
            return 1
        elif self.getDisponible() < cuenta.getDisponible():
            return -1
        else:
            return 0

    #Atributos Get & Set
    def getCupo(self):
        return self._cupo
    def setCupo(self, cupo):
        self._cupo = cupo

    def getPlazo_Pago(self):
        return self._plazo_Pago
    def setPlazo_Pago(self, plazo_Pago):
        self._plazo_Pago = plazo_Pago
    
    def getDisponible(self):
        return self._disponible
    def setDisponible(self, disponible):
        self._disponible = disponible

    def getIntereses(self):
        return self._intereses
    def setIntereses(self, intereses):
        self._intereses = intereses

    def getPrimerMensualidad(self):
        return self._primerMensualidad
    def setPrimerMensualidad(self, primerMensualidad):
        self._primerMensualidad = primerMensualidad

    @classmethod
    def getCuentasCorrienteTotales(cls):
        return cls._cuentasCorrienteTotales
    def setCuentasCorrienteTotales(cls, cuentasCorrienteTotales):
        cls._cuentasCorrienteTotales = cuentasCorrienteTotales