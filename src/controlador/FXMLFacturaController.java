/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author sanfu
 */
public class FXMLFacturaController implements Initializable {

    @FXML
    private ImageView imageVisualizer;

    private Image image;

    /**
     * Initializes the controller class.
     */

    public void setImage(Image image) {
        this.image = image;
        imageVisualizer.setImage(this.image);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
}
