/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import static objetos.alerta.mostrarAlerta;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Charge;
import objetos.alerta;
import model.Category;

/**
 * FXML Controller class
 *
 * @author sanfu
 */
public class FXMLModificarExpenseController implements Initializable {

    @FXML
    private ChoiceBox<String> categoria_eleccion;
    @FXML
    private TextField texto_nombre;
    @FXML
    private TextField texto_coste;
    @FXML
    private TextField texto_descripcion;
    @FXML
    private DatePicker texto_fecha;
    @FXML
    private Text texto_selccionado;
    @FXML
    private TextField texto_unidades;

    /**
     * Initializes the controller class.
     */

    private Charge charge;

    private Image chargeImage;
    private Stage stage;
    private boolean okPressed = false;

    

    @FXML
    private Button botonModificar;

    public void initCharge(Charge charge){
        this.charge = charge;
        texto_nombre.setText(charge.getName());
        texto_descripcion.setText(charge.getDescription());
        texto_coste.setText(String.valueOf(charge.getCost()));
        texto_unidades.setText(String.valueOf(charge.getUnits()));
        texto_fecha.setValue(charge.getDate());
        categoria_eleccion.setValue(charge.getCategory().getName());


    }

    public boolean isOkpressed(){
        return okPressed;
    }
    public Charge getCharge(){
        return charge;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            Acount acount = Acount.getInstance();
        
            // Cargar el nombre de las categorías en el ChoiceBox
            for (Category category : acount.getUserCategories()) {
                categoria_eleccion.getItems().add(category.getName());
            }
        } catch (Exception e) {
            alerta.mostrarAlerta("Error", "No se han podido cargar las categorías", Alert.AlertType.ERROR, null);
        }
        
        



    }    

    @FXML
    private void seleccionar_archivo(ActionEvent event) {
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
            chargeImage = new Image(selectedFile.toURI().toString());
        }
    }


    @FXML
    private void cancelar_gasto(ActionEvent event) {
        // Cerrar la ventana
        stage = (Stage) botonModificar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void modificarGasto(ActionEvent event) throws IOException {
        String nombre = texto_nombre.getText();
        String descripcion = texto_descripcion.getText();
        String coste = texto_coste.getText();
        String unidades = texto_unidades.getText();
        String fecha = texto_fecha.getValue().toString();
        String categoria = categoria_eleccion.getValue();
        LocalDate date = LocalDate.parse(fecha);

        charge.setName(nombre);
        charge.setDescription(descripcion);
        charge.setCost(Double.parseDouble(coste));
        charge.setUnits(Integer.parseInt(unidades));
        charge.setDate(date);
        charge.setImageScan(chargeImage);


        try {
            for (Category category : Acount.getInstance().getUserCategories()) {
                if (category.getName().equals(categoria)) {
                    charge.setCategory(category);
                    break;
            }
        }
        } catch (AcountDAOException e) {
            mostrarAlerta("Error", "No se ha podido modificar el gasto", Alert.AlertType.ERROR, null);
        }
        

        // Cerrar la ventana
        mostrarAlerta("Información", "Los datos han sido modificados de manera correcrta", Alert.AlertType.INFORMATION, null);
        stage = (Stage) botonModificar.getScene().getWindow();
        stage.close();


    }
    
}
