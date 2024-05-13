/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sanfu
 */
public class FXMLMainController implements Initializable {


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        

    }



    @FXML
    private void inicioSesionBotonS(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana emergente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLLogin.fxml"));
            Parent root = loader.load();

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


