/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;
import Interfaces.ProductoManager;
import comparators.ComparadorPorNombre;
import comparators.ComparadorPorPrecio;
import exceptions.ProductoNoEncontradoException;
import exceptions.PrecioInvalidoException;
import model.Producto.Producto;
import model.Producto.Alimento;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import model.Producto.Electronico;
import model.Producto.Ropa;

/**
 * Controlador para la vista principal de la aplicación.
 * Administra la interacción entre la interfaz gráfica y la lógica del sistema.
 * Permite realizar operaciones CRUD, ordenamiento, filtrado, y persistencia de datos.
 * 
 * @author joaxx
 */
public class MainViewController {

    @FXML private TableView<Producto> tableView;
    @FXML private TableColumn<Producto, Number> colId;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Number> colPrecio;

    private ProductoManager manager = new ProductoManager();

    /**
     * Inicializa la vista, configurando las columnas de la tabla y cargando los datos.
     */
    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getId()));
        colNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        colPrecio.setCellValueFactory(cellData -> new javafx.beans.property.SimpleDoubleProperty(cellData.getValue().getPrecio()));

        actualizarTabla();
    }

    /**
     * Actualiza los datos mostrados en la tabla con los productos actuales.
     */
    private void actualizarTabla() {
        tableView.getItems().setAll(manager.obtenerTodos());
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de error.
     * 
     * @param titulo Título del cuadro de diálogo.
     * @param mensaje Mensaje a mostrar.
     */
    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(AlertType.ERROR, mensaje, ButtonType.OK);
        alert.setTitle(titulo);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }

    /**
     * Abre la ventana para agregar un nuevo producto y actualiza la tabla si se agregó correctamente.
     */
    @FXML
    private void onAgregarProducto() {
        AgregarProductoController ctrl = AbrirVentanaHelper.abrirAgregarProductoView(manager);
        if (ctrl != null && ctrl.isProductoAgregado()) {
            actualizarTabla();
        }
    }

    /**
     * Abre la ventana para editar un producto existente según su ID.
     * Muestra un cuadro de error si el ID no es válido o no se encuentra el producto.
     */
    @FXML
    private void onEditarProducto() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Editar Producto");
        dialog.setHeaderText("Ingrese el ID del producto a editar:");
        dialog.setContentText("ID:");
        dialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                Producto p = manager.obtenerPorId(id);

                EditarProductoController ctrl = AbrirVentanaHelper.abrirEditarProductoView(manager, p);
                if (ctrl != null && ctrl.isProductoEditado()) {
                    actualizarTabla();
                }

            } catch (ProductoNoEncontradoException ex) {
                mostrarAlerta("No encontrado", ex.getMessage());
            } catch (NumberFormatException ex) {
                mostrarAlerta("Error de Formato", "ID debe ser numérico");
            }
        });
    }

    /**
     * Elimina un producto de la lista según su ID.
     * Muestra un cuadro de error si el ID no es válido o no se encuentra el producto.
     */
    @FXML
    private void onEliminarProducto() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Eliminar Producto");
        dialog.setHeaderText("Ingrese el ID del producto a eliminar:");
        dialog.setContentText("ID:");
        dialog.showAndWait().ifPresent(idStr -> {
            try {
                int id = Integer.parseInt(idStr);
                manager.eliminar(id);
                actualizarTabla();
            } catch (ProductoNoEncontradoException ex) {
                mostrarAlerta("No encontrado", ex.getMessage());
            } catch (NumberFormatException ex) {
                mostrarAlerta("Error de Formato", "ID debe ser numérico");
            }
        });
    }

    /**
     * Ordena los productos por nombre y actualiza la tabla.
     */
    @FXML
    private void onOrdenarPorNombre() {
        manager.ordenarPor(new ComparadorPorNombre());
        actualizarTabla();
    }

    /**
     * Ordena los productos por precio y actualiza la tabla.
     */
    @FXML
    private void onOrdenarPorPrecio() {
        manager.ordenarPor(new ComparadorPorPrecio());
        actualizarTabla();
    }

    /**
     * Filtra los productos con precio menor a 50 y actualiza la tabla.
     */
    @FXML
    private void onFiltrarPrecio() {
        var filtrados = manager.filtrar(p -> p.getPrecio() < 50);
        tableView.getItems().setAll(filtrados);
    }

    /**
     * Filtra los productos del tipo Alimento y actualiza la tabla.
     */
    @FXML
    private void onFiltrarAlimentos() {
        var filtrados = manager.filtrarPorTipo(manager.obtenerTodos(), Alimento.class);
        tableView.getItems().setAll(filtrados);
    }

    /**
     * Incrementa el precio de todos los productos en un 10% y actualiza la tabla.
     */
    @FXML
    private void onIncrementarPrecios() {
        Consumer<Producto> incrementa = p -> {
            double nuevoPrecio = p.getPrecio() * 1.10;
            try {
                p.setPrecio(nuevoPrecio);
            } catch (PrecioInvalidoException ex) {
                mostrarAlerta("Error de Precio", ex.getMessage());
            }
        };
        manager.aplicar(incrementa);
        actualizarTabla();
    }

    /**
     * Aplica un descuento del 5% a los productos del tipo Alimento y actualiza la tabla.
     */
    @FXML
    private void onDescuentoAlimentos() {
        manager.aplicarDescuentoAlimentos(manager.obtenerTodos(), 5);
        actualizarTabla();
    }

    /**
     * Carga productos desde un archivo CSV y actualiza la tabla.
     * Muestra errores en caso de fallas de lectura o precios inválidos.
     */
    @FXML
    private void onCargarCSV() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Cargar CSV");
        File f = fc.showOpenDialog(tableView.getScene().getWindow());
        if (f != null) {
            try {
                manager.cargarCSV(f.getAbsolutePath());
                actualizarTabla();
            } catch (IOException ex) {
                mostrarAlerta("Error IO", ex.getMessage());
            } catch (PrecioInvalidoException ex) {
                mostrarAlerta("Precio Invalido", ex.getMessage());
            }
        }
    }

    /**
     * Guarda los productos actuales en un archivo CSV.
     */
    @FXML
    private void onGuardarCSV() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar CSV");
        FileChooser.ExtensionFilter extFilterCSV = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fc.getExtensionFilters().add(extFilterCSV);
        fc.setSelectedExtensionFilter(extFilterCSV);

        File f = fc.showSaveDialog(tableView.getScene().getWindow());
        if (f != null) {
            try {
                if (!f.getName().endsWith(".csv")) {
                    f = new File(f.getAbsolutePath() + ".csv");
                }
                manager.guardarCSV(f.getAbsolutePath());
            } catch (IOException ex) {
                mostrarAlerta("Error IO", ex.getMessage());
            }
        }
    }

    /**
     * Carga productos desde un archivo JSON y actualiza la tabla.
     */
    @FXML
    private void onCargarJSON() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Cargar JSON");
        File f = fc.showOpenDialog(tableView.getScene().getWindow());
        if (f != null) {
            try {
                manager.cargarJSON(f.getAbsolutePath());
                actualizarTabla();
            } catch (IOException ex) {
                mostrarAlerta("Error IO", ex.getMessage());
            }
        }
    }

    /**
     * Guarda los productos actuales en un archivo JSON.
     */
    @FXML
    private void onGuardarJSON() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Guardar JSON");
        FileChooser.ExtensionFilter extFilterJSON = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fc.getExtensionFilters().add(extFilterJSON);
        fc.setSelectedExtensionFilter(extFilterJSON);

        File f = fc.showSaveDialog(tableView.getScene().getWindow());
        if (f != null) {
            try {
                if (!f.getName().endsWith(".json")) {
                    f = new File(f.getAbsolutePath() + ".json");
                }
                manager.guardarJSON(f.getAbsolutePath());
            } catch (IOException ex) {
                mostrarAlerta("Error IO", ex.getMessage());
            }
        }
    }

    /**
     * Serializa los productos actuales y los guarda en un archivo binario.
     */
    @FXML
    private void onSerializar() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Serializar");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Binary files (*.bin, *.dat)", "*.bin", "*.dat");
        fc.getExtensionFilters().add(extFilter);

        File f = fc.showSaveDialog(tableView.getScene().getWindow());
        if (f != null) {
            if (!f.getName().endsWith(".bin") && !f.getName().endsWith(".dat")) {
                f = new File(f.getAbsolutePath() + ".bin");
            }
            try {
                manager.serializar(f.getAbsolutePath());
            } catch (IOException ex) {
                mostrarAlerta("Error IO", ex.getMessage());
            }
        }
    }

    /**
     * Deserializa un archivo binario y actualiza la tabla con los productos cargados.
     */
    @FXML
    private void onDeserializar() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Deserializar");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Binary files (*.bin, *.dat)", "*.bin", "*.dat");
        fc.getExtensionFilters().add(extFilter);

        File f = fc.showOpenDialog(tableView.getScene().getWindow());
        if (f != null) {
            try {
                manager.deserializar(f.getAbsolutePath());
                actualizarTabla();
            } catch (IOException | ClassNotFoundException ex) {
                mostrarAlerta("Error", ex.getMessage());
            }
        }
    }

    /**
     * Exporta un archivo de texto con los productos filtrados según la selección del usuario.
     */
    @FXML
    private void onExportarTXT() {
        ChoiceDialog<String> dialog = new ChoiceDialog<>("Precio < 100", "Precio < 100", "Solo Alimentos", "Solo Electrónicos", "Solo Ropa");
        dialog.setTitle("Seleccionar Filtro");
        dialog.setHeaderText("Selecciona un filtro para exportar los productos:");
        dialog.setContentText("Filtro:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            List<Producto> filtrados = new ArrayList<>();

            switch (result.get()) {
                case "Precio < 100":
                    filtrados.addAll(manager.filtrar(p -> p.getPrecio() < 100));
                    break;
                case "Solo Alimentos":
                    filtrados.addAll(manager.filtrarPorTipo(manager.obtenerTodos(), Alimento.class));
                    break;
                case "Solo Electrónicos":
                    filtrados.addAll(manager.filtrarPorTipo(manager.obtenerTodos(), Electronico.class));
                    break;
                case "Solo Ropa":
                    filtrados.addAll(manager.filtrarPorTipo(manager.obtenerTodos(), Ropa.class));
                    break;
                default:
                    filtrados.addAll(manager.obtenerTodos());
            }

            FileChooser fc = new FileChooser();
            fc.setTitle("Exportar TXT");
            FileChooser.ExtensionFilter extFilterTXT = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
            fc.getExtensionFilters().add(extFilterTXT);
            fc.setSelectedExtensionFilter(extFilterTXT);

            File f = fc.showSaveDialog(tableView.getScene().getWindow());
            if (f != null) {
                try {
                    if (!f.getName().endsWith(".txt")) {
                        f = new File(f.getAbsolutePath() + ".txt");
                    }
                    manager.exportarTxt(f.getAbsolutePath(), filtrados);
                } catch (IOException ex) {
                    mostrarAlerta("Error IO", ex.getMessage());
                }
            }
        }
    }
}
