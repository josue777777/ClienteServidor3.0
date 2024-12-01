package Grupo4SC303MNProyectoClienteServidor.Interfaces;

import javax.swing.*;

public class InterfazCliente extends JFrame {
    private JTextField nombreUsuario;
    private JPasswordField contrasena;
    private JButton botonIniciarSesion;
    private JButton botonRegistrar;

    public InterfazCliente() {
        setTitle("October Eats - Cliente");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        nombreUsuario = new JTextField();
        contrasena = new JPasswordField();
        botonIniciarSesion = new JButton("Iniciar Sesión");
        botonRegistrar = new JButton("Registrarse");

        nombreUsuario.setBounds(50, 50, 300, 30);
        contrasena.setBounds(50, 100, 300, 30);
        botonIniciarSesion.setBounds(50, 150, 140, 30);
        botonRegistrar.setBounds(210, 150, 140, 30);

        add(nombreUsuario);
        add(contrasena);
        add(botonIniciarSesion);
        add(botonRegistrar);

        botonIniciarSesion.addActionListener(e -> iniciarSesion());
        botonRegistrar.addActionListener(e -> registrarUsuario());

        setVisible(true);
    }

    private void iniciarSesion() {
        // Implementar la lógica de inicio de sesión aquí
    }

    private void registrarUsuario() {
        // Implementar la lógica de registro de usuario aquí
    }

    public static void main(String[] args) {
        new InterfazCliente();
    }
}