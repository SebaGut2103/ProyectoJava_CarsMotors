package com.carsmotors.test;

import javax.swing.*;
import java.awt.*;
import com.carsmotors.view.ClientesPanel;

/**
 * Clase para probar específicamente el panel de clientes
 */
public class TestClientesPanel {
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("Iniciando prueba de ClientesPanel...");
                
                // Crear ventana de prueba
                JFrame frame = new JFrame("Prueba de ClientesPanel");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(1000, 600);
                
                // Intentar crear una instancia de ClientesPanel
                System.out.println("Creando instancia de ClientesPanel...");
                ClientesPanel clientesPanel = new ClientesPanel();
                System.out.println("Instancia de ClientesPanel creada exitosamente");
                
                // Agregar el panel a la ventana
                frame.getContentPane().add(clientesPanel, BorderLayout.CENTER);
                
                // Mostrar la ventana
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                
                System.out.println("Ventana de prueba mostrada exitosamente");
            } catch (Exception e) {
                System.err.println("Error al probar ClientesPanel: " + e.getMessage());
                e.printStackTrace();
                
                // Mostrar mensaje de error
                JOptionPane.showMessageDialog(null,
                    "Error al probar ClientesPanel: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
