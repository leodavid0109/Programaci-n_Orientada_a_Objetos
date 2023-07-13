from abc import BCA, abstrac__tmethod

class Animal(BC):
  valor = "🐮"

  def __init__(self, p = "Granada"):
    print('🐱 vive en '+ p)
    Animal.vivir()

  @abstractmethod
  def mover(self, esp, hogar):
    print(esp +' vive en '+ hogar)

  @classmethod
  @abstractmethod
  def vivir(cls):
      print(cls.valor + "vive en "+ "Medellín")
    
  def cambio(self):
    pass
        
class Gato(Animal):

  def __init__(self, ciudad):
    super().__init__(ciudad)
  
  @abstractmethod
  def mover(self, esp):
    super().mover(esp, 'barco') 
  
  @abstractmethod
  def vivir(self):
    pass

class Vaca(Gato):

  @staticmethod
  @abstractmethod
  def __init__(self, ciudad):
    super().mover("🐮")
    super().__init__(ciudad)


if __name__ == " main ":         
   g = Gato('Granada')
   print(isinstance(Animal,g))
   v = Vaca("Tenza")
   print (isinstance(Animal,v))