package Grupo4SC303MNProyectoClienteServidor.Servidor;

import Grupo4SC303MNProyectoClienteServidor.Controller.ControladorAdministrador;
import Grupo4SC303MNProyectoClienteServidor.Controller.ControladorCliente;
import Grupo4SC303MNProyectoClienteServidor.Controller.ControladorRestaurante;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;


public class OctoberEatsUsuario {
    private static final int PUERTO = 5432;

    public static void main(String[] args) {
        OctoberEatsUsuario cliente = new OctoberEatsUsuario();

        cliente.iniciarCliente();
    }

    public void iniciarCliente() {
        try {
            Thread.sleep(1000); // Dar tiempo para que el servidor se inicie

            Socket socketCliente = new Socket("localhost", PUERTO);
            DataOutputStream salida = new DataOutputStream(socketCliente.getOutputStream());
            DataInputStream entrada = new DataInputStream(socketCliente.getInputStream());
            Scanner scanner = new Scanner(System.in);

            // Interacción con el cliente
            boolean ejecucionCliente = true;
            while (ejecucionCliente) {
                mostrarMenuPrincipal();
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar el buffer

                // Enviar la opción al servidor
                salida.writeInt(opcion);
                salida.flush();

                if (opcion == 1 || opcion == 2) {
                    // Solicitar información al cliente según la opción elegida
                    System.out.print("Ingrese el nombre de usuario: ");
                    String nombreUsuario = scanner.nextLine();
                    System.out.print("Ingrese la contraseña: ");
                    String contrasena = scanner.nextLine();

                    // Enviar la información al servidor
                    salida.writeUTF(nombreUsuario);
                    salida.writeUTF(contrasena);
                    salida.flush();

                    // Recibir respuesta del servidor
                    String respuesta = entrada.readUTF();
                    System.out.println("Servidor: " + respuesta);

                    if (respuesta.contains("administrador")) {
                        // Espacio para mostrar menú de administrador
                    } else if (respuesta.contains("cliente")) {
                        mostrarMenuCliente(scanner, salida, entrada);
                    }

                } else if (opcion == 3) {
                    ejecucionCliente = false;
                    System.out.println("Saliendo...");
                } else {
                    System.out.println("Opción no válida.");
                }
            }

            socketCliente.close();
        } catch (IOException | InterruptedException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== Menú Principal ===");
        System.out.println("1. Registrarse");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void mostrarMenuCliente(Scanner scanner, DataOutputStream salida, DataInputStream entrada) {
        while (true) {
            mostrarMenuClienteOpciones();
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            switch (opcion) {
                case 1:
                    System.out.println("Espacio para ver restaurantes.");

                    break;
                case 2:

                    break;
                case 3:
                    System.out.println("Espacio para realizar pedido.");
                    // Llamar a método correspondiente
                    break;
                case 4:
                    System.out.println("Cerrando sesión...");
                    return; // Regresar al menú principal
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void mostrarMenuClienteOpciones() {
        System.out.println("\n=== Menú Cliente ===");
        System.out.println("1. Ver Restaurantes");
        System.out.println("2. Ver Menú de Restaurante");
        System.out.println("3. Realizar Pedido");
        System.out.println("4. Cerrar Sesión");
        System.out.print("Seleccione una opción: ");
    }

    static class ManejoCliente implements Runnable {
        private Socket socketCliente;
        private DataOutputStream salida;
        private DataInputStream entrada;

        public ManejoCliente(Socket socket) {
            this.socketCliente = socket;
        }

        @Override
        public void run() {
            Gson gson = new Gson();
            ControladorCliente controladorCliente = new ControladorCliente();
            ControladorRestaurante controladorRestaurante = new ControladorRestaurante();
            ControladorAdministrador controladorAdministrador = new ControladorAdministrador();

            try (DataInputStream entrada = new DataInputStream(socketCliente.getInputStream());
                 DataOutputStream salida = new DataOutputStream(socketCliente.getOutputStream())) {

                boolean ejecucionServidor = true;

                while (ejecucionServidor) {
                    // Leer la opción enviada por el cliente
                    int opcion = entrada.readInt();
                    System.out.println("Opción recibida: " + opcion);

                    String respuesta;
                    switch (opcion) {
                        case 1: // Registrarse
                            registrarUsuario(entrada, controladorCliente);
                            respuesta = "Usuario registrado correctamente.";
                            break;
                        case 2: // Iniciar sesión
                            if (iniciarSesion(entrada, controladorCliente, salida)) {
                                respuesta = "Inicio de sesión exitoso.";
                            } else {
                                respuesta = "Error en la autenticación.";
                            }
                            break;
                        case 3: // Salir
                            respuesta = "¡Hasta luego!";
                            ejecucionServidor = false;
                            break;
                        default:
                            respuesta = "Opción no válida.";
                    }

                    // Enviar la respuesta al cliente
                    salida.writeUTF(respuesta);
                    salida.flush();
                }
            } catch (IOException e) {
                System.err.println("Error al procesar la solicitud del cliente: " + e.getMessage());
            } finally {
                try {
                    socketCliente.close();
                } catch (IOException e) {
                    System.err.println("Error al cerrar el socket del cliente: " + e.getMessage());
                }
            }
        }

        private void registrarUsuario(DataInputStream entrada, ControladorCliente controladorCliente) throws IOException {
            try {
                String nombreUsuario = entrada.readUTF();
                String contrasena = entrada.readUTF();

                System.out.print("¿Es administrador o cliente? (admin/cliente): ");
                Scanner scanner = new Scanner(System.in);
                String rol = scanner.nextLine().toLowerCase();

                if (rol.equals("admin")) {
                    System.out.print("Ingrese el nombre del administrador: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Ingrese el apellido del administrador: ");
                    String apellido = scanner.nextLine();
                    System.out.print("Ingrese el nombre del restaurante: ");
                    String nombreRestaurante = scanner.nextLine();
                    System.out.print("Ingrese la dirección del restaurante: ");
                    String direccionRestaurante = scanner.nextLine();
                    System.out.print("Ingrese el tipo de comida del restaurante: ");
                    String tipoComida = scanner.nextLine();

                    if (controladorCliente.registrarAdministrador(nombreUsuario, contrasena, nombre, apellido, nombreRestaurante, direccionRestaurante, tipoComida)) {
                        System.out.println("Administrador y restaurante registrados con éxito.");
                    } else {
                        System.out.println("Error al registrar el administrador.");
                    }
                } else if (rol.equals("cliente")) {
                    if (controladorCliente.registrarUsuario(nombreUsuario, contrasena)) {
                        System.out.println("Cliente registrado con éxito.");
                    } else {
                        System.out.println("Error al registrar el cliente.");
                    }
                } else {
                    System.out.println("Rol no válido. Por favor, intente de nuevo.");
                }
            } catch (IOException e) {
                System.err.println("Error al registrar usuario: " + e.getMessage());
            }
        }

        private boolean iniciarSesion(DataInputStream entrada, ControladorCliente controladorCliente, DataOutputStream salida) throws IOException {
            try {
                String nombreUsuario = entrada.readUTF();
                String contrasena = entrada.readUTF();

                if (controladorCliente.autenticarUsuario(nombreUsuario, contrasena)) {
                    if (controladorCliente.esAdministrador(nombreUsuario)) {
                        salida.writeUTF("Bienvenido, administrador.");
                    } else {
                        salida.writeUTF("Bienvenido, cliente.");
                    }
                    return true;
                } else {
                    return false;
                }
            } catch (IOException e) {
                System.err.println("Error al iniciar sesión: " + e.getMessage());
                return false;
            }
        }
    }

    private void seleccionarRestauranteCliente(Scanner scanner, DataOutputStream salida, DataInputStream entrada) throws IOException {
        System.out.println("\n=== Restaurantes Disponibles ===");
        int totalRestaurantes = entrada.readInt();
        if (totalRestaurantes == 0) {
            System.out.println("No hay restaurantes disponibles en este momento.");
            return;
        }

        for (int i = 0; i < totalRestaurantes; i++) {
            String restaurante = entrada.readUTF();
            System.out.println((i + 1) + ". " + restaurante);
        }

        System.out.print("Seleccione el número del restaurante que desea ver: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine();

        salida.writeInt(seleccion);
        salida.flush();
        String mensaje = entrada.readUTF();
        System.out.println("Servidor: " + mensaje);
    }

    private void verMenuRestaurantePorSeleccionCliente(Scanner scanner, DataOutputStream salida, DataInputStream entrada) throws IOException {
        System.out.print("Ingrese el ID del restaurante: ");
        int restauranteId = scanner.nextInt();
        scanner.nextLine();
        salida.writeInt(restauranteId);
        salida.flush();

        int totalMenuItems = entrada.readInt();
        if (totalMenuItems == 0) {
            System.out.println("No se encontró un menú para el restaurante seleccionado.");
            return;
        }

        System.out.println("\n=== Menú del Restaurante ===");
        for (int i = 0; i < totalMenuItems; i++) {
            String menuItem = entrada.readUTF();
            System.out.println("- " + menuItem);
        }
    }
}



















//public class OctoberEatsUsuario {
//    private static final int PUERTO = 5432;
//
//    public static void main(String[] args) {
//        OctoberEatsUsuario cliente = new OctoberEatsUsuario();
//
//        cliente.iniciarCliente();
//    }
//
//    public void iniciarCliente() {
//        try {
//            Thread.sleep(1000); // Dar tiempo para que el servidor se inicie
//
//            Socket socketCliente = new Socket("localhost", PUERTO);
//            DataOutputStream salida = new DataOutputStream(socketCliente.getOutputStream());
//            DataInputStream entrada = new DataInputStream(socketCliente.getInputStream());
//            Scanner scanner = new Scanner(System.in);
//
//            // Interacción con el cliente
//            boolean ejecucionCliente = true;
//            while (ejecucionCliente) {
//                System.out.println("\n=== Menú Cliente ===");
//                System.out.println("1. Registrarse");
//                System.out.println("2. Iniciar sesión");
//                System.out.println("3. Salir");
//                System.out.print("Seleccione una opción: ");
//                int opcion = scanner.nextInt();
//                scanner.nextLine(); // Limpiar el buffer
//
//                // Enviar la opción al servidor
//                salida.writeInt(opcion);
//                salida.flush();
//
//                if (opcion == 1 || opcion == 2) {
//                    // Solicitar información al cliente según la opción elegida
//                    System.out.print("Ingrese el nombre de usuario: ");
//                    String nombreUsuario = scanner.nextLine();
//                    System.out.print("Ingrese la contraseña: ");
//                    String contrasena = scanner.nextLine();
//
//                    // Enviar la información al servidor
//                    salida.writeUTF(nombreUsuario);
//                    salida.writeUTF(contrasena);
//                    salida.flush();
//
//                    // Recibir respuesta del servidor
//                    String respuesta = entrada.readUTF();
//                    System.out.println("Servidor: " + respuesta);
//
//                    if (respuesta.contains("administrador")) {
//                        //mostrarMenuAdmin(scanner, salida, entrada);
//                    } else if (respuesta.contains("cliente")) {
//                       // mostrarMenuCliente(scanner, salida, entrada);
//                    }
//
//                } else if (opcion == 3) {
//                    ejecucionCliente = false;
//                    System.out.println("Saliendo...");
//                } else {
//                    System.out.println("Opción no válida.");
//                }
//            }
//
//            socketCliente.close();
//        } catch (IOException | InterruptedException e) {
//            System.err.println("Error en el cliente: " + e.getMessage());
//        }
//    }
//
//    static class ManejoCliente implements Runnable {
//        private Socket socketCliente;
//        private DataOutputStream salida;
//        private DataInputStream entrada;
//
//        public ManejoCliente(Socket socket) {
//            this.socketCliente = socket;
//        }
//
//        @Override
//        public void run() {
//            Gson gson = new Gson();
//            ControladorCliente controladorCliente = new ControladorCliente();
//            ControladorRestaurante controladorRestaurante = new ControladorRestaurante();
//            ControladorAdministrador controladorAdministrador = new ControladorAdministrador();
//
//            try (DataInputStream entrada = new DataInputStream(socketCliente.getInputStream());
//                 DataOutputStream salida = new DataOutputStream(socketCliente.getOutputStream())) {
//
//                boolean ejecucionServidor = true;
//
//                while (ejecucionServidor) {
//                    // Leer la opción enviada por el cliente
//                    int opcion = entrada.readInt();
//                    System.out.println("Opción recibida: " + opcion);
//
//                    String respuesta;
//                    switch (opcion) {
//                        case 1: // Registrarse
//                            registrarUsuario(entrada, controladorCliente);
//                            respuesta = "Usuario registrado correctamente.";
//                            break;
//                        case 2: // Iniciar sesión
//                            if (iniciarSesion(entrada, controladorCliente, salida)) {
//                                respuesta = "Inicio de sesión exitoso.";
//                            } else {
//                                respuesta = "Error en la autenticación.";
//                            }
//                            break;
//                        case 3: // Salir
//                            respuesta = "¡Hasta luego!";
//                            ejecucionServidor = false;
//                            break;
//                        default:
//                            respuesta = "Opción no válida.";
//                    }
//
//                    // Enviar la respuesta al cliente
//                    salida.writeUTF(respuesta);
//                    salida.flush();
//                }
//            } catch (IOException e) {
//                System.err.println("Error al procesar la solicitud del cliente: " + e.getMessage());
//            } finally {
//                try {
//                    socketCliente.close();
//                } catch (IOException e) {
//                    System.err.println("Error al cerrar el socket del cliente: " + e.getMessage());
//                }
//            }
//
//
//        }
//
//        private void registrarUsuario(DataInputStream entrada, ControladorCliente controladorCliente) throws IOException {
//            try {
//                String nombreUsuario = entrada.readUTF();
//                String contrasena = entrada.readUTF();
//
//                System.out.print("¿Es administrador o cliente? (admin/cliente): ");
//                Scanner scanner = new Scanner(System.in);
//                String rol = scanner.nextLine().toLowerCase();
//
//                if (rol.equals("admin")) {
//                    System.out.print("Ingrese el nombre del administrador: ");
//                    String nombre = scanner.nextLine();
//                    System.out.print("Ingrese el apellido del administrador: ");
//                    String apellido = scanner.nextLine();
//                    System.out.print("Ingrese el nombre del restaurante: ");
//                    String nombreRestaurante = scanner.nextLine();
//                    System.out.print("Ingrese la dirección del restaurante: ");
//                    String direccionRestaurante = scanner.nextLine();
//                    System.out.print("Ingrese el tipo de comida del restaurante: ");
//                    String tipoComida = scanner.nextLine();
//
//                    if (controladorCliente.registrarAdministrador(nombreUsuario, contrasena, nombre, apellido, nombreRestaurante, direccionRestaurante, tipoComida)) {
//                        System.out.println("Administrador y restaurante registrados con éxito.");
//                    } else {
//                        System.out.println("Error al registrar el administrador.");
//                    }
//                } else if (rol.equals("cliente")) {
//                    if (controladorCliente.registrarUsuario(nombreUsuario, contrasena)) {
//                        System.out.println("Cliente registrado con éxito.");
//                    } else {
//                        System.out.println("Error al registrar el cliente.");
//                    }
//                } else {
//                    System.out.println("Rol no válido. Por favor, intente de nuevo.");
//                }
//            } catch (IOException e) {
//                System.err.println("Error al registrar usuario: " + e.getMessage());
//            }
//        }
//
//        private boolean iniciarSesion(DataInputStream entrada, ControladorCliente controladorCliente, DataOutputStream salida) throws IOException {
//            try {
//                String nombreUsuario = entrada.readUTF();
//                String contrasena = entrada.readUTF();
//
//                if (controladorCliente.autenticarUsuario(nombreUsuario, contrasena)) {
//                    if (controladorCliente.esAdministrador(nombreUsuario)) {
//                        salida.writeUTF("Bienvenido, administrador.");
//                    } else {
//                        salida.writeUTF("Bienvenido, cliente.");
//                    }
//                    return true;
//                } else {
//                    return false;
//                }
//            } catch (IOException e) {
//                System.err.println("Error al iniciar sesión: " + e.getMessage());
//                return false;
//            }
//        }
//    }
//
//}

