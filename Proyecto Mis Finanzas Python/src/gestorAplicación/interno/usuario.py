from .suscripcion import Suscripcion
from .movimientos import Movimientos
from .corriente import Corriente
from .cuenta import Cuenta
from .ahorros import Ahorros
from .metas import Metas
from excepciones import banksException, usersException

class Usuario():
    #Atributos de clase
    _usuariosTotales = []

    #Constructor
    def __init__(self, **kwargs) -> None:
        #Atributos de instancia 
        Usuario._usuariosTotales.append(self)
        self.setId(len(Usuario.getUsuariosTotales()))
        self._bancosAsociados = []
        self._cuentasAhorroAsociadas = []
        self._cuentasCorrienteAsociadas = []
        self._cuentasAsociadas = []
        self._metasAsociadas = []
        self._contadorMovimientos = 0
        self._contadorMovimientosAux = 0
        self._movimientosAsociados = []
        self._limiteCuentas = 0
        for key in kwargs:
            if key == "_nombre":
                self.setNombre(kwargs[key])
            if key == "_correo":
                self.setCorreo(kwargs[key])
            if key == "_contrasena":
                self.setContrasena(kwargs[key])
            if key == "_suscripcion":
                self.setSuscripcion(kwargs[key])

    #Métodos de clase
    @classmethod
    def verificarCredenciales(cls, _nombre, _contrasena) -> object:
        for usuario in Usuario.getUsuariosTotales():
            if(usuario.getNombre() == _nombre or usuario.getCorreo() == _nombre):
                if(usuario.getContrasena() == _contrasena):
                    return usuario
        raise usersException.NoUserFoundException
    
    #Métodos de instancia
    def verificarContadorMovimientos(self):
        self.setContadorMovimientos(len(self.getMovimientosAsociados()))
        if(self.getContadorMovimientos() - self.getContadorMovimientosAux() == 5):
            self.setContadorMovimientosAux(self.getContadorMovimientosAux() + 5)
            match self.getSuscripcion():
                case Suscripcion.DIAMANTE:
                    self.setContadorMovimientos(0)
                    return("Felicidades, has alcanzado el nivel máximo de suscripción")
                case Suscripcion.ORO:
                    self.setContadorMovimientos(0)
                    self.setSuscripcion(Suscripcion.DIAMANTE)
                    return("Felicidades, has sido promovido al nivel de DIAMANTE, estos son tus beneficios: \nPuedes asociar un máximo de " + str(Suscripcion.DIAMANTE.getLimiteCuentas()) + " cuentas, la probabilidad de ganar en tu inversión es de " + str(Suscripcion.DIAMANTE.getProbabilidadInversion()))
                case Suscripcion.PLATA:
                    self.setContadorMovimientos(0)
                    self.setSuscripcion(Suscripcion.ORO)
                    return("Felicidades, has sido promovido al nivel de ORO, estos son tus beneficios: \nPuedes asociar un máximo de " + str(Suscripcion.ORO.getLimiteCuentas()) + " cuentas, la probabilidad de ganar en tu inversión es de " + str(Suscripcion.ORO.getProbabilidadInversion()))
                case Suscripcion.BRONCE:
                    self.setContadorMovimientos(0)
                    self.setSuscripcion(Suscripcion.PLATA)
                    return("Felicidades, has sido promovido al nivel de PLATA, estos son tus beneficios: \nPuedes asociar un máximo de " + str(Suscripcion.PLATA.getLimiteCuentas()) + " cuentas, la probabilidad de ganar en tu inversión es de " + str(Suscripcion.PLATA.getProbabilidadInversion()))
        else:
            return("Debes completar 5 movimientos para ser promovido de nivel, llevas " + str((self.getContadorMovimientos() - self.getContadorMovimientosAux())) + " movimiento(s)")
        
    def asociarBanco(self, banco) -> str:
        from gestorAplicación.externo.banco import Banco
        if(banco in Banco.getBancosTotales() and not(banco in self.getBancosAsociados())):
            self.getBancosAsociados().append(banco)
            return("El banco " + banco.getNombre() + " se ha asociado con éxito al usuario " + self.getNombre())
        else:
            return("No se encuentra el banco ó debes verificar que el banco que quieres asociar no se haya asociado antes, esta es la lista de bancos asociados: " + self.getBancosAsociados()[m] for m in range(0, len(self.getBancosAsociados())))
        
    def asociarCuenta(self, cuenta) -> str:
            if(not(cuenta in self.getCuentasAsociadas())):
                cuenta.setTitular(self)
                self.getCuentasAsociadas().append(cuenta)
                self.asociarBanco(cuenta.getBanco())
                if(isinstance(cuenta, Ahorros)):
                    return(self.asociarCuentaAhorros(cuenta))
                else:
                    if(cuenta.getCupo() == 0.0):
                        Corriente.inicializarCupo(cuenta)
                    return(self.asociarCuentaCorriente(cuenta))
            else:
                return("Debes comprobar que la cuenta no haya sido asociada con anterioridad.")
    
    def asociarMeta(self, meta) -> str:
        if(meta in Metas.getMetasTotales()):
            meta.setDueno(self)
            self.getMetasAsociadas().append(meta)
            return("La meta " + str(meta.getNombre()) + " se ha asociado con éxito al usuario " + self.getNombre())
        else:
            return("No se encuentra tu meta, debes verificar que la meta que quieres asociar exista" )
    
    def asociarMovimiento(self, movimiento) -> str:
        if(movimiento in Movimientos.getMovimientosTotales()):
            movimiento.setOwner(self)
            self.getMovimientosAsociados().append(movimiento)
            if(movimiento.getDestino() == None):
                return("El movimiento con origen " + movimiento.getOrigen().getNombre() + " ha sido asociada correctamente al usuario " + self.getNombre())
            else:
                if(not(movimiento.getDestino().getTitular() == self)):
                    movimiento.getDestino().getTitular().getMovimientosAsociados().append(movimiento)
                return("El movimiento con destino " + movimiento.getDestino().getNombre() + " ha sido asociada correctamente al usuario " + self.getNombre())
        else:
            return("No se encuentra el movimiento. Por favor asegurese de que el movimiento se haya realizado con éxito" )
    
    def asociarCuentaCorriente(self, corriente) -> str:
        if(corriente in Corriente.getCuentasCorrienteTotales()):
            self.getCuentasCorrienteAsociadas().append(corriente)
            return("La cuenta corriente " + corriente.getNombre() + " ha sido asociada correctamente al usuario " + self.getNombre())
        else:
            return("Debes verificar que la cuenta no haya sido asociada antes")
        
    def asociarCuentaAhorros(self, ahorros) -> str:
        if(ahorros in Ahorros.getCuentasAhorrosTotales()):
            self.getCuentasAhorroAsociadas().append(ahorros)
            return("La cuenta de ahorros " + ahorros.getNombre() + " ha sido asociada correctamente al usuario " + self.getNombre())
        else:
            return("Debes verificar que la cuenta no haya sido asociada antes")
    
    def mostrarBancosAsociados(self) -> object:
        bancos = self.getBancosAsociados()
        if(len(bancos) != 0):
            return bancos
        else:
            raise banksException.NoBanksException("Error. No hay bancos asociados a este usuario.")
        
    def mostrarCuentasAsociadas(self) -> object:
        cuentas = self.getCuentasAsociadas()
        if(len(cuentas) != 0):
            return cuentas
        else:
            return ("Primero debes asociar cuentas")
        
    def mostrarCuentasAhorroAsociadas(self) -> object:
        cuentas = self.getCuentasAhorroAsociadas()
        if(len(cuentas) != 0):
            return cuentas
        else:
            return ("Primero debes asociar cuentas")
        
    def mostrarCuentasCorrienteAsociadas(self) -> object:
        cuentas = self.getCuentasCorrienteAsociadas()
        if(len(cuentas) != 0):
            return cuentas
        else:
            return ("Primero debes asociar cuentas")
        
    # Métodos de la funcionalidad asesoramiento de inversiones
    def hallarUsuariogotaGota():
        contador = 0
        for i in range(len(Usuario.getUsuariosTotales())):
            if Usuario.getUsuariosTotales()[i].getNombre() == "gotaGota":
                contador = i
            else:
                continue
        return contador

    def hallarUsuarioImpuestosPortafolio():
        contador = 0
        for i in range(len(Usuario.getUsuariosTotales())):
            if Usuario.getUsuariosTotales()[i].getNombre() == "impuestosPortafolio":
                contador = i
            else:
                continue
        return contador

    def __str__(self) -> str:
        return("Usuario: " + self.getNombre() +
				"\nCorreo: " + self.getCorreo() +
				"\n#: " + str(self.getId()) +
				"\nCuentas Asociadas: " + str(self.getCuentasAsociadas()) +
				"\nSuscripción: " + str(self.getSuscripcion()))

    # Metodos funcionalidad de prestamos
