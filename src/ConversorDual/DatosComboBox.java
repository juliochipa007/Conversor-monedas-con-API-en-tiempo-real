package ConversorDual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatosComboBox {

	List<String> miListaDivisas = new ArrayList<>();
	List<String> miListaTemperaturas = new ArrayList<>();

	Map<String, String> miMapaDivisas;

	public DatosComboBox() {
		// Divisas
		miListaDivisas.add("USD"); // DOLAR AMERICANO
		miListaDivisas.add("EUR"); // EURO
		miListaDivisas.add("PEN"); // NUEVO SOL
		miListaDivisas.add("BOB"); // BOLIVIANO
		miListaDivisas.add("VES"); // BOLIVAR
		miListaDivisas.add("MXN"); // PESO MEXICANO
		miListaDivisas.add("COP"); // PESO COLOMBIANO
		miListaDivisas.add("CLP"); // PESO CHILENO
		miListaDivisas.add("ARS"); // PESO ARGENTINO

		// Esca las de Temperaturas
		miListaTemperaturas.add("CELCIUS");
		miListaTemperaturas.add("KELVIN");
		miListaTemperaturas.add("FAHRENHEIT");

		miMapaDivisas = new HashMap<>();
		miMapaDivisas.put("USD", "Dólar americano");
		miMapaDivisas.put("EUR", "Euro");
		miMapaDivisas.put("PEN", "Nuevo sol");
		miMapaDivisas.put("BOB", "Boliviano");
		miMapaDivisas.put("VES", "Bolívar");
		miMapaDivisas.put("MXN", "Peso mexicano");
		miMapaDivisas.put("COP", "Peso colombiano");
		miMapaDivisas.put("CLP", "Peso chileno");
		miMapaDivisas.put("ARS", "Peso argentino");
	}

}
