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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import objetos.alerta;



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
    private void presionadoAceptar(ActionEvent event) throws IOException {
        String nombre = campoNombre.getText();
        String apellidos = campoApellidos.getText();
        String correo = campoCorreo.getText();
        String usuario = campoUsuario.getText();
        String contraseña = campoContraseña.getText(); // Letras y números, al menos 6 caracteres

        // Validaciones
        if (nombre.isEmpty() || apellidos.isEmpty() || correo.isEmpty() || usuario.isEmpty() || contraseña.isEmpty()) {
            alerta.mostrarAlerta("Error", "Por favor, rellene todos los campos.", AlertType.ERROR, null);
            return;
        }

        if (!correo.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            alerta.mostrarAlerta("Error", "El formato del correo electrónico es incorrecto.", AlertType.ERROR, null);
            return;
        }

        if (usuario.contains(" ")) {
            alerta.mostrarAlerta("Error", "El nombre de usuario no puede contener espacios.", AlertType.ERROR, null);
            return;
        }

        if (contraseña.length() < 6 || !contraseña.matches("[a-zA-Z0-9]+")) {
            alerta.mostrarAlerta("Error", "La contraseña debe contener al menos 6 caracteres alfanuméricos.", AlertType.ERROR, null);
            return;
        }

        // Registro de usuario
        
        try {
            Acount acount = Acount.getInstance();
            boolean isRegistered = acount.registerUser(nombre, apellidos, correo, usuario, contraseña, UserImage, LocalDate.now());
            if (isRegistered) {
                System.out.println("Usuario registrado");
            if (stage == null) {
                stage = (Stage) campoNombre.getScene().getWindow();
            }

            alerta.mostrarAlerta("Registro correcto", "Usuario registrado correctamente, ahora inicie sesión", AlertType.INFORMATION, null);

            stage.close();
            } else {
                alerta.mostrarAlerta("Error", "Error al registrar al usuario.", AlertType.ERROR, null);
            }
        } catch (AcountDAOException e) {
            alerta.mostrarAlerta("Error", "El nombre de usuario ya está en uso.", AlertType.ERROR, null);
            return;
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
