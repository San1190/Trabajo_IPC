package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;
import static objetos.alerta.mostrarAlerta;

public class FXMLVGastosController implements Initializable {

    @FXML
    private Pane panel_central;
    @FXML
    private Text texto_ficheroSeleccionado;
    @FXML
    private TableView<Charge> tablaGastos;
    @FXML
    private TableColumn<Charge, String> columnaNombreG;
    @FXML
    private TableColumn<Charge, String> columnaDescripcionG;
    @FXML
    private TableColumn<Charge, String> columnaCategoríaG;
    @FXML
    private TableColumn<Charge, Double> columnaCosteG;
    @FXML
    private TableColumn<Charge, Integer> columnaUnidadesG;
    @FXML
    private TableColumn<Charge, String> columnaFechaG;
    @FXML
    private Button boton_añadirG;
    @FXML
    private Button boton_modificarG;
    @FXML
    private Button boton_eliminarG;
    @FXML
    private Button boton_fotoG;
    @FXML
    private TableView<Category> tablaCategorias;
    @FXML
    private TableColumn<Category, String> columnaNombreC;
    @FXML
    private TableColumn<Category, String> columnaDescripcionC;
    @FXML
    private Button boton_añadirC;
    @FXML
    private Button boton_eliminarC;
    @FXML
    private Button boton_modificarC;

    private ObservableList<Charge> gastos;
    private ObservableList<Category> categorias;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // Obtén la instancia de Acount
            Acount acount = Acount.getInstance();

            // Obtén los gastos del usuario
            List<Charge> gastosUser = acount.getUserCharges();
    
            // Inicializa la lista de gastos
            gastos = FXCollections.observableArrayList(gastosUser);
            
            // Asigna los datos a la tabla
            tablaGastos.setItems(gastos);

            // Configura las columnas de la tabla de gastos
            columnaNombreG.setCellValueFactory(new PropertyValueFactory<>("name"));
            columnaDescripcionG.setCellValueFactory(new PropertyValueFactory<>("description"));
            columnaCosteG.setCellValueFactory(new PropertyValueFactory<>("cost"));
            columnaUnidadesG.setCellValueFactory(new PropertyValueFactory<>("units"));
            columnaFechaG.setCellValueFactory(new PropertyValueFactory<>("date"));

            columnaCategoríaG.setCellValueFactory(cellData -> {
                Category category = cellData.getValue().getCategory();
                return new SimpleStringProperty(category != null ? category.getName() : "Sin categoría");
            }); 

            // Obtén las categorías del usuario
            List<Category> categoriasUser = acount.getUserCategories();
    
            // Inicializa la lista de categorías
            categorias = FXCollections.observableArrayList(categoriasUser);
            
            // Asigna los datos a la tabla de categorías
            tablaCategorias.setItems(categorias);

