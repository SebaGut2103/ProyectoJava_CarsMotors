package com.carsmotors.view;

import com.carsmotors.controller.ProveedorController;
import com.carsmotors.model.Proveedor;
import com.carsmotors.utils.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ProveedorDialog extends JDialog {
    private JTextField txtNombre;
    private JTextField txtNit;
    private JTextField txtDireccion;
    private JTextField txtContacto;
    private JComboBox<String> cboFrecuencia;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    private ProveedorController controller;
    private Proveedor proveedor;
    private boolean esNuevo;
    private SoundManager soundManager;
    
    /**
     * Constructor del diálogo
     * @param parent Frame padre
     * @param title Título del diálogo
     * @param proveedor Proveedor a editar (null para nuevo)
     */
    public ProveedorDialog(JFrame parent, String title, Proveedor proveedor) {
        super(parent, title, true);
        this.controller = new ProveedorController();
        this.proveedor = proveedor;
        this.esNuevo = (proveedor == null);
        this.soundManager = SoundManager.getInstance();
        
        initComponents();
        
        if (!esNuevo) {
            cargarDatosProveedor();
        }
        
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
        
        // Reproducir sonido al abrir el diálogo
        soundManager.playSound(SoundManager.SOUND_WINDOW_OPEN);
    }
    
   
    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Nombre
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNombre = new JTextField(25);
        panel.add(txtNombre, gbc);
        
        // NIT
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("NIT:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtNit = new JTextField(25);
        panel.add(txtNit, gbc);
        
        // Dirección
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtDireccion = new JTextField(25);
        panel.add(txtDireccion, gbc);
        
        // Contacto
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panel.add(new JLabel("Contacto:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtContacto = new JTextField(25);
        panel.add(txtContacto, gbc);
        
        // Frecuencia de Suministro
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        panel.add(new JLabel("Frecuencia:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cboFrecuencia = new JComboBox<>(new String[]{"Semanal", "Quincenal", "Mensual", "Trimestral", "Semestral", "Anual"});
        panel.add(cboFrecuencia, gbc);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnGuardar.setIcon(new ImageIcon(getClass().getResource("/images/save.png")));
        btnCancelar = new JButton("Cancelar");
        btnCancelar.setIcon(new ImageIcon(getClass().getResource("/images/cancel.png")));
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                guardarProveedor();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soundManager.playSound(SoundManager.SOUND_BUTTON_CLICK);
                dispose();
            }
        });
        
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);
        
        add(panel);
    }
    
   
    private void cargarDatosProveedor() {
        if (proveedor != null) {
            txtNombre.setText(proveedor.getNombre());
            txtNit.setText(proveedor.getNit());
            txtDireccion.setText(proveedor.getDireccion());
            txtContacto.setText(proveedor.getContacto());
            cboFrecuencia.setSelectedItem(proveedor.getFrecuenciaSuministro());
        }
    }
    
   
     
    private void guardarProveedor() {
        // Validar campos obligatorios
        if (txtNombre.getText().trim().isEmpty()) {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this, 
                "El nombre es obligatorio", 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
            return;
        }
        
        if (txtNit.getText().trim().isEmpty()) {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this, 
                "El NIT es obligatorio", 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
            txtNit.requestFocus();
            return;
        }
        
     
        if (esNuevo) {
            proveedor = new Proveedor();
        }
        
        proveedor.setNombre(txtNombre.getText().trim());
        proveedor.setNit(txtNit.getText().trim());
        proveedor.setDireccion(txtDireccion.getText().trim());
        proveedor.setContacto(txtContacto.getText().trim());
        proveedor.setFrecuenciaSuministro((String) cboFrecuencia.getSelectedItem());
        
        boolean resultado;
        if (esNuevo) {
            resultado = controller.registrarProveedor(proveedor);
        } else {
            resultado = controller.actualizarProveedor(proveedor);
        }
        
        if (resultado) {
            soundManager.playSound(SoundManager.SOUND_SUCCESS);
            JOptionPane.showMessageDialog(this, 
                "Proveedor guardado correctamente", 
                "Operación exitosa", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this, 
                "Error al guardar el proveedor", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}
