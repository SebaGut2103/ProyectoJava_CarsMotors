package com.carsmotors.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Panel con imagen de fondo
 */
public class BackgroundPanel extends JPanel {
    private BufferedImage backgroundImage;
    
    /**
     * Constructor con ruta de imagen
     * @param imagePath Ruta de la imagen
     */
    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
            e.printStackTrace();
        }
        setOpaque(false);
    }
    
    /**
     * Constructor con imagen
     * @param image Imagen de fondo
     */
    public BackgroundPanel(BufferedImage image) {
        this.backgroundImage = image;
        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (backgroundImage != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            
            // Dibujar la imagen escalada para que se ajuste al panel
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            
            g2d.dispose();
        }
    }
}

