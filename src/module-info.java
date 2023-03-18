module ConversorDual {
	requires javafx.controls;
	requires javafx.fxml;
	
	opens ConversorDual to javafx.graphics, javafx.fxml;
}
