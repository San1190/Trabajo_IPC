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
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Category;
import objetos.alerta;

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

    private Stage stage;
    private Category category;
    private boolean okPressed = false;

    /**
     * Initializes the controller class.
     */

    public void initCategory(Category category) {
        this.category = category;
        nombre_categoria.setText(category.getName());
        descripcion_categoria.setText(category.getDescription());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public boolean isOkpressed() {
        return okPressed;
    }
    public Category getCategory() {
        return category;
    }


    @FXML
    private void modificarCategoria(ActionEvent event) {
        String nombre = nombre_categoria.getText();
        String descripcion = descripcion_categoria.getText();

        category.setName(nombre);
        category.setDescription(descripcion);

        stage = (Stage) nombre_categoria.getScene().getWindow();
        alerta.mostrarAlerta("Categoria modificada", "La categoria ha sido modificada correctamente", Alert.AlertType.INFORMATION, null);
        stage.close();  
        
    }

    @FXML
    private void cancelar(ActionEvent event) {
        stage = (Stage) nombre_categoria.getScene().getWindow();
        stage.close();
    }

	public void setStage(Stage window) {
		this.stage = window;
	}
    
}
