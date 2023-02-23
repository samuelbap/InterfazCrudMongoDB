package com.mycompany.MongoDBCrud;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Shadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AddController implements Initializable {
	
	@FXML private TextField txtEmpNo, txtApellido, txtOficio, txtDir, txtFechaAlt, txtSalario, txtDeptNo, txtDNombre, txtLoc;
	@FXML private Button btnBack, btnModify, btnShow, btnAccept, btnCancel;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Button[] menu = new Button[] {btnShow, btnModify};
		TextField[] contenido = new TextField[] {txtEmpNo, txtApellido, txtOficio, txtDir, txtFechaAlt, txtSalario, txtDeptNo, txtDNombre, txtLoc};
	

		
		// Establece icono de volver a inicio
		//btnBack.setGraphic(new ImageView(getClass().getResource("img/casa.png").toExternalForm()));
		
		// LISTENERS MOUSEENTERED & MOUSEEXITED MENU
		for(Button b:menu) {
			b.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					b.setStyle("-fx-background-color : #5bc582");
				}
	    		
	    	});
	    	
	    	b.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					b.setStyle("-fx-background-color : #5ba760");
				}
	    		
	    	});
		}
		
		// LISTENER CLICK BACK
		/*btnBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				try {
					App.setRoot("menu");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});
		*/
		
		// LISTENER CLICK SHOW
		btnShow.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				try {
					App.setRoot("show");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		});
		
		// LISTENER CLICK MODIFY
		btnModify.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				try {
					App.setRoot("modify");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		
		// LISTENER CLICK CANCEL
		btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				for(TextField t: contenido) {
					t.setText("");
				}
			}
		});
		
		// LISTENER CLICK ACCEPT
		btnAccept.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				App.addRegistros(contenido);
				
				for(TextField t: contenido) {
					t.setText("");
				}
			}
		});
	}


	
	// LISTENER MOUSEOVER & MOUSEOUT ACCEPT
	@FXML private void btnAcceptOver() {
		btnAccept.setFont(Font.font("System",FontWeight.BOLD, 17));
		btnAccept.setStyle("-fx-background-color:  #81caff");
	}

	@FXML private void btnAcceptOut() {
		btnAccept.setFont(Font.font("System",FontWeight.BOLD, 15));
		btnAccept.setStyle("-fx-background-color: #5bc582");
	}

	// LISTENER MOUSEOVER & MOUSEOUT CANCEL
	@FXML private void btnCancelOver() {
		btnCancel.setFont(Font.font("System",FontWeight.BOLD, 17));
		btnCancel.setStyle("-fx-background-color: #b90f0f");
	}
	
	@FXML private void btnCancelOut() {
		btnCancel.setFont(Font.font("System",FontWeight.BOLD, 15));
		btnCancel.setStyle("-fx-background-color: #da1e1e");
	}
}
