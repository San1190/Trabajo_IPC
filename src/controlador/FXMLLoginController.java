package controlador;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;

public class FXMLLoginController {

    private Stage mainStage;

    @FXML
    private TextField usuario_entrada;
    @FXML
    private PasswordField contraseña_entrada;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @FXML
    private void presionadoAceptar(ActionEvent event) throws IOException, AcountDAOException {
        // Validar los datos del usuario y abrir la ventana de home
        String usuario = usuario_entrada.getText();
        String contraseña = contraseña_entrada.getText();

        if (usuario.isEmpty() || contraseña.isEmpty()) {
            mostrarAlerta("Error", "Usuario o contraseña vacíos", Alert.AlertType.ERROR);
            return;
        }

        Acount account = Acount.getInstance();

        if (account.logInUserByCredentials(usuario, contraseña)) {
            // Cambiar la ventana principal a la ventana de home ya logeado :)
            Parent homeRoot = FXMLLoader.load(getClass().getResource("/vista/FXMLHome.fxml"));
            Scene homeScene = new Scene(homeRoot);

            // Establecer la nueva escena en el stage principal
            mainStage.setScene(homeScene);
            mainStage.show();

            // Cerrar la ventana de login
            Stage loginStage = (Stage) usuario_entrada.getScene().getWindow();
            loginStage.close();
        } else {
            mostrarAlerta("Error", "Usuario o contraseña incorrectos", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void presionadoCancelar(ActionEvent event) {
        // Cerrar la ventana de login
        Stage loginStage = (Stage) usuario_entrada.getScene().getWindow();
        loginStage.close();
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
