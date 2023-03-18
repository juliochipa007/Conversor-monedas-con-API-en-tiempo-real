 package ConversorDual;

import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextField;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ConversorController {
	@FXML
	private ComboBox<String> cbxDivisa1;
	@FXML
	private ComboBox<String> cbxDivisa2;
	@FXML
	private TextField txtDivisas1;
	@FXML
	private TextField txtDivisas2;

	// Variables para guardar los listeners
	private UnaryOperator<TextFormatter.Change> num1Filtro;
	private UnaryOperator<TextFormatter.Change> num2Filtro;
	private javafx.beans.value.ChangeListener<String> num1Listener;
	private javafx.beans.value.ChangeListener<String> num2Listener;

	// Llamando a la clase API
	API ApiDivisas = new API();
	
	Map<String, Double> tasaCambio;
	
	double PrecioDolar = ApiDivisas.ApiMap().get("USD");

	DatosComboBox divisasLista = new DatosComboBox();

	@FXML
	private void initialize() {

		// System.out.println("Prueba para saber si entra a inialize");
		// Cargando la API y lo Almacenamos en Map para que no este consultando cada
		// digito ingresado
		
		tasaCambio = ApiDivisas.ApiMap();
		
		
		Collections.sort(divisasLista.miLista);
		ObservableList<String> items1 = FXCollections.observableArrayList(divisasLista.miLista);
		cbxDivisa1.setItems(items1);
		cbxDivisa1.setValue("PEN");

		ObservableList<String> items2 = FXCollections.observableArrayList(divisasLista.miLista);
		cbxDivisa2.setItems(items2);
		cbxDivisa2.setValue("USD");

		// Crear los listeners para los TextFields
		num1Filtro = CrearFiltro("[0-9\\.]*");
		num2Filtro = CrearFiltro("[0-9\\.]*");
		num1Listener = (observable, oldValue, newValue) -> CalculoDivisasUNO();
		num2Listener = (observable, oldValue, newValue) -> CalculoDivisasDOS();

		txtDivisas1.setTextFormatter(new TextFormatter<>(num1Filtro));
		txtDivisas2.setTextFormatter(new TextFormatter<>(num2Filtro));

		txtDivisas1.textProperty().addListener((observable, oldValue, newValue) -> {
		
			if (txtDivisas1.isFocused()) {
		        CalculoDivisasUNO();
		    }
			
		});

		txtDivisas2.textProperty().addListener((observable, oldValue, newValue) -> {
			if (txtDivisas2.isFocused()) {
		        CalculoDivisasDOS();
		    }
		});

		// Cuando Seleccionamos un ComboBox
	}

	private void CalculoDivisasUNO() {
		String DatoCBX1 = cbxDivisa1.getValue(); // Capturando el valor actual del combobox
		
		Set<String> claves = tasaCambio.keySet(); // Capturamos las Claves del Map en un Set
		//System.out.println(DatoCBX1);
		
		// Comparamos las claves que son tipo Set con el String DatoCBX1
		if (claves.contains(DatoCBX1)) {
			try {
				double numero1 = txtDivisas1.getText().isEmpty() ? 0 : Double.parseDouble(txtDivisas1.getText());
				int numero1Entero = (int) numero1;
				if (numero1 == numero1Entero) { // si el número es entero

					double MontoConversion = numero1Entero * tasaCambio.get(DatoCBX1) / PrecioDolar ;
					txtDivisas2.setText(String.valueOf(MontoConversion)); // establecer el número como entero en el otro
																	
				} else {
					double MontoConversion = numero1 * tasaCambio.get(DatoCBX1) / PrecioDolar ;
					txtDivisas2.setText(String.valueOf(MontoConversion)); // establecer el número como entero en el otro
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}

	private void CalculoDivisasDOS() {
		String DatoCBX2 = cbxDivisa2.getValue(); // Capturando el valor actual del combobox
			
		Set<String> claves = tasaCambio.keySet(); // Capturamos las Claves del Map en un Set
		//System.out.println(DatoCBX1);
		
		// Comparamos las claves que son tipo Set con el String DatoCBX1
		if (claves.contains(DatoCBX2)) {
			try {
				double numero2 = txtDivisas2.getText().isEmpty() ? 0 : Double.parseDouble(txtDivisas2.getText());
				int numero2Entero = (int) numero2;
				if (numero2 == numero2Entero) { // si el número es entero

					double MontoConversion = numero2Entero * tasaCambio.get(DatoCBX2) / PrecioDolar ;
					txtDivisas1.setText(String.valueOf(MontoConversion)); // establecer el número como entero en el otro
																	
				} else {
					double MontoConversion = numero2 * tasaCambio.get(DatoCBX2) / PrecioDolar ;
					txtDivisas1.setText(String.valueOf(MontoConversion)); // establecer el número como entero en el otro
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
	}

	// Creando el Filtro al ingresar datos al textField
	private UnaryOperator<TextFormatter.Change> CrearFiltro(String Captura) {
		Pattern pattern = Pattern.compile(Captura);
		return change -> pattern.matcher(change.getControlNewText()).matches() ? change : null;
	}

}