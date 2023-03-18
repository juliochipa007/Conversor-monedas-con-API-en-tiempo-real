module ConversorDual {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.json;
	requires javafx.graphics;
	
	opens ConversorDual to javafx.graphics, javafx.fxml;
}