            // Configura las columnas de la tabla de categorías
            columnaNombreC.setCellValueFactory(new PropertyValueFactory<>("name"));
            columnaDescripcionC.setCellValueFactory(new PropertyValueFactory<>("description"));

        } catch (AcountDAOException e) {
            mostrarAlerta("Error", "No se han podido cargar los datos", Alert.AlertType.ERROR, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Bindings botones Gastos
        boton_modificarG.disableProperty().bind(tablaGastos.getSelectionModel().selectedItemProperty().isNull());
        boton_eliminarG.disableProperty().bind(tablaGastos.getSelectionModel().selectedItemProperty().isNull());
        boton_añadirG.disableProperty().bind(tablaGastos.getSelectionModel().selectedItemProperty().isNotNull());
        boton_fotoG.disableProperty().bind(tablaGastos.getSelectionModel().selectedItemProperty().isNull());

        // Bindings botones Categorías
        boton_modificarC.disableProperty().bind(tablaCategorias.getSelectionModel().selectedItemProperty().isNull());
        boton_eliminarC.disableProperty().bind(tablaCategorias.getSelectionModel().selectedItemProperty().isNull());
        boton_añadirC.disableProperty().bind(tablaCategorias.getSelectionModel().selectedItemProperty().isNotNull());
    }

    @FXML
    private void añadirGasto(ActionEvent event) {
        try {
            // Cargar el archivo FXML de la ventana emergente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLCreateExpense.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del login y pasarle el Stage principal
            FXMLCreateExpenseController controller = loader.getController();
            controller.setStage((Stage) panel_central.getScene().getWindow());

            // Crear una nueva escena
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.setTitle("Crear gasto"); // Establecer el título de la ventana
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquear otras ventanas mientras esta está abierta
            stage.showAndWait(); // Mostrar la ventana y esperar hasta que se cierre

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void modificarGasto(ActionEvent event) {
        // Modificar gasto en la db y en la lista

        Charge gastoSeleccionado = tablaGastos.getSelectionModel().getSelectedItem();
        if (gastoSeleccionado != null) {
            try {
                // Cargar el archivo FXML de la ventana emergente
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLModifyExpense.fxml"));
                Parent root = loader.load();

                // Obtener el controlador del login y pasarle el Stage principal
                // FXMLModifyExpenseController controller = loader.getController();
                //controller.setStage((Stage) panel_central.getScene().getWindow());
                //controller.setCharge(gastoSeleccionado);

                // Crear una nueva escena
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);

                stage.setTitle("Modificar gasto"); // Establecer el título de la ventana
                stage.initModality(Modality.APPLICATION_MODAL); // Bloquear otras ventanas mientras esta está abierta
                stage.showAndWait(); // Mostrar la ventana y esperar hasta que se cierre

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mostrarAlerta("Error", "No se ha seleccionado ningún gasto", Alert.AlertType.ERROR, null);
        }
    }

    @FXML
    private void eliminarGasto(ActionEvent event) throws IOException {
        // Si hay un elemento seleccionado eliminarlo de la lista y de la db
        Charge gastoSeleccionado = tablaGastos.getSelectionModel().getSelectedItem();
        if (gastoSeleccionado != null) {
            try {
                // Eliminar el gasto de la base de datos
                Acount acount = Acount.getInstance();
                acount.removeCharge(gastoSeleccionado);

                // Eliminar el gasto de la lista
                gastos.remove(gastoSeleccionado);
            } catch (AcountDAOException e) {
                mostrarAlerta("Error", "No se ha podido eliminar el gasto", Alert.AlertType.ERROR, null);
            }
        } else {
            mostrarAlerta("Error", "No se ha seleccionado ningún gasto", Alert.AlertType.ERROR, null);
        }
        
    }

    @FXML
    private void verFoto(ActionEvent event) {
        // Implementar la lógica para ver la foto del gasto
    }

    @FXML
    private void añadirCategoria(ActionEvent event) {
        // Implementar la lógica para añadir una categoría

        try {
            // Cargar el archivo FXML de la ventana emergente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLCreateCategory.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del login y pasarle el Stage principal
            // FXMLCreateCategoryController controller = loader.getController();
            // controller.setStage((Stage) panel_central.getScene().getWindow());

            // Crear una nueva escena
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.setTitle("Crear categoría"); // Establecer el título de la ventana
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquear otras ventanas mientras esta está abierta
            stage.showAndWait(); // Mostrar la ventana y esperar hasta que se cierre

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void eliminarCategoria(ActionEvent event) throws IOException {
        // Implementar la lógica para eliminar una categoría
        Category categoriaSeleccionada = tablaCategorias.getSelectionModel().getSelectedItem();
        if (categoriaSeleccionada != null) {
            try {
                // Eliminar la categoría de la base de datos
                Acount acount = Acount.getInstance();
                acount.removeCategory(categoriaSeleccionada);

                // Eliminar la categoría de la lista
                categorias.remove(categoriaSeleccionada);
            } catch (AcountDAOException e) {
                mostrarAlerta("Error", "No se ha podido eliminar la categoría", Alert.AlertType.ERROR, null);
            }
        } else {
            mostrarAlerta("Error", "No se ha seleccionado ninguna categoría", Alert.AlertType.ERROR, null);
        }
    }

    @FXML
    private void modificarCateogriaC(ActionEvent event) {
        // Implementar la lógica para modificar una categoría
        Category categoriaSeleccionada = tablaCategorias.getSelectionModel().getSelectedItem();

        if (categoriaSeleccionada != null) {
            try {
                // Cargar el archivo FXML de la ventana emergente
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLModifyCategory.fxml"));
                Parent root = loader.load();

                // Obtener el controlador del login y pasarle el Stage principal
                // FXMLModifyCategoryController controller = loader.getController();
                // controller.setStage((Stage) panel_central.getScene().getWindow());
                // controller.setCategory(categoriaSeleccionada);

                // Crear una nueva escena
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);

                stage.setTitle("Modificar categoría"); // Establecer el título de la ventana
                stage.initModality(Modality.APPLICATION_MODAL); // Bloquear otras ventanas mientras esta está abierta
                stage.showAndWait(); // Mostrar la ventana y esperar hasta que se cierre

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mostrarAlerta("Error", "No se ha seleccionado ninguna categoría", Alert.AlertType.ERROR, null);
        }
    }
}
