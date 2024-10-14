# Conversor_de_Monedas
Challenge alura Latam

Este es un conversor de monedas desarrollado en Java que utiliza la API de ExchangeRate para obtener tasas de cambio actualizadas. Permite a los usuarios convertir entre diferentes monedas de manera interactiva a través de la consola.

## Tabla de Contenidos

- [Características](#características)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Uso](#uso)
- [Contribuciones](#contribuciones)
- [Licencia](#licencia)

## Características

- Obtiene tasas de cambio en tiempo real desde la API de ExchangeRate.
- Interfaz de usuario basada en consola para interactuar y realizar conversiones de moneda.
- Soporte para múltiples monedas (USD, EUR, etc.).

## Requisitos

- Java 11 o superior
- Acceso a Internet para consultar la API de ExchangeRate.

## Instalación

1. Clona este repositorio:
   ```bash
   git clone https://github.com/tu_usuario/conversor-monedas.git
   ```
   
2. Navega al directorio del proyecto:
   ```bash
   cd conversor-monedas
   ```

3. Asegúrate de tener las dependencias necesarias en tu proyecto. Si utilizas Maven, añade la dependencia de Gson en tu archivo `pom.xml`:
   ```xml
   <dependency>
       <groupId>com.google.code.gson</groupId>
       <artifactId>gson</artifactId>
       <version>2.8.9</version>
   </dependency>
   ```

## Uso

1. Compila y ejecuta la clase principal:
   ```bash
   javac -d out src/Main/Conversor.java src/api/ExchangeRateClient.java
   java -cp out Main.Conversor
   ```

2. Sigue las instrucciones en la consola para seleccionar la opción de conversión de moneda, ingresa la cantidad y especifica las monedas de origen y destino.

## Contribuciones

Las contribuciones son bienvenidas. Si deseas colaborar, por favor sigue estos pasos:

1. Haz un fork del proyecto.
2. Crea tu característica o corrige un error.
3. Realiza un commit de tus cambios.
4. Envía un pull request.

## Licencia

Este proyecto está bajo la Licencia MIT. Para más detalles, consulta el archivo [LICENSE](LICENSE).
