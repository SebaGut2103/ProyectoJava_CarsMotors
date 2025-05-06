package com.carsmotors.view;

import com.carsmotors.controller.ClienteController;
import com.carsmotors.model.Cliente;
import com.carsmotors.utils.SoundManager;

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
    private boolean clienteGuardado;
    
    /**
     * Constructor para crear un nuevo cliente
     * @param parent Ventana padre
     */
    public ClienteDialog(Window parent) {
        super(parent, "Nuevo Cliente", ModalityType.APPLICATION_MODAL);
        this.controller = new ClienteController();
        this.cliente = new Cliente();
        this.clienteGuardado = false;
        
        initComponents();
    }
    
    /**
     * Constructor para editar un cliente existente
     * @param parent Ventana padre
     * @param cliente Cliente a editar
     */
    public ClienteDialog(Window parent, Cliente cliente) {
        super(parent, "Editar Cliente", ModalityType.APPLICATION_MODAL);
        this.controller = new ClienteController();
        this.cliente = cliente;
        this.clienteGuardado = false;
        
        initComponents();
        cargarDatosCliente();
    }

   
    
    /**
     * Inicializa los componentes del diálogo
     */
    private void initComponents() {
        setSize(400, 350);
        setLocationRelativeTo(getOwner());
        setResizable(false);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel de título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(new Color(52, 152, 219)); // Azul
        JLabel lblTitulo = new JLabel(cliente.getId() == 0 ? "Nuevo Cliente" : "Editar Cliente");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        panel.add(panelTitulo, BorderLayout.NORTH);
        
        // Panel de campos
        JPanel panelCampos = new JPanel(new GridLayout(5, 2, 10, 10));
        
        panelCampos.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(20);
        panelCampos.add(txtNombre);
        
        panelCampos.add(new JLabel("Identificación:"));
        txtIdentificacion = new JTextField(20);
        panelCampos.add(txtIdentificacion);
        
        panelCampos.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField(20);
        panelCampos.add(txtTelefono);
        
        panelCampos.add(new JLabel("Email:"));
        txtEmail = new JTextField(20);
        panelCampos.add(txtEmail);
        
        panelCampos.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField(20);
        panelCampos.add(txtDireccion);
        
        panel.add(panelCampos, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarCliente();
            }
        });
        
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        setContentPane(panel);
    }
    
    /**
     * Carga los datos del cliente en los campos
     */
    private void cargarDatosCliente() {
        txtNombre.setText(cliente.getNombre());
        txtIdentificacion.setText(cliente.getIdentificacion());
        txtTelefono.setText(cliente.getTelefono());
        txtEmail.setText(cliente.getEmail());
        txtDireccion.setText(cliente.getDireccion());
    }
    
    /**
     * Guarda el cliente
     */
    private void guardarCliente() {
        // Validar campos
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
        
        // Actualizar datos del cliente
        cliente.setNombre(txtNombre.getText().trim());
        cliente.setIdentificacion(txtIdentificacion.getText().trim());
        cliente.setTelefono(txtTelefono.getText().trim());
        cliente.setEmail(txtEmail.getText().trim());
        cliente.setDireccion(txtDireccion.getText().trim());
        
        // Guardar cliente
        boolean resultado;
        if (cliente.getId() == 0) {
            resultado = controller.registrarCliente(cliente);
        } else {
            resultado = controller.actualizarCliente(cliente);
        }
        
        if (resultado) {
            clienteGuardado = true;
            JOptionPane.showMessageDialog(this, "Cliente guardado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el cliente", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Indica si el cliente fue guardado
     * @return true si el cliente fue guardado, false en caso contrario
     */
    public boolean isClienteGuardado() {
        return clienteGuardado;
    }
}
