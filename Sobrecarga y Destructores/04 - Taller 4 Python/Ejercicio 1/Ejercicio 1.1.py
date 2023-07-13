class Carro:
    puertas = 4
    carros = 0

    def __init__(self, referencia, peso = 4, marca = "Ford", motor = None):
        self._referencia = referencia
        self._peso = peso #Peso en toneladas
        self._marca = marca
        self._motor = motor
        Carro.carros = Carro.carros+1

    def getPeso(self):
        return self._peso

    def getReferencia(self):
        return self._referencia

    @staticmethod
    def carro_mas_pesado(carros):
        aux_ref = carros[0].getReferencia()
        aux_peso = carros[0].getPeso()
        for carro in carros:
            if (carro.getPeso() > aux_peso):
                aux_ref = carro.getReferencia()
                aux_peso = carro.getPeso()
        
        return aux_ref

if __name__ == "__main__":
    #carro0 = Carro()
    carro1 = Carro('Tracker')
    carro2 = Carro("Sandero Stepway", 3, "Renault", "v8")
    carro3 = Carro("Picanto", 2, "Kia")

    print(Carro.carro_mas_pesado([carro1, carro2, carro3]))