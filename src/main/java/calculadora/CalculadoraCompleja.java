package calculadora;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraCompleja extends Application {

	private Label signoOpera1,signoOpera2,signoOpera3,i1Label,i2Label,i3Label;
	private TextField primerNumText, segundoNumText,tercerNumText,cuartoNumText,result1Text,result2Text;
	private ComboBox<String> operacionCombo;
	
	private DoubleProperty primerOperando = new SimpleDoubleProperty();
	private DoubleProperty segundoOperando = new SimpleDoubleProperty();
	private DoubleProperty tercerOperando = new SimpleDoubleProperty();
	private DoubleProperty cuartoOperando = new SimpleDoubleProperty();
	private DoubleProperty primerResultado = new SimpleDoubleProperty();
	private DoubleProperty segundoResultado = new SimpleDoubleProperty();
	
	private StringProperty operador = new SimpleStringProperty();

	@Override
	public void start(Stage primaryStage) throws Exception {

		
		signoOpera1= new Label("+");
		signoOpera2= new Label("+");
		signoOpera3= new Label("+");
		i1Label= new Label("i");
		i2Label= new Label("i");
		i3Label= new Label("i");

		primerNumText = new TextField("0");
		primerNumText.setMaxWidth(50);
		primerNumText.setAlignment(Pos.CENTER);

		segundoNumText = new TextField("0");
		segundoNumText.setMaxWidth(50);
		segundoNumText.setAlignment(Pos.CENTER);
		
		tercerNumText= new TextField("0");
		tercerNumText.setMaxWidth(50);
		tercerNumText.setAlignment(Pos.CENTER);
		
		cuartoNumText= new TextField("0");
		cuartoNumText.setMaxWidth(50);
		cuartoNumText.setAlignment(Pos.CENTER);

		result1Text = new TextField("0");
		result1Text.setMaxWidth(50);
		result1Text.setEditable(false);
		result1Text.setOpacity(0.5);
		result1Text.setAlignment(Pos.CENTER);
		
		result2Text = new TextField("0");
		result2Text.setMaxWidth(50);
		result2Text.setEditable(false);
		result2Text.setOpacity(0.5);
		result2Text.setAlignment(Pos.CENTER);

		operacionCombo = new ComboBox<String>();
		operacionCombo.getItems().addAll("+","-","*","/");
		operacionCombo.getSelectionModel().select(1);
		
		HBox operacion1 = new HBox(5, primerNumText,signoOpera1,segundoNumText,i1Label);
		
		HBox operacion2 = new HBox(5, tercerNumText,signoOpera2,cuartoNumText,i2Label);
		
		Separator separator = new Separator();
		separator.setMaxWidth(120);
		
		HBox resultado = new HBox(5,result1Text,signoOpera3,result2Text,i3Label);
		
		VBox selectOpera = new VBox(5,operacionCombo);
		selectOpera.setAlignment(Pos.CENTER);
		
		VBox operacionPadre = new VBox(5, operacion1,operacion2,separator,resultado);
		operacionPadre.setAlignment(Pos.CENTER);
		
		
		HBox root  = new HBox(5,selectOpera,operacionPadre);
		root.setAlignment(Pos.CENTER);
		Scene scene = new Scene(root, 320, 250);
		
		//bindeos
		
		Bindings.bindBidirectional(primerNumText.textProperty(), primerOperando, new NumberStringConverter());
		Bindings.bindBidirectional(segundoNumText.textProperty(), segundoOperando, new NumberStringConverter());
		Bindings.bindBidirectional(tercerNumText.textProperty(), tercerOperando, new NumberStringConverter());
		Bindings.bindBidirectional(cuartoNumText.textProperty(), cuartoOperando, new NumberStringConverter());
		Bindings.bindBidirectional(result1Text.textProperty(), primerResultado, new NumberStringConverter());
		Bindings.bindBidirectional(result2Text.textProperty(), segundoResultado, new NumberStringConverter());

		operador.bind(operacionCombo.getSelectionModel().selectedItemProperty());
		
		// listeners

				operador.addListener((o, ov, nv) -> onOperadorChanged(nv));

				operacionCombo.getSelectionModel().selectFirst();
				
		//scene		

		primaryStage.setTitle("Calculadora Avanzada");
		primaryStage.setScene(scene);
		primaryStage.show();


	}

	private void onOperadorChanged(String nv) {

		switch (nv) {
		case "+":
			primerResultado.bind(primerOperando.add(tercerOperando));
			segundoResultado.bind(segundoOperando.add(cuartoOperando));
			break;
		case "-":
			primerResultado.bind(primerOperando.subtract(tercerOperando));
			segundoResultado.bind(segundoOperando.subtract(cuartoOperando));
			break;
		case "*":
			primerResultado.bind(primerOperando.multiply(tercerOperando).subtract(segundoOperando.multiply(cuartoOperando)));
			segundoResultado.bind(primerOperando.multiply(cuartoOperando).add(segundoOperando.multiply(tercerOperando)));
			
			break;
		case "/":
			primerResultado.bind((primerOperando.multiply(tercerOperando).add(segundoOperando.multiply(cuartoOperando))
					.divide(tercerOperando.multiply(tercerOperando).add(cuartoOperando.multiply(cuartoOperando)))));
			segundoResultado.bind((segundoOperando.multiply(tercerOperando).subtract(primerOperando.multiply(cuartoOperando))
					.divide(tercerOperando.multiply(tercerOperando).add(cuartoOperando.multiply(cuartoOperando)))));
			
			break;
		}

	}

	public static void main(String[] args) {
		launch(args);

	}

}
