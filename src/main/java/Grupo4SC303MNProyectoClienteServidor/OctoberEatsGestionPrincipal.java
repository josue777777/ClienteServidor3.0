package Grupo4SC303MNProyectoClienteServidor;

import Grupo4SC303MNProyectoClienteServidor.Controller.ControladorAdministrador;
import Grupo4SC303MNProyectoClienteServidor.Controller.ControladorCliente;
import Grupo4SC303MNProyectoClienteServidor.Controller.ControladorRestaurante;

import Grupo4SC303MNProyectoClienteServidor.Utilidades.DataBase;

import java.util.List;
import java.util.Scanner;

public class OctoberEatsGestionPrincipal {
    public static void main(String[] args) {

        ControladorCliente controladorCliente = new ControladorCliente();
        ControladorRestaurante controladorRestaurante = new ControladorRestaurante();
        ControladorAdministrador controladorAdministrador = new ControladorAdministrador();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            mostrarMenuPrincipal();
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            switch (opcion) {
                case 1:
                    registrarUsuario(scanner, controladorCliente);
                    break;
                case 2:
                    if (iniciarSesion(scanner, controladorCliente)) {
                        mostrarMenuCliente(scanner, controladorCliente, controladorRestaurante);
                    } else {
                        System.out.println("Error en la autenticación. Intente de nuevo.");
                    }
                    break;
                case 3:
                    System.out.println("¡Hasta luego!");
                    return; // Salir del programa
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("\n=== Menú Principal ===");
        System.out.println("1. Registrar Usuario");
        System.out.println("2. Iniciar Sesión");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
    }


    private static void mostrarMenuCliente(Scanner scanner, ControladorCliente controladorCliente, ControladorRestaurante controladorRestaurante) {
        while (true) {
            mostrarMenuCliente();
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer de entrada

            switch (opcion) {
                case 1:
                    listarRestaurantes(controladorRestaurante);
                    break;
                case 2:
                    //verMenuRestaurante(scanner, controladorRestaurante);
                    break;
                case 3:
                    //realizarPedido(scanner, controladorRestaurante);
                    break;
                case 4:
                    System.out.println("Cerrando sesión...");
                    return; // Regresar al menú principal
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void mostrarMenuCliente() {
        System.out.println("\n=== Menú Cliente ===");
        System.out.println("1. Listar Restaurantes");
        System.out.println("2. Ver Menú de Restaurante");
        System.out.println("3. Realizar Pedido");
        System.out.println("4. Cerrar Sesión");
        System.out.print("Seleccione una opción: ");
    }


    private static void mostrarMenuAdmin(Scanner scanner, ControladorCliente controladorCliente) {
        while (true) {
            System.out.println("\n=== Menú Administrador ===");
            System.out.println("1. Ver Pedidos");
            System.out.println("2. Agregar Platillo al Menú");
            System.out.println("3. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer

            switch (opcion) {
                case 1:
                    System.out.println("Mostrando pedidos...");
                    // Implementar lógica para mostrar pedidos
                    break;
                case 2:
                    System.out.println("Agregando platillo...");
                    // Implementar lógica para agregar platillo
                    break;
                case 3:
                    System.out.println("Cerrando sesión...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

private static void registrarUsuario(Scanner scanner, ControladorCliente controladorCliente) {
    System.out.print("Ingrese el nombre de usuario: ");
    String nombreUsuario = scanner.nextLine();
    System.out.print("Ingrese la contraseña: ");
    String contrasena = scanner.nextLine();

    System.out.print("¿Es administrador o cliente? (admin/cliente): ");
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
}

    private static boolean iniciarSesion(Scanner scanner, ControladorCliente controladorCliente) {
        System.out.print("Ingrese el nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Ingrese la contraseña: ");
        String contrasena = scanner.nextLine();

        if (controladorCliente.autenticarUsuario(nombreUsuario, contrasena)) {
            if (controladorCliente.esAdministrador(nombreUsuario)) {
                mostrarMenuAdmin(scanner, controladorCliente);
            } else {
                mostrarMenuCliente(scanner, controladorCliente, new ControladorRestaurante());
            }
            return true;
        } else {
            System.out.println("Error en la autenticación. Intente de nuevo.");
            return false;
        }
    }



    private static void listarRestaurantes(ControladorRestaurante controladorRestaurante) {
        System.out.println("\n=== Restaurantes Disponibles ===");
        controladorRestaurante.listarRestaurantesBD().forEach(System.out::println);
    }

    private static void verMenuRestaurante(Scanner scanner, ControladorRestaurante controladorRestaurante) {
        System.out.print("Ingrese el ID del restaurante: ");
        int restauranteID = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

        //controladorRestaurante.mostrarMenuRestaurante(restauranteID);
    }

    private static void realizarPedido(Scanner scanner, ControladorRestaurante controladorRestaurante) {
        System.out.print("Ingrese el ID del restaurante: ");
        int restauranteID = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

        System.out.print("Ingrese el ID del platillo: ");
        int platilloID = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

        System.out.print("Ingrese la cantidad: ");
        int cantidad = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer de entrada

        //controladorRestaurante.realizarPedido(restauranteID, platilloID, cantidad);
        System.out.println("Pedido realizado con éxito.");
    }
}



//    private static boolean deseaContinuarEnMenu(Scanner scanner) {
//        while (true) {
//            System.out.println("¿Deseas volver al menu? Sí/No (No: Si deseas salir por completo)");
//            String respuesta = scanner.nextLine().trim().toLowerCase();
//
//            if (respuesta.equals("sí") || respuesta.equals("si")) {
//                return true;
//            } else if (respuesta.equals("no")) {
//                return false;
//            } else {
//                System.out.println("Respuesta no válida. Por favor, escriba 'Sí' o 'No'.");
//            }
//        }
//    }
//



