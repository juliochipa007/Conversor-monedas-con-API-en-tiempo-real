package ConversorDual;

import java.util.ArrayList;
import java.util.List;

public class DatosComboBox {

	List<String> miListaDivisas = new ArrayList<>();
	List<String> miListaTemperaturas = new ArrayList<>();

	public DatosComboBox() {
		//Divisas
		miListaDivisas.add("USD"); // DOLAR AMERICANO
		miListaDivisas.add("EUR"); // EURO
		miListaDivisas.add("PEN"); // NUEVO SOL
		miListaDivisas.add("BOB"); // BOLIVIANO
		miListaDivisas.add("VES"); // BOLIVAR
		miListaDivisas.add("MXN"); // PESO MEXICANO
		miListaDivisas.add("COP"); // PESO COLOMBIANO
		miListaDivisas.add("CLP"); // PESO CHILENO
		miListaDivisas.add("ARS"); // PESO ARGENTINO
		
		//Esca las de Temperaturas
		miListaTemperaturas.add("CELCIUS");
		miListaTemperaturas.add("KELVIN");
		miListaTemperaturas.add("FAHRENHEIT");
	}
}
