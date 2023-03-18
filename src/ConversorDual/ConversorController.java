package ConversorDual;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.text.DecimalFormat;
import java.util.Collections;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextField;

import java.util.Map;
import java.util.Set;

public class ConversorController {

	// Interfaz de DIVISAS ------ INICIO------------
	@FXML
	private ComboBox<String> cbxDivisa1;
	@FXML
	private ComboBox<String> cbxDivisa2;
	@FXML
	private TextField txtDivisas1;
	@FXML
	private TextField txtDivisas2;
	// Interfaz de DIVISAS ------ FIN-----------------

	// Interfaz de TEMPERATURAS ------ INICIO --------
	@FXML
	private ComboBox<String> cbxTemp1;
	@FXML
	private ComboBox<String> cbxTemp2;
	@FXML
	private TextField txtTemp1;
	@FXML
	private TextField txtTemp2;

	// Interfaz de TEMPERATURAS ------ FIN -----------

	// Variables para guardar cambios en texto editado DIVISAS
	private UnaryOperator<TextFormatter.Change> num1DivisaFiltro;
	private UnaryOperator<TextFormatter.Change> num2DivisaFiltro;

	// Variables para guardar cambios en texto editado TEMPERATURA
	private UnaryOperator<TextFormatter.Change> num1TempFiltro;
	private UnaryOperator<TextFormatter.Change> num2TempFiltro;

	// Llamando a la clase API
	API ApiDivisas = new API();

	Map<String, Double> tasaCambio;
	DatosComboBox ListaComboBox = new DatosComboBox();
	
