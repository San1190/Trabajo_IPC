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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import static objetos.alerta.mostrarAlerta;

/**
 * FXML Controller class
 *
 * @author andre
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private TextField usuario_entrada;
    @FXML
    private PasswordField contraseña_entrada;
    @FXML
    private Pane panel_central;
    @FXML
    private Text texto_ficheroSeleccionado;
    @FXML
    private Button iniciar_sesion_boton;
    @FXML
    private Button borrar_boton;




    @Override
    public void initialize(URL url, ResourceBundle rb) {

        usuario_entrada.textProperty().addListener((observable, oldValue, newValue) -> {
            actualizarEstadoBotonInicioSesion();
        });

        contraseña_entrada.textProperty().addListener((observable, oldValue, newValue) -> {
            actualizarEstadoBotonInicioSesion();
        });


        iniciar_sesion_boton.setDisable(true);
    }    

    @FXML
    private void presionadoAceptar(ActionEvent event) throws IOException, AcountDAOException {
        // Validar los datos del usuario y abrir la ventana de home
        String usuario = usuario_entrada.getText();
        String contraseña = contraseña_entrada.getText();

        if (usuario.isEmpty() || contraseña.isEmpty()) {
            mostrarAlerta("Error", "Usuario o contraseña vacíos", Alert.AlertType.ERROR, null);
            return;
        }

        Acount account = Acount.getInstance();

        if (account.logInUserByCredentials(usuario, contraseña)) {
            
            // Avisar de que el usuario ha iniciado sesión correctamente
            mostrarAlerta("Éxito", "Inicio de sesión correcto", Alert.AlertType.INFORMATION, null);
            FXMLMainController.isLogged = true;
            
            // Cambiar la ventana principal a la ventana de home ya logeado :)
            Parent homeRoot = FXMLLoader.load(getClass().getResource("/vista/FXMLHome.fxml"));
            Scene homeScene = new Scene(homeRoot);
            Stage window = (Stage) usuario_entrada.getScene().getWindow();

            //Establecer la nueva ventana
            window.setScene(homeScene);
            window.setHeight(window.getHeight() + 120);
            window.setWidth(window.getWidth() + 275);
            window.show();
            

        } else {
            mostrarAlerta("Error", "Usuario o contraseña incorrectos", Alert.AlertType.ERROR, null);
        }
    }

    @FXML
    private void presionadoLimpiar(ActionEvent event) {
        // Limpiar los campos de usuario y contraseña
        usuario_entrada.clear();
        contraseña_entrada.clear();
    }

    @FXML
    private void iniciarSesionLabel(ActionEvent event)  throws IOException, AcountDAOException{
        presionadoAceptar(event);
    }
    
    private void actualizarEstadoBotonInicioSesion() {
        String usuario = usuario_entrada.getText().trim();
        String contraseña = contraseña_entrada.getText().trim();
        iniciar_sesion_boton.setDisable(usuario.isEmpty() || contraseña.isEmpty());
    }
}
