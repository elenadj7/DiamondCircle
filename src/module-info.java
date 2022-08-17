module ProjekatPJ2 {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.desktop;
	requires java.logging;
	
	opens application to javafx.graphics, javafx.fxml;
}
