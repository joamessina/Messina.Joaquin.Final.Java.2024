/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal para la aplicación JavaFX.
 * Configura y muestra la ventana principal de la aplicación.
 * 
 * @author joaxx
 */
public class MainAppFX extends Application {

    /**
     * Método de inicio de la aplicación.
     * Carga la vista principal desde el archivo FXML y la muestra en un escenario (stage).
     * 
     * @param primaryStage el escenario principal donde se cargará la interfaz gráfica.
     * @throws Exception si ocurre un error al cargar el archivo FXML.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Carga el archivo FXML para la vista principal
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainView.fxml"));
        Parent root = loader.load();

        // Configura la escena principal
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Inventario - Main"); // Establece el título de la ventana
        primaryStage.setScene(scene);
        primaryStage.show(); // Muestra la ventana principal
    }

    /**
     * Método principal de la aplicación.
     * Inicia la aplicación JavaFX.
     * 
     * @param args los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        launch(args); // Llama al método launch para iniciar la aplicación JavaFX
    }
}
