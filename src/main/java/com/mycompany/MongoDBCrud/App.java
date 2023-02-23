package com.mycompany.MongoDBCrud;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
    	
    	//stage.setFullScreen(true);
        scene = new Scene(loadFXML("show"), 750, 800);
        stage.setScene(scene);
        
        stage.show();
    }
    
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    
    // Añadir registros a MongoDb
    public static void addRegistros(TextField[] textos) {
    	MongoClient cliente = new MongoClient();
    	MongoDatabase db = cliente.getDatabase("empleadosDepartamentos");
    	MongoCollection<Document> collection = db.getCollection("empleados");
    	
    	Document doc = new Document();
    	doc.append("emp_no", Integer.valueOf(textos[0].getText().toString()));
    	doc.append("apellido", textos[1].getText().toString());
    	doc.append("oficio", textos[2].getText().toString());
    	doc.append("dir", textos[3].getText().toString());
    	doc.append("fecha_alt", textos[4].getText().toString());
    	doc.append("salario", Integer.valueOf(textos[5].getText().toString()));
    	doc.append("dept_no", Integer.valueOf(textos[6].getText().toString()));
    	doc.append("dnombre", textos[7].getText().toString());
    	doc.append("loc", textos[8].getText().toString());
    	
    	collection.insertOne(doc);
    	
    	
    }
    
    
    /* Enviar número de registros en MongoDb
    public static int recibirNumRegistros() {
    	
    	MongoClient cliente = new MongoClient();
    	MongoDatabase db = cliente.getDatabase("mibasededatos");
    	MongoCollection<Document> collection = db.getCollection("empleados");
    	
    	return ((int) collection.count());
    }*/
    
}

















