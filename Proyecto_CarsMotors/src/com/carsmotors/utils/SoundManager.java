package com.carsmotors.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase para gestionar los sonidos de la aplicación
 */
public class SoundManager {
    // Singleton
    private static SoundManager instance;
    
    // Mapa para almacenar clips de sonido precargados
    private Map<String, Clip> soundClips;
    
    // Control de volumen
    private float volume = 1.0f;
    
    // Control para activar/desactivar sonidos
    private boolean soundEnabled = true;
    
    // Constantes para los nombres de los sonidos
    public static final String SOUND_BUTTON_CLICK = "button_click";
    public static final String SOUND_WINDOW_OPEN = "window_open";
    public static final String SOUND_WINDOW_CLOSE = "window_close";
    public static final String SOUND_ERROR = "error";
    public static final String SOUND_SUCCESS = "success";
    public static final String SOUND_NOTIFICATION = "notification";
    
    private SoundManager() {
        soundClips = new HashMap<>();
        initializeSounds();
    }
    
    public static synchronized SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }
    
    /**
     * Inicializa y precarga los sonidos comunes
     */
    private void initializeSounds() {
        try {
            // Cargar sonidos desde archivos
            // Nota: Estos archivos deben existir en la ruta especificada
            loadSound(SOUND_BUTTON_CLICK, "sounds/button_click.wav");
            loadSound(SOUND_WINDOW_OPEN, "sounds/window_open.wav");
            loadSound(SOUND_WINDOW_CLOSE, "sounds/window_close.wav");
            loadSound(SOUND_ERROR, "sounds/error.wav");
            loadSound(SOUND_SUCCESS, "sounds/success.wav");
            loadSound(SOUND_NOTIFICATION, "sounds/notification.wav");
        } catch (Exception e) {
            System.err.println("Error al inicializar sonidos: " + e.getMessage());
        }
    }
    
    /**
     * Carga un sonido desde un archivo
     */
    public void loadSound(String soundName, String filePath) {
        try {
            // Intentar cargar desde el sistema de archivos
            File soundFile = new File(filePath);
            AudioInputStream audioInputStream;
            
            if (soundFile.exists()) {
                audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            } else {
                // Si no existe, intentar cargar desde recursos
                URL url = getClass().getClassLoader().getResource(filePath);
                if (url != null) {
                    audioInputStream = AudioSystem.getAudioInputStream(url);
                } else {
                    System.err.println("No se pudo encontrar el archivo de sonido: " + filePath);
                    return;
                }
            }
            
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            
            // Guardar el clip en el mapa
            soundClips.put(soundName, clip);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error al cargar el sonido " + soundName + ": " + e.getMessage());
        }
    }
    
    /**
     * Reproduce un sonido por su nombre
     */
    public void playSound(String soundName) {
        if (!soundEnabled) {
            return;
        }
        
        Clip clip = soundClips.get(soundName);
        if (clip != null) {
            try {
                if (clip.isRunning()) {
                    clip.stop();
                }
                clip.setFramePosition(0);
                
                // Ajustar volumen si es necesario
                if (volume != 1.0f) {
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                    gainControl.setValue(dB);
                }
                
                clip.start();
            } catch (Exception e) {
                System.err.println("Error al reproducir el sonido " + soundName + ": " + e.getMessage());
            }
        } else {
            System.err.println("El sonido " + soundName + " no está cargado");
        }
    }
    
    /**
     * Reproduce un sonido desde un archivo sin precargarlo
     */
    public void playSound(File soundFile) {
        if (!soundEnabled) {
            return;
        }
        
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            
            // Ajustar volumen si es necesario
            if (volume != 1.0f) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
            }
            
            clip.start();
        } catch (Exception e) {
            System.err.println("Error al reproducir el sonido desde archivo: " + e.getMessage());
        }
    }
    
    /**
     * Detiene un sonido específico
     */
    public void stopSound(String soundName) {
        Clip clip = soundClips.get(soundName);
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
    
    /**
     * Detiene todos los sonidos
     */
    public void stopAllSounds() {
        for (Clip clip : soundClips.values()) {
            if (clip.isRunning()) {
                clip.stop();
            }
        }
    }
    
    /**
     * Establece el volumen (0.0 a 1.0)
     */
    public void setVolume(float volume) {
        if (volume < 0.0f) {
            volume = 0.0f;
        } else if (volume > 1.0f) {
            volume = 1.0f;
        }
        this.volume = volume;
    }
    
    /**
     * Obtiene el volumen actual
     */
    public float getVolume() {
        return volume;
    }
    
    /**
     * Activa o desactiva los sonidos
     */
    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
        if (!enabled) {
            stopAllSounds();
        }
    }
    
    /**
     * Verifica si los sonidos están activados
     */
    public boolean isSoundEnabled() {
        return soundEnabled;
    }
    
    /**
     * Libera los recursos al cerrar la aplicación
     */
    public void cleanup() {
        stopAllSounds();
        for (Clip clip : soundClips.values()) {
            clip.close();
        }
        soundClips.clear();
    }
}
