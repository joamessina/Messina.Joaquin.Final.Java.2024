/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;
import Interfaces.ProductoManager;
import exceptions.PrecioInvalidoException;
import model.Producto.*;
import model.enums.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
/**
 * Controlador para la vista de agregar un nuevo producto.
 * Permite al usuario crear un nuevo producto, manejando campos específicos según su tipo.
 * 
 * @author joaxx
 */
public class AgregarProductoController {

    // Campos generales del producto
    @FXML private TextField txtNombre; // Campo de texto para el nombre del producto
    @FXML private TextField txtPrecio; // Campo de texto para el precio del producto

    // Botones de selección de tipo de producto
    @FXML private RadioButton rbAlimento; // RadioButton para seleccionar un producto de tipo Alimento
    @FXML private RadioButton rbElectronico; // RadioButton para seleccionar un producto de tipo Electrónico
    @FXML private RadioButton rbRopa; // RadioButton para seleccionar un producto de tipo Ropa

    @FXML private VBox vboxEspecifico; // Contenedor para los campos específicos según el tipo de producto

    private ToggleGroup tgTipo = new ToggleGroup(); // Grupo para los botones de selección de tipo

    // Controles específicos para cada tipo de producto
    private ComboBox<TipoAlimento> cbTipoAlimento = new ComboBox<>();
    private TextField txtCalorias = new TextField();

    private ComboBox<MarcaElectronicos> cbMarca = new ComboBox<>();
    private TextField txtGarantia = new TextField();

    private ComboBox<TallaRopa> cbTalla = new ComboBox<>();
    private TextField txtMaterial = new TextField();

    // Atributos del controlador
    private ProductoManager manager; // Gestor para manejar operaciones de inventario
    private boolean productoAgregado = false; // Indica si el producto fue agregado correctamente

    /**
     * Establece el gestor del inventario para realizar operaciones.
     *
     * @param manager el gestor de productos.
     */
    public void setManager(ProductoManager manager) {
        this.manager = manager;
    }

    /**
     * Inicializa el controlador configurando los elementos de la vista.
     * Asocia los RadioButtons al ToggleGroup y prepara los campos específicos para cada tipo de producto.
     */
    @FXML
    public void initialize() {
        rbAlimento.setToggleGroup(tgTipo);
        rbElectronico.setToggleGroup(tgTipo);
        rbRopa.setToggleGroup(tgTipo);

        rbAlimento.setSelected(true); // Selecciona Alimento como tipo predeterminado

        // Inicializa las opciones de los ComboBox
        cbTipoAlimento.getItems().addAll(TipoAlimento.PERECEDERO, TipoAlimento.NO_PERECEDERO);
        cbMarca.getItems().addAll(MarcaElectronicos.SAMSUNG, MarcaElectronicos.APPLE, MarcaElectronicos.SONY);
        cbTalla.getItems().addAll(TallaRopa.XS, TallaRopa.S, TallaRopa.M, TallaRopa.L, TallaRopa.XL);

        actualizarCamposTipo();

        // Cambia los campos específicos al cambiar de tipo de producto
        tgTipo.selectedToggleProperty().addListener((obs, old, nuevo) -> {
            actualizarCamposTipo();
        });
    }

    /**
     * Actualiza los campos específicos mostrados según el tipo de producto seleccionado.
     */
    private void actualizarCamposTipo() {
        vboxEspecifico.getChildren().clear();
        if (rbAlimento.isSelected()) {
            txtCalorias.setPromptText("Calorías");
            vboxEspecifico.getChildren().addAll(new Label("Tipo Alimento:"), cbTipoAlimento, new Label("Calorías:"), txtCalorias);
        } else if (rbElectronico.isSelected()) {
            txtGarantia.setPromptText("Garantía (meses)");
            vboxEspecifico.getChildren().addAll(new Label("Marca:"), cbMarca, new Label("Garantía:"), txtGarantia);
        } else if (rbRopa.isSelected()) {
            txtMaterial.setPromptText("Material");
            vboxEspecifico.getChildren().addAll(new Label("Talla:"), cbTalla, new Label("Material:"), txtMaterial);
        }
    }

    /**
     * Agrega un nuevo producto basado en los datos ingresados.
     * Valida los datos y utiliza el gestor para añadir el producto al inventario.
     */
    @FXML
    private void onAgregar() {
        try {
            int id = 0; // ID temporal, se asignará automáticamente en el manager
            String nombre = txtNombre.getText();
            double precio = Double.parseDouble(txtPrecio.getText());

            Producto p;

            if (rbAlimento.isSelected()) {
                TipoAlimento ta = cbTipoAlimento.getValue() != null ? cbTipoAlimento.getValue() : TipoAlimento.NO_PERECEDERO;
                int cal = txtCalorias.getText().isEmpty() ? 100 : Integer.parseInt(txtCalorias.getText());
                p = new Alimento(id, nombre, precio, ta, cal);
            } else if (rbElectronico.isSelected()) {
                MarcaElectronicos marca = cbMarca.getValue() != null ? cbMarca.getValue() : MarcaElectronicos.SAMSUNG;
                int gar = txtGarantia.getText().isEmpty() ? 12 : Integer.parseInt(txtGarantia.getText());
                p = new Electronico(id, nombre, precio, marca, gar);
            } else {
                TallaRopa talla = cbTalla.getValue() != null ? cbTalla.getValue() : TallaRopa.M;
                String mat = txtMaterial.getText().isEmpty() ? "Algodón" : txtMaterial.getText();
                p = new Ropa(id, nombre, precio, talla, mat);
            }

            manager.agregar(p); // Agrega el producto al inventario
            productoAgregado = true;
            cerrarVentana(); // Cierra la ventana al completar la operación
        } catch (NumberFormatException ex) {
            mostrarAlerta("Error de Formato", "ID, Precio y otros campos numéricos deben ser válidos.");
        } catch (PrecioInvalidoException ex) {
            mostrarAlerta("Precio inválido", ex.getMessage());
        } catch (Exception ex) {
            mostrarAlerta("Error", ex.getMessage());
        }
    }

    /**
     * Cancela la operación de agregar un producto y cierra la ventana.
     */
    @FXML
    private void onCancelar() {
        cerrarVentana();
    }

    /**
     * Cierra la ventana actual.
     */
    private void cerrarVentana() {
        txtNombre.getScene().getWindow().hide();
    }

    /**
     * Muestra un cuadro de diálogo con un mensaje de error.
     *
     * @param titulo el título del cuadro de diálogo.
     * @param msg el mensaje de error.
     */
    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.setTitle(titulo);
        alert.showAndWait();
    }

    /**
     * Verifica si un producto fue agregado exitosamente.
     *
     * @return true si el producto fue agregado, false en caso contrario.
     */
    public boolean isProductoAgregado() {
        return productoAgregado;
    }
}
