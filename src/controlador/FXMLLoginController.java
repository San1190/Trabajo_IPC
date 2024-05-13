/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author san
 */
public class FXMLLoginController implements Initializable {
    private Stage stage;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void presionadoAceptar(ActionEvent event) {
        //Validar los datos del usuario y abrir la ventana de home
       

    }

    @FXML
    private void presionadoCancelar(ActionEvent event) {
        //Volver a la ventana principal y cerrar la ventana de login
        if (stage == null) {
            stage = (Stage) this.stage.getScene().getWindow();
        }
        stage.close();
    }
    
}