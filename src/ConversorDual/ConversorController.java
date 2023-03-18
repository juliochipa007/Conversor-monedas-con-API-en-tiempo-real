package ConversorDual;

import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextField;

public class ConversorController {
	@FXML
	private ComboBox<String> cbxDivisa1;
	@FXML
	private ComboBox<String> cbxDivisa2;

	List<String> divisas = Arrays.asList("DOLAR", "EURO", "SOL", "BOLIVIANO");

	@FXML
	private TextField txtDivisas1;
	@FXML
	private TextField txtDivisas2;

	@FXML
	private void initialize() {
		System.out.println("Prueba para saber si entra a inialize");
		ObservableList<String> items1 = FXCollections.observableArrayList(divisas);
		cbxDivisa1.setItems(items1);
		cbxDivisa1.setValue("DOLAR");

		ObservableList<String> items2 = FXCollections.observableArrayList(divisas);
		cbxDivisa2.setItems(items2);
		cbxDivisa2.setValue("SOL");
		
		UnaryOperator<TextFormatter.Change> filtro = CrearFiltro("[0-9\\.]*"); // solo permitirá expresión regular proporcionada ([0-9\\.]*)
	
		txtDivisas1.setTextFormatter(new TextFormatter<>(filtro));
		txtDivisas2.setTextFormatter(new TextFormatter<>(filtro));

		txtDivisas1.textProperty().addListener((observable, oldValue, newValue) -> {
			ResultadoDivisas2();
		});

		txtDivisas2.textProperty().addListener((observable, oldValue, newValue) -> {
			ResultadoDivisas1();
		});
	}

	private void ResultadoDivisas2() {
		try {
	        double numero1 = txtDivisas1.getText().isEmpty() ? 0 : Double.parseDouble(txtDivisas1.getText());
	        int numero1Entero = (int) numero1;
	        if (numero1 == numero1Entero) { // si es número es entero
	            txtDivisas2.setText(String.valueOf(numero1Entero)); // establecer el número como entero en el otro TextField
	        } else {
	            txtDivisas2.setText(String.valueOf(numero1)); // establecer el número como double en el otro TextField
	        }
	    } catch (NumberFormatException e) {
	    	e.printStackTrace();
	    }
	}

	private void ResultadoDivisas1() {
		try {
	        double numero2 = txtDivisas2.getText().isEmpty() ? 0 : Double.parseDouble(txtDivisas2.getText());
	        int numero2Entero = (int) numero2;
	        if (numero2 == numero2Entero) { // si el número es entero
	            txtDivisas2.setText(String.valueOf(numero2Entero)); // establecer el número como entero en el otro TextField
	        } else {
	            txtDivisas2.setText(String.valueOf(numero2)); // establecer el número como double en el otro TextField
	        }
	    } catch (NumberFormatException e) {
	       e.printStackTrace();
	    }
	}

	private UnaryOperator<TextFormatter.Change> CrearFiltro(String capturado) {
		return change -> {
			String text = change.getControlNewText();
			if (Pattern.matches(capturado, text)) {
				return change;
			} else {
				return null;
			}
		};
	}

}
