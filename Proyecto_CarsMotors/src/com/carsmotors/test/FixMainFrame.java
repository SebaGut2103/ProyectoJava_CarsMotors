package com.carsmotors.test;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;

/**
 * Clase para arreglar problemas con MainFrame
 */
public class FixMainFrame {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("Iniciando diagnóstico de MainFrame...");
                
                // Verificar la existencia de las clases principales
                verificarClase("com.carsmotors.view.MainFrame");
                verificarClase("com.carsmotors.view.ClientesPanel");
                verificarClase("com.carsmotors.view.ProveedoresPanel");
                verificarClase("com.carsmotors.view.FacturasPanel");
                verificarClase("com.carsmotors.view.OrdenesServicioPanel");
                verificarClase("com.carsmotors.view.RepuestosPanel");
                
                // Verificar la conexión a la base de datos
                verificarClase("com.carsmotors.database.DatabaseConnection");
                
                // Intentar crear una instancia de MainFrame
                System.out.println("Creando instancia de MainFrame...");
                Class<?> mainFrameClass = Class.forName("com.carsmotors.view.MainFrame");
                Object mainFrame = mainFrameClass.newInstance();
                System.out.println("Instancia de MainFrame creada exitosamente");
                
                // Verificar que sea un JFrame
                if (mainFrame instanceof JFrame) {
                    JFrame frame = (JFrame) mainFrame;
                    frame.setVisible(true);
                    System.out.println("MainFrame mostrado exitosamente");
                } else {
                    System.err.println("MainFrame no es un JFrame");
                }
            } catch (Exception e) {
                System.err.println("Error al diagnosticar MainFrame: " + e.getMessage());
                e.printStackTrace();
                
                // Obtener el stack trace completo
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                e.printStackTrace(pw);
                String stackTrace = sw.toString();
                
                // Mostrar mensaje de error detallado
                JTextArea textArea = new JTextArea(stackTrace);
                textArea.setEditable(false);
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(600, 400));
                
                JOptionPane.showMessageDialog(null,
                    scrollPane,
                    "Error al diagnosticar MainFrame",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    
    /**
     * Verifica si una clase existe y puede ser instanciada
     */
    private static void verificarClase(String nombreClase) {
        System.out.println("\nVerificando clase: " + nombreClase);
        
        try {
            // Intentar cargar la clase
            System.out.println("  - Intentando cargar la clase...");
            Class<?> clase = Class.forName(nombreClase);
            System.out.println("  - Clase cargada exitosamente.");
            
            // Verificar si tiene constructor sin argumentos
            System.out.println("  - Verificando constructores...");
            Constructor<?>[] constructores = clase.getConstructors();
            boolean tieneConstructorSinArgs = false;
            
            for (Constructor<?> constructor : constructores) {
                if (constructor.getParameterCount() == 0) {
                    tieneConstructorSinArgs = true;
                    break;
                }
            }
            
            if (tieneConstructorSinArgs) {
                System.out.println("  - Tiene constructor sin argumentos.");
                
                // Intentar crear una instancia
                System.out.println("  - Intentando crear una instancia...");
                Object instancia = clase.newInstance();
                System.out.println("  - Instancia creada exitosamente: " + instancia.getClass().getName());
            } else {
                System.out.println("  - ADVERTENCIA: No tiene constructor sin argumentos.");
                System.out.println("  - Constructores disponibles:");
                
                for (Constructor<?> constructor : constructores) {
                    System.out.println("    * " + constructor);
                }
            }
            
        } catch (ClassNotFoundException e) {
            System.out.println("  - ERROR: Clase no encontrada.");
            System.out.println("  - Detalles: " + e.getMessage());
        } catch (InstantiationException e) {
            System.out.println("  - ERROR: No se puede instanciar la clase.");
            System.out.println("  - Detalles: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println("  - ERROR: Acceso ilegal al constructor.");
            System.out.println("  - Detalles: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("  - ERROR: Excepción al verificar la clase.");
            System.out.println("  - Tipo: " + e.getClass().getName());
            System.out.println("  - Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
