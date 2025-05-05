package com.carsmotors.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.prefs.Preferences;

import com.carsmotors.controller.ClienteController;
import com.carsmotors.controller.RepuestoController;
import com.carsmotors.controller.ProveedorController;
import com.carsmotors.controller.OrdenServicioController;
import com.carsmotors.controller.FacturaController;
import com.carsmotors.utils.SoundManager;
import com.carsmotors.utils.BackgroundPanel;

/**
 * Ventana principal del sistema
 */
public class MainFrame extends JFrame {
    private JPanel mainPanel;
    private JMenuBar menuBar;
    private SoundManager soundManager;
    
    /**
     * Constructor de la ventana principal
     */
    public MainFrame() {
        setTitle("Sistema Taller Automotriz CarMotors");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Inicializar el gestor de sonidos
        soundManager = SoundManager.getInstance();
        
        // Inicializar componentes
        initComponents();
        
        // Mostrar panel de inicio por defecto
        showPanel(new HomePanel());
        
        // Reproducir sonido al abrir la ventana principal
        try {
            soundManager.playSound(SoundManager.SOUND_WINDOW_OPEN);
        } catch (Exception e) {
            System.err.println("Error al reproducir sonido: " + e.getMessage());
        }
        
        // Agregar listener para reproducir sonido al cerrar la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    soundManager.playSound(SoundManager.SOUND_WINDOW_CLOSE);
                    // Dar tiempo para que se reproduzca el sonido antes de cerrar
                    Thread.sleep(500);
                } catch (Exception ex) {
                    System.err.println("Error al reproducir sonido: " + ex.getMessage());
                }
                soundManager.cleanup();
            }
        });
    }
    
    /**
     * Inicializa los componentes de la ventana
     */
    private void initComponents() {
        // Panel principal
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);
        
        // Barra de menú
        menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        // Menú Inventario
        JMenu menuInventario = new JMenu("Gestión de Inventarios");
        menuBar.add(menuInventario);
        
        JMenuItem itemRepuestos = new JMenuItem("Repuestos");
        try {
            itemRepuestos.setIcon(createSafeIcon("/images/icons/repuesto.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemRepuestos.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            showPanel(new RepuestosPanel());
        });
        menuInventario.add(itemRepuestos);
        
        JMenuItem itemReabastecer = new JMenuItem("Reabastecimiento");
        try {
            itemReabastecer.setIcon(createSafeIcon("/images/icons/reabastecer.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemReabastecer.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            showPanel(new ReabastecimientoPanel());
        });
        menuInventario.add(itemReabastecer);
        
        // Menú Mantenimiento
        JMenu menuMantenimiento = new JMenu("Mantenimiento y Reparaciones");
        menuBar.add(menuMantenimiento);
        
        JMenuItem itemOrdenesServicio = new JMenuItem("Órdenes de Servicio");
        try {
            itemOrdenesServicio.setIcon(createSafeIcon("/images/icons/orden.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemOrdenesServicio.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            showPanel(new OrdenesServicioPanel());
        });
        menuMantenimiento.add(itemOrdenesServicio);
        
        JMenuItem itemServicios = new JMenuItem("Servicios");
        try {
            itemServicios.setIcon(createSafeIcon("/images/icons/servicio.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemServicios.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            showPanel(new ServiciosPanel());
        });
        menuMantenimiento.add(itemServicios);
        
        // Menú Clientes
        JMenu menuClientes = new JMenu("Clientes y Facturación");
        menuBar.add(menuClientes);
        
        JMenuItem itemClientes = new JMenuItem("Clientes");
        try {
            itemClientes.setIcon(createSafeIcon("/images/icons/cliente.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemClientes.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            showPanel(new ClientesPanel());
        });
        menuClientes.add(itemClientes);
        
        JMenuItem itemFacturas = new JMenuItem("Facturas");
        try {
            itemFacturas.setIcon(createSafeIcon("/images/icons/factura.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemFacturas.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            showPanel(new FacturasPanel());
        });
        menuClientes.add(itemFacturas);
        
        // Menú Proveedores
        JMenu menuProveedores = new JMenu("Proveedores y Compras");
        menuBar.add(menuProveedores);
        
        JMenuItem itemProveedores = new JMenuItem("Proveedores");
        try {
            itemProveedores.setIcon(createSafeIcon("/images/icons/proveedor.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemProveedores.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            showPanel(new ProveedoresPanel());
        });
        menuProveedores.add(itemProveedores);
        
        JMenuItem itemCompras = new JMenuItem("Órdenes de Compra");
        try {
            itemCompras.setIcon(createSafeIcon("/images/icons/compra.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemCompras.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            showPanel(new OrdenesCompraPanel());
        });
        menuProveedores.add(itemCompras);
        
        // Menú Reportes
        JMenu menuReportes = new JMenu("Reportes y Estadísticas");
        menuBar.add(menuReportes);
        
        JMenuItem itemReporteInventario = new JMenuItem("Reporte de Inventario");
        try {
            itemReporteInventario.setIcon(createSafeIcon("/images/icons/reporte_inventario.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemReporteInventario.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            showPanel(new ReporteInventarioPanel());
        });
        menuReportes.add(itemReporteInventario);
        
        JMenuItem itemReporteServicios = new JMenuItem("Reporte de Servicios");
        try {
            itemReporteServicios.setIcon(createSafeIcon("/images/icons/reporte_servicios.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemReporteServicios.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            showPanel(new ReporteServiciosPanel());
        });
        menuReportes.add(itemReporteServicios);
        
        JMenuItem itemReporteVentas = new JMenuItem("Reporte de Ventas");
        try {
            itemReporteVentas.setIcon(createSafeIcon("/images/icons/reporte_ventas.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemReporteVentas.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            showPanel(new ReporteVentasPanel());
        });
        menuReportes.add(itemReporteVentas);
        
        JMenuItem itemReporteProveedores = new JMenuItem("Reporte de Proveedores");
        try {
            itemReporteProveedores.setIcon(createSafeIcon("/images/icons/reporte_proveedores.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemReporteProveedores.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            showPanel(new ReporteProveedoresPanel());
        });
        menuReportes.add(itemReporteProveedores);
        
        // Menú Configuración
        JMenu menuConfiguracion = new JMenu("Configuración");
        menuBar.add(menuConfiguracion);
        
        JMenuItem itemSonido = new JMenuItem("Configuración de Sonido");
        try {
            itemSonido.setIcon(createSafeIcon("/images/icons/sound.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemSonido.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            mostrarConfiguracionSonido();
        });
        menuConfiguracion.add(itemSonido);
        
        JMenuItem itemApariencia = new JMenuItem("Cambiar Imagen de Fondo");
        try {
            itemApariencia.setIcon(createSafeIcon("/images/icons/background.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemApariencia.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            cambiarImagenFondo();
        });
        menuConfiguracion.add(itemApariencia);
        
        JMenuItem itemBackup = new JMenuItem("Realizar Copia de Seguridad");
        try {
            itemBackup.setIcon(createSafeIcon("/images/icons/backup.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemBackup.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            realizarBackup();
        });
        menuConfiguracion.add(itemBackup);
        
        // Menú Ayuda
        JMenu menuAyuda = new JMenu("Ayuda");
        menuBar.add(menuAyuda);
        
        JMenuItem itemManual = new JMenuItem("Manual de Usuario");
        try {
            itemManual.setIcon(createSafeIcon("/images/icons/manual.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemManual.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            mostrarManualUsuario();
        });
        menuAyuda.add(itemManual);
        
        JMenuItem itemAcerca = new JMenuItem("Acerca de");
        try {
            itemAcerca.setIcon(createSafeIcon("/images/icons/about.png"));
        } catch (Exception e) {
            System.err.println("Error al cargar icono: " + e.getMessage());
        }
        itemAcerca.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            mostrarAcercaDe();
        });
        menuAyuda.add(itemAcerca);
    }
    
    /**
     * Crea un icono de manera segura, devolviendo null si hay error
     */
    private ImageIcon createSafeIcon(String path) {
        try {
            return new ImageIcon(getClass().getResource(path));
        } catch (Exception e) {
            System.err.println("No se pudo cargar el icono: " + path);
            return null;
        }
    }
    
    /**
     * Reproduce un sonido de manera segura, capturando excepciones
     */
    private void playSafeSound(String soundName) {
        try {
            soundManager.playSound(soundName);
        } catch (Exception e) {
            System.err.println("Error al reproducir sonido: " + e.getMessage());
        }
    }
    
    /**
     * Muestra un panel en el área principal
     * @param panel Panel a mostrar
     */
    private void showPanel(JPanel panel) {
        mainPanel.removeAll();
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
    
    /**
     * Muestra el diálogo de configuración de sonido
     */
    private void mostrarConfiguracionSonido() {
        // Crear un diálogo simple para configurar el sonido
        JDialog dialog = new JDialog(this, "Configuración de Sonido", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel titleLabel = new JLabel("Configuración de Sonido", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Panel de opciones
        JPanel optionsPanel = new JPanel(new GridLayout(3, 1, 5, 10));
        
        // Checkbox para habilitar/deshabilitar sonido
        JCheckBox chkSonidoHabilitado = new JCheckBox("Habilitar sonidos del sistema");
        chkSonidoHabilitado.setSelected(true);
        optionsPanel.add(chkSonidoHabilitado);
        
        // Slider para volumen
        JPanel volumePanel = new JPanel(new BorderLayout(5, 0));
        volumePanel.add(new JLabel("Volumen:"), BorderLayout.WEST);
        JSlider sliderVolumen = new JSlider(0, 100, 80);
        sliderVolumen.setMajorTickSpacing(20);
        sliderVolumen.setMinorTickSpacing(5);
        sliderVolumen.setPaintTicks(true);
        sliderVolumen.setPaintLabels(true);
        volumePanel.add(sliderVolumen, BorderLayout.CENTER);
        optionsPanel.add(volumePanel);
        
        // Botones de prueba
        JPanel testPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnProbarSonido = new JButton("Probar Sonido");
        btnProbarSonido.addActionListener(e -> {
            if (chkSonidoHabilitado.isSelected()) {
                playSafeSound(SoundManager.SOUND_SUCCESS);
            }
        });
        testPanel.add(btnProbarSonido);
        optionsPanel.add(testPanel);
        
        panel.add(optionsPanel, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            // Aquí se guardarían las preferencias
            Preferences prefs = Preferences.userNodeForPackage(MainFrame.class);
            prefs.putBoolean("sound.enabled", chkSonidoHabilitado.isSelected());
            prefs.putInt("sound.volume", sliderVolumen.getValue());
            
            JOptionPane.showMessageDialog(dialog, 
                "Configuración guardada correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> {
            playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
            dialog.dispose();
        });
        
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    /**
     * Cambia la imagen de fondo
     */
    private void cambiarImagenFondo() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Imágenes", "jpg", "jpeg", "png", "gif"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String imagePath = fileChooser.getSelectedFile().getAbsolutePath();
            // Guardar la ruta de la imagen seleccionada en las preferencias
            Preferences prefs = Preferences.userNodeForPackage(MainFrame.class);
            prefs.put("background.image", imagePath);
            
            // Mostrar el panel de inicio con la nueva imagen
            showPanel(new HomePanel());
        }
    }
    
    /**
     * Realiza una copia de seguridad de la base de datos
     */
    private void realizarBackup() {
        // Crear directorio de backups si no existe
        File backupDir = new File("backups");
        if (!backupDir.exists()) {
            backupDir.mkdir();
        }
        
        // Generar nombre de archivo con fecha y hora
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        String backupFileName = "backup_" + timestamp + ".sql";
        
        // Mostrar diálogo para seleccionar ubicación
        JFileChooser fileChooser = new JFileChooser(backupDir);
        fileChooser.setSelectedFile(new File(backupFileName));
        fileChooser.setDialogTitle("Guardar Copia de Seguridad");
        
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File backupFile = fileChooser.getSelectedFile();
            
            // Aquí iría el código para realizar el backup
            // Por ejemplo, usando ProcessBuilder para ejecutar mysqldump
            
            try {
                // Ejemplo de comando mysqldump (ajustar según configuración)
                String[] command = {
                    "mysqldump",
                    "--user=root",
                    "--password=oscar2429",
                    "taller_automotriz",
                    "-r",
                    backupFile.getAbsolutePath()
                };
                
                ProcessBuilder pb = new ProcessBuilder(command);
                Process process = pb.start();
                
                int exitCode = process.waitFor();
                
                if (exitCode == 0) {
                    playSafeSound(SoundManager.SOUND_SUCCESS);
                    JOptionPane.showMessageDialog(this,
                        "Copia de seguridad creada correctamente en:\n" + backupFile.getAbsolutePath(),
                        "Backup Exitoso",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    playSafeSound(SoundManager.SOUND_ERROR);
                    JOptionPane.showMessageDialog(this,
                        "Error al crear la copia de seguridad.\nCódigo de salida: " + exitCode,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                playSafeSound(SoundManager.SOUND_ERROR);
                JOptionPane.showMessageDialog(this,
                    "Error al crear la copia de seguridad:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Muestra el manual de usuario
     */
    private void mostrarManualUsuario() {
        // Verificar si existe el archivo del manual
        File manualFile = new File("docs/manual_usuario.pdf");
        
        if (manualFile.exists()) {
            // Intentar abrir el archivo con el visor de PDF predeterminado
            try {
                Desktop.getDesktop().open(manualFile);
            } catch (Exception e) {
                playSafeSound(SoundManager.SOUND_ERROR);
                JOptionPane.showMessageDialog(this,
                    "No se pudo abrir el manual de usuario:\n" + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            playSafeSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this,
                "El manual de usuario no se encuentra disponible.\n" +
                "Por favor, contacte al administrador del sistema.",
                "Manual no encontrado",
                JOptionPane.WARNING_MESSAGE);
        }
    }
    
    /**
     * Muestra información acerca del sistema
     */
    private void mostrarAcercaDe() {
        StringBuilder info = new StringBuilder();
        info.append("Sistema de Gestión para Taller Automotriz CarMotors\n\n");
        info.append("Versión: 1.0.0\n");
        info.append("Fecha: Mayo 2024\n\n");
        info.append("Desarrollado por: Equipo CarMotors\n\n");
        info.append("Este software permite la gestión integral de un taller automotriz,\n");
        info.append("incluyendo inventario, órdenes de servicio, facturación,\n");
        info.append("clientes, proveedores y reportes estadísticos.");
        
        ImageIcon logoIcon = null;
        try {
            logoIcon = new ImageIcon(getClass().getResource("/images/logo.png"));
        } catch (Exception e) {
            System.err.println("No se pudo cargar el logo: " + e.getMessage());
        }
        
        JOptionPane.showMessageDialog(this,
            info.toString(),
            "Acerca de CarMotors",
            JOptionPane.INFORMATION_MESSAGE,
            logoIcon);
    }
    
    /**
     * Clase interna para el panel de inicio
     */
    private class HomePanel extends JPanel {
        public HomePanel() {
            setLayout(new BorderLayout());
            
            // Obtener la ruta de la imagen de fondo desde las preferencias
            Preferences prefs = Preferences.userNodeForPackage(MainFrame.class);
            String imagePath = prefs.get("background.image", "images/background.jpg");
            
            // Crear el panel con la imagen de fondo
            JPanel backgroundPanel;
            try {
                // Verificar si el archivo existe
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    backgroundPanel = new BackgroundPanel(imagePath);
                } else {
                    // Si no existe, usar una imagen predeterminada
                    File defaultImageDir = new File("images");
                    if (!defaultImageDir.exists()) {
                        defaultImageDir.mkdir();
                    }
                    backgroundPanel = new BackgroundPanel("images/background.jpg");
                    System.out.println("Imagen de fondo no encontrada. Por favor, coloque una imagen llamada 'background.jpg' en la carpeta 'images'.");
                }
            } catch (Exception e) {
                // Si hay error, usar un panel simple
                backgroundPanel = new JPanel();
                backgroundPanel.setBackground(new Color(240, 240, 240));
                System.err.println("Error al cargar la imagen de fondo: " + e.getMessage());
            }
            
            backgroundPanel.setLayout(new BorderLayout());
            
            // Panel semitransparente para el título
            JPanel titlePanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setColor(new Color(0, 0, 0, 180)); // Negro semitransparente
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    g2d.dispose();
                    super.paintComponent(g);
                }
            };
            titlePanel.setOpaque(false);
            titlePanel.setLayout(new BorderLayout());
            
            JLabel titleLabel = new JLabel("Bienvenido al Sistema de Taller Automotriz CarMotors", JLabel.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            titlePanel.add(titleLabel, BorderLayout.CENTER);
            
            backgroundPanel.add(titlePanel, BorderLayout.NORTH);
            
            // Panel semitransparente para los botones
            JPanel buttonsOuterPanel = new JPanel(new GridBagLayout());
            buttonsOuterPanel.setOpaque(false);
            
            JPanel buttonsPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setColor(new Color(255, 255, 255, 180)); // Blanco semitransparente
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                    g2d.dispose();
                    super.paintComponent(g);
                }
            };
            buttonsPanel.setOpaque(false);
            buttonsPanel.setLayout(new GridLayout(2, 3, 20, 20));
            buttonsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // Botones de acceso rápido
            JButton btnClientes = createMenuButton("Clientes", "/images/icons/cliente.png", e -> {
                playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
                showPanel(new ClientesPanel());
            });
            
            JButton btnRepuestos = createMenuButton("Repuestos", "/images/icons/repuesto.png", e -> {
                playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
                showPanel(new RepuestosPanel());
            });
            
            JButton btnOrdenes = createMenuButton("Órdenes de Servicio", "/images/icons/orden.png", e -> {
                playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
                showPanel(new OrdenesServicioPanel());
            });
            
            JButton btnFacturas = createMenuButton("Facturas", "/images/icons/factura.png", e -> {
                playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
                showPanel(new FacturasPanel());
            });
            
            JButton btnProveedores = createMenuButton("Proveedores", "/images/icons/proveedor.png", e -> {
                playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
                showPanel(new ProveedoresPanel());
            });
            
            JButton btnReportes = createMenuButton("Reportes", "/images/icons/reporte.png", e -> {
                playSafeSound(SoundManager.SOUND_BUTTON_CLICK);
                showPanel(new ReporteInventarioPanel());
            });
            
            buttonsPanel.add(btnClientes);
            buttonsPanel.add(btnRepuestos);
            buttonsPanel.add(btnOrdenes);
            buttonsPanel.add(btnFacturas);
            buttonsPanel.add(btnProveedores);
            buttonsPanel.add(btnReportes);
            
            buttonsOuterPanel.add(buttonsPanel);
            backgroundPanel.add(buttonsOuterPanel, BorderLayout.CENTER);
            
            // Panel para información del sistema
            JPanel infoPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setColor(new Color(0, 0, 0, 150)); // Negro semitransparente  g.create();
                    g2d.setColor(new Color(0, 0, 0, 150)); // Negro semitransparente
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                    g2d.dispose();
                    super.paintComponent(g);
                }
            };
            infoPanel.setOpaque(false);
            infoPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            
            // Obtener fecha y hora actual
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String fechaHora = sdf.format(new Date());
            
            JLabel lblInfo = new JLabel("Sistema CarMotors v1.0.0 | " + fechaHora);
            lblInfo.setForeground(Color.WHITE);
            lblInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 15));
            infoPanel.add(lblInfo);
            
            backgroundPanel.add(infoPanel, BorderLayout.SOUTH);
            
            add(backgroundPanel);
        }
        
        /**
         * Crea un botón de menú personalizado
         */
        private JButton createMenuButton(String text, String iconPath, ActionListener listener) {
            JButton button = new JButton(text);
            try {
                button.setIcon(new ImageIcon(getClass().getResource(iconPath)));
            } catch (Exception e) {
                System.err.println("Error al cargar icono: " + e.getMessage());
            }
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setFont(new Font("Arial", Font.BOLD, 14));
            button.setFocusPainted(false);
            button.addActionListener(listener);
            
            // Estilo personalizado
            button.setBackground(new Color(52, 152, 219)); // Azul
            button.setForeground(Color.WHITE);
            button.setPreferredSize(new Dimension(180, 120));
            
            return button;
        }
    }
    
    /**
     * Clase interna para el panel de reabastecimiento
     * Nota: Esta es una implementación básica, se debe completar según los requisitos
     */
    private class ReabastecimientoPanel extends JPanel {
        public ReabastecimientoPanel() {
            setLayout(new BorderLayout());
            
            // Panel de título
            JPanel panelTitulo = new JPanel();
            panelTitulo.setBackground(new Color(41, 128, 185)); // Azul
            JLabel lblTitulo = new JLabel("Reabastecimiento de Inventario");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(Color.WHITE);
            panelTitulo.add(lblTitulo);
            
            add(panelTitulo, BorderLayout.NORTH);
            
            // Panel central con mensaje de implementación pendiente
            JPanel panelCentral = new JPanel(new GridBagLayout());
            JLabel lblMensaje = new JLabel("Módulo en desarrollo. Próximamente disponible.");
            lblMensaje.setFont(new Font("Arial", Font.ITALIC, 16));
            panelCentral.add(lblMensaje);
            
            add(panelCentral, BorderLayout.CENTER);
        }
    }
    
    /**
     * Clase interna para el panel de órdenes de servicio
     * Nota: Esta es una implementación básica, se debe completar según los requisitos
     */
    private class OrdenesServicioPanel extends JPanel {
        public OrdenesServicioPanel() {
            setLayout(new BorderLayout());
            
            // Panel de título
            JPanel panelTitulo = new JPanel();
            panelTitulo.setBackground(new Color(46, 204, 113)); // Verde
            JLabel lblTitulo = new JLabel("Gestión de Órdenes de Servicio");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(Color.WHITE);
            panelTitulo.add(lblTitulo);
            
            add(panelTitulo, BorderLayout.NORTH);
            
            // Panel central con mensaje de implementación pendiente
            JPanel panelCentral = new JPanel(new GridBagLayout());
            JLabel lblMensaje = new JLabel("Módulo en desarrollo. Próximamente disponible.");
            lblMensaje.setFont(new Font("Arial", Font.ITALIC, 16));
            panelCentral.add(lblMensaje);
            
            add(panelCentral, BorderLayout.CENTER);
        }
    }
    
    /**
     * Clase interna para el panel de servicios
     * Nota: Esta es una implementación básica, se debe completar según los requisitos
     */
    private class ServiciosPanel extends JPanel {
        public ServiciosPanel() {
            setLayout(new BorderLayout());
            
            // Panel de título
            JPanel panelTitulo = new JPanel();
            panelTitulo.setBackground(new Color(230, 126, 34)); // Naranja
            JLabel lblTitulo = new JLabel("Gestión de Servicios");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(Color.WHITE);
            panelTitulo.add(lblTitulo);
            
            add(panelTitulo, BorderLayout.NORTH);
            
            // Panel central con mensaje de implementación pendiente
            JPanel panelCentral = new JPanel(new GridBagLayout());
            JLabel lblMensaje = new JLabel("Módulo en desarrollo. Próximamente disponible.");
            lblMensaje.setFont(new Font("Arial", Font.ITALIC, 16));
            panelCentral.add(lblMensaje);
            
            add(panelCentral, BorderLayout.CENTER);
        }
    }
    
    /**
     * Clase interna para el panel de clientes
     * Nota: Esta es una implementación básica, se debe completar según los requisitos
     */
    private class ClientesPanel extends JPanel {
        public ClientesPanel() {
            setLayout(new BorderLayout());
            
            // Panel de título
            JPanel panelTitulo = new JPanel();
            panelTitulo.setBackground(new Color(52, 152, 219)); // Azul
            JLabel lblTitulo = new JLabel("Gestión de Clientes");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(Color.WHITE);
            panelTitulo.add(lblTitulo);
            
            add(panelTitulo, BorderLayout.NORTH);
            
            // Panel central con mensaje de implementación pendiente
            JPanel panelCentral = new JPanel(new GridBagLayout());
            JLabel lblMensaje = new JLabel("Módulo en desarrollo. Próximamente disponible.");
            lblMensaje.setFont(new Font("Arial", Font.ITALIC, 16));
            panelCentral.add(lblMensaje);
            
            add(panelCentral, BorderLayout.CENTER);
        }
    }
    
    /**
     * Clase interna para el panel de facturas
     * Nota: Esta es una implementación básica, se debe completar según los requisitos
     */
    private class FacturasPanel extends JPanel {
        public FacturasPanel() {
            setLayout(new BorderLayout());
            
            // Panel de título
            JPanel panelTitulo = new JPanel();
            panelTitulo.setBackground(new Color(142, 68, 173)); // Púrpura
            JLabel lblTitulo = new JLabel("Gestión de Facturas");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(Color.WHITE);
            panelTitulo.add(lblTitulo);
            
            add(panelTitulo, BorderLayout.NORTH);
            
            // Panel central con mensaje de implementación pendiente
            JPanel panelCentral = new JPanel(new GridBagLayout());
            JLabel lblMensaje = new JLabel("Módulo en desarrollo. Próximamente disponible.");
            lblMensaje.setFont(new Font("Arial", Font.ITALIC, 16));
            panelCentral.add(lblMensaje);
            
            add(panelCentral, BorderLayout.CENTER);
        }
    }
    
    /**
     * Clase interna para el panel de proveedores
     * Nota: Esta es una implementación básica, se debe completar según los requisitos
     */
    private class ProveedoresPanel extends JPanel {
        public ProveedoresPanel() {
            setLayout(new BorderLayout());
            
            // Panel de título
            JPanel panelTitulo = new JPanel();
            panelTitulo.setBackground(new Color(41, 128, 185)); // Azul
            JLabel lblTitulo = new JLabel("Gestión de Proveedores");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(Color.WHITE);
            panelTitulo.add(lblTitulo);
            
            add(panelTitulo, BorderLayout.NORTH);
            
            // Panel central con mensaje de implementación pendiente
            JPanel panelCentral = new JPanel(new GridBagLayout());
            JLabel lblMensaje = new JLabel("Módulo en desarrollo. Próximamente disponible.");
            lblMensaje.setFont(new Font("Arial", Font.ITALIC, 16));
            panelCentral.add(lblMensaje);
            
            add(panelCentral, BorderLayout.CENTER);
        }
    }
    
    /**
     * Clase interna para el panel de órdenes de compra
     * Nota: Esta es una implementación básica, se debe completar según los requisitos
     */
    private class OrdenesCompraPanel extends JPanel {
        public OrdenesCompraPanel() {
            setLayout(new BorderLayout());
            
            // Panel de título
            JPanel panelTitulo = new JPanel();
            panelTitulo.setBackground(new Color(211, 84, 0)); // Naranja oscuro
            JLabel lblTitulo = new JLabel("Gestión de Órdenes de Compra");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(Color.WHITE);
            panelTitulo.add(lblTitulo);
            
            add(panelTitulo, BorderLayout.NORTH);
            
            // Panel central con mensaje de implementación pendiente
            JPanel panelCentral = new JPanel(new GridBagLayout());
            JLabel lblMensaje = new JLabel("Módulo en desarrollo. Próximamente disponible.");
            lblMensaje.setFont(new Font("Arial", Font.ITALIC, 16));
            panelCentral.add(lblMensaje);
            
            add(panelCentral, BorderLayout.CENTER);
        }
    }
    
    /**
     * Clase interna para el panel de reporte de inventario
     * Nota: Esta es una implementación básica, se debe completar según los requisitos
     */
    private class ReporteInventarioPanel extends JPanel {
        public ReporteInventarioPanel() {
            setLayout(new BorderLayout());
            
            // Panel de título
            JPanel panelTitulo = new JPanel();
            panelTitulo.setBackground(new Color(22, 160, 133)); // Verde azulado
            JLabel lblTitulo = new JLabel("Reporte de Inventario");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(Color.WHITE);
            panelTitulo.add(lblTitulo);
            
            add(panelTitulo, BorderLayout.NORTH);
            
            // Panel central con mensaje de implementación pendiente
            JPanel panelCentral = new JPanel(new GridBagLayout());
            JLabel lblMensaje = new JLabel("Módulo en desarrollo. Próximamente disponible.");
            lblMensaje.setFont(new Font("Arial", Font.ITALIC, 16));
            panelCentral.add(lblMensaje);
            
            add(panelCentral, BorderLayout.CENTER);
        }
    }
    
    /**
     * Clase interna para el panel de reporte de servicios
     * Nota: Esta es una implementación básica, se debe completar según los requisitos
     */
    private class ReporteServiciosPanel extends JPanel {
        public ReporteServiciosPanel() {
            setLayout(new BorderLayout());
            
            // Panel de título
            JPanel panelTitulo = new JPanel();
            panelTitulo.setBackground(new Color(41, 128, 185)); // Azul
            JLabel lblTitulo = new JLabel("Reporte de Servicios");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(Color.WHITE);
            panelTitulo.add(lblTitulo);
            
            add(panelTitulo, BorderLayout.NORTH);
            
            // Panel central con mensaje de implementación pendiente
            JPanel panelCentral = new JPanel(new GridBagLayout());
            JLabel lblMensaje = new JLabel("Módulo en desarrollo. Próximamente disponible.");
            lblMensaje.setFont(new Font("Arial", Font.ITALIC, 16));
            panelCentral.add(lblMensaje);
            
            add(panelCentral, BorderLayout.CENTER);
        }
    }
    
    /**
     * Clase interna para el panel de reporte de ventas
     * Nota: Esta es una implementación básica, se debe completar según los requisitos
     */
    private class ReporteVentasPanel extends JPanel {
        public ReporteVentasPanel() {
            setLayout(new BorderLayout());
            
            // Panel de título
            JPanel panelTitulo = new JPanel();
            panelTitulo.setBackground(new Color(39, 174, 96)); // Verde
            JLabel lblTitulo = new JLabel("Reporte de Ventas");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(Color.WHITE);
            panelTitulo.add(lblTitulo);
            
            add(panelTitulo, BorderLayout.NORTH);
            
            // Panel central con mensaje de implementación pendiente
            JPanel panelCentral = new JPanel(new GridBagLayout());
            JLabel lblMensaje = new JLabel("Módulo en desarrollo. Próximamente disponible.");
            lblMensaje.setFont(new Font("Arial", Font.ITALIC, 16));
            panelCentral.add(lblMensaje);
            
            add(panelCentral, BorderLayout.CENTER);
        }
    }
    
    /**
     * Clase interna para el panel de reporte de proveedores
     * Nota: Esta es una implementación básica, se debe completar según los requisitos
     */
    private class ReporteProveedoresPanel extends JPanel {
        public ReporteProveedoresPanel() {
            setLayout(new BorderLayout());
            
            // Panel de título
            JPanel panelTitulo = new JPanel();
            panelTitulo.setBackground(new Color(155, 89, 182)); // Púrpura
            JLabel lblTitulo = new JLabel("Reporte de Proveedores");
            lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
            lblTitulo.setForeground(Color.WHITE);
            panelTitulo.add(lblTitulo);
            
            add(panelTitulo, BorderLayout.NORTH);
            
            // Panel central con mensaje de implementación pendiente
            JPanel panelCentral = new JPanel(new GridBagLayout());
            JLabel lblMensaje = new JLabel("Módulo en desarrollo. Próximamente disponible.");
            lblMensaje.setFont(new Font("Arial", Font.ITALIC, 16));
            panelCentral.add(lblMensaje);
            
            add(panelCentral, BorderLayout.CENTER);
        }
    }
}
