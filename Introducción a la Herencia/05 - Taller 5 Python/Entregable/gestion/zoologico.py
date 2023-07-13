from gestion.zona import Zona
class Zoologico:
    
    def __init__(self, nombre = "", ubicacion = ""):
        self._nombre = nombre
        self._ubicacion = ubicacion
        self._zonas = list()
    
    def cantidadTotalAnimales(self):
        cantidad1 = 0
        for zona in self._zonas:
            cantidad1 += zona.cantidadAnimales()
        return cantidad1
    
    def agregarZonas(self, zona):
        self._zonas.append(zona)
    
    def __str__(self):
        return self._nombre
    
    def getNombre(self):
        return self._nombre
    
    def setNombre(self, nombre):
        self._nombre = nombre
    
    def getUbicacion(self):
        return self._ubicacion
    
    def setUbicacion(self, ubicacion):
        self._ubicacion = ubicacion
    
    def getZona(self):
        return self._zonas
    
    def setZona(self, zonas):
        self._zonas = zonas
