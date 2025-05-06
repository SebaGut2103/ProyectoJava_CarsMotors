package com.carsmotors.test;

/**
 * Clase para probar el ClassLoader
 */
public class ClassLoaderTest {
    
    public static void main(String[] args) {
        System.out.println("Probando ClassLoader...");
        
        // Obtener el ClassLoader actual
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println("ClassLoader: " + classLoader);
        
        // Intentar cargar algunas clases
        try {
            Class<?> clientesPanelClass = Class.forName("com.carsmotors.view.ClientesPanel");
            System.out.println("ClientesPanel cargado correctamente: " + clientesPanelClass);
            
            // Intentar crear una instancia
            Object clientesPanel = clientesPanelClass.newInstance();
            System.out.println("Instancia de ClientesPanel creada: " + clientesPanel);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Clase ClientesPanel no encontrada");
            e.printStackTrace();
        } catch (InstantiationException | IllegalAccessException e) {
            System.err.println("Error al instanciar ClientesPanel");
            e.printStackTrace();
        }
    }
}

