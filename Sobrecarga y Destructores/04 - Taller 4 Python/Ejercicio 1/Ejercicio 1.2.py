class Punto:
    def __init__(self, x, y=0, *args):
        self.x = x
        self.y = y

class Circulo:
    def __init__(self, radio, *args):
        self.radio = radio
        self.punto = Punto(*args)


cir = Circulo(10, 1, 1, 1)
print(cir.radio)
print(cir.punto.x)
print(cir.punto.y)