	@FXML
	private void initialize() {
		// Cargando la API y lo Almacenamos en un Map
		tasaCambio = ApiDivisas.ApiMap();

		// Llenamos los combBox de DIVISAS
		Collections.sort(ListaComboBox.miListaDivisas);
		ObservableList<String> OpDivisas1 = FXCollections.observableArrayList(ListaComboBox.miListaDivisas);
		cbxDivisa1.setItems(OpDivisas1);
		cbxDivisa1.setValue("USD");
		ObservableList<String> OpDivisas2 = FXCollections.observableArrayList(ListaComboBox.miListaDivisas);
		cbxDivisa2.setItems(OpDivisas2);
		cbxDivisa2.setValue("PEN");

		// Llenamos los combBox de TEMPERATURAS
		Collections.sort(ListaComboBox.miListaTemperaturas);
		ObservableList<String> OpTemp1 = FXCollections.observableArrayList(ListaComboBox.miListaTemperaturas);
		cbxTemp1.setItems(OpTemp1);
		cbxTemp1.setValue("CELCIUS");
		ObservableList<String> OpTemp2 = FXCollections.observableArrayList(ListaComboBox.miListaTemperaturas);
		cbxTemp2.setItems(OpTemp2);
		cbxTemp2.setValue("KELVIN");
		
		// Crear los filtros para los TextFields de DIVISAS
		num1DivisaFiltro = CrearFiltro("[0-9\\.]*");
		num2DivisaFiltro = CrearFiltro("[0-9\\.]*");

		// Crear los filtros para los textFields de DIVISAS
		num1TempFiltro = CrearFiltro("[0-9\\.-]*");
		num2TempFiltro = CrearFiltro("[0-9\\.-]*");

		// Capturando los datos ingresado en los textField de DIVISAS
		txtDivisas1.setTextFormatter(new TextFormatter<>(num1DivisaFiltro));
		txtDivisas2.setTextFormatter(new TextFormatter<>(num2DivisaFiltro));

		// Capturando los datos ingresado en los textField de DIVISAS
		txtTemp1.setTextFormatter(new TextFormatter<>(num1TempFiltro));
		txtTemp2.setTextFormatter(new TextFormatter<>(num2TempFiltro));

		// Carga el Aplicativo y digitamos algun dato en el textField DIVISAS
		txtDivisas1.textProperty().addListener((observable, oldValue, newValue) -> {
			if (txtDivisas1.isFocused())
				CalculoDivisasUNO();
		});
		txtDivisas2.textProperty().addListener((observable, oldValue, newValue) -> {
			if (txtDivisas2.isFocused())
				CalculoDivisasDOS();
		});

		// Carga el Aplicativo y digitamos algun dato en el textField TEMPERATURAS
		txtTemp1.textProperty().addListener((observable, oldValue, newValue) -> {
			if (txtTemp1.isFocused() && !newValue.equals("-"))
				CalculoTempUNO();
		});
		txtTemp2.textProperty().addListener((observable, oldValue, newValue) -> {
			if (txtTemp2.isFocused() && !newValue.equals("-"))
				CalculoTempDOS();
		});

		// Cuando Seleccionamos un nuevo valor del comboBox1 de DIVISAS
		cbxDivisa1.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				String NuevoValor = txtDivisas1.getText();
				txtDivisas1.setText(NuevoValor);
				CalculoDivisasDOS();
			}
		});

		// Cuando Seleccionamos un nuevo valor del comboBox2 de DIVISAS
		cbxDivisa2.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null) {
				String NuevoValor = txtDivisas2.getText();
				txtDivisas2.setText(NuevoValor);
				CalculoDivisasUNO();
			}
		});
		
		// Cuando Seleccionamos un nuevo valor del comboBox1 de TEMPERATURA
				cbxTemp1.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (newValue != null) {
						String NuevoValor = txtTemp1.getText();
						txtTemp1.setText(NuevoValor);
						CalculoTempDOS();
					}
				});

				// Cuando Seleccionamos un nuevo valor del comboBox2 de TEMPERATURA
				cbxTemp2.valueProperty().addListener((observable, oldValue, newValue) -> {
					if (newValue != null) {
						String NuevoValor = txtTemp2.getText();
						txtTemp2.setText(NuevoValor);
						CalculoTempUNO();
					}
				});
	}

	private void CalculoDivisasUNO() {

		String DatoCBX1 = cbxDivisa1.getValue(); // Capturando el valor actual del combobox1
		String DatoCBX2 = cbxDivisa2.getValue(); // Capturando el valor actual del combobox2

		Set<String> claves = tasaCambio.keySet(); // Capturamos las Claves del Map en un Set
		// System.out.println(DatoCBX1);

		// Comparamos las claves que son tipo Set con el String DatoCBX1
		if (claves.contains(DatoCBX1)) {
			try {
				double numero1 = txtDivisas1.getText().isEmpty() ? 0 : Double.parseDouble(txtDivisas1.getText());
				double MontoConversion = numero1 * tasaCambio.get(DatoCBX2) / tasaCambio.get(DatoCBX1);
				DecimalFormat TresDecimales = new DecimalFormat("#.###"); // Formato para que solo tenga 3 digitos
				double ResultadoFinal = Double.parseDouble(TresDecimales.format(MontoConversion));
				if (MontoConversion == 0) {
					txtDivisas2.setText(""); // establecer el número como entero en el
				} else {
					txtDivisas2.setText(String.valueOf(ResultadoFinal)); // establecer el número como entero en el
				}

			} catch (NumberFormatException e) {
				AlertaError();
				e.printStackTrace();
			}
		}
	}

	private void CalculoDivisasDOS() {
		String DatoCBX1 = cbxDivisa1.getValue(); // Capturando el valor actual del combobox1
		String DatoCBX2 = cbxDivisa2.getValue(); // Capturando el valor actual del combobox2
		Set<String> claves = tasaCambio.keySet(); // Capturamos las Claves del Map en un Set
		// System.out.println(DatoCBX1);

		// Comparamos las claves que son tipo Set con el String DatoCBX1
		if (claves.contains(DatoCBX2)) {
			try {
				double numero2 = txtDivisas2.getText().isEmpty() ? 0 : Double.parseDouble(txtDivisas2.getText());
				double MontoConversion = numero2 * tasaCambio.get(DatoCBX1) / tasaCambio.get(DatoCBX2);
				DecimalFormat TresDecimales = new DecimalFormat("#.###"); // Formato para que solo tenga 3 digitos
				double ResultadoFinal = Double.parseDouble(TresDecimales.format(MontoConversion));
				if (MontoConversion == 0) {
					txtDivisas1.setText(""); // establecer el número como entero en el
				} else {
					txtDivisas1.setText(String.valueOf(ResultadoFinal)); // establecer el número como entero en el
				}

			} catch (NumberFormatException e) {
				AlertaError();
				e.printStackTrace();
			}
		}
	}

	private void CalculoTempUNO() {
		String DatoCBX1 = cbxTemp1.getValue(); // Capturando el valor actual del combobox1
		String DatoCBX2 = cbxTemp2.getValue(); // Capturando el valor actual del combobox2

		try {
			double numero1 = txtTemp1.getText().isEmpty() ? 0 : Double.parseDouble(txtTemp1.getText());

			CalcularTemperatura formulaTemperatura = new CalcularTemperatura(numero1, DatoCBX1, DatoCBX2);
			double Resultado = formulaTemperatura.CalcularTemperatura();
			DecimalFormat TresDecimales = new DecimalFormat("#.###"); // Formato para que solo tenga 3 digitos
			double ResultadoFinal = Double.parseDouble(TresDecimales.format(Resultado));
			if (Resultado == 0) {
				txtTemp2.setText(""); // establecer el número como entero en el
			} else {
				txtTemp2.setText(String.valueOf(ResultadoFinal)); // establecer el número como entero en el
			}
		} catch (NumberFormatException e) {
			AlertaError();
			e.printStackTrace();
		}
	}

	private void CalculoTempDOS() {
		String DatoCBX1 = cbxTemp1.getValue(); // Capturando el valor actual del combobox1
		String DatoCBX2 = cbxTemp2.getValue(); // Capturando el valor actual del combobox2

		try {
			double numero2 = txtTemp2.getText().isEmpty() ? 0 : Double.parseDouble(txtTemp2.getText());
			CalcularTemperatura formulaTemperatura = new CalcularTemperatura(numero2, DatoCBX2, DatoCBX1);
			double Resultado = formulaTemperatura.CalcularTemperatura();
			DecimalFormat TresDecimales = new DecimalFormat("#.###"); // Formato para que solo tenga 3 digitos
			double ResultadoFinal = Double.parseDouble(TresDecimales.format(Resultado));
			if (Resultado == 0) {
				txtTemp1.setText(""); // establecer el número como entero en el
			} else {
				txtTemp1.setText(String.valueOf(ResultadoFinal)); // establecer el número como entero en el
			}
		} catch (NumberFormatException e) {
			AlertaError();
			e.printStackTrace();
		}
	}

	private void AlertaError() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Aviso de Error");
		alert.setHeaderText(null);
		alert.setContentText("Verifique si ingreso correctamente el numero");
		alert.showAndWait();
	}

	// Creando el Filtro al ingresar datos al textField
	private UnaryOperator<TextFormatter.Change> CrearFiltro(String Captura) {
		Pattern pattern = Pattern.compile(Captura);
		return change -> pattern.matcher(change.getControlNewText()).matches() ? change : null;
	}

}