package controlador;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLMainController {

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;

        
    }

    @FXML
    private void inicioSesionBotonS(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana emergente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLLogin.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del login y pasarle el Stage principal
            FXMLLoginController loginController = loader.getController();
            loginController.setMainStage(primaryStage);

            // Crear una nueva escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Inicio de Sesión"); // Establecer el título de la ventana
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquear otras ventanas mientras esta está abierta
            stage.showAndWait(); // Mostrar la ventana y esperar hasta que se cierre

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void RegistroBoton(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana emergente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLRegister.fxml"));
            Parent root = loader.load();

            // Crear una nueva escena
            Scene scene = new Scene(root);

            // Crear un nuevo escenario (ventana)
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Registro"); // Establecer el título de la ventana
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquear otras ventanas mientras esta está abierta
            stage.showAndWait(); // Mostrar la ventana y esperar hasta que se cierre

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
