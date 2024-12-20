# Inventario - Gestión de Productos

## Sobre mí
Hola, mi nombre es **Joaquin Messina**. Soy estudiante de desarrollo de software, apasionado por aprender y aplicar conceptos avanzados de programación orientada a objetos. Este proyecto fue desarrollado como parte de mi evaluación final en Java, abordando temas como herencia, polimorfismo, interfaces genéricas y persistencia de datos.

## Resumen
Esta aplicación es un sistema de gestión de inventario que permite manejar productos de diferentes categorías: Alimentos, Electrónicos y Ropa. Proporciona funcionalidades para agregar, editar, eliminar, ordenar, filtrar y persistir datos de productos. La interfaz gráfica está desarrollada con **JavaFX** para facilitar la interacción del usuario.

### Funcionalidades principales:
- **Gestión de productos:** Agregar, editar y eliminar productos.
- **Ordenar y filtrar:** Ordenar productos por precio o nombre, y filtrar por categorías.
- **Persistencia de datos:** Guardar y cargar productos en formatos **CSV**, **JSON** y **binarios**.
- **Exportación:** Exportar productos filtrados a un archivo **TXT** legible.
- **Iteración:** Implementación del patrón Iterator para recorrer los productos.

## Video Ejemplo del Programa
El video de demostración de la aplicación se encuentra en la raíz del proyecto con el nombre: `CrudFinalMessinaJoaquin.mp4`.

## Diagrama de clases UML
A continuación se presenta el diagrama UML que detalla la estructura del proyecto:
El archivo `UMLFINAL.PNG` se encuentra en la raíz del proyecto y detalla la arquitectura completa del sistema.

## Archivos generados
Ejemplos prácticos de cada tipo de archivo generado por la aplicación:

1. **Archivo `.dat` (binario):**
   - Se utiliza para serializar y deserializar la lista de productos.
   - Ejemplo de archivo generado: `inventario.dat`

2. **Archivo `.csv` (Valores separados por comas):**
   - Guarda los productos en formato CSV.
   - Ejemplo de contenido:
     ```csv
     1,Leche,100.0,Alimento
     2,Camiseta,200.0,Ropa
     3,Smartphone,5000.0,Electronico
     ```

3. **Archivo `.json` (Formato JSON):**
   - Guarda los productos con una estructura clara y legible.
   - Ejemplo de contenido:
     ```json
     [
       {
         "tipo": "Alimento",
         "datos": {
           "id": 1,
           "nombre": "Leche",
           "precio": 100.0,
           "tipo": "PERECEDERO",
           "calorias": 200
         }
       },
       {
         "tipo": "Ropa",
         "datos": {
           "id": 2,
           "nombre": "Camiseta",
           "precio": 200.0,
           "talla": "M",
           "material": "Algodón"
         }
       }
     ]
     ```

4. **Archivo `.txt` (Texto):**
   - Exporta productos filtrados de manera legible.
   - Ejemplo de contenido:
     ```
     Reporte de Productos Filtrados
     -----------------------------
     Leche - Precio: 100.0
     Camiseta - Precio: 200.0

---

**Nota:** El video, el diagrama UML y los archivos generados se encuentran en la raíz del proyecto para facilitar su acceso.
