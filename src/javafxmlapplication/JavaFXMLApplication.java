package javafxmlapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controlador.FXMLMainController;

public class JavaFXMLApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //======================================================================
        // 1- creación del grafo de escena a partir del fichero FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLMain.fxml"));
        Parent root = loader.load();

        // Obtener el controlador y pasar el stage principal
        FXMLMainController mainController = loader.getController();
        mainController.setPrimaryStage(stage);

        //======================================================================
        // 2- creación de la escena con el nodo raiz del grafo de escena
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/estilos/estilos.css").toExternalForm();
        String botonescss = this.getClass().getResource("/estilos/estilos-botones.css").toExternalForm();
        scene.getStylesheets().add(css);
        scene.getStylesheets().add(botonescss);

        //======================================================================
        // 3- asiganación de la escena al Stage que recibe el metodo 
        //     - configuracion del stage
        //     - se muestra el stage de manera no modal mediante el metodo show()
        stage.setScene(scene);
        stage.setTitle("start PROJECT - IPC:");
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
