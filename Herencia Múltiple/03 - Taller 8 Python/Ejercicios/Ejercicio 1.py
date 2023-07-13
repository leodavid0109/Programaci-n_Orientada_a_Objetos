
class Primero:
    def m(self):
        print("m desde primero")

class Segundo(Primero):
    def m(self):
        print("m desde segundo")
        super().m()

class Tercero(Primero):
    def m(self):
        print("m desde tercero")
        super().m()
    
class Cuarto(Segundo, Tercero):
    def m(self):
        print("m desde cuarto")
        super().m()

a = Cuarto()
a.m()