package Grupo4SC303MNProyectoClienteServidor;

import Grupo4SC303MNProyectoClienteServidor.Controller.ControladorCliente;
import Grupo4SC303MNProyectoClienteServidor.Controller.ControladorRestaurante;
import Grupo4SC303MNProyectoClienteServidor.Data.DataBase;

import java.util.List;
import java.util.Scanner;

public class OctoberEatsGestionPrincipal {
    public static void main(String[] args) {

        ControladorCliente controladorCliente = new ControladorCliente();
        ControladorRestaurante controladorRestaurante = new ControladorRestaurante();
        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.println("¿Desea ingresar como cliente o administrador? (1: Cliente / 2: Administrador / 0: Salir)");
            int opcion = s.nextInt();
            s.nextLine();

            if (opcion == 0) {
                System.out.println("Saliendo del sistema. ¡Hasta luego!");
                break;
            } else if (opcion == 1) {
                manejarCliente(s, controladorCliente, controladorRestaurante);
            } else if (opcion == 2) {
                manejarAdministrador(s, controladorRestaurante);
            } else {
                System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void manejarCliente(Scanner scanner, ControladorCliente controladorCliente, ControladorRestaurante controladorRestaurante) {
        while (true) {
            mostrarMenuPrincipalCliente();
            int subOpcion = scanner.nextInt();

            switch (subOpcion) {
                case 1:
                    registrarUsuario(scanner, controladorCliente);
                    break;
                case 2:
                    autenticarUsuario(scanner, controladorCliente);
                    break;
                case 3:
                    listarRestaurantes(controladorRestaurante);
                    break;
                case 4:
//                    listarMenu(scanner, controladorRestaurante);
                    break;
                case 0:
                    System.out.println("Volviendo al menú inicial...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }

            if (!deseaContinuarEnMenu(scanner)) {
                System.out.println("Volviendo al menú inicial...");
                return;
            }
        }
    }

    private static void manejarAdministrador(Scanner scanner, ControladorRestaurante controladorRestaurante) {
        while (true) {
            mostrarMenuPrincipalAdministrador();
            int subOpcion = scanner.nextInt();
            scanner.nextLine();

            switch (subOpcion) {
                case 1:
                    // Registrar restaurante
                    break;
                case 2:
                    // Autenticar administrador
                    break;
                case 3:
                    listarPedidos(controladorRestaurante);
                    break;
                case 4:
                    // Modificar menú del restaurante
                    break;
                case 0:
                    System.out.println("Volviendo al menú inicial...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }

            if (!deseaContinuarEnMenu(scanner)) {
                System.out.println("Volviendo al menú inicial...");
                return;
            }
        }
    }

    private static boolean deseaContinuarEnMenu(Scanner scanner) {
        while (true) {
            System.out.println("¿Deseas volver al menu? Sí/No (No: Si deseas salir por completo)");
            String respuesta = scanner.nextLine().trim().toLowerCase();

            if (respuesta.equals("sí") || respuesta.equals("si")) {
                return true;
            } else if (respuesta.equals("no")) {
                return false;
            } else {
                System.out.println("Respuesta no válida. Por favor, escriba 'Sí' o 'No'.");
            }
        }
    }


    private static void mostrarMenuPrincipalAdministrador() {
        System.out.println("\n===== Menú Principal =====");
        System.out.println("1. Registrar Su Restaurante");
        System.out.println("2. Autenticar Usuario Como administrador");
        System.out.println("3. Listar Pedidos pendientes de despachar");
        System.out.println("4. Modificar Menu Menu del restaurante");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void mostrarMenuPrincipalCliente() {
        System.out.println("\n===== Menú Principal =====");
        System.out.println("1. Registrar Usuario");
        System.out.println("2. Autenticar Usuario");
        System.out.println("3. Listar Restaurantes");
        System.out.println("4. Listar Menú");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void mostrarMenuPedidos() {
        System.out.println("\n===== Menú Principal =====");
        System.out.println("1. Registrar Pedidos");
        System.out.println("2. Consultar Pedidos");
        System.out.println("3. Listar Pedidos");
        System.out.println("4. Eliminar pedido");
        System.out.println("4. Actualizar pedido");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }


    private static void registrarUsuario(Scanner scanner, ControladorCliente controladorCliente) {
        try {
            System.out.print("Ingrese el nombre de usuario: ");
            String nombreUsuario = scanner.nextLine();
            if (nombreUsuario.equalsIgnoreCase("salir")) {
                System.out.println("Regresando al menú principal...");
                return;
            }
            scanner.nextLine();
            System.out.print("Ingrese la contraseña: ");
            String contrasena = scanner.nextLine();
            if (controladorCliente.registrarUsuario(nombreUsuario, contrasena)) {
                System.out.println("Usuario registrado con éxito.");
            } else {
                System.out.println("Error al registrar el usuario.");
            }
        } catch (Exception e) {
            System.err.println("Ocurrió un error al registrar el usuario: " + e.getMessage());
        }
    }

    private static void autenticarUsuario(Scanner scanner, ControladorCliente controladorCliente) {
        try {
            System.out.print("Ingrese el nombre de usuario: ");
            String nombreUsuario = scanner.nextLine();
            scanner.nextLine();
            System.out.print("Ingrese la contraseña: ");
            String contrasena = scanner.nextLine();
            if (controladorCliente.autenticarUsuario(nombreUsuario, contrasena)) {
                System.out.println("Autenticación exitosa. ¡Bienvenido!");
            } else {
                System.out.println("Error en la autenticación. Usuario o contraseña incorrectos.");
            }
        } catch (Exception e) {
            System.err.println("Ocurrió un error al autenticar el usuario: " + e.getMessage());
        }
    }

    private static void listarRestaurantes(ControladorRestaurante controladorRestaurante) {
        try {
            List<String> restaurantes = controladorRestaurante.listarRestaurantesBD();

            if (restaurantes.isEmpty()) {
                System.out.println("\nNo hay restaurantes disponibles.");
            } else {
                System.out.println("\n===== Restaurantes Disponibles =====");
                for (String restaurante : restaurantes) {
                    System.out.println(restaurante);
                }
            }
        } catch (Exception e) {
            System.err.println("Ocurrió un error al listar los restaurantes: " + e.getMessage());
        }
    }

//    private static void listarMenu(Scanner scanner, ControladorRestaurante controladorRestaurante) {
//        try {
//            System.out.print("Ingrese el nombre del restaurante para listar su menú: ");
//            String nombreRestaurante = scanner.nextLine();
//            List<String> menu = controladorRestaurante.listarMenu();
//
//            System.out.println("\n===== Menú de " + nombreRestaurante + " =====");
//            for (String item : menu) {
//                System.out.println(item);
//            }
//
//        } catch (Exception e) {
//            System.err.println("Ocurrió un error al listar el menú: " + e.getMessage());
//        }
//    }


    private static void registrarPedido(Scanner scanner, ControladorRestaurante pedidos) {
        try {
            DataBase db = DataBase.getInstance();

            System.out.print("Ingrese el ID del cliente: ");
            int clienteID = scanner.nextInt();
            System.out.print("Ingrese el ID del restaurante: ");
            int restauranteID = scanner.nextInt();
            scanner.nextLine(); // Limpia la entrada del Scanner

            String nombreCliente = db.obtenerNombreCliente(clienteID);
            String nombreRestaurante = db.obtenerNombreRestaurante(restauranteID);

            if (nombreCliente == null) {
                System.out.println("Error: Cliente no encontrado con ID " + clienteID);
                return;
            }
            if (nombreRestaurante == null) {
                System.out.println("Error: Restaurante no encontrado con ID " + restauranteID);
                return;
            }

            System.out.println("Cliente: " + nombreCliente);
            System.out.println("Restaurante: " + nombreRestaurante);

            if (pedidos.registrarPedidos(clienteID, restauranteID)) {
                System.out.println("Pedido registrado con éxito.");
            } else {
                System.out.println("Error al registrar el pedido.");
            }
        } catch (Exception e) {
            System.err.println("Ocurrió un error al registrar el pedido: " + e.getMessage());
        }
    }

    private static void consultarPedido(Scanner scanner, ControladorRestaurante pedidos) {
        try {
            System.out.print("Ingrese el ID del pedido: ");
            int pedidoID = scanner.nextInt();

            String resultado = pedidos.consultarPedido(pedidoID);
            if (resultado != null) {
                System.out.println("Detalles del pedido:");
                System.out.println(resultado);
            } else {
                System.out.println("No se encontró el pedido.");
            }

        } catch (Exception e) {
            System.err.println("Ocurrió un error al consultar el pedido: " + e.getMessage());
        }

    }

    private static void listarPedidos(ControladorRestaurante controladorRestaurante) {
        try {
            List<String> pedidos = controladorRestaurante.listarPedidos();

            if (pedidos.isEmpty()) {
                System.out.println("\nNo hay pedidos pendientes.");
            } else {
                System.out.println("\n===== Pedidos Pendientes =====");
                for (String pedido : pedidos) {
                    System.out.println(pedido);
                }
            }
        } catch (Exception e) {
            System.err.println("Ocurrió un error al listar los pedidos: " + e.getMessage());
        }
    }

    private static void eliminarPedido(Scanner scanner, ControladorRestaurante pedidos) {
        try{
            System.out.print("Ingrese el ID del pedido a eliminar: ");
            int pedidoID = scanner.nextInt();

            if (pedidos.eliminarPedido(pedidoID)) {
                System.out.println("Pedido eliminado con éxito.");
            } else {
                System.out.println("Error al eliminar el pedido.");
            }

        } catch (Exception e) {
            System.err.println("Ocurrió un error al eliminar el pedido: " + e.getMessage());

        }
    }

    private static void actualizarPedido(Scanner scanner, ControladorRestaurante pedidos) {
        try {
            System.out.print("Ingrese el ID del pedido a actualizar: ");
            int pedidoID = scanner.nextInt();
            scanner.nextLine(); // Consumir nueva línea
            System.out.print("Ingrese el nuevo estado del pedido: ");
            String nuevoEstado = scanner.nextLine();

            if (pedidos.actualizarPedido(pedidoID, nuevoEstado)) {
                System.out.println("Pedido actualizado con éxito.");
            } else {
                System.out.println("Error al actualizar el pedido.");
            }

        } catch (Exception e) {
            System.err.println("Ocurrió un error al actualizar el pedido: " + e.getMessage());
        }


    }
}
