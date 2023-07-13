from SerVivo import SerVivo
from Animal import Animal
from Perro import Perro
from Gato import Gato
from Pajaro import Pajaro
from Persona import Persona

if __name__== "__main__":
    #Pa1 = Pajaro("Pepito", 2, "Loro", "Blancas")
    Pe1 = Perro("Nash", 5, "Chiguagua", "Café")
    Ga1 = Gato("Zeus", 3, "Gata", "Limpio")

    #print(Pa1.ruido())
    print(Pe1.ruido())
    print(Ga1.ruido())
    print(Animal.ruido())