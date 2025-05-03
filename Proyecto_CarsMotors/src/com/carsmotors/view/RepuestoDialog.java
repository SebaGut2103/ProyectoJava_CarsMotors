package com.carsmotors.view;

import com.carsmotors.controller.RepuestoController;
import com.carsmotors.model.Repuesto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Diálogo para crear o editar un repuesto
 */
public class RepuestoDialog extends JDialog {
    private JTextField txtNombre;
    private JComboBox<String> cboTipo;
    private JTextField txtMarcaCompatible;
    private JTextField txtModeloCompatible;
    private JTextArea txtDescripcion;
    private JTextField txtPrecioUnitario;
    private JTextField txtStockActual;
    private JTextField txtStockMinimo;
    private JTextField txtFechaIngreso;
    private JTextField txtVidaUtilMeses;
    private JComboBox<String> cboEstado;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    private RepuestoController controller;
    private Repuesto repuesto;
    private boolean esNuevo;
    
    public RepuestoDialog(JFrame parent, String title, Repuesto repuesto) {
        super(parent, title, true);
        this.controller = new RepuestoController();
        this.repuesto = repuesto;
        this.esNuevo = (repuesto == null || repuesto.getId() == 0);
        
        initComponents();
        
        if (!esNuevo) {
            cargarDatosRepuesto();
        }
        
        pack();
        setLocationRelativeTo(parent);
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
        txtNombre = new JTextField(20);
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
        
        // Marca Compatible
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Marca Compatible:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtMarcaCompatible = new JTextField(20);
        panel.add(txtMarcaCompatible, gbc);
        
        // Modelo Compatible
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panel.add(new JLabel("Modelo Compatible:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtModeloCompatible = new JTextField(20);
        panel.add(txtModeloCompatible, gbc);
        
        // Descripción
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        panel.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtDescripcion = new JTextArea(3, 20);
        txtDescripcion.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(txtDescripcion);
        panel.add(scrollPane, gbc);
        
        // Precio Unitario
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 0;
        panel.add(new JLabel("Precio Unitario:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtPrecioUnitario = new JTextField(20);
        panel.add(txtPrecioUnitario, gbc);
        
        // Stock Actual
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 0;
        panel.add(new JLabel("Stock Actual:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtStockActual = new JTextField(20);
        panel.add(txtStockActual, gbc);
        
        // Stock Mínimo
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.weightx = 0;
        panel.add(new JLabel("Stock Mínimo:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtStockMinimo = new JTextField(20);
        panel.add(txtStockMinimo, gbc);
        
        // Fecha Ingreso
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.weightx = 0;
        panel.add(new JLabel("Fecha Ingreso (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtFechaIngreso = new JTextField(20);
        // Establecer fecha actual por defecto
        txtFechaIngreso.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        panel.add(txtFechaIngreso, gbc);
        
        // Vida Útil (meses)
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.weightx = 0;
        panel.add(new JLabel("Vida Útil (meses):"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtVidaUtilMeses = new JTextField(20);
        panel.add(txtVidaUtilMeses, gbc);
        
        // Estado
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.weightx = 0;
        panel.add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        cboEstado = new JComboBox<>(new String[]{"Disponible", "Reservado", "Fuera de servicio"});
        panel.add(cboEstado, gbc);
        
        // Botones
        JPanel buttonPanel = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarRepuesto();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        buttonPanel.add(btnGuardar);
        buttonPanel.add(btnCancelar);
        
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);
        
        add(panel);
    }
    
    private void cargarDatosRepuesto() {
        if (repuesto != null) {
            txtNombre.setText(repuesto.getNombre());
            cboTipo.setSelectedItem(repuesto.getTipo());
            txtMarcaCompatible.setText(repuesto.getMarcaCompatible());
            txtModeloCompatible.setText(repuesto.getModeloCompatible());
            txtDescripcion.setText(repuesto.getDescripcion());
            txtPrecioUnitario.setText(String.valueOf(repuesto.getPrecioUnitario()));
            txtStockActual.setText(String.valueOf(repuesto.getStockActual()));
            txtStockMinimo.setText(String.valueOf(repuesto.getStockMinimo()));
            
            if (repuesto.getFechaIngreso() != null) {
                txtFechaIngreso.setText(new SimpleDateFormat("yyyy-MM-dd").format(repuesto.getFechaIngreso()));
            }
            
            txtVidaUtilMeses.setText(String.valueOf(repuesto.getVidaUtilMeses()));
            cboEstado.setSelectedItem(repuesto.getEstado());
        }
    }
    
    private void guardarRepuesto() {
        // Validar campos obligatorios
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
            return;
        }
        
        // Validar campos numéricos
        double precioUnitario;
        int stockActual;
        int stockMinimo;
        int vidaUtilMeses;
        
        try {
            precioUnitario = Double.parseDouble(txtPrecioUnitario.getText().trim());
            if (precioUnitario <= 0) {
                throw new NumberFormatException("El precio debe ser mayor que cero");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio unitario debe ser un número válido mayor que cero", "Error", JOptionPane.ERROR_MESSAGE);
            txtPrecioUnitario.requestFocus();
            return;
        }
        
        try {
            stockActual = Integer.parseInt(txtStockActual.getText().trim());
            if (stockActual < 0) {
                throw new NumberFormatException("El stock no puede ser negativo");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El stock actual debe ser un número entero no negativo", "Error", JOptionPane.ERROR_MESSAGE);
            txtStockActual.requestFocus();
            return;
        }
        
        try {
            stockMinimo = Integer.parseInt(txtStockMinimo.getText().trim());
            if (stockMinimo < 0) {
                throw new NumberFormatException("El stock mínimo no puede ser negativo");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El stock mínimo debe ser un número entero no negativo", "Error", JOptionPane.ERROR_MESSAGE);
            txtStockMinimo.requestFocus();
            return;
        }
        
        try {
            vidaUtilMeses = Integer.parseInt(txtVidaUtilMeses.getText().trim());
            if (vidaUtilMeses < 0) {
                throw new NumberFormatException("La vida útil no puede ser negativa");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La vida útil debe ser un número entero no negativo", "Error", JOptionPane.ERROR_MESSAGE);
            txtVidaUtilMeses.requestFocus();
            return;
        }
        
        // Validar fecha
        Date fechaIngreso;
        try {
            fechaIngreso = new SimpleDateFormat("yyyy-MM-dd").parse(txtFechaIngreso.getText().trim());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "La fecha debe tener el formato yyyy-MM-dd", "Error", JOptionPane.ERROR_MESSAGE);
            txtFechaIngreso.requestFocus();
            return;
        }
        
        // Crear o actualizar el objeto repuesto
        if (esNuevo) {
            repuesto = new Repuesto();
        }
        
        repuesto.setNombre(txtNombre.getText().trim());
        repuesto.setTipo((String) cboTipo.getSelectedItem());
        repuesto.setMarcaCompatible(txtMarcaCompatible.getText().trim());
        repuesto.setModeloCompatible(txtModeloCompatible.getText().trim());
        repuesto.setDescripcion(txtDescripcion.getText().trim());
        repuesto.setPrecioUnitario(precioUnitario);
        repuesto.setStockActual(stockActual);
        repuesto.setStockMinimo(stockMinimo);
        repuesto.setFechaIngreso(fechaIngreso);
        repuesto.setVidaUtilMeses(vidaUtilMeses);
        repuesto.setEstado((String) cboEstado.getSelectedItem());
        
        boolean resultado;
        if (esNuevo) {
            resultado = controller.registrarRepuesto(repuesto);
        } else {
            resultado = controller.actualizarRepuesto(repuesto);
        }
        
        if (resultado) {
            JOptionPane.showMessageDialog(this, "Repuesto guardado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el repuesto", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
