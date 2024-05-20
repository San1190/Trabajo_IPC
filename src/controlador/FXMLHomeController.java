package controlador;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import objetos.alerta;
import model.Acount;
import model.AcountDAOException;
import javafx.scene.control.Alert;


/**
 * FXML Controller class
 *
 * @author sanfu
 */
public class FXMLHomeController implements Initializable {

    @FXML
    private Button boton_cerrar_sesion;
    @FXML
    private Button boton_cuenta;
    @FXML
    private Button boton_get_gastos;
    @FXML
    private Pane panel_central;
    @FXML
    private Text texto_ficheroSeleccionado;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void cerrarSesionLabel(ActionEvent event) {
        try {
            Acount acount = Acount.getInstance();
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Cerrar sesión");
            alert.setHeaderText("¿Está seguro de que desea cerrar la sesión?");
            
            ButtonType botonAceptar = new  ButtonType("Aceptar");
            ButtonType botonCancelar = new  ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(botonAceptar, botonCancelar);

            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == botonCancelar){
                return;
            }
            else if (result.isPresent() && result.get() == botonAceptar) {

                if (acount.logOutUser()) {
                    
    
                    // Cambiar de ventana
                    Parent homeRoot = FXMLLoader.load(getClass().getResource("/vista/FXMLMain.fxml"));
                    Scene homeScene = new Scene(homeRoot);
                    Stage window = (Stage) boton_cerrar_sesion.getScene().getWindow();
    
                    //Establecer la nueva ventana
                    window.setScene(homeScene);
                    window.show();
    
                    FXMLMainController.isLogged = false;
                    
                } else {
                    alerta.mostrarAlerta("Error", "No se ha podido cerrar la sesión", Alert.AlertType.ERROR, null);
                }
            }
            


        } catch (AcountDAOException e) {
            alerta.mostrarAlerta("Error", "No se ha podido cerrar la sesión", Alert.AlertType.ERROR, null);
        } catch (Exception e) {
            alerta.mostrarAlerta("Error", "No se ha podido cerrar la sesión", Alert.AlertType.ERROR, null);
        }

    }

    @FXML
    private void verCuentaLabel(ActionEvent event) {
        // Cambiar el panel central por el panel de la cuenta
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLCuenta.fxml"));
            Parent root = loader.load();
            panel_central.getChildren().clear();
            panel_central.getChildren().add(root);
        } catch (Exception e) {
            alerta.mostrarAlerta("Error", "No se ha podido cargar la cuenta", Alert.AlertType.ERROR, null);
        }
    }

    @FXML
    private void verGastosLabel(ActionEvent event) {
        // Cambiar el panel central por el panel de los gastos
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLVGastos.fxml"));
            Parent root = loader.load();    
            panel_central.getChildren().clear();
            panel_central.getChildren().add(root);

        } catch (Exception e) {
            alerta.mostrarAlerta("Error", "No se ha podido cargar los gastos", Alert.AlertType.ERROR, null);
        }
    }
    
}
