module ConversorDual {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.json;
	
	opens ConversorDual to javafx.graphics, javafx.fxml;
}
