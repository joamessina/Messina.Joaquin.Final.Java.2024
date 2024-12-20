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
 * Controlador para la vista de edición de productos.
 * Permite editar las propiedades de un producto, gestionando los campos específicos según su tipo.
 * 
 * @author joaxx
 */
public class EditarProductoController {

    // Campos generales del producto
    @FXML private TextField txtId; // Campo de texto para el ID del producto
    @FXML private TextField txtNombre; // Campo de texto para el nombre del producto
    @FXML private TextField txtPrecio; // Campo de texto para el precio del producto

    // Información específica del producto
    @FXML private Label lblTipoProducto; // Etiqueta que muestra el tipo del producto
    @FXML private VBox vboxEspecifico; // Contenedor para los campos específicos según el tipo de producto

    // Controles específicos para Alimento
    private ComboBox<TipoAlimento> cbTipoAlimento = new ComboBox<>();
    private TextField txtCalorias = new TextField();

    // Controles específicos para Electrónico
    private ComboBox<MarcaElectronicos> cbMarca = new ComboBox<>();
    private TextField txtGarantia = new TextField();

    // Controles específicos para Ropa
    private ComboBox<TallaRopa> cbTalla = new ComboBox<>();
    private TextField txtMaterial = new TextField();

    // Atributos del controlador
    private ProductoManager manager; // Gestor que maneja las operaciones del inventario
    private Producto productoOriginal; // Producto que se está editando
    private boolean productoEditado = false; // Indica si el producto fue editado

    /**
     * Establece el gestor del inventario para realizar las operaciones necesarias.
     *
     * @param manager el gestor del inventario.
     */
    public void setManager(ProductoManager manager) {
        this.manager = manager;
    }

    /**
     * Carga los datos de un producto en los campos de la vista.
     * También configura los campos específicos según el tipo de producto.
     *
     * @param p el producto cuyos datos serán cargados.
     */
    public void cargarDatosProducto(Producto p) {
        this.productoOriginal = p;
        txtId.setText(String.valueOf(p.getId()));
        txtNombre.setText(p.getNombre());
        txtPrecio.setText(String.valueOf(p.getPrecio()));

        if (p instanceof Alimento) {
            Alimento a = (Alimento) p;
            lblTipoProducto.setText("Alimento");
            cbTipoAlimento.getItems().setAll(TipoAlimento.PERECEDERO, TipoAlimento.NO_PERECEDERO);
            cbTipoAlimento.setValue(a.getTipo());
            txtCalorias.setText(String.valueOf(a.getCalorias()));
            vboxEspecifico.getChildren().setAll(
                new Label("Tipo Alimento:"), cbTipoAlimento,
                new Label("Calorías:"), txtCalorias
            );
        } else if (p instanceof Electronico) {
            Electronico e = (Electronico) p;
            lblTipoProducto.setText("Electrónico");
            cbMarca.getItems().setAll(MarcaElectronicos.SAMSUNG, MarcaElectronicos.APPLE, MarcaElectronicos.SONY);
            cbMarca.setValue(e.getMarca());
            txtGarantia.setText(String.valueOf(e.getGarantiaMeses()));
            vboxEspecifico.getChildren().setAll(
                new Label("Marca:"), cbMarca,
                new Label("Garantía:"), txtGarantia
            );
        } else if (p instanceof Ropa) {
            Ropa r = (Ropa) p;
            lblTipoProducto.setText("Ropa");
            cbTalla.getItems().setAll(TallaRopa.XS, TallaRopa.S, TallaRopa.M, TallaRopa.L, TallaRopa.XL);
            cbTalla.setValue(r.getTalla());
            txtMaterial.setText(r.getMaterial());
            vboxEspecifico.getChildren().setAll(
                new Label("Talla:"), cbTalla,
                new Label("Material:"), txtMaterial
            );
        } else {
            lblTipoProducto.setText("Desconocido");
        }
    }

    /**
     * Guarda los cambios realizados al producto.
     * Valida los datos, reconstruye el producto y lo actualiza en el gestor.
     */
    @FXML
    private void onGuardar() {
        try {
            String nombre = txtNombre.getText();
            double precio = Double.parseDouble(txtPrecio.getText());

            Producto pNuevo;

            if (productoOriginal instanceof Alimento) {
                TipoAlimento tipo = cbTipoAlimento.getValue() != null ? cbTipoAlimento.getValue() : TipoAlimento.NO_PERECEDERO;
                int cal = txtCalorias.getText().isEmpty() ? 100 : Integer.parseInt(txtCalorias.getText());
                pNuevo = new Alimento(productoOriginal.getId(), nombre, precio, tipo, cal);
            } else if (productoOriginal instanceof Electronico) {
                MarcaElectronicos marca = cbMarca.getValue() != null ? cbMarca.getValue() : MarcaElectronicos.SAMSUNG;
                int gar = txtGarantia.getText().isEmpty() ? 12 : Integer.parseInt(txtGarantia.getText());
                pNuevo = new Electronico(productoOriginal.getId(), nombre, precio, marca, gar);
            } else if (productoOriginal instanceof Ropa) {
                TallaRopa talla = cbTalla.getValue() != null ? cbTalla.getValue() : TallaRopa.M;
                String mat = txtMaterial.getText().isEmpty() ? "Algodón" : txtMaterial.getText();
                pNuevo = new Ropa(productoOriginal.getId(), nombre, precio, talla, mat);
            } else {
                mostrarAlerta("Error", "Tipo de producto desconocido.");
                return;
            }

            manager.actualizar(pNuevo);
            productoEditado = true;
            cerrarVentana();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Error de Formato", "Precio, garantías, calorías deben ser numéricos.");
        } catch (PrecioInvalidoException ex) {
            mostrarAlerta("Precio inválido", ex.getMessage());
        } catch (Exception ex) {
            mostrarAlerta("Error", ex.getMessage());
        }
    }

    /**
     * Cierra la ventana sin realizar cambios.
     */
    @FXML
    private void onCancelar() {
        cerrarVentana();
    }

    /**
     * Cierra la ventana actual.
     */
    private void cerrarVentana() {
        txtId.getScene().getWindow().hide();
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
     * Verifica si el producto fue editado exitosamente.
     *
     * @return true si el producto fue editado, false en caso contrario.
     */
    public boolean isProductoEditado() {
        return productoEditado;
    }
}
