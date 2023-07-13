
class First(object):
    def __init__(self):
        print("First")
        super().__init__()


class Second(First):
    def __init__(self):
        print("Second")
        super().__init__()

class Third(First):
    def __init__(self):
        print("Third")
        super().__init__()

class Fourth(Second, Third):
    def __init__(self):
        print("Fourth")
        Second.__init__(self)
        Third.__init__(self)

        #Cambio:
        #Third.__init__(self)
        #Second.__init__(self)


a = Fourth()
