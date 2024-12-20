/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;
import Interfaces.ProductoManager;
import model.Producto.Producto;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 * Clase de ayuda para abrir ventanas modales en la aplicación.
 * Proporciona métodos estáticos para cargar y mostrar las vistas de agregar y editar productos.
 * 
 * @author joaxx
 */
public class AbrirVentanaHelper {

    /**
     * Abre la vista para agregar un nuevo producto y retorna el controlador asociado.
     * La ventana es modal, lo que significa que bloquea la interacción con otras ventanas hasta que se cierra.
     *
     * @param manager el gestor de productos que se pasa al controlador.
     * @return el controlador de la vista de agregar producto, o null si ocurre un error.
     */
    public static AgregarProductoController abrirAgregarProductoView(ProductoManager manager) {
        try {
            // Carga el archivo FXML para la vista de agregar producto
            FXMLLoader loader = new FXMLLoader(AbrirVentanaHelper.class.getResource("/views/AgregarProductoView.fxml"));
            Parent root = loader.load();

            // Obtiene el controlador asociado y configura el gestor
            AgregarProductoController ctrl = loader.getController();
            ctrl.setManager(manager);

            // Configura la ventana modal
            Stage stage = new Stage();
            stage.setTitle("Agregar Producto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Hace que la ventana sea modal
            stage.showAndWait(); // Espera a que la ventana se cierre antes de continuar

            return ctrl;
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la excepción en la consola para depuración
            return null;
        }
    }

    /**
     * Abre la vista para editar un producto existente y retorna el controlador asociado.
     * La ventana es modal y permite al usuario modificar los datos de un producto.
     *
     * @param manager el gestor de productos que se pasa al controlador.
     * @param p el producto que se desea editar.
     * @return el controlador de la vista de editar producto, o null si ocurre un error.
     */
    public static EditarProductoController abrirEditarProductoView(ProductoManager manager, Producto p) {
        try {
            // Carga el archivo FXML para la vista de editar producto
            FXMLLoader loader = new FXMLLoader(AbrirVentanaHelper.class.getResource("/views/EditarProductoView.fxml"));
            Parent root = loader.load();

            // Obtiene el controlador asociado, configura el gestor y carga los datos del producto
            EditarProductoController ctrl = loader.getController();
            ctrl.setManager(manager);
            ctrl.cargarDatosProducto(p);

            // Configura la ventana modal
            Stage stage = new Stage();
            stage.setTitle("Editar Producto");
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL); // Hace que la ventana sea modal
            stage.showAndWait(); // Espera a que la ventana se cierre antes de continuar

            return ctrl;
        } catch (Exception e) {
            e.printStackTrace(); // Imprime la excepción en la consola para depuración
            return null;
        }
    }
}
