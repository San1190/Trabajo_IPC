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
import javafx.stage.Stage;
import model.Acount;
import model.User;
import objetos.alerta;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author sanfu
 */
public class FXMLCategoryController implements Initializable {

    @FXML
    private TextField nombre_categoria;
    @FXML
    private TextField descripcion_categoria;

    private Acount acount;

    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            acount = Acount.getInstance();
        } catch (Exception e) {
            System.out.println("Error al cargar el usuario");
        }

    }    

    @FXML
    private void añadir_categoria(ActionEvent event) {
        String nombre = nombre_categoria.getText();
        String descripcion = descripcion_categoria.getText();
        System.out.println("Nombre: " + nombre + " Descripcion: " + descripcion);

        if (nombre.isEmpty() || descripcion.isEmpty()) {
            alerta.mostrarAlerta("Error", "Campos vacios, por favor rellene todos los campos", Alert.AlertType.ERROR, null);
        } else {
            try{

                boolean isCreated = acount.registerCategory(nombre, descripcion);
                
                if (isCreated) {
                    alerta.mostrarAlerta("Categoria creada", "La categoria ha sido creada con exito", Alert.AlertType.INFORMATION, null);
                    // Cerrar la ventana actual
                    Stage crateCategoryStage = (Stage) nombre_categoria.getScene().getWindow();
                    crateCategoryStage.close();
                    
                } else {
                    alerta.mostrarAlerta("Error", "Error al registrar la categoria", Alert.AlertType.ERROR, null);
                }
            }
            catch (Exception e) {
                alerta.mostrarAlerta("Error", "Error al registrar la categoria: " + e, Alert.AlertType.ERROR, null);
            }
            
        }
    }

    @FXML
    private void cancelar(ActionEvent event) {
        // Cerrar la ventana actual
        Stage crateCategoryStage = (Stage) nombre_categoria.getScene().getWindow();
        crateCategoryStage.close();
    }

	public void setStage(Stage window) {
		this.stage = window;
	}

    @FXML
    private void categoriaLabel(ActionEvent event) {
        añadir_categoria(event);

    }
    
}
