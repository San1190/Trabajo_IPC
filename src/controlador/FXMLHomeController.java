/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.User;
import objetos.alerta;

/**
 * FXML Controller class
 *
 * @author sanfu
 */
public class FXMLHomeController implements Initializable {

    @FXML
    private Text nombre_usuario;
    @FXML
    private ImageView imagen_usuario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            Acount account = Acount.getInstance();
            User user = account.getLoggedUser();
            nombre_usuario.setText(user.getName());
            imagen_usuario.setImage(user.getImage());
        } catch (AcountDAOException ex) {
            alerta.mostrarAlerta("Error", "Error obteniendo los datos de la base de datos", Alert.AlertType.ERROR, null);
        } catch (IOException e) {
            alerta.mostrarAlerta("Error", "Error obteniendo los datos de la base de datos", Alert.AlertType.ERROR, null);
        }
      

    }    

    @FXML
    private void añadir_gasto(ActionEvent event) {
        // Abrir la ventana emergente de añadir gasto
        try {
            // Cargar el archivo FXML de la ventana emergente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLCreateExpense.fxml"));
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
    private void añadir_categoria(ActionEvent event) {
        // Cargar la ventana de crear categoria
        try {
            // Cargar el archivo FXML de la ventana emergente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLCreateCategory.fxml"));
            Parent root = loader.load();            
            
            // Crear la ventana emergente
            Scene scene = new Scene(root);
            
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Inicio de Sesión"); // Establecer el título de la ventana
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquear otras ventanas mientras esta está abierta
            stage.showAndWait(); // Mostrar la ventana y esperar hasta que se cierre

        } catch (IOException e) {
            alerta.mostrarAlerta("Error", "Error al cargar la ventana de inicio de sesión", Alert.AlertType.ERROR, null);
        }
    }
    
}
