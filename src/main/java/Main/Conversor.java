package Main;

import api.ExchangeRateClient;
import com.google.gson.JsonObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Conversor {

    private List<String> historial = new ArrayList<>();
    ExchangeRateClient client = new ExchangeRateClient();

    public static void main(String[] args) {
        Conversor conversor = new Conversor();
        conversor.menu();
    }

    // Método que presenta el menú al usuario
    public void menu() {

        JsonObject conversionRates = client.obtenerTasasDeCambio();

        // Llamar a la función para inicializar monedas y países
        client.inicializarMonedasYpaises();

        try (Scanner scanner = new Scanner(System.in)) {
            int opcion;

            do {
                System.out.println("\n--- Conversor de Monedas ---");
                System.out.println("1. Realizar conversión de moneda");
                System.out.println("2. Mostrar historial de conversiones");
                System.out.println("3. Ver tasas de cambio disponibles");
                System.out.println("4. Salir");
                System.out.print("Seleccione una opción: ");
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
                    case 2 ->
                        mostrarHistorial();
                    case 3 ->
                        mostrarTasasDisponibles();
                    case 5 ->
                        System.out.println("Saliendo del programa...");
                    default ->
                        System.out.println("Opción no válida. Por favor, intente de nuevo.");
                }
            }
            while (opcion != 5);
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
        registrarConversion(monedaOrigen, monedaDestino, resultado);
    }

    private void registrarConversion(String monedaOrigen, String monedaDestino, double montoConvertido) {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String registro = String.format("Conversión: %s a %s de %.2f en %s", monedaOrigen, monedaDestino, montoConvertido, ahora.format(formato));
        agregarHistorial(registro);
    }

    public void agregarHistorial(String conversion) {
        historial.add(conversion);
    }

    public double convertirMoneda(double cantidad, String monedaOrigen, String monedaDestino, JsonObject conversionRates) {
        double tasaOrigen = conversionRates.get(monedaOrigen).getAsDouble();
        double tasaDestino = conversionRates.get(monedaDestino).getAsDouble();

        double valorEnDolares = cantidad / tasaOrigen; // Convertir a USD
        double valorConvertido = valorEnDolares * tasaDestino; // Convertir a la moneda de destino

        return valorConvertido;
    }

    public void mostrarHistorial() {
        System.out.println("Historial de conversiones:");
        if (historial.isEmpty()) {
            System.out.println("No hay conversiones realizadas.");
        }
        else {
            for (String h : historial) {
                System.out.println(h);
            }
        }
    }

    public void mostrarTasasDisponibles() {
        String[] monedasDisponibles = new ExchangeRateClient().obtenerMonedasDisponibles();
        System.out.println("Monedas disponibles para conversión:");

        if (monedasDisponibles.length == 0) {
            System.out.println("No hay monedas disponibles.");
        }
        else {
            for (String moneda : monedasDisponibles) {
                // Mostrar el código de la moneda y su descripción (país)
                String pais = client.obtenerPaisPorMoneda(moneda); // Obtener el país de la moneda
                if (pais != null) {
                    System.out.println("- " + moneda + ": " + pais);
                }
                else {
                    System.out.println("- " + moneda + ": Descripción no disponible"); // Manejo de monedas sin descripción
                }
            }
        }
    }
}
