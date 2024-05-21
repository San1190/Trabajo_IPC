/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import objetos.alerta;

/**
 * FXML Controller class
 *
 * @author sanfu
 */
public class FXMLMainController implements Initializable {

    @FXML
    private Pane panel_central;
    @FXML
    private Text texto_ficheroSeleccionado;

    public static boolean isLogged = false;
    public static Stage stage;

    @FXML
    private Button boton_registro;
    @FXML
    private Button boton_iniciar_sesion;
    @FXML
    private Button boton_salir;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Obtener la instancia de la ventana principal
    
    }    

    @FXML
    private void registrarseLabel(ActionEvent event) throws IOException{
        // Cargar la vista de registro
        try {
            // Cambiar el CSS del botón pulsado

            //quitar el css de los otros botones
            boton_iniciar_sesion.getStyleClass().remove("button-left-selected");


            Button button = (Button) event.getSource(); // Obtener el botón que fue pulsado
            button.getStyleClass().add("button-left-selected");
             
            // Cargar el FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLRegister.fxml"));
            VBox childView = loader.load();

            // Limpiar el panel central
            panel_central.getChildren().clear();
            
            // Agregar la vista cargada al Pane principal
            panel_central.getChildren().add(childView);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    
    }

    @FXML
    private void iniciarSesionLabel(ActionEvent event) {
        // Cargar la vista de inicio de sesión en el panel central
        // Cambiar el CSS del botón pulsado
        
        //quitar el css de los otros botones
        boton_registro.getStyleClass().remove("button-left-selected");

        
        Button button = (Button) event.getSource(); // Obtener el botón que fue pulsado
        button.getStyleClass().add("button-left-selected");
        try {
            // Cargar el FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLLogin.fxml"));
            VBox childView = loader.load();

            // Limpiar el panel central
            panel_central.getChildren().clear();
            
            // Agregar la vista cargada al Pane principal
            panel_central.getChildren().add(childView);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void salirLabel(ActionEvent event) {
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Cerrar sesión");
        alert.setHeaderText("¿Está seguro de que desea salir de la aplicación?");
        
        ButtonType botonAceptar = new  ButtonType("Aceptar");
        ButtonType botonCancelar = new  ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(botonAceptar, botonCancelar);
        
        alert.getDialogPane().lookupButton(botonAceptar).setId("botonAceptar");
        alert.getDialogPane().lookupButton(botonCancelar).setId("botonCancelar");
        alert.getDialogPane().getStylesheets().add("estilos/alertstyles.css");

        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == botonCancelar){
            return;
        }
        else if (result.isPresent() && result.get() == botonAceptar) {
                
        
            System.exit(0);
        }
    }
    
}