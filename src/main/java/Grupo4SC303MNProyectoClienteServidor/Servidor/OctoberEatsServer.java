package Grupo4SC303MNProyectoClienteServidor.Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class OctoberEatsServer {

    private static final int PORT = 5432;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor iniciado. Esperando conexiones...");

            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado!");

                // Creamos un nuevo hilo para cada cliente
                new Thread(new ManejoClientes(clienteSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Error al escuchar en el puerto: " + e.getMessage());
        }
    }
}

class ManejoClientes implements Runnable {

    private Socket clientSocket;
    private DataOutputStream out;
    private DataInputStream in;


    public ManejoClientes(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream in = new DataInputStream(clientSocket.getInputStream());
             DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())) {

            while (true) {
                // Leer la opción enviada por el cliente
                int opcion = in.readInt();
                System.out.println("Opción recibida: " + opcion);

                String respuesta;
                switch (opcion) {
                    case 1:
                        respuesta = "Pedido de comida rápida confirmado.";
                        break;
                    case 2:
                        respuesta = "Pedido de platos fuertes confirmado.";
                        break;
                    case 3:
                        respuesta = "Pedido de plato del día confirmado.";
                        break;
                    case 4:
                        respuesta = "Pedido de bebidas confirmado.";
                        break;
                    default:
                        respuesta = "Opción no válida.";
                }

                // Enviar la respuesta al cliente
                out.writeUTF(respuesta);
                out.flush();
            }

        } catch (IOException e) {
            System.err.println("Error al procesar la solicitud del cliente: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Error al cerrar el socket del cliente: " + e.getMessage());
            }
        }
    }

//    public void conectarAlServidor() {
//        String host = "localhost";
//        int port = 5432;
//
//        try {
//            // Usamos el socket como atributo de la clase en lugar de un socket local
//            socket = new Socket(host, port);
//            System.out.println("Conectado al servidor en " + host + ":" + port);
//
//            out = new DataOutputStream(socket.getOutputStream());
//            in = new DataInputStream(socket.getInputStream());
//
//            Scanner s = new Scanner(System.in);
//            while (true) {
//                System.out.println("Bienvenido " + getNombre() + " a October Eats");
//                System.out.println("Elija una categoría del menú:");
//                System.out.println("1. Comida rápida");
//                System.out.println("2. Platos Fuertes");
//                System.out.println("3. Plato del día");
//                System.out.println("4. Bebidas");
//                System.out.println("5. Salir");
//
//                int opcion = s.nextInt();
//                if (opcion == 5) {
//                    System.out.println("Saliendo...");
//                    break;
//                }
//
//                // Enviar la opción al servidor
//                out.writeInt(opcion);
//                out.flush();
//
//                // Leer la respuesta del servidor
//                String respuestaServidor = in.readUTF();
//                System.out.println("Respuesta del Servidor: " + respuestaServidor);
//
//                // Preguntar si quiere hacer más pedidos
//                System.out.println("¿Desea continuar realizando más pedidos? (si / no)");
//                String continuar = s.next();
//                if (!continuar.equalsIgnoreCase("si")) {
//                    break;
//                }
//            }
//        } catch (IOException e) {
//            System.err.println("Error al conectar al servidor: " + e.getMessage());
//        } finally {
//            // Asegurarse de cerrar lo que usamos
//            try {
//                if (in != null) in.close();
//                if (out != null) out.close();
//                if (socket != null) socket.close();
//            } catch (IOException e) {
//                System.err.println("Error al cerrar los recursos: " + e.getMessage());
//            }
//        }
//    }

}
