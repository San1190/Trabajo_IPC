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
import javafx.scene.control.Menu;

/**
 * FXML Controller class
 *
 * @author sanfu
 */
public class FXMLMainController implements Initializable {

    @FXML
    private Menu home;
    @FXML
    private Menu register;
    @FXML
    private Menu login;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void presionadoHome(ActionEvent event) {
        // Aqui se debe de cargar la vista de home
        System.out.println("Presionado Home");
    }

    @FXML
    private void persionadoRegister(ActionEvent event) {
        // Aqui se debe de cargar la vista de registro
        // Actualizar ventana y cambiar aFXMLRegistr y a FXMLRegisterController.java
        System.out.println("Presionado Register");
    }

    @FXML
    private void PresionadoLogin(ActionEvent event) {
        // Aqui se debe de cargar la vista de login
        // Actualizar ventana y cambiar aFXMLLogin.fxml y a FXMLLoginController.java
        System.out.println("Presionado Login");
    }

}
