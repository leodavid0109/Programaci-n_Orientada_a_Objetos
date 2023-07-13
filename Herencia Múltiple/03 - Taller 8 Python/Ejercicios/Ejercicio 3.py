class Numeros:
    def __init__(self):
        self.a = 4
        self.b = 5

class Operadores1:
    def sumar(self):
        return self.a + self.b
    def multiplicar(self):
        return self.a * self.b
    
class Operadores2:
    def dividir(self):
        return self.a / self.b
    def restar(self):
        return self.a - self.b
    
class Operadores3(Operadores1, Operadores2):
    pass

class Ejecucion1(Numeros):
    pass

class Ejecucion2(Operadores1, Operadores2):
    pass

class Ejecucion3(Operadores1, Numeros):
    pass

class Ejecucion4(Operadores3, Numeros):
    pass

if __name__ == "__main__":
    ob1 = Ejecucion1()
    ob2 = Ejecucion2()
    ob3 = Ejecucion3()
    ob4 = Ejecucion4()

    ob1.sumar()
    ob2.sumar()
    ob3.sumar()
    ob4.sumar()

    ob1.restar()
    ob2.restar()
    ob3.restar()
    ob4.restar()

    ob1.multiplicar()
    ob2.multiplicar()
    ob3.multiplicar()
    ob4.multiplicar()

    ob1.dividir()
    ob2.dividir()
    ob3.dividir()
    ob4.dividir()