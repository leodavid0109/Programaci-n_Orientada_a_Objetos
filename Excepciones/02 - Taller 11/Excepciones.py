class SomeError(Exception):
    def __init__(self, error):
        self.error = "Manejo errores aplicacion " + error


class SomeCriticalError(SomeError):
    def __init__(self):
        error = "Esto es un error critico"
        super().__init__(error)


class SomeCookiesError(SomeError):
    def __init__(self):
        error = "cookies fallo .."
        super().__init__(error)


try:
    a = SomeCookiesError()
    raise a
except SomeCookiesError as a:
    print(a.error)
except SomeCriticalError as a:
    print(a.error)