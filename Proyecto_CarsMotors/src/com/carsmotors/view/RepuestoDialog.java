package com.carsmotors.view;

import com.carsmotors.controller.RepuestoController;
import com.carsmotors.model.Repuesto;
import com.carsmotors.utils.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Diálogo para crear o editar un repuesto
 */
public class RepuestoDialog extends JDialog {
    private JTextField txtNombre;
    private JComboBox<String> cboTipo;
    private JTextArea txtDescripcion;
    private JTextField txtPrecioUnitario;
    private JTextField txtStockActual;
    private JTextField txtStockMinimo;
    private JComboBox<String> cboEstado;
    private JTextField txtUbicacion;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    private RepuestoController controller;
    private Repuesto repuesto;
    private boolean esNuevo;
    private SoundManager soundManager;
    
    /**
     * Constructor del diálogo
     * @param parent Frame padre
     * @param title Título del diálogo
     * @param repuesto Repuesto a editar (null para nuevo)
     */
    public RepuestoDialog(JFrame parent, String title, Repuesto repuesto) {
        super(parent, title, true);
        this.controller = new RepuestoController();
        this.repuesto = repuesto;
        this.esNuevo = (repuesto == null);
        this.soundManager = SoundManager.getInstance();
        
        initComponents();
        
        if (!esNuevo) {
            cargarDatosRepuesto();
        }
        
        pack();
        setLocationRelativeTo(parent);
        setResizable(false);
        
       
        soundManager.playSound(SoundManager.SOUND_WINDOW_OPEN);
    }

    RepuestoDialog(Window windowAncestor, Repuesto repuesto) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
        
        // Tipo
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cboTipo = new JComboBox<>(new String[]{"Mecánico", "Eléctrico", "Carrocería", "Consumo"});
        panel.add(cboTipo, gbc);
        
        // Descripción
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtDescripcion = new JTextArea(3, 25);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        panel.add(scrollDescripcion, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panel.add(new JLabel("Precio Unitario:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtPrecioUnitario = new JTextField(25);
        panel.add(txtPrecioUnitario, gbc);
        
   
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        panel.add(new JLabel("Stock Actual:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtStockActual = new JTextField(25);
        panel.add(txtStockActual, gbc);
        
        // Stock Mínimo
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        panel.add(new JLabel("Stock Mínimo:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtStockMinimo = new JTextField(25);
        panel.add(txtStockMinimo, gbc);
        
        // Estado
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0;
        panel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cboEstado = new JComboBox<>(new String[]{"Activo", "Inactivo", "Agotado"});
        panel.add(cboEstado, gbc);
        
        // Ubicación
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0;
        panel.add(new JLabel("Ubicación:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtUbicacion = new JTextField(25);
        panel.add(txtUbicacion, gbc);
        
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
                guardarRepuesto();
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
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);
        
        add(panel);
    }
    
    /**
     * Carga los datos del repuesto en los campos del formulario
     */
    private void cargarDatosRepuesto() {
        if (repuesto != null) {
            txtNombre.setText(repuesto.getNombre());
            cboTipo.setSelectedItem(repuesto.getTipo());
            txtDescripcion.setText(repuesto.getDescripcion());
            txtPrecioUnitario.setText(String.valueOf(repuesto.getPrecioUnitario()));
            txtStockActual.setText(String.valueOf(repuesto.getStockActual()));
            txtStockMinimo.setText(String.valueOf(repuesto.getStockMinimo()));
            cboEstado.setSelectedItem(repuesto.getEstado());
            
        }
    }
    
  
    private void guardarRepuesto() {
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
        
        // Validar que el precio sea un número válido
        double precioUnitario;
        try {
            precioUnitario = Double.parseDouble(txtPrecioUnitario.getText().trim());
            if (precioUnitario <= 0) {
                throw new NumberFormatException("El precio debe ser mayor que cero");
            }
        } catch (NumberFormatException e) {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this, 
                "El precio unitario debe ser un número válido mayor que cero", 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
            txtPrecioUnitario.requestFocus();
            return;
        }
        
        // Validar que el stock sea un número entero válido
        int stockActual, stockMinimo;
        try {
            stockActual = Integer.parseInt(txtStockActual.getText().trim());
            if (stockActual < 0) {
                throw new NumberFormatException("El stock no puede ser negativo");
            }
        } catch (NumberFormatException e) {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this, 
                "El stock actual debe ser un número entero no negativo", 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
            txtStockActual.requestFocus();
            return;
        }
        
        try {
            stockMinimo = Integer.parseInt(txtStockMinimo.getText().trim());
            if (stockMinimo < 0) {
                throw new NumberFormatException("El stock mínimo no puede ser negativo");
            }
        } catch (NumberFormatException e) {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this, 
                "El stock mínimo debe ser un número entero no negativo", 
                "Error de validación", 
                JOptionPane.ERROR_MESSAGE);
            txtStockMinimo.requestFocus();
            return;
        }
        
        // Crear o actualizar el objeto repuesto
        if (esNuevo) {
            repuesto = new Repuesto();
        }
        
        repuesto.setNombre(txtNombre.getText().trim());
        repuesto.setTipo((String) cboTipo.getSelectedItem());
        repuesto.setDescripcion(txtDescripcion.getText().trim());
        repuesto.setPrecioUnitario(precioUnitario);
        repuesto.setStockActual(stockActual);
        repuesto.setStockMinimo(stockMinimo);
        repuesto.setEstado((String) cboEstado.getSelectedItem());
        
        
        boolean resultado;
        if (esNuevo) {
            resultado = controller.registrarRepuesto(repuesto);
        } else {
            resultado = controller.actualizarRepuesto(repuesto);
        }
        
        if (resultado) {
            soundManager.playSound(SoundManager.SOUND_SUCCESS);
            JOptionPane.showMessageDialog(this, 
                "Repuesto guardado correctamente", 
                "Operación exitosa", 
                JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            soundManager.playSound(SoundManager.SOUND_ERROR);
            JOptionPane.showMessageDialog(this, 
                "Error al guardar el repuesto", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    boolean isRepuestoGuardado() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
