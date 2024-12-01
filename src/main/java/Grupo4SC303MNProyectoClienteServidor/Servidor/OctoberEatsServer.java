package Grupo4SC303MNProyectoClienteServidor.Servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

//    public class ManejoClientesBS {
//        public void obtenerClientes() {
//            String query = "SELECT * FROM clientes";
//            try (Connection connection = Database.getConnection();
//                 PreparedStatement pstmt = connection.prepareStatement(query);
//                 ResultSet rs = pstmt.executeQuery()) {
//
//                System.out.println("Lista de clientes:");
//                while (rs.next()) {
//                    System.out.println("Cliente: " + rs.getString("nombre"));
//                }
//            } catch (SQLException e) {
//                System.err.println("Error al obtener clientes:");
//                e.printStackTrace();
//            }
//        }
//
//    }


    // buenas noches
}
