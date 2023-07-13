import random
class Apostador:
    def __init__(self, id, name, phone_number, email):
        self.id = id
        self.name = name
        self.phone_number = phone_number
        self.email = email
        self.wallet = 0
    
    def deposit(self, amount):
        self.wallet += amount
    
    def play(self, value):
        if(self.wallet >= value):
            loteria = Loteria(value, self)
            loteria.playGame()
        else:
            print("Necesitas poner mas dinero en tu wallet")

class ComisionJuegoEspectaculos:
    COMMIPERCENTAJE = 0.20
    def __init__(self, loteria):
        self.loteria = loteria
    
    def commission(self):
        loteriaValue = self.loteria.value
        commission = self.gain(loteriaValue, self.COMMIPERCENTAJE)
        return commission
    
    @staticmethod
    def gain(loteriaValue, percentage):
        gain = loteriaValue-(loteriaValue*percentage)
        return gain

class Loteria:
    probability = 0.5
    def __init__(self, value, apostador):
        self.value = value
        self.apostador = apostador

    def payMoney(self, gain):
        self.apostador.wallet += gain

    def recieveMoney(self):
        self.apostador.wallet -= self.value

    def playGame(self):
        a = random.randint(0,1)
        if(a < self.probability):
            commi = ComisionJuegoEspectaculos(self)
            gain = commi.commission()
            total = gain + self.value
            print("Has ganado "+ str(total))
            self.payMoney(gain)
        else:
            print("Has perdido lo que apostaste")
            self.recieveMoney()

if __name__ == "__main__":
    apostador1 = Apostador(1, "Juan", 302, "j@gmail.com")
    apostador1.deposit(500)
    print(apostador1.wallet)
    apostador1.play(400)
    print(apostador1.wallet)

    apostador2 = Apostador(2, "Ricardo", 548, "r@gmail.com")
    apostador2.deposit(500)
    print(apostador2.wallet)
    apostador2.play(400)
    print(apostador2.wallet)