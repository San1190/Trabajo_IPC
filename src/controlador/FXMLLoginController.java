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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import objetos.alerta;


/**
 * FXML Controller class
 *
 * @author san
 */
public class FXMLLoginController implements Initializable {
    private Stage stage;
    @FXML
    private TextField usuario_entrada;
    @FXML
    private PasswordField contraseña_entrada;




    @FXML
    private Parent root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //hacer que la ventana sea no redimensionable
        if (stage == null) {
            try {
                stage = (Stage) contraseña_entrada.getScene().getWindow();
            } catch (Exception e) {
                System.out.println("Error al intentar obtener la ventana");
            }
        }
        try {
            stage.setResizable(false);
        } catch (Exception e) {
            System.out.println("Error al intentar hacer la ventana no redimensionable");
        }

        
    }

    @FXML
    private void presionadoAceptar(ActionEvent event) throws IOException, AcountDAOException {
        // Validar los datos del usuario y abrir la ventana de home
        String usuario = usuario_entrada.getText();
        String contraseña = contraseña_entrada.getText();

        if (usuario.isEmpty() || contraseña.isEmpty()) {
            alerta.mostrarAlerta("Error", "Usuario o contraseña vacios", AlertType.ERROR, null);
            return;
        }
        
        Acount account = Acount.getInstance();

        if (account.logInUserByCredentials(usuario, contraseña)) {
            // Cambiar la ventana principal a la ventana de home ya logeado :)
            stage = (Stage) usuario_entrada.getScene().getWindow();

            // Cargar el nuevo archivo FXML
            Parent homeRoot = FXMLLoader.load(getClass().getResource("/vista/FXMLHome.fxml"));
            Scene homeScene = new Scene(homeRoot);
            
            // Establecer la nueva escena en el stage
            stage.setScene(homeScene);
            stage.show();

        } else {
            alerta.mostrarAlerta("Error", "Usuario o contraseña incorrectos", AlertType.ERROR, null);
        }
    }       

    @FXML
    private void presionadoCancelar(ActionEvent event) {
        //Volver a la ventana principal y cerrar la ventana de login
        if (stage == null) {
            stage = (Stage) usuario_entrada.getScene().getWindow();
        }
        stage.close();
    }
    
}