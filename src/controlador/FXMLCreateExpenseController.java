/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Category;
import model.Acount;
import model.AcountDAOException;
import objetos.alerta;

import java.time.DateTimeException;
import java.time.LocalDate;

/**
 * FXML Controller class
 *
 * @author sanfu
 */
public class FXMLCreateExpenseController implements Initializable {

    @FXML
    private ChoiceBox<String> categoria_eleccion;
    @FXML
    private Text texto_selccionado;

    private Acount acount;
    @FXML
    private TextField texto_nombre;
    @FXML
    private TextField texto_coste;
    @FXML
    private TextField texto_descripcion;
    @FXML
    private DatePicker texto_fecha;
    @FXML
    private TextField texto_unidades;

    private Image expenseImage = null;
    private Stage stage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            categoria_eleccion.getItems().clear();
            acount = Acount.getInstance();
            List<Category> categorias = acount.getUserCategories();
            for (Category categoria : categorias) {
                categoria_eleccion.getItems().add(categoria.getName());
            }

            texto_fecha.setDayCellFactory((texto_fecha) -> {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        LocalDate minDate = LocalDate.of(2020, 1, 1);
                        setDisable(empty || date.compareTo(minDate) < 0);
                    }
                };
            });
        } catch (Exception e) {
            alerta.mostrarAlerta("Error", "Error al cargar las categorias", Alert.AlertType.ERROR, null);
        }

    }

    @FXML
    private void crear_categoria(ActionEvent event) {
        // Cargar la ventana de crear categoria
        try {
            // Cargar el archivo FXML de la ventana emergente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLCreateCategory.fxml"));
            Parent root = loader.load();

            // Crear la ventana emergente
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Crear categoría"); // Establecer el título de la ventana
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquear otras ventanas mientras esta está abierta
            stage.showAndWait(); // Mostrar la ventana y esperar hasta que se cierre

            initialize(null, null); // Actualizar la lista de categorías

        } catch (IOException e) {
            alerta.mostrarAlerta("Error", "Error al cargar la ventana de inicio de sesión", Alert.AlertType.ERROR,
                    null);
        }
    }

    @FXML
    private void seleccionar_archivo(ActionEvent event) {
        // Abrir el explorador de archivos para que seleccione una imagen el usuario
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar imagen");

        // Filtro para mostrar solo archivos de imagen
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Archivos de imagen", "*.png", "*.jpg",
                "*.jpeg");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mostrar el diálogo y obtener el archivo seleccionado
        File selectedFile = fileChooser.showOpenDialog(stage);

        // Si se seleccionó un archivo, puedes utilizarlo
        if (selectedFile != null) {
            // Aquí puedes procesar el archivo seleccionado, por ejemplo, cargarlo en un
            // ImageView
            expenseImage = new Image(selectedFile.toURI().toString());
        }
    }

    @FXML
    private void añadir_gasto(ActionEvent event) {
        try {
            String nombre = texto_nombre.getText();
            String descripcion = texto_descripcion.getText();
            LocalDate fecha;
            String categoria = categoria_eleccion.getValue();

            // Validar que los campos obligatorios no estén vacíos
            if (nombre.isEmpty() || descripcion.isEmpty() || categoria == null) {
                throw new IllegalArgumentException("Por favor, complete todos los campos obligatorios.");
            }

            // Validar fecha
            try {
                fecha = texto_fecha.getValue();
                if (fecha == null) {
                    throw new IllegalArgumentException("Por favor, seleccione una fecha válida.");
                }
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                fecha.format(formatter);

            } catch (DateTimeException e) {
                throw new IllegalArgumentException("Por favor, seleccione una fecha válida.");
            }

            // Validar formato de coste
            double coste = 0;
            try {
                coste = Double.parseDouble(texto_coste.getText());
                if (coste < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                throw new NumberFormatException("El coste debe ser un número positivo.");
            }

            // Validar formato de unidades
            int unidades = 0;
            try {
                unidades = Integer.parseInt(texto_unidades.getText());
                if (unidades < 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Las unidades deben ser un número entero positivo.");
            }

            Category categoriaObjeto = getCategoryByName(categoria);

            // Registro de gasto
            acount.registerCharge(nombre, descripcion, coste, unidades, expenseImage, fecha, categoriaObjeto);
            alerta.mostrarAlerta("Gasto añadido", "El gasto ha sido añadido con éxito", Alert.AlertType.INFORMATION,
                    null);

            // Cerrar la ventana de ingreso de gasto
            Stage stage = (Stage) categoria_eleccion.getScene().getWindow();
            stage.close();
        } catch (IllegalArgumentException e) {
            // Manejo de errores
            alerta.mostrarAlerta("Error", e.getMessage(), Alert.AlertType.ERROR, null);
        } catch (AcountDAOException e) {
            // Manejo de excepciones DAO
            alerta.mostrarAlerta("Error", "Error al registrar el gasto: " + e.getMessage(), Alert.AlertType.ERROR,
                    null);
        }
    }

    @FXML
    private void cancelar_gasto(ActionEvent event) {
        // Cerrar la ventana de login
        Stage crateExpenseStage = (Stage) categoria_eleccion.getScene().getWindow();
        crateExpenseStage.close();
    }

    private Category getCategoryByName(String name) {
        try {

            List<Category> categorias = acount.getUserCategories();
            for (Category categoria : categorias) {
                if (categoria.getName().equals(name)) {
                    return categoria;
                }
            }
        } catch (Exception e) {
            alerta.mostrarAlerta("Error", "Error al cargar las categorias", Alert.AlertType.ERROR, null);
        }
        return null;
    }

    public void setStage(Stage window) {
        this.stage = window;
    }

    @FXML
    private void labeldescp(ActionEvent event) {
        añadir_gasto(event);
    }
}
