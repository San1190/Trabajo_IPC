/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import javafx.scene.image.Image;
import model.User;
import objetos.alerta;


/**
 * FXML Controller class
 *
 * @author sanfu
 */
public class FXMLCuentaController implements Initializable {

    @FXML
    private Pane panel_central;
    @FXML
    private TextField texto_nombre;
    @FXML
    private TextField texto_apellido;
    @FXML
    private TextField texto_correo;
    @FXML
    private TextField texto_usuario;
    @FXML
    private PasswordField texto_contraseña;
    @FXML
    private Text texto_ficheroSeleccionado;
    @FXML
    private ImageView imagen_user;
    @FXML
    private Label nombre_user;

    private Stage stage;
    private Image UserImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Acount acount = Acount.getInstance();
            User user = acount.getLoggedUser();
            nombre_user.setText(user.getName() + " " + user.getSurname());
            texto_nombre.setText(user.getName());
            texto_apellido.setText(user.getSurname());
            texto_correo.setText(user.getEmail());
            texto_usuario.setText(user.getNickName());
            texto_contraseña.setText(user.getPassword());
            imagen_user.setImage(user.getImage());
        } catch (Exception e) {
            alerta.mostrarAlerta("Error", "Error al cargar el perfil", Alert.AlertType.ERROR, null);
        }
    }    

    @FXML
    private void subirFichero(ActionEvent event) {
        // Abrir el explorador de archivos para que seleccione una imagen el usuario
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");
        
        // Filtro para mostrar solo archivos de imagen
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg", "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);
        
        // Mostrar el diálogo y obtener el archivo seleccionado
        File selectedFile = fileChooser.showOpenDialog(stage);
        
        // Si se seleccionó un archivo, puedes utilizarlo
        if (selectedFile != null) {
            // Aquí puedes procesar el archivo seleccionado, por ejemplo, cargarlo en un ImageView
            UserImage = new Image(selectedFile.toURI().toString());
            texto_ficheroSeleccionado.setText(selectedFile.getName());
        }
    }


    @FXML
    private void actualizar_perfil(ActionEvent event){
        // Aquí puedes guardar los datos del formulario en la base de datos
        // Por ejemplo, puedes guardar el nombre en la base de datos
        String nombre = texto_nombre.getText();
        String apellido = texto_apellido.getText();
        String correo = texto_correo.getText();
        String contraseña = texto_contraseña.getText();
        try {
            Acount acount = Acount.getInstance();
            User user = acount.getLoggedUser();

            if (!nombre.isEmpty()) {
                user.setName(nombre);
            }
            
            if (!apellido.isEmpty()) {
                user.setSurname(apellido);
            }

            if (!correo.isEmpty()) {
                user.setEmail(correo);
            }

            if (!contraseña.isEmpty()) {
                user.setPassword(contraseña);
            }
           
            if (UserImage != null) {
                user.setImage(UserImage);
            }

            alerta.mostrarAlerta("Perfil actualizado", "Perfil actualizado correctamente", Alert.AlertType.INFORMATION, null);

        } catch (Exception e) {
            alerta.mostrarAlerta("Error", "Error al actualizar el perfil", Alert.AlertType.ERROR, null);
        }

        // Volver a recargar la vista
        initialize(null, null);
        
        
    }
    
}
