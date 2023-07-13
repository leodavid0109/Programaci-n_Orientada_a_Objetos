class Celular:
    _cantidad = 0

    def __init__(self, dueno, modelo, almacenamiento):
        self.dueno = dueno
        self.modelo = modelo
        self.almacenamiento = almacenamiento
        Celular._cantidad += 1
    
    @staticmethod
    def getCantidad():
        return Celular._cantidad