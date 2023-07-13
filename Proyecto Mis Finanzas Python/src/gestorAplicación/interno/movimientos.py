from datetime import datetime
from .metas import Metas
from .ahorros import Ahorros
from .corriente import Corriente
from .cuenta import Cuenta
from .categoria import Categoria
from .deuda import Deuda
from gestorAplicación.externo.banco import Banco
from gestorAplicación.externo.divisas import Divisas

class Movimientos():
    _movimientosTotales = []

    # Atributos de clase para la funiconalidad Asesoramiento de inversiones
    #_owner= None
    _nombre_categoria = "Ninguna"
    _fecha_categoria = "01/01/2024"
    _cantidad_categoria = 0
    
    def __init__(self, **kwargs):
        Movimientos._movimientosTotales.append(self)
        self._id = len(Movimientos._movimientosTotales)
        self._cantidad = 0
        self._categoria = None
        self._fecha = None
        self._destino = None
        self._origen = None
        self._divisa = None
        self._divisaAux = None
        self._banco = None
        self._cuotaManejo = None
        self._owner = None

        for key in kwargs:
            if key == "cantidad":
                self._cantidad = kwargs[key]
            if key == "categoria":
                self._categoria = kwargs[key]
            if key == "fecha":
                self._fecha = kwargs[key]
            if key == "destino":
                self._destino = kwargs[key]
            if key == "origen":
                self._origen = kwargs[key]
            if key == "divisa":
                self._divisa = kwargs[key]
            if key == "divisaAux":
                self._divisaAux= kwargs[key]
            if key == "banco":
                self._banco = kwargs[key]
            if key == "cuotaManejo":
                self._cuotaManejo = kwargs[key]
            if key == "owner":
                self._owner = kwargs[key]

    @staticmethod
    def crearMovimiento(destino, cantidad, categoria, fecha, origen = None, **kwargs):
        if(origen != None and categoria != Categoria.PRESTAMO):
            origen.setSaldo(origen.getSaldo() - cantidad)
            destino.setSaldo(destino.getSaldo() + (cantidad - cantidad * (destino.getBanco().getEstadoAsociado().getTasa_impuestos() +  destino.getBanco().getComision())))
            return(Movimientos(cantidad=cantidad - cantidad * (destino.getBanco().getEstadoAsociado().getTasa_impuestos() +  destino.getBanco().getComision()), origen=origen, destino=destino, categoria=categoria, fecha=fecha))
        if(categoria == Categoria.PRESTAMO and origen == None):
            destino.setSaldo(destino.getSaldo() + cantidad)
            print("entra aca")
            return(Movimientos(cantidad=cantidad,destino=destino, categoria=categoria, fecha=fecha))
        else:
            destino.setSaldo(destino.getSaldo() + (cantidad - cantidad * (destino.getBanco().getEstadoAsociado().getTasa_impuestos() +  destino.getBanco().getComision())))
            return(Movimientos(cantidad = cantidad - cantidad * (destino.getBanco().getEstadoAsociado().getTasa_impuestos() +  destino.getBanco().getComision()), destino=destino, categoria=categoria, fecha=fecha))

    # Métodos de la funiconalidad Asesoramiento de inversiones
    def analizar_categoria(plazo):
        from .usuario import Usuario
        user = Usuario.getUsuariosTotales()[0]
        transporte = 0 
        comida = 0 
        educacion = 0 
        finanzas = 0 
        otros = 0 
        regalos = 0 
        salud = 0 
        prestamos = 0 
        big = 0 
        posicion = 0 
        mayor = []
        _cantidad_categoria = 0

        # Buscar la categoría en la que más dinero ha gastado el usuario
        for i in range(len(user.getMovimientosAsociados())):
            categoria = user.getMovimientosAsociados()[i].getCategoria()
            if categoria == Categoria.TRANSPORTE:
                transporte += 1
            elif categoria == Categoria.COMIDA:
                comida += 1
            elif categoria == Categoria.EDUCACION:
                educacion += 1
            elif categoria == Categoria.SALUD:
                salud += 1
            elif categoria == Categoria.REGALOS:
                regalos += 1
            elif categoria == Categoria.FINANZAS:
                finanzas += 1
            elif categoria == Categoria.OTROS:
                otros += 1
            elif categoria == Categoria.PRESTAMO:
                prestamos += 1

        mayor.extend([transporte, comida, educacion, salud, regalos, finanzas, otros, prestamos])

        for e in range(len(mayor)):
            if mayor[e] > big:
                big = mayor[e]
                posicion = e

        if posicion == 0:
            Movimientos.setNombreCategoria("Transporte")
            for i in range(len(user.getMovimientosAsociados())):
                if Categoria.TRANSPORTE == user.getMovimientosAsociados()[i].getCategoria():
                    _cantidad_categoria += user.getMovimientosAsociados()[i].getCantidad()

        elif posicion == 1:
            Movimientos.setNombreCategoria("Comida")
            for i in range(len(user.getMovimientosAsociados())):
                if Categoria.COMIDA == user.getMovimientosAsociados()[i].getCategoria():
                    _cantidad_categoria += user.getMovimientosAsociados()[i].getCantidad()

        elif posicion == 2:
            Movimientos.setNombreCategoria("Educacion")
            for i in range(len(user.getMovimientosAsociados())):
                if Categoria.EDUCACION == user.getMovimientosAsociados()[i].getCategoria():
                    _cantidad_categoria += user.getMovimientosAsociados()[i].getCantidad()

        elif posicion == 3:
            Movimientos.setNombreCategoria("Salud")
            for i in range(len(user.getMovimientosAsociados())):
                if Categoria.SALUD == user.getMovimientosAsociados()[i].getCategoria():
                    _cantidad_categoria += user.getMovimientosAsociados()[i].getCantidad()

        elif posicion == 4:
            Movimientos.setNombreCategoria("Regalos")
            for i in range(len(user.getMovimientosAsociados())):
                if Categoria.REGALOS == user.getMovimientosAsociados()[i].getCategoria():
                    _cantidad_categoria += user.getMovimientosAsociados()[i].getCantidad()

        elif posicion == 5:
            Movimientos.setNombreCategoria("Finanzas")
            for i in range(len(user.getMovimientosAsociados())):
                if Categoria.FINANZAS == user.getMovimientosAsociados()[i].getCategoria():
                    _cantidad_categoria += user.getMovimientosAsociados()[i].getCantidad()

        elif posicion == 6:
            Movimientos.setNombreCategoria("Otros")
            for i in range(len(user.getMovimientosAsociados())):
                if Categoria.OTROS == user.getMovimientosAsociados()[i].getCategoria():
                    _cantidad_categoria += user.getMovimientosAsociados()[i].getCantidad()

        elif posicion == 7:
            Movimientos.setNombreCategoria("Prestamos")
            for i in range(len(user.getMovimientosAsociados())):
                if Categoria.OTROS == user.getMovimientosAsociados()[i].getCategoria():
                    _cantidad_categoria += user.getMovimientosAsociados()[i].getCantidad()

        # Recomendadar fecha
        if len(user.getMovimientosAsociados()) != 0:
            fecha_meta = datetime.strptime(str(Metas.revision_metas(user).getFecha()), "%d/%m/%Y")
            fecha_movimiento = user.getMovimientosAsociados()[len(user.getMovimientosAsociados()) - 1].getFecha()
            if plazo == "Corto":
                if  fecha_movimiento < fecha_meta:
                    Movimientos.setFechaCategoria("01/01/2024")
                else:
                    Movimientos.setFechaCategoria("01/06/2025")
            elif plazo == "Mediano":
                if fecha_movimiento < fecha_meta:
                    Movimientos.setFechaCategoria("01/01/2026")
                else:
                    Movimientos.setFechaCategoria("01/06/2027")
            elif plazo == "Largo":
                if fecha_movimiento < fecha_meta:
                    Movimientos.setFechaCategoria("01/01/2028")
                else:
                    Movimientos.setFechaCategoria("01/06/2029")

        meta_categoria = Metas(nombre = Movimientos.getNombreCategoria(), cantidad = Movimientos.get_cantidad_categoria(), fecha = Movimientos.getFechaCategoria())
        Usuario.getUsuariosTotales()[0].asociarMeta(meta_categoria)
        Metas.prioridad_metas(user, meta_categoria)

    def impuestos_movimiento(self, interes):
        impuestosBanco = Ahorros(banco = self.getOrigen().getBanco(), clave = 1234, divisa = Divisas.COP, nombre = "Ahorros", saldo = 10.0)
        if self.getOrigen().getBanco() == self.getDestino().getBanco():
            if isinstance(self.getOrigen(), Corriente):
                movimiento1 = Movimientos(origen = self.getOrigen(), destino = impuestosBanco, cantidad = interes, categoria = Categoria.OTROS, fecha = datetime.now())
                Movimientos.getMovimientosTotales().remove(movimiento1)
            else:
                movimiento1 = Movimientos(origen = self.getOrigen(), destino = impuestosBanco, cantidad = interes, categoria = Categoria.OTROS, fecha = datetime.now())
                Movimientos.getMovimientosTotales().remove(movimiento1)
            Ahorros.getCuentasAhorrosTotales().remove(impuestosBanco)
            Cuenta.getCuentasTotales().remove(impuestosBanco)
            return True
        else:
            if isinstance(self.getOrigen(), Corriente):
                movimiento1 = Movimientos(origen = self.getOrigen(), destino = impuestosBanco, cantidad = interes + 1, categoria = Categoria.OTROS, fecha = datetime.now())
                Movimientos.getMovimientosTotales().remove(movimiento1)
            else:
                movimiento1 = Movimientos(origen = self.getOrigen(), destino = impuestosBanco, cantidad = interes + 1, categoria = Categoria.OTROS, fecha = datetime.now())
                Movimientos.getMovimientosTotales().remove(movimiento1)
            Ahorros.getCuentasAhorrosTotales().remove(impuestosBanco)
            Cuenta.getCuentasTotales().remove(impuestosBanco)
            return False

    # Funcionlidad Prestamo
    @classmethod
    def realizarPrestamo(cls,_cuenta,_cantidad):
        banco = _cuenta.getBanco()
        titular = _cuenta.getTitular()
        maxCantidad = banco.getPrestamo() * titular.getSuscripcion().getPorcentajePrestamo()
        deuda = Deuda(_cantidad,_cuenta,titular,banco)
        return(Movimientos.crearMovimiento(_cuenta,_cantidad,categoria=Categoria.PRESTAMO,fecha=datetime.now()))
        
    @classmethod
    def pagarDeuda(cls,_usuario,_deuda,cantidad):
        cuenta = _deuda.getCuenta()
        for cuentaUsuario in _usuario.getCuentasAhorroAsociadas(): 
            if cuentaUsuario.getNombre()== cuenta.getNombre():
                cuenta = cuentaUsuario
        if _deuda.getCantidad()==cantidad:
            Deuda.getDeudasTotales().remove(_deuda)
            Metas.getMetasTotales().remove(_deuda)
            _deuda.setCantidad(0)
            cantidad= -cantidad
            return Movimientos.crearMovimiento(cuenta,cantidad,Categoria.PRESTAMO, datetime.now())
        else:
            _deuda.setCantidad(_deuda.getCantidad()-cantidad)
            cantidad = -cantidad
            return Movimientos.crearMovimiento(cuenta,cantidad,Categoria.PRESTAMO,datetime.now())
        
    # Funcionalidad de cambio de divisa
    @staticmethod
    def facilitar_informacion(mov):
        for i in range(len(mov.getOwner().getBancosAsociados())):
            mov.getOwner().getBancosAsociados()[i].setAsociado(True)

        cadena = mov.getDivisa().name + mov.getDivisaAux().name
        existe_cambio = []
        for j in range(len(Banco.getBancosTotales())):
            for k in range(len(Banco.getBancosTotales()[j].getDic())):
                if cadena == Banco.getBancosTotales()[j].getDic()[k]:
                    existe_cambio.append(Banco.getBancosTotales()[j])

        return existe_cambio
    
    @staticmethod
    def verificar_movimientos_usuario_banco(usuario, banco):
        movimientos_asociados = usuario.getMovimientosAsociados()
        cuentas_asociadas = usuario.getCuentasAsociadas()
        cuentas_asociadas_a_banco = []
        movimientos_usuario_banco = []
        for cuenta in cuentas_asociadas:
            if cuenta.getBanco() == banco:
                cuentas_asociadas_a_banco.append(cuenta)
        
        for cuenta in cuentas_asociadas_a_banco:
            movimientos_aux = Movimientos.verificar_origen_movimientos(movimientos_asociados, cuenta)
            for movimiento in movimientos_aux:
                movimientos_usuario_banco.append(movimiento)

        return movimientos_usuario_banco

    @staticmethod
    def verificar_origen_movimientos(movimientos_asociados, cuenta):
        movimientos_originarios_cuenta = []
        for movimiento in movimientos_asociados:
            if movimiento.getOrigen() == cuenta:
                movimientos_originarios_cuenta.append(movimiento)

        return movimientos_originarios_cuenta

    def __str__(self):
        if self.getOrigen() == None:
            return("Movimiento creado \nFecha: " + str(self.getFecha()) + "\nID: " + str(self.getId()) + "\nDestino: " + str(self.getDestino().getId()) + "\nCantidad: " +
					str(self.getCantidad()) + "\nCategoria: " + str(self.getCategoria().name))
        elif(self.getDestino() == None):
            return("Movimiento creado \nFecha: " + str(self.getFecha()) + "\nID: " + str(self.getId()) + "\nOrigen: " + str(self.getOrigen().getId()) + "\nCantidad: " +
					str(self.getCantidad()) + "\nCategoria: " + str(self.getCategoria().name))
        else:
            return("Movimiento creado \nFecha: " + str(self.getFecha()) + "\nID: " + str(self.getId()) + "\nOrigen: " + str(self.getOrigen().getId()) + "\nDestino: " + str(self.getDestino().getId()) + "\nCantidad: " +
					str(self.getCantidad()) + "\nCategoria: " + str(self.getCategoria().name))

    @classmethod
    def getMovimientosTotales(cls):
        return cls._movimientosTotales
    @classmethod
    def setMovimientosTotales(cls, _movimientosTotales):
        cls._movimientosTotales = _movimientosTotales
    def getOwner(self):
        return self._owner
    def setOwner(self, owner):
        self._owner = owner
    @classmethod
    def getNombreCategoria(cls):
        return cls._nombre_categoria
    @classmethod
    def setNombreCategoria(cls, _nombre_categoria):
        cls._nombre_categoria = _nombre_categoria
    @classmethod
    def get_cantidad_categoria(cls):
        return cls._cantidad_categoria
    @classmethod
    def set_cantidad_categoria(cls, _cantidad_categoria):
        cls._cantidad_categoria = _cantidad_categoria
    @classmethod
    def getFechaCategoria(cls):
        return cls._fecha_categoria
    @classmethod
    def setFechaCategoria(cls, _fecha_categoria):
        cls._fecha_categoria = _fecha_categoria

    def getCategoria(self):
        return self._categoria
    
    def setCategoria(self, _categoria):
        self._categoria = _categoria

    def getDestino(self):
        return self._destino
    
    def setDestino(self, _destino):
        self._destino = _destino

    def getOrigen(self):
        return self._origen
    
    def setOrigen(self, _origen):
        self._origen = _origen

    def getDestino(self):
        return self._destino
    
    def setDestino(self, _destino):
        self._destino = _destino

    def getCantidad(self):
        return self._cantidad
    
    def setCantidad(self, _cantidad):
        self._cantidad = _cantidad

    def getFecha(self):
        return self._fecha
    
    def setFecha(self, _fecha):
        self._fecha = _fecha

    def getId(self):
        return self._id
    
    def setId(self, _id):
        self._id = _id

    def getDivisa(self):
        return self._divisa
    
    def setDivisa(self, divisa):
        self._divisa = divisa

    def getDivisaAux(self):
        return self._divisaAux
    
    def setDivisaAux(self, divisaAux):
        self._divisaAux = divisaAux