import random
from datetime import datetime

from gestorAplicación.interno.suscripcion import Suscripcion
from gestorAplicación.interno.categoria import Categoria
from .estado import Estado

class Banco():
    _bancosTotales = []
    conf = True


    def __init__(self, nombre = "Banco de Colombia", comision = 0.3, estado = Estado(), prestamo = 200, **kwargs):
        #Revisar ingreso de atributo Estado, para que su defecto sea el primero como en Java
        self._nombre = nombre
        self._comision = comision
        self._estado = estado
        self._prestamo = prestamo
        self._asociado = False
        self._dic = []
        self.cionario=[]

        self._cupo_base = 1000000
        self._multiplicador = 2
        self._desc_suscripcion = 0.2
        self._desc_movimientos_porcentaje = 0.2
        self._desc_movimientos_cantidad = 5


        #Atributos de instancia
        Banco._bancosTotales.append(self)
        self._id = len(Banco._bancosTotales)
        for key in kwargs:
            if key == "desc_suscripcion":
                self._desc_suscripcion = kwargs[key]
            if key == "desc_movimientos_porcentaje":
                self._desc_movimientos_porcentaje = kwargs[key]
            if key == "desc_movimientos_cantidad":
                self._desc_movimientos_cantidad = kwargs[key]
            if key == "divisa":
                self._divisa = kwargs[key]
            if key == "dic":
                self._dic = kwargs[key]
            if key == "cionario":
                self._cionario = kwargs[key]
            if key == "cupo_base":
                self._cupo_base
            if key == "multiplicador":
                self._multiplicador
            if key == "desc_suscripcion":
                self._desc_suscripcion
            if key == "desc_movimientos_porcentaje":
                self._desc_movimientos_porcentaje
            if key == "desc_movimientos_cantidad":
                self._desc_movimientos_cantidad

    #Métodos de la funcionalidad de cambio de divisa
    @staticmethod
    def cotizar_taza(user, existe_cambio, cadena, ahorros_posibles):
        from gestorAplicación.interno.movimientos import Movimientos
        imprimir = []
        for ahorro in ahorros_posibles:
            for banco in existe_cambio:
                indice = banco.getDic().index(cadena)
                valor = banco.get_cionario()[indice]
                if ahorro.getBanco() == banco:
                    valor *= 0.98
                if banco.getAsociado():
                    valor *= 0.97
                cuota_manejo = Banco.divisa_suscripcion(user)
                aux = {"banco": banco, "origen": ahorro, "cantidad":valor, "cuotaManejo":cuota_manejo}
                cotizacion = Movimientos(aux)
                imprimir.append(cotizacion)

        return imprimir

    @staticmethod
    def divisa_suscripcion(user):
        if user.getSuscripcion() == Suscripcion.BRONCE:
            return 0.01
        elif user.getSuscripcion() == Suscripcion.PLATA:
            return 0.008
        elif user.getSuscripcion() == Suscripcion.ORO:
            return 0.006
        else:
            return 0.004

    @staticmethod
    def cotizar_taza_aux(user, existe_cambio, cadena, cuentas_posibles):
        from gestorAplicación.interno.movimientos import Movimientos
        imprimir = []
        for conta in cuentas_posibles:
            for banco in existe_cambio:
                indice = banco.get_dic().index(cadena)
                valor = banco.get_cionario()[indice]
                if conta.get_banco() == banco:
                    valor *= 0.98
                if banco.is_asociado():
                    valor *= 0.97
                cuota_manejo = Banco.divisa_suscripcion(user)
                cotizacion = Movimientos(banco, conta, valor, cuota_manejo)
                imprimir.append(cotizacion)

        return imprimir

    #Funcionalidad de Suscripciones de Usuarios
    def comprobarSuscripcion(self, user):
        user.setLimiteCuentas(user.getSuscripcion().getLimiteCuentas())
        suscripcion = user.getSuscripcion()
        if(suscripcion == Suscripcion.DIAMANTE):
            if(Banco.getConf()):
                self.setComision(self.getComision() * 0.50)
                Banco.setConf(False)
            return ("Bienvenido " + user.getNombre() + ", eres un cliente " + user.getSuscripcion().name + " de nuestro banco, " + "por eso te cobramos " + str(self.getComision()) + " de comision")
        elif(suscripcion == Suscripcion.ORO):
            if(Banco.getConf()):
                self.setComision(self.getComision() * 0.65)
                Banco.setConf(False)
            return ("Bienvenido " + user.getNombre() + ", eres un cliente " + user.getSuscripcion().name + " de nuestro banco, " + "por eso te cobramos " + str(self.getComision()) + " de comision")
        elif(suscripcion == Suscripcion.PLATA):
            if(Banco.getConf()):
                self.setComision(self.getComision() * 0.85)
                Banco.setConf(False)
            return ("Bienvenido " + user.getNombre() + ", eres un cliente " + user.getSuscripcion().name + " de nuestro banco, " + "por eso te cobramos " + str(self.getComision()) + " de comision")
        elif(suscripcion == Suscripcion.BRONCE):
            return ("Bienvenido " + user.getNombre() + ", eres un cliente " + user.getSuscripcion().name + " de nuestro banco, " + "por eso te cobramos " + str(self.getComision()) + " de comision")
    
    # Métodos de la funcionalidad Asesoramiento de inversiones
    def retorno_portafolio(riesgo, invertir):
        from gestorAplicación.interno.ahorros import Ahorros
        from gestorAplicación.interno.usuario import Usuario
        from gestorAplicación.interno.movimientos import Movimientos

        user = Usuario.getUsuariosTotales()[0]
        numero_riesgo = 0
        
        if riesgo == "Baja":
            numero_riesgo = 1
        elif riesgo == "Media":
            numero_riesgo = 2
        elif riesgo == "Alta":
            numero_riesgo = 3

        for i in range (len(user.getCuentasAsociadas())):
            if isinstance(user.getCuentasAsociadas()[i], Ahorros):
                valor = True
                user_cuenta = user.getCuentasAsociadas()[i]
                cobro = user_cuenta.getSaldo() / 32
                break
            else:
                user_cuenta = user.getCuentasAsociadas()[i]
                cobro = user_cuenta.getDisponible() / 32
        
        movimiento = Movimientos(origen = user_cuenta, destino = Usuario.getUsuariosTotales()[Usuario.hallarUsuarioImpuestosPortafolio()].getCuentasAsociadas()[0], cantidad = cobro, categoria = Categoria.OTROS, fecha = datetime.now())
        invest = int(invertir)
        booleano = movimiento.impuestos_movimiento(random.random() + numero_riesgo)

        if valor and user_cuenta.getSaldo() > invest:
            if booleano and user_cuenta.getSaldo() < invest:
                Movimientos.getMovimientosTotales().remove(movimiento)
                return 1
            elif not booleano and user_cuenta.getSaldo() < invest:
                Movimientos.getMovimientosTotales().remove(movimiento)
                return 2
            elif booleano and user_cuenta.getSaldo() > invest:
                Movimientos.getMovimientosTotales().remove(movimiento)
                return 3
            elif not booleano and user_cuenta.getSaldo() > invest:
                Movimientos.getMovimientosTotales().remove(movimiento)
                return 4

        elif valor and user_cuenta.getSaldo() < invest:
            if booleano and user_cuenta.getSaldo() < invest:
                Movimientos.getMovimientosTotales().remove(movimiento)
                return 5
            elif not booleano and user_cuenta.getSaldo() < invest:
                Movimientos.getMovimientosTotales().remove(movimiento)
                return 6
            elif booleano and user_cuenta.getSaldo() > invest:
                Movimientos.getMovimientosTotales().remove(movimiento)
                return 7
            elif not booleano and user_cuenta.getSaldo() > invest:
                Movimientos.getMovimientosTotales().remove(movimiento)
                return 8

        elif not valor and user_cuenta.getDisponible()() > invest:
            if booleano and user_cuenta.getDisponible() < invest:
                Movimientos.getMovimientosTotales().remove(movimiento)
                return 1
            elif not booleano and user_cuenta.getDisponible() < invest:
                Movimientos.getMovimientosTotales().remove(movimiento)
                return 2
            elif booleano and user_cuenta.getDisponible() > invest:
                Movimientos.getMovimientosTotales().remove(movimiento)
                return 3
            elif not booleano and user_cuenta.getDisponible() > invest:
                Movimientos.getMovimientosTotales().remove(movimiento)
                return 4

        elif not valor and user_cuenta.getDisponible() < invest:
            if booleano and user_cuenta.getDisponible() < invest:
                Movimientos.getMovimientosTotales().remove(movimiento)
                return 5
            elif not booleano and user_cuenta.getDisponible() < invest:
                Movimientos.getMovimientosTotales().remove(movimiento)
                return 6
            elif booleano and user_cuenta.getDisponible() > invest:
                Movimientos.getMovimientosTotales().remove(movimiento)
                return 7
            elif not booleano and user_cuenta.getDisponible() > invest:
                Movimientos.getMovimientosTotales().remove(movimiento)
                return 8
        else:
            return 0
    
    def banco_portafolio(user):
        banco = None
        if len(user.getBancosAsociados()) == 1:
            banco = user.getBancosAsociados()[0]
        else:
            for i in range(1, len(user.getBancosAsociados())):
                if user.getBancosAsociados()[i - 1] != user.getBancosAsociados()[i]:
                    banco = user.getBancosAsociados()[i]
                else:
                    continue
        return banco

    def intereses_portafolio(banco, user):
        interes = 0.0

        for i in range(len(user.getBancosAsociados())):
            if user.getBancosAsociados()[i] == banco:
                interes = round((interes + random.random() + i), 2)
        return interes

    def decisionCupo(self, suscripcion):
        cupo = 0
        if suscripcion == Suscripcion.DIAMANTE:
            cupo = self._cupo_base * (self._multiplicador * 3)
        elif suscripcion == Suscripcion.ORO:
            cupo = self._cupo_base * (self._multiplicador * 2)
        elif suscripcion == Suscripcion.PLATA:
            cupo = self._cupo_base * (self._multiplicador)
        else:
            cupo = self._cupo_base
        return cupo
    
    @staticmethod
    def verificar_tasas_de_interes(usuario, cuentas):
        tasas_de_interes = []
        suscripcion = usuario.getSuscripcion()
        for cuenta in cuentas:
            interes = Banco.verificar_tasas_de_interes_1(suscripcion, cuenta)
            tasas_de_interes.append(interes)
        
        return tasas_de_interes

    @staticmethod
    def verificar_tasas_de_interes_1(suscripcion, cuenta):
        interes = 0
        descuento_movimientos = cuenta.getBanco().retornar_descuentos_movimientos(cuenta)
        descuento_suscripcion = cuenta.getBanco().retornar_descuentos_suscripcion()
        descuento_total = Banco.descuento_total(descuento_movimientos, descuento_suscripcion)
        if suscripcion == Suscripcion.DIAMANTE:
            if cuenta.getBanco().getEstadoAsociado().getInteres_bancario_corriente() >= descuento_total[3]:
                interes = cuenta.getBanco().getEstadoAsociado().getInteres_bancario_corriente() - descuento_total[3]
            else:
                interes = 0.0
        elif suscripcion == Suscripcion.ORO:
            if cuenta.getBanco().getEstadoAsociado().getInteres_bancario_corriente() >= descuento_total[2]:
                interes = cuenta.getBanco().getEstadoAsociado().getInteres_bancario_corriente() - descuento_total[2]
            else:
                interes = 0.0
        elif suscripcion == Suscripcion.PLATA:
            if cuenta.getBanco().getEstadoAsociado().getInteres_bancario_corriente() >= descuento_total[1]:
                interes = cuenta.getBanco().getEstadoAsociado().getInteres_bancario_corriente() - descuento_total[1]
            else:
                interes = 0.0
        else:
            if cuenta.getBanco().getEstadoAsociado().getInteres_bancario_corriente() >= descuento_total[0]:
                interes = cuenta.getBanco().getEstadoAsociado().getInteres_bancario_corriente() - descuento_total[0]
            else:
                interes = 0.0
        
        return interes
    
    def retornar_descuentos_movimientos(self, cuenta):
        from gestorAplicación.interno.movimientos import Movimientos
        movimientos_originarios_con_banco = Movimientos.verificar_movimientos_usuario_banco(cuenta.getTitular(), cuenta.getBanco())
        descuento = (len(movimientos_originarios_con_banco) // (self._desc_movimientos_cantidad * self._desc_movimientos_porcentaje))
        return descuento
    
    def retornar_descuentos_suscripcion(self):
        descuento = []
        i = 1
        while i < 5:
            descuento.append(self._desc_suscripcion * i)
            i += 1

        return descuento
    
    @staticmethod
    def descuento_total(movimientos, suscripcion):
        descuento_total = suscripcion
        for descuento in descuento_total:
            descuento = descuento + movimientos
        
        return descuento_total

    def getNombre(self):
        return self._nombre
    
    def getComision(self):
        return self._comision
    def getPrestamo(self):
        return self._prestamo
    def setComision(self, _comision):
        self._comision = _comision

    def getEstadoAsociado(self):
        return self._estado
    
    def setEstadoAsociado(self, _estado):
        self._estado = _estado

    @classmethod
    def getBancosTotales(cls):
        return cls._bancosTotales
    @classmethod
    def setBancosTotales(cls, _bancosTotales):
        cls._bancosTotales = _bancosTotales

    @classmethod
    def getConf(cls):
        return cls.conf
    
    @classmethod
    def setConf(cls, conf):
        cls.conf = conf

    def setAsociado(self, aso):
        self._asociado = aso

    def getAsociado(self):
        return self._asociado
    
    def setDic(self, dic):
        self._dic = dic

    def getDic(self):
        return self._dic
    
    def set_cionario(self, cionario):
        self._cionario = cionario
    
    def get_cionario(self):
        return self._cionario