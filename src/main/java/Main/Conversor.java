package Main;

import api.ExchangeRateClient;
import com.google.gson.JsonObject;

import java.util.Scanner;

public class Conversor {

    public static void main(String[] args) {
        Conversor conversor = new Conversor();
        conversor.menu();
    }

    // Método que presenta el menú al usuario
    public void menu() {
        ExchangeRateClient client = new ExchangeRateClient();
        JsonObject conversionRates = client.obtenerTasasDeCambio();
        try (Scanner scanner = new Scanner(System.in)) {
            int opcion;
            
            do {
                System.out.println("\n--- Conversor de Monedas ---");
                System.out.println("1. Realizar conversion de moneda");
                System.out.println("2. Salir");
                System.out.print("Seleccione una opcion: ");
                opcion = scanner.nextInt();
                
                switch (opcion) {
                    case 1 -> {
                        if (conversionRates != null) {
                            realizarConversion(scanner, conversionRates);
                        }
                        else {
                            System.out.println("Primero debe obtener las tasas de cambio.");
                        }
                    }
                    case 2 -> System.out.println("Saliendo del programa...");
                    default -> System.out.println("Opción no válida. Por favor, intente de nuevo.");
                }
            }
            while (opcion != 2);
        }
    }

    // Método para realizar la conversión
    public void realizarConversion(Scanner scanner, JsonObject conversionRates) {
        System.out.print("Ingrese la cantidad a convertir: ");
        double cantidad = scanner.nextDouble();

        System.out.print("Ingrese la moneda de origen (ej. USD, EUR): ");
        String monedaOrigen = scanner.next().toUpperCase();

        System.out.print("Ingrese la moneda de destino (ej. USD, EUR): ");
        String monedaDestino = scanner.next().toUpperCase();

        double resultado = convertirMoneda(cantidad, monedaOrigen, monedaDestino, conversionRates);
        System.out.printf("%.2f %s son %.2f %s%n", cantidad, monedaOrigen, resultado, monedaDestino);
    }

    public double convertirMoneda(double cantidad, String monedaOrigen, String monedaDestino, JsonObject conversionRates) {
        double tasaOrigen = conversionRates.get(monedaOrigen).getAsDouble();
        double tasaDestino = conversionRates.get(monedaDestino).getAsDouble();

        double valorEnDolares = cantidad / tasaOrigen; // Convertir a USD
        double valorConvertido = valorEnDolares * tasaDestino; // Convertir a la moneda de destino

        return valorConvertido;
    }
}
