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
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author sanfu
 */
public class FXMLModificarCategoryController implements Initializable {

    @FXML
    private TextField nombre_categoria;
    @FXML
    private TextField descripcion_categoria;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modificarCategoria(ActionEvent event) {
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }
    
}
