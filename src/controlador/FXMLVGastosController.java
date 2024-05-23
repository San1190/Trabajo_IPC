package controlador;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.swing.text.Document;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Acount;
import model.AcountDAOException;
import model.Category;
import model.Charge;
import static objetos.alerta.mostrarAlerta;

import org.apache.fontbox.util.autodetect.FontFileFinder;

import org.apache.pdfbox.pdmodel.font.PDType0Font;



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

    @FXML
    private LineChart<String, Number> grafica;
    @FXML
    private NumberAxis numeroAxis;
    @FXML
    private CategoryAxis categoriaAxis;
    @FXML
    private Button imprimirBoton;

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

            mostrarGastoMensual(gastosUser);
            mostrarGastoPorCategoria(gastosUser);
            mostrarComparacionAnual(gastosUser);

        } catch (AcountDAOException e) {
            mostrarAlerta("Error", "No se han podido cargar los datos", Alert.AlertType.ERROR, null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Bindings botones Gastos
        boton_modificarG.disableProperty().bind(tablaGastos.getSelectionModel().selectedItemProperty().isNull());
        boton_eliminarG.disableProperty().bind(tablaGastos.getSelectionModel().selectedItemProperty().isNull());

        // Comprabarasi en la celda seleccionada hay una imagen
        boton_fotoG.disableProperty().bind(Bindings.createBooleanBinding(() -> {
            Charge gastoSeleccionado = tablaGastos.getSelectionModel().getSelectedItem();
            boolean imageIsNull = compararBotonFoto(gastoSeleccionado);
            return imageIsNull;
        }, tablaGastos.getSelectionModel().selectedItemProperty()));

        // Bindings botones Categorías
        boton_modificarC.disableProperty().bind(tablaCategorias.getSelectionModel().selectedItemProperty().isNull());
        boton_eliminarC.disableProperty().bind(tablaCategorias.getSelectionModel().selectedItemProperty().isNull());

        imprimirBoton.disableProperty().bind(tablaGastos.getSelectionModel().selectedItemProperty().isNull());
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
            initialize(null, null);

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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLModificarExpense.fxml"));
                Parent root = loader.load();

                // Obtener el controlador del login y pasarle el Stage principal
                FXMLModificarExpenseController controller = loader.getController();

                controller.initCharge(gastoSeleccionado);

                // Crear una nueva escena
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);

                stage.setTitle("Modificar gasto"); // Establecer el título de la ventana
                stage.initModality(Modality.APPLICATION_MODAL); // Bloquear otras ventanas mientras esta está abierta
                stage.showAndWait(); // Mostrar la ventana y esperar hasta que se cierre
                if (controller.isOkpressed()) {
                    int index = tablaGastos.getSelectionModel().getSelectedIndex();
                    Charge c = controller.getCharge();
                    gastos.set(index, c);
                }
                initialize(null, null);

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
                initialize(null, null);

            } catch (AcountDAOException e) {
                mostrarAlerta("Error", "No se ha podido eliminar el gasto", Alert.AlertType.ERROR, null);
            }
        } else {
            mostrarAlerta("Error", "No se ha seleccionado ningún gasto", Alert.AlertType.ERROR, null);
        }

    }

    @SuppressWarnings("unused")
    @FXML
    private void verFoto(ActionEvent event) {
        // Implementar la lógica para ver la foto del gasto
        Charge gastoSeleccionado = tablaGastos.getSelectionModel().getSelectedItem();
        Image image = gastoSeleccionado.getImageScan();

        if (gastoSeleccionado != null) {
            try {
                // Crear una ventana que visualice la foto del gasto
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLFactura.fxml"));
                Parent root = loader.load();

                // Obtener el controlador de la ventana y pasarle el gasto seleccionado
                FXMLFacturaController controller = loader.getController();
                controller.setImage(image);

                // Crear una nueva escena
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);

                stage.setTitle("Ver foto"); // Establecer el título de la ventana
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
    private void añadirCategoria(ActionEvent event) {
        // Implementar la lógica para añadir una categoría

        try {
            // Cargar el archivo FXML de la ventana emergente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLCreateCategory.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del login y pasarle el Stage principal
            FXMLCategoryController controller = loader.getController();
            controller.setStage((Stage) panel_central.getScene().getWindow());

            // Crear una nueva escena
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);

            stage.setTitle("Crear categoría"); // Establecer el título de la ventana
            stage.initModality(Modality.APPLICATION_MODAL); // Bloquear otras ventanas mientras esta está abierta
            stage.showAndWait(); // Mostrar la ventana y esperar hasta que se cierre

            initialize(null, null);

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

                initialize(null, null);
            } catch (AcountDAOException e) {
                mostrarAlerta("Error", "No se ha podido eliminar la categoría", Alert.AlertType.ERROR, null);
            }
        } else {
            mostrarAlerta("Error", "No se ha seleccionado ninguna categoría", Alert.AlertType.ERROR, null);
        }
    }

    @FXML
    private void modificarCateogriaC(ActionEvent event) {
        // Modificar gasto en la db y en la lista

        Category categoriaSeleccionada = tablaCategorias.getSelectionModel().getSelectedItem();

        if (categoriaSeleccionada != null) {
            try {
                // Cargar el archivo FXML de la ventana emergente
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLModificarCategory.fxml"));
                Parent root = loader.load();

                // Obtener el controlador del login y pasarle el Stage principal
                FXMLModificarCategoryController controller = loader.getController();

                controller.initCategory(categoriaSeleccionada);

                // Crear una nueva escena
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);

                stage.setTitle("Modificar gasto"); // Establecer el título de la ventana
                stage.initModality(Modality.APPLICATION_MODAL); // Bloquear otras ventanas mientras esta está abierta
                stage.showAndWait(); // Mostrar la ventana y esperar hasta que se cierre
                if (controller.isOkpressed()) {
                    int index = tablaCategorias.getSelectionModel().getSelectedIndex();
                    Category c = controller.getCategory();
                    categorias.set(index, c);
                }
                initialize(null, null);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            mostrarAlerta("Error", "No se ha seleccionado ningún gasto", Alert.AlertType.ERROR, null);
        }
    }

    private void mostrarGastoMensual(List<Charge> gastosUser) {
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        Map<String, Double> gastosPorMes = new TreeMap<>();

        for (Charge gasto : gastosUser) {
            LocalDate fecha = gasto.getDate();
            String mes = fecha.with(TemporalAdjusters.firstDayOfMonth()).format(monthFormatter);
            double costo = gasto.getCost();
            gastosPorMes.put(mes, gastosPorMes.getOrDefault(mes, 0.0) + costo);
        }

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Gasto Total Mensual");

        for (Map.Entry<String, Double> entry : gastosPorMes.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        grafica.getData().add(series);
    }

    private void mostrarGastoPorCategoria(List<Charge> gastosUser) {
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        Map<String, Map<String, Double>> gastosPorCategoriaYMes = new TreeMap<>();

        for (Charge gasto : gastosUser) {
            LocalDate fecha = gasto.getDate();
            String mes = fecha.with(TemporalAdjusters.firstDayOfMonth()).format(monthFormatter);
            String categoria = gasto.getCategory().getName();
            double costo = gasto.getCost();

            gastosPorCategoriaYMes.putIfAbsent(categoria, new TreeMap<>());
            Map<String, Double> gastosPorMes = gastosPorCategoriaYMes.get(categoria);
            gastosPorMes.put(mes, gastosPorMes.getOrDefault(mes, 0.0) + costo);
        }

        for (Map.Entry<String, Map<String, Double>> entry : gastosPorCategoriaYMes.entrySet()) {
            String categoria = entry.getKey();
            Map<String, Double> gastosPorMes = entry.getValue();

            for (Map.Entry<String, Double> mesEntry : gastosPorMes.entrySet()) {
                String mes = mesEntry.getKey();
                Double costo = mesEntry.getValue();

                XYChart.Series<String, Number> series = new XYChart.Series<>();
                series.setName("Categoría: " + categoria + " - Mes: " + mes);
                series.getData().add(new XYChart.Data<>(mes, costo));

                grafica.getData().add(series);
            }
        }
    }

    private void mostrarComparacionAnual(List<Charge> gastosUser) {
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        LocalDate now = LocalDate.now();
        String mesActual = now.format(monthFormatter);

        Map<String, Double> gastosEsteAno = new TreeMap<>();
        Map<String, Double> gastosAnoAnterior = new TreeMap<>();

        for (Charge gasto : gastosUser) {
            LocalDate fecha = gasto.getDate();
            String mes = fecha.with(TemporalAdjusters.firstDayOfMonth()).format(monthFormatter);
            double costo = gasto.getCost();

            if (fecha.getYear() == now.getYear()) {
                gastosEsteAno.put(mes, gastosEsteAno.getOrDefault(mes, 0.0) + costo);
            } else if (fecha.getYear() == now.getYear() - 1) {
                gastosAnoAnterior.put(mes, gastosAnoAnterior.getOrDefault(mes, 0.0) + costo);
            }
        }

        XYChart.Series<String, Number> seriesEsteAno = new XYChart.Series<>();
        seriesEsteAno.setName("Este Año");

        for (Map.Entry<String, Double> entry : gastosEsteAno.entrySet()) {
            seriesEsteAno.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        XYChart.Series<String, Number> seriesAnoAnterior = new XYChart.Series<>();
        seriesAnoAnterior.setName("Año Anterior");

        for (Map.Entry<String, Double> entry : gastosAnoAnterior.entrySet()) {
            seriesAnoAnterior.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }

        grafica.getData().addAll(seriesEsteAno, seriesAnoAnterior);
    }

    @FXML
    private void imprimirPDF(ActionEvent event) {
        crearPDF();
    }

    private boolean compararBotonFoto(Charge g) {
        try {
            if (g.getImageScan().getWidth() == 0 && g.getImageScan().getHeight() == 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {

            return true;

        }

    }


    //crear pdf con apache pdfbox
    private void crearPDF() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
        File file = fileChooser.showSaveDialog(panel_central.getScene().getWindow());

        if (file != null) {
            try {
                org.apache.pdfbox.pdmodel.PDDocument document = new org.apache.pdfbox.pdmodel.PDDocument();
                org.apache.pdfbox.pdmodel.PDPage page = new org.apache.pdfbox.pdmodel.PDPage();
                document.addPage(page);

                org.apache.pdfbox.pdmodel.PDPageContentStream contentStream = new org.apache.pdfbox.pdmodel.PDPageContentStream(document, page);
                contentStream.beginText();
                FontFileFinder fontFinder = new FontFileFinder();
                List<URI> fontURIs = fontFinder.find();
        
                File fontFile = null;
        
                for (URI uri : fontURIs) {
                    File font = new File(uri);
                    if (font.getName().equals("CHILLER.TTF")) {
                        fontFile = font;
                    }
                }

                PDType0Font font = PDType0Font.load(document, fontFile);
                contentStream.setFont(font, 12);
                contentStream.setLeading(14.5f);
                contentStream.newLineAtOffset(100, 700);

                contentStream.showText("Gastos");
                contentStream.newLine();
                contentStream.newLine();

                for (Charge gasto : gastos) {
                    contentStream.showText("Nombre: " + gasto.getName());
                    contentStream.newLine();
                    contentStream.showText("Descripción: " + gasto.getDescription());
                    contentStream.newLine();
                    contentStream.showText("Categoría: " + gasto.getCategory().getName());
                    contentStream.newLine();
                    contentStream.showText("Coste: " + gasto.getCost());
                    contentStream.newLine();
                    contentStream.showText("Unidades: " + gasto.getUnits());
                    contentStream.newLine();
                    contentStream.showText("Fecha: " + gasto.getDate());
                    contentStream.newLine();
                    contentStream.newLine();
                }

                contentStream.endText();
                contentStream.close();

                document.save(new FileOutputStream(file));
                document.close();

            } catch (IOException e) {
                e.printStackTrace();


            }
        }
    }
}
