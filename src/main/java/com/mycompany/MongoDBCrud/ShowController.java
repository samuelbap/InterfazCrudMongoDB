package com.mycompany.MongoDBCrud;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ShowController implements Initializable {

	private int contRegistros;
	@FXML private Label txtEmpNo, txtApellido, txtOficio, txtDir, txtFechaAlt, txtSalario, txtDeptNo, txtDNombre, txtLoc;
	@FXML private Button btnBack, btnModify, btnAdd, btnNext, btnPrevious;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Button[] menu = new Button[] {btnAdd, btnModify};
		Label[] contenido = new Label[] {txtEmpNo, txtApellido, txtOficio, txtDir, txtFechaAlt, txtSalario, txtDeptNo, txtDNombre, txtLoc};
		
		contRegistros = 0;
		mostrar(contenido, contRegistros);
		
		// Establece imagen para nbotones de siguiente y anterior
		btnNext.setGraphic(new ImageView(getClass().getResource("img/next.png").toExternalForm()));
		btnPrevious.setGraphic(new ImageView(getClass().getResource("img/previous.png").toExternalForm()));
		
		// Establece icono de volver a inicio
		//btnBack.setGraphic(new ImageView(getClass().getResource("img/casa.png").toExternalForm()));
		
		// LISTENERS MOUSEENTERED & MOUSEEXITED MENU
		for(Button b:menu) {
			b.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					b.setStyle("-fx-background-color : #5bd560");
				}
	    		
	    	});
	    	
	    	b.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					b.setStyle("-fx-background-color :  #5ba760");
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
			
		});*/
		
		// LISTENER CLICK SHOW
		btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				try {
					App.setRoot("add");
				} catch (IOException e) {
					// TODO Auto-generated catch block
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
		
		
		
		// LISTENERS MOUSEENTERED & MOUSEEXITED NAV
		Button[] btnNav = new Button[] {btnNext, btnPrevious};
		
		for(Button b: btnNav) {
			b.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					b.setStyle("-fx-background-color : #b0b0b0cd; "
							+ "-fx-background-radius: 10");
				}
				    		
			});
			
			b.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent arg0) {
					b.setStyle("-fx-background-color : TRANSPARENT");
				}
				    		
			});
		}
		
		// LISTENER CLICK NEXT
		btnNext.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				
				MongoClient client = new MongoClient();
				MongoDatabase db = client.getDatabase("empleadosDepartamentos");
				MongoCollection<Document> collection = db.getCollection("empleados");
				
				if(contRegistros < collection.count() -1) {
					contRegistros++;
					mostrar(contenido, contRegistros);
				}
			}
		});
		
		// LISTENER CLICK PREVIOUS
				btnPrevious.setOnMouseClicked(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent arg0) {
						
						MongoClient client = new MongoClient();
						MongoDatabase db = client.getDatabase("empleadosDepartamentos");
						MongoCollection<Document> collection = db.getCollection("empleados");
						
						if(contRegistros > 0) {
							contRegistros--;
							mostrar(contenido, contRegistros);
						}
					}
				});
	}

	
	// METODO MOSTRAR REGISTROS MONGODB
	private void mostrar(Label[] l, int cont) {
		
		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("empleadosDepartamentos");
		MongoCollection<Document> collection = db.getCollection("empleados");
		List<Document> consulta = collection.find().into(new ArrayList<Document>());
		
		l[0].setText("NUMERO: "+ String.valueOf(consulta.get(cont).get("emp_no")));
		l[1].setText("APELLIDO: "+ String.valueOf(consulta.get(cont).get("apellido")));
		l[2].setText("OFICIO: "+ String.valueOf(consulta.get(cont).get("oficio")));
		l[3].setText("DIRECTOR: "+ String.valueOf(consulta.get(cont).get("dir")));
		l[4].setText("FECHA DE ALTA: "+ String.valueOf(consulta.get(cont).get("fecha_alt")));
		l[5].setText("SALARIO: "+ String.valueOf(consulta.get(cont).get("salario")));
		l[6].setText("NUMERO: "+ String.valueOf(consulta.get(cont).get("dept_no")));
		l[7].setText("NOMBRE: "+ String.valueOf(consulta.get(cont).get("dnombre")));
		l[8].setText("LOCALIDAD: "+ String.valueOf(consulta.get(cont).get("loc")));
		
		if(contRegistros > 0) {
			btnPrevious.setVisible(true);
		} else {
			btnPrevious.setVisible(false);
		}
		
		if(contRegistros < collection.count() -1) {
			btnNext.setVisible(true);
		} else {
			btnNext.setVisible(false);
		}
	}
}
