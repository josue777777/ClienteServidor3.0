package Grupo4SC303MNProyectoClienteServidor;

import java.util.List;
import java.util.Scanner;

public class OctoberEatsGestionPrincipal {
    public static void main(String[] args) {

        UsuariosOperaciones usuariosOperaciones = new UsuariosOperaciones();
        RestauranteBD restaurantesOperaciones = new RestauranteBD();
        MenuOperaciones menuOperaciones = new MenuOperaciones(); // Agrega una instancia de MenuOperaciones
        Pedidos pedidos = new Pedidos();
        Scanner s = new Scanner(System.in);
        while (true){
            System.out.println("¿Desea ingresar como cliente o administrador? (1/2)");
            int opcion = s.nextInt();
            if(opcion == 1){
                mostrarMenuPrincipalCliente();

                Scanner scanner = new Scanner(System.in);
                while (true) {
                    switch (opcion) {
                        case 1:
                            registrarUsuario(scanner, usuariosOperaciones);
                            break;
                        case 2:
                            autenticarUsuario(scanner, usuariosOperaciones);
                            break;
                        case 3:
                            listarRestaurantes(restaurantesOperaciones);
                            break;
                        case 4:
                            listarMenu(scanner, menuOperaciones); // Cambia a usar MenuOperaciones
                            break;
                        case 0:
                            System.out.println("Saliendo del sistema. ¡Hasta luego!");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Opción no válida. Intente de nuevo.");
                    }
                }
            } else if (opcion == 2){
                mostrarMenuPrincipalAdministrador();
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    switch (opcion) {
                        case 1:
                            //seleccionarRestaurante(scanner, restaurantesOperaciones);
                            break;
                        case 2:
                           // autenticarAdministrador(scanner, usuariosOperaciones);
                            break;
                        case 3:
                            //listarPedidosPendientes(pedidos);
                            break;
                        case 4:
                            //modificarMenu(scanner, menuOperaciones); // Cambia a usar MenuOperaciones
                            break;
                        case 0:
                            System.out.println("Saliendo del sistema. ��Hasta luego!");
                            scanner.close();
                            return;
                        default:
                            System.out.println("Opción no válida. Intente de nuevo.");
                    }
                }
            } else {
                System.out.println("Opción no válida. Intente de nuevo.");
            }
        }

    }


    private static void mostrarMenuPrincipalAdministrador(){
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

    private static void mostrarMenuPedidos(){
        System.out.println("\n===== Menú Principal =====");
        System.out.println("1. Registrar Pedidos");
        System.out.println("2. Consultar Pedidos");
        System.out.println("3. Listar Pedidos");
        System.out.println("4. Eliminar pedido");
        System.out.println("4. Actualizar pedido");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void registrarUsuario(Scanner scanner, UsuariosOperaciones usuariosOperaciones) {
        System.out.print("Ingrese el nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Ingrese la contraseña: ");
        String contrasena = scanner.nextLine();

        if (usuariosOperaciones.registrarUsuario(nombreUsuario, contrasena)) {
            System.out.println("Usuario registrado con éxito.");
        } else {
            System.out.println("Error al registrar el usuario.");
        }
    }

    private static void autenticarUsuario(Scanner scanner, UsuariosOperaciones usuariosOperaciones) {
        System.out.print("Ingrese el nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Ingrese la contraseña: ");
        String contrasena = scanner.nextLine();

        if (usuariosOperaciones.autenticarUsuario(nombreUsuario, contrasena)) {
            System.out.println("Autenticación exitosa. ¡Bienvenido!");
        } else {
            System.out.println("Error en la autenticación. Usuario o contraseña incorrectos.");
        }
    }

    private static void listarRestaurantes(RestauranteBD restaurantesOperaciones) {
        List<String> restaurantes = restaurantesOperaciones.listarRestaurantesBD();

        System.out.println("\n===== Restaurantes Disponibles =====");
        for (String restaurante : restaurantes) {
            System.out.println(restaurante);
        }
    }

    private static void listarMenu(Scanner scanner, MenuOperaciones menuOperaciones) { // Cambia el parámetro a MenuOperaciones
        System.out.print("Ingrese el nombre del restaurante para listar su menú: ");
        String nombreRestaurante = scanner.nextLine();
        List<String> menu = menuOperaciones.listarMenu(); // metodo menu de opciones

        System.out.println("\n===== Menú de " + nombreRestaurante + " =====");
        for (String item : menu) {
            System.out.println(item);
        }
    }
}
