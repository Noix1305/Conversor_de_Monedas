package api;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ExchangeRateClient {

    private static final String API_KEY = "c887bdc178e048b15761cd89";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private JsonObject conversionRates; // Almacena las tasas de conversión
    private final Map<String, String> monedasYpaises;

    public ExchangeRateClient() {
        // Inicializar el mapa de monedas y países
        monedasYpaises = new HashMap<>();
        inicializarMonedasYpaises();
    }

    public JsonObject obtenerTasasDeCambio() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            String url = BASE_URL + API_KEY + "/latest/USD";  // Solicita las tasas desde USD

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .header("Accept", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                String responseBody = response.body();
                conversionRates = procesarRespuesta(responseBody);
            }
            else {
                System.out.println("Error en la solicitud: " + response.statusCode());
            }
        }
        catch (IOException | InterruptedException e) {
            e.printStackTrace(); // Manejo de errores mejorado
        }
        return conversionRates;
    }

    // Método para procesar la respuesta JSON
    public JsonObject procesarRespuesta(String respuestaJson) {
        JsonObject jsonObject = JsonParser.parseString(respuestaJson).getAsJsonObject();
        if (jsonObject.has("conversion_rates")) {
            return jsonObject.getAsJsonObject("conversion_rates");
        }
        return null; // Retorna null si no se encuentra la clave
    }

    public JsonObject getConversionRates() {
        return conversionRates;
    }

    // Método para obtener un mapa con monedas y sus países
    public Map<String, String> obtenerMonedasYpaises() {
        return monedasYpaises;
    }

    public String[] obtenerMonedasDisponibles() {
        JsonObject tasasDeCambio = obtenerTasasDeCambio();
        if (tasasDeCambio != null) {
            return tasasDeCambio.keySet().toArray(new String[0]); // Retorna un array con las monedas disponibles
        }
        return new String[0]; // Retorna un array vacío si no se puede obtener
    }

    public void mostrarMonedasDisponibles() {
        Map<String, String> monedas = obtenerMonedasYpaises();
        System.out.println("--- Monedas Disponibles ---");
        for (String codigo : monedas.keySet()) {
            System.out.println(codigo + " - " + monedas.get(codigo));
        }
    }

    public String obtenerPaisPorMoneda(String moneda) {
        String pais = monedasYpaises.get(moneda);
        return (pais != null) ? pais : "Código de moneda no válido"; // Retorna un mensaje si el código no es válido
    }

    public void inicializarMonedasYpaises() {
        monedasYpaises.put("USD", "Dólar estadounidense"); // Estados Unidos
        monedasYpaises.put("AED", "Dirham de los Emiratos Árabes Unidos"); // Emiratos Árabes Unidos
        monedasYpaises.put("AFN", "Afgani afgano"); // Afganistán
        monedasYpaises.put("ALL", "Lek albanés"); // Albania
        monedasYpaises.put("AMD", "Dram armenio"); // Armenia
        monedasYpaises.put("ANG", "Florín de las Antillas Neerlandesas"); // Antillas Neerlandesas
        monedasYpaises.put("AOA", "Kwanza angoleño"); // Angola
        monedasYpaises.put("ARS", "Peso argentino"); // Argentina
        monedasYpaises.put("AUD", "Dólar australiano"); // Australia
        monedasYpaises.put("AWG", "Florín arubeño"); // Aruba
        monedasYpaises.put("AZN", "Manat azerbaiyano"); // Azerbaiyán
        monedasYpaises.put("BAM", "Marco convertible de Bosnia y Herzegovina"); // Bosnia y Herzegovina
        monedasYpaises.put("BBD", "Dólar de Barbados"); // Barbados
        monedasYpaises.put("BDT", "Taka bangladesí"); // Bangladés
        monedasYpaises.put("BGN", "Lev búlgaro"); // Bulgaria
        monedasYpaises.put("BHD", "Dinar bahreiní"); // Baréin
        monedasYpaises.put("BIF", "Franco burundés"); // Burundi
        monedasYpaises.put("BMD", "Dólar bermudeño"); // Bermudas
        monedasYpaises.put("BND", "Dólar de Brunei"); // Brunei
        monedasYpaises.put("BOB", "Boliviano"); // Bolivia
        monedasYpaises.put("BRL", "Real brasileño"); // Brasil
        monedasYpaises.put("BSD", "Dólar bahameño"); // Bahamas
        monedasYpaises.put("BTN", "Ngultrum butanés"); // Bután
        monedasYpaises.put("BWP", "Pula de Botsuana"); // Botsuana
        monedasYpaises.put("BYN", "Rublo bielorruso"); // Bielorrusia
        monedasYpaises.put("BZD", "Dólar beliceño"); // Belice
        monedasYpaises.put("CAD", "Dólar canadiense"); // Canadá
        monedasYpaises.put("CDF", "Franco congoleño"); // República del Congo
        monedasYpaises.put("CHF", "Franco suizo"); // Suiza
        monedasYpaises.put("CLP", "Peso chileno"); // Chile
        monedasYpaises.put("CNY", "Yuan chino"); // China
        monedasYpaises.put("COP", "Peso colombiano"); // Colombia
        monedasYpaises.put("CRC", "Colón costarricense"); // Costa Rica
        monedasYpaises.put("CUP", "Peso cubano"); // Cuba
        monedasYpaises.put("CVE", "Escudo caboverdiano"); // Cabo Verde
        monedasYpaises.put("CZK", "Corona checa"); // República Checa
        monedasYpaises.put("DJF", "Franco yemení"); // Yibuti
        monedasYpaises.put("DKK", "Corona danesa"); // Dinamarca
        monedasYpaises.put("DOP", "Peso dominicano"); // República Dominicana
        monedasYpaises.put("DZD", "Dinar argelino"); // Argelia
        monedasYpaises.put("EGP", "Libra egipcia"); // Egipto
        monedasYpaises.put("ERN", "Nakfa eritreo"); // Eritrea
        monedasYpaises.put("ETB", "Birr etíope"); // Etiopía
        monedasYpaises.put("EUR", "Euro"); // Eurozona
        monedasYpaises.put("FJD", "Dólar fiyiano"); // Fiyi
        monedasYpaises.put("FKP", "Libra de las Islas Malvinas"); // Islas Malvinas
        monedasYpaises.put("FOK", "Corona de las Islas Feroe"); // Islas Feroe
        monedasYpaises.put("GBP", "Libra esterlina"); // Reino Unido
        monedasYpaises.put("GEL", "Lari georgiano"); // Georgia
        monedasYpaises.put("GGP", "Libra de Guernsey"); // Guernsey
        monedasYpaises.put("GHS", "Cedi ghanés"); // Ghana
        monedasYpaises.put("GIP", "Libra de Gibraltar"); // Gibraltar
        monedasYpaises.put("GMD", "Dalasi gambiano"); // Gambia
        monedasYpaises.put("GNF", "Franco guineano"); // Guinea
        monedasYpaises.put("GTQ", "Quetzal guatemalteco"); // Guatemala
        monedasYpaises.put("GYD", "Dólar guyanés"); // Guyana
        monedasYpaises.put("HKD", "Dólar de Hong Kong"); // Hong Kong
        monedasYpaises.put("HNL", "Lempira hondureña"); // Honduras
        monedasYpaises.put("HRK", "Kuna croata"); // Croacia
        monedasYpaises.put("HTG", "Gourde haitiano"); // Haití
        monedasYpaises.put("HUF", "Forinto húngaro"); // Hungría
        monedasYpaises.put("IDR", "Rupia indonesia"); // Indonesia
        monedasYpaises.put("ILS", "Nuevo shekel israelí"); // Israel
        monedasYpaises.put("IMP", "Libra de la Isla de Man"); // Isla de Man
        monedasYpaises.put("INR", "Rupia india"); // India
        monedasYpaises.put("IQD", "Dinar iraquí"); // Irak
        monedasYpaises.put("IRR", "Rial iraní"); // Irán
        monedasYpaises.put("ISK", "Corona islandesa"); // Islandia
        monedasYpaises.put("JEP", "Libra de Jersey"); // Jersey
        monedasYpaises.put("JMD", "Dólar jamaiquino"); // Jamaica
        monedasYpaises.put("JOD", "Dinar jordano"); // Jordania
        monedasYpaises.put("JPY", "Yen japonés"); // Japón
        monedasYpaises.put("KES", "Chelín keniano"); // Kenia
        monedasYpaises.put("KGS", "Som kirguís"); // Kirguistán
        monedasYpaises.put("KHR", "Riel camboyano"); // Camboya
        monedasYpaises.put("KID", "Dólar de las Islas KI"); // Islas KI
        monedasYpaises.put("KMF", "Franco comorense"); // Comoras
        monedasYpaises.put("KRW", "Won surcoreano"); // Corea del Sur
        monedasYpaises.put("KWD", "Dinar kuwaití"); // Kuwait
        monedasYpaises.put("KYD", "Dólar de las Islas Caimán"); // Islas Caimán
        monedasYpaises.put("KZT", "Tenge kazajo"); // Kazajistán
        monedasYpaises.put("LAK", "Kip laosiano"); // Laos
        monedasYpaises.put("LBP", "Libra libanesa"); // Líbano
        monedasYpaises.put("LKR", "Rupia de Sri Lanka"); // Sri Lanka
        monedasYpaises.put("LRD", "Dólar liberiano"); // Liberia
        monedasYpaises.put("LSL", "Loti de Lesoto"); // Lesoto
        monedasYpaises.put("LYD", "Dinar libio"); // Libia
        monedasYpaises.put("MAD", "Dirham marroquí"); // Marruecos
        monedasYpaises.put("MDL", "Leu moldavo"); // Moldavia
        monedasYpaises.put("MGA", "Ariary malgache"); // Madagascar
        monedasYpaises.put("MKD", "Dinar macedonio"); // Macedonia del Norte
        monedasYpaises.put("MMK", "Kyat birmano"); // Birmania
        monedasYpaises.put("MNT", "Tugrik mongol"); // Mongolia
        monedasYpaises.put("MOP", "Pataca de Macao"); // Macao
        monedasYpaises.put("MRU", "Ouguiya mauritana"); // Mauritania
        monedasYpaises.put("MUR", "Rupia mauriciana"); // Mauricio
        monedasYpaises.put("MVR", "Rufiyaa maldiva"); // Maldivas
        monedasYpaises.put("MWK", "Kwacha malauí"); // Malaui
        monedasYpaises.put("MXN", "Peso mexicano"); // México
        monedasYpaises.put("MYR", "Ringgit malayo"); // Malasia
        monedasYpaises.put("MZN", "Metical mozambiqueño"); // Mozambique
        monedasYpaises.put("NAD", "Dólar namibio"); // Namibia
        monedasYpaises.put("NGN", "Naira nigeriana"); // Nigeria
        monedasYpaises.put("NIO", "Córdoba nicaragüense"); // Nicaragua
        monedasYpaises.put("NOK", "Corona noruega"); // Noruega
        monedasYpaises.put("NPR", "Rupia nepalí"); // Nepal
        monedasYpaises.put("NZD", "Dólar neozelandés"); // Nueva Zelanda
        monedasYpaises.put("OMR", "Rial omaní"); // Omán
        monedasYpaises.put("PAB", "Balboa panameño"); // Panamá
        monedasYpaises.put("PEN", "Sol peruano"); // Perú
        monedasYpaises.put("PGK", "Kina papuana"); // Papúa Nueva Guinea
        monedasYpaises.put("PHP", "Peso filipino"); // Filipinas
        monedasYpaises.put("PKR", "Rupia pakistaní"); // Pakistán
        monedasYpaises.put("PLN", "Zloty polaco"); // Polonia
        monedasYpaises.put("PYG", "Guaraní paraguayo"); // Paraguay
        monedasYpaises.put("QAR", "Rial catarí"); // Catar
        monedasYpaises.put("RON", "Leu rumano"); // Rumania
        monedasYpaises.put("RSD", "Dinar serbio"); // Serbia
        monedasYpaises.put("RUB", "Rublo ruso"); // Rusia
        monedasYpaises.put("RWF", "Franco ruandés"); // Ruanda
        monedasYpaises.put("SAR", "Riyal saudí"); // Arabia Saudita
        monedasYpaises.put("SBD", "Dólar de las Islas Salomón"); // Islas Salomón
        monedasYpaises.put("SCR", "Rupia seychelense"); // Seychelles
        monedasYpaises.put("SDG", "Libra sudanesa"); // Sudán
        monedasYpaises.put("SEK", "Corona sueca"); // Suecia
        monedasYpaises.put("SGD", "Dólar de Singapur"); // Singapur
        monedasYpaises.put("SHP", "Libra de Santa Helena"); // Santa Elena
        monedasYpaises.put("SLE", "León de Sierra Leona"); // Sierra Leona
        monedasYpaises.put("SLL", "León de Sierra Leona"); // Sierra Leona
        monedasYpaises.put("SOS", "Chelín somalí"); // Somalia
        monedasYpaises.put("SRD", "Dólar surinamés"); // Surinam
        monedasYpaises.put("SSP", "Libra sursudanesa"); // Sudán del Sur
        monedasYpaises.put("STN", "Dobra de Santo Tomé y Príncipe"); // Santo Tomé y Príncipe
        monedasYpaises.put("SYP", "Libra siria"); // Siria
        monedasYpaises.put("SZL", "Lilangeni suazi"); // Suazilandia
        monedasYpaises.put("THB", "Baht tailandés"); // Tailandia
        monedasYpaises.put("TJS", "Somoni tayiko"); // Tayikistán
        monedasYpaises.put("TMT", "Manat turkmeno"); // Turkmenistán
        monedasYpaises.put("TND", "Dinar tunecino"); // Túnez
        monedasYpaises.put("TOP", "Paʻanga tongano"); // Tonga
        monedasYpaises.put("TRY", "Lira turca"); // Turquía
        monedasYpaises.put("TTD", "Dólar trinitense"); // Trinidad y Tobago
        monedasYpaises.put("TVD", "Dólar de Tuvalu"); // Tuvalu
        monedasYpaises.put("TWD", "Nuevo dólar taiwanés"); // Taiwán
        monedasYpaises.put("TZS", "Chelín tanzano"); // Tanzania
        monedasYpaises.put("UAH", "Grivna ucraniana"); // Ucrania
        monedasYpaises.put("UGX", "Chelín ugandés"); // Uganda
        monedasYpaises.put("UYU", "Peso uruguayo"); // Uruguay
        monedasYpaises.put("UZS", "Som uzbeko"); // Uzbekistán
        monedasYpaises.put("VES", "Bolívar venezolano"); // Venezuela
        monedasYpaises.put("VND", "Dong vietnamita"); // Vietnam
        monedasYpaises.put("VUV", "Vatu de Vanuatu"); // Vanuatu
        monedasYpaises.put("WST", "Tala samoano"); // Samoa
        monedasYpaises.put("XAF", "Franco CFA de África Central"); // África Central
        monedasYpaises.put("XCD", "Dólar del Caribe Oriental"); // Caribe Oriental
        monedasYpaises.put("XDR", "Derechos especiales de giro"); // FMI
        monedasYpaises.put("XOF", "Franco CFA de África Occidental"); // África Occidental
        monedasYpaises.put("XPF", "Franco CFP"); // Territorios franceses del Pacífico
        monedasYpaises.put("YER", "Rial yemení"); // Yemen
        monedasYpaises.put("ZAR", "Rand sudafricano"); // Sudáfrica
        monedasYpaises.put("ZMW", "Kwacha zambiano"); // Zambia
        monedasYpaises.put("ZWL", "Dólar de Zimbabue"); // Zimbabue
    }

}
