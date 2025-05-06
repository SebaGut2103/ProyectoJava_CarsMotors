package com.carsmotors.main;

import com.carsmotors.database.DatabaseConnection;
import com.carsmotors.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;


public class Main {
    
    /**
     * Método principal
     * @param args Argumentos de línea de comandos
     */
    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Error al establecer el look and feel: " + e.getMessage());
        }
        
        
        verificarConexionBD();
        
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });
    }
    
   
    private static void verificarConexionBD() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            if (conn == null) {
                mostrarErrorConexion("No se pudo establecer la conexión a la base de datos.");
            } else {
                System.out.println("Conexión a la base de datos establecida correctamente.");
            }
        } catch (Exception e) {
            mostrarErrorConexion("Error al conectar a la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Muestra un diálogo de error de conexión
     * @param mensaje Mensaje de error
     */
    private static void mostrarErrorConexion(String mensaje) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JDialog dialog = new JDialog((Frame) null, "Error de conexión", true);
                dialog.setSize(500, 250);
                dialog.setLocationRelativeTo(null);
                
                JPanel panel = new JPanel(new BorderLayout(10, 10));
                panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
                
              
                JLabel iconLabel = new JLabel(UIManager.getIcon("OptionPane.errorIcon"));
                panel.add(iconLabel, BorderLayout.WEST);
                
              
                JPanel messagePanel = new JPanel(new BorderLayout());
                
                JLabel titleLabel = new JLabel("Error de conexión a la base de datos");
                titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
                messagePanel.add(titleLabel, BorderLayout.NORTH);
                
                JTextArea messageArea = new JTextArea(mensaje + "\n\n" +
                    "Verifique que:\n" +
                    "1. El servidor MySQL esté en ejecución\n" +
                    "2. La base de datos 'taller_automotriz' exista\n" +
                    "3. Las credenciales de acceso sean correctas\n\n" +
                    "La aplicación continuará en modo limitado.");
                messageArea.setEditable(false);
                messageArea.setLineWrap(true);
                messageArea.setWrapStyleWord(true);
                messageArea.setBackground(panel.getBackground());
                messagePanel.add(messageArea, BorderLayout.CENTER);
                
                panel.add(messagePanel, BorderLayout.CENTER);
                
                
                JButton btnAceptar = new JButton("Aceptar");
                btnAceptar.addActionListener(e -> dialog.dispose());
                
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                buttonPanel.add(btnAceptar);
                panel.add(buttonPanel, BorderLayout.SOUTH);
                
                dialog.setContentPane(panel);
                dialog.setVisible(true);
            }
        });
    }
}
