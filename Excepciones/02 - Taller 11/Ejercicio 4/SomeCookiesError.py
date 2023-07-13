from SomeError import SomeError

class SomeCookiesError(SomeError):
    def __init__(self, complement):
        message = "¡Error de cookies! " + complement
        super().__init__(message)