# Metodos funcionalidad de prestamos
    def comprobarConfiabilidad(self):
        from .deuda import Deuda

        cuentasUsuario = self.getCuentasAhorroAsociadas()
        # conseguir la suscripcion
        suscripcion = self.getSuscripcion()
        # Comprobamos y contamos las deudas que estan asociadas al usuario
        deudasUsuario = Deuda.conseguirDeuda(self)
        if len(deudasUsuario)<suscripcion.getMaxDeudas():
            if len(cuentasUsuario) !=0:
                return cuentasUsuario
            else:
                return "¡Error! Usted no tiene ninguna cuenta Ahorros creada"
        else:
            return f"¡Error! La suscripción {suscripcion.name} solo permite realizar un total de {suscripcion.getMaxDeudas()}.Usted tiene {suscripcion.getMaxDeudas()}/{suscripcion.getMaxDeudas()}"
            ###########Falta imprimir las deudas

    #Métodos funcionalidad Compra de Cartera
    def retornarDeudas(self):
        cuentasConDeuda = []
        for cuenta in self.getCuentasAsociadas():
            if isinstance(cuenta, Corriente):
                if cuenta.getDisponible() != cuenta.getCupo():
                    cuentasConDeuda.append(cuenta)
        
        return cuentasConDeuda
    
    @staticmethod
    def capacidad_endeudamiento(cuentas, cuenta_a_aplicar):
        cuentas_capaces_deuda = []
        for cuenta in cuentas:
            if isinstance(cuenta, Corriente):
                deuda_validar = cuenta_a_aplicar.getCupo() - cuenta_a_aplicar.getDisponible()
                if cuenta.getDivisa() != cuenta_a_aplicar.getDivisa():
                    deuda_validar = Cuenta.dineroATenerDisponible(cuenta_a_aplicar, cuenta.getDivisa())
                if cuenta.getDisponible() >= deuda_validar and deuda_validar != 0:
                    cuentas_capaces_deuda.append(cuenta)
        
        return cuentas_capaces_deuda

    #Métodos Get & Set
    @classmethod
    def getUsuariosTotales(cls) -> list:
        return cls._usuariosTotales
    @classmethod
    def setUsuariosTotales(cls, _usuariosTotales) -> None:
        cls._usuariosTotales = _usuariosTotales
    def getNombre(self) -> str:
        return self._nombre
    def setNombre(self, _nombre) -> None:
        self._nombre = _nombre
    def getCorreo(self) -> str:
        return self._correo
    def setCorreo(self, _correo) -> None:
        self._correo = _correo
    def getContrasena(self) -> str:
        return self._contrasena
    def setContrasena(self, _contrasena) -> None:
        self._contrasena = _contrasena
    def getId(self) -> int:
        return self._id
    def setId(self, _id) -> None:
        self._id = _id
    def getSuscripcion(self) -> Suscripcion:
        return self._suscripcion
    def setSuscripcion(self, _suscripcion) -> None:
        self._suscripcion = _suscripcion
        self.setLimiteCuentas(self.getSuscripcion().getLimiteCuentas())
    def getCuentasAsociadas(self) -> list:
        return self._cuentasAsociadas
    def setCuentasAsociadas(self, _cuentasAsociadas) -> None:
        self._cuentasAsociadas = _cuentasAsociadas
    def getLimiteCuentas(self) -> int:
        return self._limiteCuentas
    def setLimiteCuentas(self, _limiteCuentas) -> None:
        self._limiteCuentas = _limiteCuentas
    def getBancosAsociados(self) -> list:
        return self._bancosAsociados
    def setBancosAsociados(self, _bancosAsociados) -> None:
        self._bancosAsociados = _bancosAsociados
    def getContadorMovimientos(self) -> int:
        return self._contadorMovimientos
    def setContadorMovimientos(self, _contadorMovimientos) -> None:
        self._contadorMovimientos = _contadorMovimientos
    def getMetasAsociadas(self) -> list:
        return self._metasAsociadas
    def setMetasAsociadas(self, _metasAsociadas) -> None:
        self._metasAsociadas = _metasAsociadas
    def getMovimientosAsociados(self) -> list:
        return self._movimientosAsociados
    def setMovimientosAsociados(self, _movimientosAsociados) -> None:
        self._movimientosAsociados = _movimientosAsociados
    def getCuentasCorrienteAsociadas(self) -> list:
        return self._cuentasCorrienteAsociadas
    def setCuentasCorrienteAsociadas(self, _cuentasCorrienteAsociadas) -> None:
        self._cuentasCorrienteAsociadas = _cuentasCorrienteAsociadas
    def getCuentasAhorroAsociadas(self) -> list:
        return self._cuentasAhorroAsociadas
    def setCuentasAhorroAsociadas(self, _cuentasAhorroAsociadas) -> None:
        self._cuentasAhorroAsociadas = _cuentasAhorroAsociadas
    def getContadorMovimientosAux(self) -> int:
        return self._contadorMovimientosAux
    def setContadorMovimientosAux(self, _contadorMovimientosAux) -> None:
        self._contadorMovimientosAux = _contadorMovimientosAux