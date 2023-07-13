import pickle as pk
import os
from gestorAplicación.interno.usuario import Usuario
from gestorAplicación.interno.cuenta import Cuenta
from gestorAplicación.interno.metas import Metas
from gestorAplicación.interno.movimientos import Movimientos
from gestorAplicación.externo.estado import Estado
from gestorAplicación.externo.banco import Banco

class Serializador():

    # Obtener ruta relativa
    current_directory = os.path.dirname(os.path.abspath(__file__))
    route_db = os.path.join(current_directory + '\\temp')

    #Método para serializar las listas con objetos de cualquier clase
    @classmethod
    def serializar(cls, nombre_clase) -> None:
        if(nombre_clase == "Usuarios"):
            objects_file = open(cls.route_db + "\lista_usuarios.pkl", "wb")
            pk.dump(Usuario.getUsuariosTotales(), objects_file)
            objects_file.close()
        if(nombre_clase == "Bancos"):
            objects_file = open(cls.route_db + "\lista_bancos.pkl", "wb")
            pk.dump(Banco.getBancosTotales(), objects_file)
            objects_file.close()
        if(nombre_clase == "Estados"):
            objects_file = open(cls.route_db + "\lista_estados.pkl", "wb")
            pk.dump(Estado.getEstadoTotales(), objects_file)
            objects_file.close()
        if(nombre_clase == "Cuentas"):
            objects_file = open(cls.route_db + "\lista_cuentas.pkl", "wb")
            pk.dump(Cuenta.getCuentasTotales(), objects_file)
            objects_file.close()
        if(nombre_clase == "Movimientos"):
            objects_file = open(cls.route_db + "\lista_movimientos.pkl", "wb")
            pk.dump(Movimientos.getMovimientosTotales(), objects_file)
            objects_file.close()
        if(nombre_clase == "Metas"):
            objects_file = open(cls.route_db + "\lista_metas.pkl", "wb")
            pk.dump(Metas.getMetasTotales(), objects_file)
            objects_file.close()