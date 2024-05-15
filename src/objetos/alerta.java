package objetos;

import javafx.scene.control.Alert;

public class alerta {

    public static void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo, String header) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);

        if (header != null) {
            alert.setHeaderText(header);
        }

        
        alert.setContentText(mensaje);
        alert.showAndWait();
    }


    
}
