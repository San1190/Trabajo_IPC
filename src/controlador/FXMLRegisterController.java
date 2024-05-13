/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;



/**
 * FXML Controller class
 *
 * @author san
 */
public class FXMLRegisterController implements Initializable {

    @FXML
    private TextField campoNombre;
    @FXML
    private TextField campoApellidos;
    @FXML
    private TextField campoCorreo;
    @FXML
    private TextField campoUsuario;
    @FXML
    private PasswordField campoContraseña;

    private Stage stage;
    private Image UserImage;
    @FXML
    private Text nombreImagen;

    @FXML
    private Parent root;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void presionadoCancelar(ActionEvent event) {
        // Cerrar la ventana
        if(stage == null){
            stage = (Stage) campoNombre.getScene().getWindow();
        }
        stage.close();
    }

    @FXML
    private void presionadoAceptar(ActionEvent event) throws AcountDAOException, IOException {
        String nombre = campoNombre.getText();
        String apellidos = campoApellidos.getText();
        String correo = campoCorreo.getText();
        String usuario = campoUsuario.getText();
        String contraseña = campoContraseña.getText(); // Letras y numeros, al menos 6 caracteres

        Acount acount = Acount.getInstance();

        //comprobar que el nick (usuario) no esta en la base de datos, que el correo tenga el formato correcto y que la contraseña tenga al menos 6 caracteres
        //si todo esta correcto, se debe de registrar al usuario en la base de datos y abrir la ventana de home
        //si algo esta mal, se debe de mostrar un mensaje de error
        
         
        // Cerrar la ventana

        boolean isRegistered = acount.registerUser(nombre,apellidos, correo, usuario, contraseña,  UserImage, LocalDate.now());
        if(isRegistered){
            System.out.println("Usuario registrado");
            if(stage == null){
                stage = (Stage) campoNombre.getScene().getWindow();
            }
            stage.close();

            //crear ventana de registro correcto
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLCorrectRegister.fxml")); // Por implementar
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
            


        } else {
            System.out.println("Usuario no registrado");
            //crear ventana de registro incorrecto, de error
            
        }
    }
       

        

    
    @FXML
    private void presionarSeleccionarImagen(ActionEvent event) {
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
            nombreImagen.setText(selectedFile.getName());
        }
    }
    
}
