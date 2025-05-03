package com.carsmotors.view;

import com.carsmotors.controller.ClienteController;
import com.carsmotors.model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Diálogo para crear o editar un cliente
 */
public class ClienteDialog extends JDialog {
    private JTextField txtNombre;
    private JTextField txtIdentificacion;
    private JTextField txtTelefono;
    private JTextField txtEmail;
    private JTextField txtDireccion;
    private JButton btnGuardar;
    private JButton btnCancelar;
    
    private ClienteController controller;
    private Cliente cliente;
    private boolean esNuevo;
    
    
    
    
    
    public ClienteDialog(JFrame parent, String title, Cliente cliente) {
        super(parent, title, true);
        this.controller = new ClienteController();
        this.cliente = cliente;
        this.esNuevo = (cliente == null || cliente.getId() == 0);
        
        initComponents();
        
        if (!esNuevo) {
            cargarDatosCliente();
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
        
        // nombre
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        txtNombre = new JTextField(20);
        panel.add(txtNombre, gbc);
        
        // identificacion
        
        
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Identificación:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtIdentificacion = new JTextField(20);
        panel.add(txtIdentificacion, gbc);
        
        // Teléfono
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtTelefono = new JTextField(20);
        panel.add(txtTelefono, gbc);
        
         // Email
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtEmail = new JTextField(20);
        panel.add(txtEmail, gbc);
        
        // Dirección
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        panel.add(new JLabel("Dirección:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtDireccion = new JTextField(20);
        panel.add(txtDireccion, gbc);
        
        // Botones
        JPanel buttonPanel = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCliente();
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
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(buttonPanel, gbc);
        
        add(panel);
    }
    
    private void cargarDatosCliente() {
        if (cliente != null) {
            txtNombre.setText(cliente.getNombre());
            txtIdentificacion.setText(cliente.getIdentificacion());
            txtTelefono.setText(cliente.getTelefono());
            txtEmail.setText(cliente.getEmail());
            txtDireccion.setText(cliente.getDireccion());
        }
    }
    
    private void guardarCliente() {
        // Validar campos obligatorios
        if (txtNombre.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre es obligatorio", "Error", JOptionPane.ERROR_MESSAGE);
            txtNombre.requestFocus();
            return;
        }
        
        if (txtIdentificacion.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "La identificación es obligatoria", "Error", JOptionPane.ERROR_MESSAGE);
            txtIdentificacion.requestFocus();
            return;
        }
        
        // Crear o actualizar el objeto cliente
        if (esNuevo) {
            cliente = new Cliente();
        }
        
        cliente.setNombre(txtNombre.getText().trim());
        cliente.setIdentificacion(txtIdentificacion.getText().trim());
        cliente.setTelefono(txtTelefono.getText().trim());
        cliente.setEmail(txtEmail.getText().trim());
        cliente.setDireccion(txtDireccion.getText().trim());
        
        boolean resultado;
        if (esNuevo) {
            resultado = controller.registrarCliente(cliente);
        } else {
            resultado = controller.actualizarCliente(cliente);
        }
        
        if (resultado) {
            JOptionPane.showMessageDialog(this, "Cliente guardado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
