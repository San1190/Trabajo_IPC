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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
public class FXMLRegisterController implements Initializable {

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

    
    private Image UserImage;
    private Stage stage;
    @FXML
    private Button borrar_boton;
    @FXML
    private Button registrarse_boton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        texto_nombre.textProperty().addListener((observable, oldValue, newValue) -> {
            actualizarEstadoBotonRegistro();
        });

        texto_apellido.textProperty().addListener((observable, oldValue, newValue) -> {
            actualizarEstadoBotonRegistro();
        });

        texto_correo.textProperty().addListener((observable, oldValue, newValue) -> {
            actualizarEstadoBotonRegistro();
        });

        texto_usuario.textProperty().addListener((observable, oldValue, newValue) -> {
            actualizarEstadoBotonRegistro();
        });

        texto_contraseña.textProperty().addListener((observable, oldValue, newValue) -> {
            actualizarEstadoBotonRegistro();
        });


        registrarse_boton.setDisable(true);
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
    private void borrar_todo(ActionEvent event) {
        // Limpiar los campos de texto
        texto_nombre.clear();
        texto_apellido.clear();
        texto_correo.clear();
        texto_usuario.clear();
        texto_contraseña.clear();
        UserImage = null;
        texto_ficheroSeleccionado.setText("");

    }

    @FXML
    private void registrarse(ActionEvent event) throws IOException{
        String nombre = texto_nombre.getText();
        String apellidos = texto_apellido.getText();
        String correo = texto_correo.getText();
        String usuario = texto_usuario.getText();
        String contraseña = texto_contraseña.getText(); // Letras y números, al menos 6 caracteres

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
                stage = (Stage) texto_nombre.getScene().getWindow();
            }

            alerta.mostrarAlerta("Registro correcto", "Usuario registrado correctamente, ahora inicie sesión", AlertType.INFORMATION, null);

            // abrir la ventana de login            
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

            } else {
                alerta.mostrarAlerta("Error", "Error al registrar al usuario.", AlertType.ERROR, null);
            }
        } catch (AcountDAOException e) {
            alerta.mostrarAlerta("Error", "El nombre de usuario ya está en uso.", AlertType.ERROR, null);
            return;
        }

    }

    @FXML
    private void registerLabel(ActionEvent event) throws IOException {
        registrarse(event);
    }

    private void actualizarEstadoBotonRegistro() {
        String nombre = texto_nombre.getText().trim();
        String apellido = texto_apellido.getText().trim();
        String correo = texto_correo.getText().trim();
        String usuario = texto_usuario.getText().trim();
        String contraseña = texto_contraseña.getText().trim();
        registrarse_boton.setDisable(nombre.isEmpty() || apellido.isEmpty() || correo.isEmpty() || usuario.isEmpty() || contraseña.isEmpty());
    }
    
}
