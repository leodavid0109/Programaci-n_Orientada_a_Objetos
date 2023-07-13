import pickle as pk
import os
import os.path as os_query
from gestorAplicación.interno.usuario import Usuario
from gestorAplicación.interno.movimientos import Movimientos
from gestorAplicación.externo.estado import Estado
from gestorAplicación.externo.banco import Banco
from gestorAplicación.interno.ahorros import Ahorros, Cuenta
from gestorAplicación.interno.corriente import Corriente
from gestorAplicación.interno.deuda import Deuda, Metas

class Deserializador():
    # Método para deserializar las listas con objetos de cualquier clase, que añade automáticamente los objetos a las listas de clase respectiva para cada clase
    
    # Obtener ruta relativa
    current_directory = os.path.dirname(os.path.abspath(__file__))
    route_db = os.path.join(current_directory + '\\temp')

    @classmethod
    def deserializar(cls, clase) -> list:
        if(clase == "Usuarios"):
            if(os_query.isfile(cls.route_db + "\lista_usuarios.pkl")):
                objects_file = open(cls.route_db + "\lista_usuarios.pkl", "rb")
                objects_list = pk.load(objects_file)
                objects_file.close()
                Usuario.setUsuariosTotales(objects_list)
        if(clase == "Estados"):
            if(os_query.isfile(cls.route_db + "/lista_estados.pkl")):
                objects_file = open(cls.route_db + "/lista_estados.pkl", "rb")
                objects_list = pk.load(objects_file)
                objects_file.close()
                Estado.setEstadosTotales(objects_list)
        if(clase == "Bancos"):
            if(os_query.isfile(cls.route_db + "/lista_bancos.pkl")):
                objects_file = open(cls.route_db + "/lista_bancos.pkl", "rb")
                objects_list = pk.load(objects_file)
                objects_file.close()
                Banco.setBancosTotales(objects_list)
        if(clase == "Cuentas"):
            if(os_query.isfile(cls.route_db + "/lista_cuentas.pkl")):
                objects_file = open(cls.route_db + "/lista_cuentas.pkl", "rb")
                objects_list = pk.load(objects_file)
                objects_file.close()
                Cuenta.setCuentasTotales(objects_list)
                for account in Cuenta.getCuentasTotales():
                    if(isinstance(account, Ahorros)):
                        Ahorros.getCuentasAhorrosTotales().append(account)
                    else:
                        Corriente.getCuentasCorrienteTotales().append(account)
        if(clase == "Movimientos"):
            if(os_query.isfile(cls.route_db + "/lista_movimientos.pkl")):
                objects_file = open(cls.route_db + "/lista_movimientos.pkl", "rb")
                objects_list = pk.load(objects_file)
                objects_file.close()
                Movimientos.setMovimientosTotales(objects_list)
        if(clase == "Metas"):
            if(os_query.isfile(cls.route_db + "/lista_metas.pkl")):
                objects_file = open(cls.route_db + "/lista_metas.pkl", "rb")
                objects_list = pk.load(objects_file)
                objects_file.close()
                Metas.setMetasTotales(objects_list)
                for meta in Metas.getMetasTotales():
                    if(isinstance(meta, Deuda)):
                        Deuda.getDeudasTotales().append(meta)
