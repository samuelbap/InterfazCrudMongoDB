package com.mycompany.MongoDBCrud;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.bson.BSON;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ModifyController implements Initializable {

	private static ObjectId id;
	private int contRegistros;
	@FXML private TextField txtEmpNo, txtApellido, txtOficio, txtDir, txtFechaAlt, txtSalario, txtDeptNo, txtDNombre, txtLoc;
	@FXML private Button btnBack, btnAdd, btnShow, btnNext, btnPrevious, btnDelete, btnModify, btnCancel;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
		
		Button[] menu = new Button[] {btnShow, btnAdd};
		TextField[] contenido = new TextField[] {txtEmpNo, txtApellido, txtOficio, txtDir, txtFechaAlt, txtSalario, txtDeptNo, txtDNombre, txtLoc};
		
		// Crea un nuevo Scene con un AnchorPane como raíz
		AnchorPane root = new AnchorPane();
		Scene scene = new Scene(root, 800, 600);

		// Cambia el tamaño preferido de la escena
		//scene.setPrefSize(800, 600);
		
		// Establece imagen para nbotones de siguiente y anterior
		btnNext.setGraphic(new ImageView(getClass().getResource("img/next.png").toExternalForm()));
		btnPrevious.setGraphic(new ImageView(getClass().getResource("img/previous.png").toExternalForm()));
		
		// Establece icono de volver a inicio
		//btnBack.setGraphic(new ImageView(getClass().getResource("img/casa.png").toExternalForm()));
		
		contRegistros = 0;
		mostrar(contenido, contRegistros);
		
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
					b.setStyle("-fx-background-color :  #5ba760");
				}
	    		
	    	});
		}
		
		// LISTENER CLICK BACK
		/* btnBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
		btnAdd.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				try {
					App.setRoot("add");
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
		
		
		// LISTENER CLICK DELETE
		btnDelete.setOnMouseClicked(new EventHandler<MouseEvent> () {
			@Override
			public void handle(MouseEvent arg0) {
				
				MongoClient client = new MongoClient();
				MongoDatabase db = client.getDatabase("empleadosDepartamentos");
				MongoCollection<Document> collection = db.getCollection("empleados");
				
				collection.deleteMany(Filters.eq("_id",id));

				contRegistros = 0;
				mostrar(contenido, contRegistros);
			}
			
		}); 
		
		// LISTENER CLICK CANCEL
		btnCancel.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				for(TextField t: contenido) {
					id = null;
					contRegistros = 0;
					mostrar(contenido, contRegistros);
				}
			}
			
		});
		
		
		// LISTNER CLICK MODIFY
		btnModify.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				
				MongoClient client = new MongoClient();
				MongoDatabase db = client.getDatabase("empleadosDepartamentos");
				MongoCollection<Document> collection = db.getCollection("empleados");
				
				collection.updateMany(Filters.eq("_id", id), new Document("$set", new Document("emp_no", txtEmpNo.getText())));
				collection.updateMany(Filters.eq("_id", id), new Document("$set", new Document("apellido", txtApellido.getText().toString())));
				collection.updateMany(Filters.eq("_id", id), new Document("$set", new Document("oficio", txtOficio.getText().toString())));
				collection.updateMany(Filters.eq("_id", id), new Document("$set", new Document("dir", txtDir.getText())));
				collection.updateMany(Filters.eq("_id", id), new Document("$set", new Document("fecha_alt", txtFechaAlt.getText().toString())));
				collection.updateMany(Filters.eq("_id", id), new Document("$set", new Document("salario", txtSalario.getText())));
				collection.updateMany(Filters.eq("_id", id), new Document("$set", new Document("dept_no", txtDeptNo.getText())));
				collection.updateMany(Filters.eq("_id", id), new Document("$set", new Document("dnombre", txtDNombre.getText().toString())));
				collection.updateMany(Filters.eq("_id", id), new Document("$set", new Document("loc", txtLoc.getText().toString())));
				
				contRegistros = 0;
				mostrar(contenido, contRegistros);
			}
		});
		
	}
	
	
	// LISTENER MOUSEENTERED & MOUSEEXITED DELETE
	@FXML private void btnDeleteEntered() {
		btnDelete.setFont(Font.font("System",FontWeight.BOLD, 17));
		btnDelete.setStyle("-fx-background-color: #b90f0f");
	}
	
	@FXML private void btnDeleteExited() {
		btnDelete.setFont(Font.font("System",FontWeight.BOLD, 15));
		btnDelete.setStyle("-fx-background-color: #da1e1e");
	}
	
	
	// LISTENER MOUSEENTERED & MOUSEEXITED CANCEL
	@FXML private void btnCancelEntered() {
		btnCancel.setFont(Font.font("System",FontWeight.BOLD, 17));
		btnCancel.setStyle("-fx-background-color: #a1a1a1");
	}
		
	@FXML private void btnCancelExited() {
		btnCancel.setFont(Font.font("System",FontWeight.BOLD, 15));
		btnCancel.setStyle("-fx-background-color: #d0d0d0");
	}
	
	
	// LISTENER MOUSEENTERED & MOUSEEXITED MODIFY
	@FXML private void btnModifyEntered() {
		btnModify.setFont(Font.font("System",FontWeight.BOLD, 17));
		btnModify.setStyle("-fx-background-color: #5bc582");
	}
		
	@FXML private void btnModifyExited() {
		btnModify.setFont(Font.font("System",FontWeight.BOLD, 15));
		btnModify.setStyle("-fx-background-color:  # #5ba760");
	}
	
	
	// METODO MOSTRAR REGISTROS MONGODB
	private void mostrar(TextField[] l, int cont) {
		
		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("empleadosDepartamentos");
		MongoCollection<Document> collection = db.getCollection("empleados");
		List<Document> consulta = collection.find().into(new ArrayList<Document>());
		
		id = consulta.get(cont).getObjectId("_id");
		
		l[0].setText(String.valueOf(consulta.get(cont).get("emp_no")));
		l[1].setText(String.valueOf(consulta.get(cont).get("apellido")));
		l[2].setText(String.valueOf(consulta.get(cont).get("oficio")));
		l[3].setText(String.valueOf(consulta.get(cont).get("dir")));
		l[4].setText(String.valueOf(consulta.get(cont).get("fecha_alt")));
		l[5].setText(String.valueOf(consulta.get(cont).get("salario")));
		l[6].setText(String.valueOf(consulta.get(cont).get("dept_no")));
		l[7].setText(String.valueOf(consulta.get(cont).get("dnombre")));
		l[8].setText(String.valueOf(consulta.get(cont).get("loc")));
		
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
