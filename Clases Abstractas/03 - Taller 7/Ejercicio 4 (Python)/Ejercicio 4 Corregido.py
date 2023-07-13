from abc import ABC, abstractmethod #Cambio BCA por ABC, quito "__" para dejar abstractmethod

class Animal(ABC): #Agrego la A restante para tener ABC
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
  
  #@abstractmethod  (Quito el decorador, Gato no es un método abstracto pues estoy redefiniendo nuevamente mis métodos)
  def mover(self, esp):
    super().mover(esp, 'barco') 
  
  #@abstractmethod  (Quito el decorador, Gato no es un método abstracto pues estoy redefiniendo nuevamente mis métodos)
  def vivir(self):
    pass

class Vaca(Gato):

  #@staticmethod    (Quito el decorador, el inicializador de atributos no es un método estático, además, estoy usando self)
  #@abstractmethod  (Quito el decorador, Vaca no es un método abstracto)
  def __init__(self, ciudad):
    super().mover("🐮")
    super().__init__(ciudad)


if __name__ == "__main__": #Agrego "__" restantes para completar el "__main__"
   g = Gato('Granada')
   print(isinstance(g, Animal)) #Cambio orden del isintance, primero objeto, luego tipo de objeto
   v = Vaca("Tenza")
   print (isinstance(v, Animal)) #Cambio orden del isintance, primero objeto, luego tipo de objeto