from SomeError import SomeError

class SomeCriticalError(SomeError):
    def __init__(self, complement):
        message = "¡Error crítico! " + complement
        super().__init__(message)