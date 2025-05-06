package com.carsmotors.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class BackgroundPanel extends JPanel {
    private BufferedImage backgroundImage;
    private int style;
    
    
    public static final int STYLE_TILED = 0;
    public static final int STYLE_SCALED = 1;
    public static final int STYLE_ACTUAL = 2;
    
    
    public BackgroundPanel(String imagePath) {
        this(imagePath, STYLE_SCALED);
    }
    
    
 
    public BackgroundPanel(String imagePath, int style) {
        this.style = style;
        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                backgroundImage = ImageIO.read(imageFile);
            } else {
                
                backgroundImage = ImageIO.read(getClass().getClassLoader().getResource(imagePath));
            }
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
           
            backgroundImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        }
        
        setLayout(new BorderLayout());
    }
    
    
    public BackgroundPanel(BufferedImage image) {
        this(image, STYLE_SCALED);
    }
    
    
    public BackgroundPanel(BufferedImage image, int style) {
        this.backgroundImage = image;
        this.style = style;
        setLayout(new BorderLayout());
    }
    
   
    public void setBackgroundImage(BufferedImage image) {
        this.backgroundImage = image;
        repaint();
    }
    
    
    public void setStyle(int style) {
        this.style = style;
        repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (backgroundImage == null) {
            return;
        }
        
        Graphics2D g2d = (Graphics2D) g.create();
        
       
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        switch (style) {
            case STYLE_TILED:
               
                int width = getWidth();
                int height = getHeight();
                int imageWidth = backgroundImage.getWidth();
                int imageHeight = backgroundImage.getHeight();
                
                for (int x = 0; x < width; x += imageWidth) {
                    for (int y = 0; y < height; y += imageHeight) {
                        g2d.drawImage(backgroundImage, x, y, this);
                    }
                }
                break;
                
            case STYLE_SCALED:
                
                g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                break;
                
            case STYLE_ACTUAL:
                
                int x = (getWidth() - backgroundImage.getWidth()) / 2;
                int y = (getHeight() - backgroundImage.getHeight()) / 2;
                g2d.drawImage(backgroundImage, x, y, this);
                break;
        }
        
        g2d.dispose();
    }
}
