from datetime import date
from datetime import datetime

class Metas:
    _metasTotales = []
    _metaProxima = None
    def __init__(self,**kwargs):
        self._nombre = None
        self.cantidad = None
        self._fecha = None
        self.dueno = None
        Metas._metasTotales.append(self)
        self._id = len(Metas._metasTotales)

        for key in kwargs:
            if key == "nombre":
                self._nombre = kwargs[key]
            if key == "cantidad":
                self.cantidad = kwargs[key]
            if key == "fecha":
                self._fecha = kwargs[key]
            if key == "dueno":
                self.dueno = kwargs[key]

    # Metodos asesoramiento Inversiones
    def revision_metas(user):
        proximaFecha = datetime.strptime("01/01/0001", "%d/%m/%Y")
        proximaMeta = None
        for meta1 in user.getMetasAsociadas():
            if meta1.getFecha() != None:
                proximaMeta = meta1
                for meta2 in user.getMetasAsociadas():
                    if not meta2.getFecha() is None:
                        fecha1 = datetime.strptime(str(meta1.getFecha()), "%d/%m/%Y")
                        fecha2 = datetime.strptime(str(meta2.getFecha()), "%d/%m/%Y")
                        if fecha2 < fecha1:
                            if fecha2 < proximaFecha or proximaFecha == None:
                                proximaFecha = fecha2
                                proximaMeta = meta2
                            else:
                                continue
                        else:
                            continue
                    else:
                        continue
            else:
                continue

        return proximaMeta

    @staticmethod
    def cambio_fecha(meta, fecha):
        meta.setFecha(fecha)
        return meta

    @staticmethod
    def determinar_plazo(meta):
        date1 = datetime.strptime("01/01/2024", "%d/%m/%Y")
        date2 = datetime.strptime("01/01/2026", "%d/%m/%Y")
        fecha = datetime.strptime(meta.getFecha(), "%d/%m/%Y")

        if fecha < date1:
            return "Corto"
        elif date1 < fecha < date2:
            return "Mediano"
        else:
            return "Largo"

    @staticmethod
    def prioridad_metas(user, meta):
        user.getMetasAsociadas().pop(len(user.getMetasAsociadas()) - 1)
        user.getMetasAsociadas().insert(0, meta)

    @staticmethod
    def limpiar_propiedades(arreglo):
        arreglo.remove("serialVersionUID")
        arreglo.remove("nombreD")
        arreglo.remove("metasTotales")
        arreglo.remove("DATE_FORMAT")
        arreglo.remove("metaProxima")
        
# Sets Y Gets
    
    def getNombre(self) -> str:
        return self._nombre
    def getCantidad(self):
        return self.cantidad
    def getFecha(self) -> str:
        return self._fecha
    def getDueno(self) -> str:
        return self.dueno
    def getMetasTotales():
        return Metas._metasTotales
    def getId(self):
        return self._id
    def getMetaProxima():
        return Metas._metaProxima

    def setNombre(self,nombre):
        self._nombre = nombre 
    def setCantidad(self,cantidad):
        self.cantidad = cantidad
    def setFecha(self,fecha):
        self._fecha = fecha
    def setDueno(self,dueno):
        self.dueno = dueno
    def setMetasTotales(MetasTotales):
        Metas._metasTotales = MetasTotales
    def setId(self,id):
        self._id = id
    def setMetaProxima(metaProxima):
        Metas._metaProxima = metaProxima

        