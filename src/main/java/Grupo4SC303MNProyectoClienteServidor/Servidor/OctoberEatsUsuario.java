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
                System.out.println("\n=== Menú Cliente ===");
                System.out.println("1. Registrarse");
                System.out.println("2. Iniciar sesión");
                System.out.println("3. Salir");
                System.out.print("Seleccione una opción: ");
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
                        mostrarMenuAdmin(scanner, salida, entrada);
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

    private void mostrarMenuCliente(Scanner scanner, DataOutputStream salida, DataInputStream entrada) throws IOException {
        while (true) {
            System.out.println("\n=== Menú Cliente ===");
            System.out.println("1. Listar Restaurantes");
            System.out.println("2. Ver Menú de Restaurante");
            System.out.println("3. Realizar Pedido");
            System.out.println("4. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            salida.writeInt(opcion);
            salida.flush();

            if (opcion == 4) {
                System.out.println("Cerrando sesión...");
                break;
            }

            switch (opcion) {
                case 1:
                    System.out.println("\n=== Listando Restaurantes ===");
                    String listaRestaurantes = entrada.readUTF(); // Recibir respuesta del servidor
                    System.out.println(listaRestaurantes);
                    break;

                case 2:
                    System.out.print("Ingrese el ID del restaurante para ver el menú: ");
                    int restauranteId = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    verMenuRestaurantePorSeleccion(scanner, restauranteId, entrada, salida);
                    break;

                case 3:
                    System.out.println("Función de realizar pedido aún no implementada.");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }

            String respuesta = entrada.readUTF();
            System.out.println(respuesta);
        }
    }

    private void mostrarMenuAdmin(Scanner scanner, DataOutputStream salida, DataInputStream entrada) throws IOException {
        while (true) {
            System.out.println("\n=== Menú Administrador ===");
            System.out.println("1. Ver Pedidos");
            System.out.println("2. Agregar Platillo al Menú");
            System.out.println("3. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            salida.writeInt(opcion);
            salida.flush();

            if (opcion == 3) {
                System.out.println("Cerrando sesión...");
                break;
            }

            String respuesta = entrada.readUTF();
            System.out.println(respuesta);
        }
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

    private void listarRestaurantes(DataOutputStream salida, ControladorRestaurante controladorRestaurante) throws IOException {
        List<String> restaurantes = controladorRestaurante.listarRestaurantesBD();
        if (restaurantes.isEmpty()) {
            salida.writeUTF("No hay restaurantes disponibles en este momento.");
        } else {
            salida.writeUTF(String.join("\n", restaurantes));
        }
    }

    private void seleccionarRestaurante(DataInputStream entrada, DataOutputStream salida, ControladorRestaurante controladorRestaurante) throws IOException {
        int restauranteId = entrada.readInt();
        List<String> restaurantes = controladorRestaurante.listarRestaurantesBD();
        if (restauranteId > 0 && restauranteId <= restaurantes.size()) {
            // Enviar confirmación al cliente
            salida.writeUTF("Restaurante seleccionado correctamente.");
            salida.flush();
            // Aquí puedes continuar con la siguiente parte de la lógica, como mostrar el menú del restaurante
        } else {
            salida.writeUTF("Selección no válida. Por favor, intente de nuevo.");
            salida.flush();
        }
    }


    private static void verMenuRestaurantePorSeleccion(Scanner scanner, int restauranteSeleccionado, DataInputStream entrada, DataOutputStream salida) throws IOException {
        salida.writeInt(4); // Indicar que es una operación de tipo 4 (ver menú por selección)
        salida.writeInt(restauranteSeleccionado);
        salida.flush();
        String respuesta = entrada.readUTF();
        if (respuesta.equalsIgnoreCase("no_menu")) {
            System.out.println("No se encontró un menú para el restaurante seleccionado o el restaurante no existe.");
        } else {
            System.out.print("¿Desea ver el menú de este restaurante? (Sí/No): ");
            String confirmacion = scanner.nextLine().trim().toLowerCase();

            if (confirmacion.equals("sí") || confirmacion.equals("si")) {
                System.out.println("\n=== Menú del Restaurante ===");
                System.out.println(respuesta); // Mostrar el menú
            } else {
                System.out.println("No se mostrará el menú.");
            }
        }
    }

    private static void verMenuRestaurante(Scanner scanner, DataInputStream entrada, DataOutputStream salida) throws IOException {
        System.out.print("Ingrese el ID del restaurante: ");
        int restauranteId = scanner.nextInt();
        scanner.nextLine(); // Limpiar buffer

        salida.writeInt(5);
        salida.writeInt(restauranteId);
        salida.flush();

        String respuesta = entrada.readUTF();
        if (respuesta.equalsIgnoreCase("no_menu")) {
            System.out.println("No se encontró un menú para el restaurante especificado o el restaurante no existe.");
        } else {
            System.out.println("\n=== Menú del Restaurante ===");
            System.out.println(respuesta);
        }
    }
}

