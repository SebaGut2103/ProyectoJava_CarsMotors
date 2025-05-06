package com.carsmotors.utils;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase para gestionar los sonidos de la aplicación
 */
public class SoundManager {
    // Constantes para los sonidos
    public static final String SOUND_BUTTON_CLICK = "button_click";
    public static final String SOUND_SUCCESS = "success";
    public static final String SOUND_ERROR = "error";
    public static final String SOUND_WINDOW_OPEN = "window_open";
    public static final String SOUND_WINDOW_CLOSE = "window_close";
    
    // Instancia única (patrón Singleton)
    private static SoundManager instance;
    
    // Mapa de clips de sonido
    private Map<String, Clip> soundClips;
    
    // Volumen (0.0 a 1.0)
    private float volume = 0.5f;
    
    // Habilitar/deshabilitar sonidos
    private boolean soundEnabled = true;
    
    /**
     * Constructor privado
     */
    private SoundManager() {
        soundClips = new HashMap<>();
        loadSounds();
    }
    
    /**
     * Obtiene la instancia única
     * @return Instancia de SoundManager
     */
    public static synchronized SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }
    
    /**
     * Carga los sonidos
     */
    private void loadSounds() {
        try {
            // Crear directorio de sonidos si no existe
            File soundDir = new File("sounds");
            if (!soundDir.exists()) {
                soundDir.mkdir();
            }
            
            // Cargar sonidos
            loadSound(SOUND_BUTTON_CLICK, "sounds/button_click.wav");
            loadSound(SOUND_SUCCESS, "sounds/success.wav");
            loadSound(SOUND_ERROR, "sounds/error.wav");
            loadSound(SOUND_WINDOW_OPEN, "sounds/window_open.wav");
            loadSound(SOUND_WINDOW_CLOSE, "sounds/window_close.wav");
            
        } catch (Exception e) {
            System.err.println("Error al cargar sonidos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Carga un sonido
     * @param soundName Nombre del sonido
     * @param filePath Ruta del archivo
     */
    private void loadSound(String soundName, String filePath) {
        try {
            File soundFile = new File(filePath);
            if (soundFile.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                soundClips.put(soundName, clip);
            } else {
                System.err.println("Archivo de sonido no encontrado: " + filePath);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar sonido " + soundName + ": " + e.getMessage());
        }
    }
    
    /**
     * Reproduce un sonido
     * @param soundName Nombre del sonido
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
                
                // Ajustar volumen
                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float range = gainControl.getMaximum() - gainControl.getMinimum();
                    float gain = (range * volume) + gainControl.getMinimum();
                    gainControl.setValue(gain);
                }
                
                clip.start();
            } catch (Exception e) {
                System.err.println("Error al reproducir sonido " + soundName + ": " + e.getMessage());
            }
        }
    }
    
    /**
     * Establece el volumen
     * @param volume Volumen (0.0 a 1.0)
     */
    public void setVolume(float volume) {
        this.volume = Math.max(0.0f, Math.min(1.0f, volume));
    }
    
    /**
     * Habilita o deshabilita los sonidos
     * @param enabled true para habilitar, false para deshabilitar
     */
    public void setSoundEnabled(boolean enabled) {
        this.soundEnabled = enabled;
    }
    
    /**
     * Limpia los recursos
     */
    public void cleanup() {
        for (Clip clip : soundClips.values()) {
            if (clip != null) {
                clip.close();
            }
        }
        soundClips.clear();
    }
}

