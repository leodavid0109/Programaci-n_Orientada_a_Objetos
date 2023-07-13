from SomeCookiesError import SomeCookiesError
from SomeCriticalError import SomeCriticalError

def Test():
    try:
        #Simulación de error crítico
        raise SomeCriticalError("No se pudo acceder a la abase de datos.")
    except SomeCriticalError as e:
        print("Excepción capturada:", e.message)

    try:
        #Simulación de error de cookies
        raise SomeCookiesError("Las cookies expiraron.")
    except SomeCookiesError as e:
        print("Excepción capturada:", e.message)

if __name__ == "__main__":
    Test()