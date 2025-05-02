
package com.carsmotors.main;

import com.carsmotors.view.MainFrame;

import javax.swing.*;

/**
 * Clase principal para iniciar la aplicación
 */
public class Main {
    public static void main(String[] args) {
        // Establecer el Look and Feel del sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Iniciar la aplicación en el Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame mainFrame = new MainFrame();
                mainFrame.setVisible(true);
            }
        });
    }
}

