module com.mycompany.MongoDBCrud {
    requires javafx.controls;
    requires javafx.fxml;
	requires mongo.java.driver;
	requires javafx.graphics;
	requires javafx.base;

    opens com.mycompany.MongoDBCrud to javafx.fxml;
    exports com.mycompany.MongoDBCrud;
}
