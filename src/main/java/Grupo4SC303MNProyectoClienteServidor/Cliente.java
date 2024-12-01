package Grupo4SC303MNProyectoClienteServidor;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente extends Usuario {

    private String direccion;
    private String telefono;
    private Socket socket; // socket como atributo de la clase para reutilizarlo
    private DataOutputStream out;
    private DataInputStream in;

    public Cliente(String nombre, String apellido, String direccion, String email ,String telefono ,String id) {
        super(nombre, apellido, id, email);
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void consultarHistorial() {
        //
    }

    public void conectarAlServidor() {
        String host = "localhost";
        int port = 5432;

        try {
            // Usamos el socket como atributo de la clase en lugar de un socket local
            socket = new Socket(host, port);
            System.out.println("Conectado al servidor en " + host + ":" + port);

            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            Scanner s = new Scanner(System.in);
            while (true) {
                System.out.println("Bienvenido " + getNombre() + " a October Eats");
                System.out.println("Elija una categoría del menú:");
                System.out.println("1. Comida rápida");
                System.out.println("2. Platos Fuertes");
                System.out.println("3. Plato del día");
                System.out.println("4. Bebidas");
                System.out.println("5. Salir");

                int opcion = s.nextInt();
                if (opcion == 5) {
                    System.out.println("Saliendo...");
                    break;
                }

                // Enviar la opción al servidor
                out.writeInt(opcion);
                out.flush();

                // Leer la respuesta del servidor
                String respuestaServidor = in.readUTF();
                System.out.println("Respuesta del Servidor: " + respuestaServidor);

                // Preguntar si quiere hacer más pedidos
                System.out.println("¿Desea continuar realizando más pedidos? (si / no)");
                String continuar = s.next();
                if (!continuar.equalsIgnoreCase("si")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al conectar al servidor: " + e.getMessage());
        } finally {
            // Asegurarse de cerrar lo que usamos
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar los recursos: " + e.getMessage());
            }
        }
    }
}
