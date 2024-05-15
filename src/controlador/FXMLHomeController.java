/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.net.URL;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
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
    
}
