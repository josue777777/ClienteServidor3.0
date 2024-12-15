package Grupo4SC303MNProyectoClienteServidor;

import Grupo4SC303MNProyectoClienteServidor.Controller.ControladorAdministrador;
import Grupo4SC303MNProyectoClienteServidor.Controller.ControladorCliente;
import Grupo4SC303MNProyectoClienteServidor.Controller.ControladorRestaurante;

import Grupo4SC303MNProyectoClienteServidor.Model.Cliente;
import Grupo4SC303MNProyectoClienteServidor.Model.Pedido;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class OctoberEatsGestionPrincipal {

    // contador id cliente por cada pedido
    int idClientePedido = 1;

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
                    if (iniciarSesion(scanner, controladorCliente, controladorRestaurante)) {
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
                    int restauranteSeleccionado = seleccionarRestaurante(scanner, controladorRestaurante);
                    if (restauranteSeleccionado != -1) {
                        verMenuRestaurantePorSeleccion(scanner, restauranteSeleccionado, controladorRestaurante);
                    }
                    break;
                case 2:
                    verMenuRestaurante(scanner, controladorRestaurante);
                    break;
                case 3:
                    realizarPedido(scanner, controladorRestaurante);
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
        System.out.println("1. Ver Restaurantes");
        System.out.println("2. Ver Menú de Restaurante");
        System.out.println("3. Realizar Pedido");
        System.out.println("4. Cerrar Sesión");
        System.out.print("Seleccione una opción: ");
    }

    private static void mostrarMenuAdmin(Scanner scanner, ControladorCliente controladorCliente, ControladorRestaurante controladorRestaurante) {
        while (true) {
            System.out.println("\n=== Menú Administrador ===");
            System.out.println("1. Ver Pedidos");
            System.out.println("2. Agregar Platillo al Menú");
            System.out.println("3. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    menuGestionPedidos(scanner, controladorRestaurante);
                    break;
                case 2:
                    agregarPlato(scanner, controladorRestaurante);
                    break;
                case 3:
                    System.out.println("Cerrando sesión...");
                    return;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    public static void menuGestionPedidos(Scanner scanner, ControladorRestaurante controladorRestaurante) {
        while (true) {
            System.out.println("\n=== Gestión de Pedidos ===");
            System.out.println("1. Registrar Pedido");
            System.out.println("2. Consultar Pedido");
            System.out.println("3. Listar Pedidos");
            System.out.println("4. Eliminar Pedido");
            System.out.println("5. Actualizar Pedido");
            System.out.println("6. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    registrarPedido(scanner, controladorRestaurante);
                    break;
                case 2:
                    consultarPedido(scanner, controladorRestaurante);
                    break;
                case 3:
                    listarPedidos(controladorRestaurante);
                    break;
                case 4:
                    eliminarPedido(scanner, controladorRestaurante);
                    break;
                case 5:
                    actualizarPedido(scanner, controladorRestaurante);
                    break;
                case 6:
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

    private static boolean iniciarSesion(Scanner scanner, ControladorCliente controladorCliente, ControladorRestaurante controladorRestaurante) {
        System.out.print("Ingrese el nombre de usuario: ");
        String nombreUsuario = scanner.nextLine();
        System.out.print("Ingrese la contraseña: ");
        String contrasena = scanner.nextLine();

        if (controladorCliente.autenticarUsuario(nombreUsuario, contrasena)) {
            if (controladorCliente.esAdministrador(nombreUsuario)) {
                mostrarMenuAdmin(scanner, controladorCliente, controladorRestaurante);
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

    private static int seleccionarRestaurante(Scanner scanner, ControladorRestaurante controladorRestaurante) {
        System.out.println("\n=== Restaurantes Disponibles ===");
        List<String> restaurantes = controladorRestaurante.listarRestaurantesBD();

        if (restaurantes.isEmpty()) {
            System.out.println("No hay restaurantes disponibles en este momento.");
            return -1;
        }

        for (int i = 0; i < restaurantes.size(); i++) {
            System.out.println((i + 1) + ". " + restaurantes.get(i));
        }

        System.out.print("Seleccione el número del restaurante que desea ver: ");
        int seleccion = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        if (seleccion > 0 && seleccion <= restaurantes.size()) {
            System.out.println("Ha seleccionado el restaurante: " + restaurantes.get(seleccion - 1));
            return seleccion; // Retornar índice seleccionado (puedes mapear esto a un ID si es necesario)
        } else {
            System.out.println("Selección no válida. Intente de nuevo.");
            return -1;
        }
    }

    private static void verMenuRestaurantePorSeleccion(Scanner scanner, int restauranteSeleccionado, ControladorRestaurante controladorRestaurante) {
        List<String> menu = controladorRestaurante.listarMenuPorId(restauranteSeleccionado);

        if (menu.isEmpty()) {
            System.out.println("No se encontró un menú para el restaurante seleccionado o el restaurante no existe.");
        } else {
            System.out.print("¿Desea ver el menú de este restaurante? (Sí/No): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();

            if (respuesta.equals("sí") || respuesta.equals("si")) {
                System.out.println("\n=== Menú del Restaurante ===");
                menu.forEach(System.out::println);
            } else {
                System.out.println("No se mostrará el menú.");
            }
        }
    }

    private static void verMenuRestaurante(Scanner scanner, ControladorRestaurante controladorRestaurante) {
        System.out.print("Ingrese el ID del restaurante: ");
        int restauranteId = scanner.nextInt();
        scanner.nextLine();

        List<String> menu = controladorRestaurante.listarMenuPorId(restauranteId);

        if (menu.isEmpty()) {
            System.out.println("No se encontró un menú para el restaurante especificado o el restaurante no existe.");
        } else {
            System.out.println("\n=== Menú del Restaurante ===");
            menu.forEach(System.out::println);
        }
    }


    private static void realizarPedido(Scanner scanner, ControladorRestaurante controladorRestaurante) {

        List<String> detallesDelPedido = new ArrayList<String>();

        System.out.print("Ingrese el ID del restaurante para ver su menú: ");
        int restauranteID = scanner.nextInt();
        scanner.nextLine();

        List<String> menu = controladorRestaurante.listarMenuPorId(restauranteID);
        if (menu.isEmpty()) {
            System.out.println("No hay productos disponibles en el menú.");
            return;
        }

        System.out.println("Menú del restaurante:");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i));
        }

        System.out.print("Ingrese el número del producto que desea comprar: ");
        int productoSeleccionado = scanner.nextInt();
        scanner.nextLine();

        // Ingrese su numero de cedula
        System.out.print("Ingrese su número de cédula: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Ingrese la cantidad que desea comprar: ");
        String cantidad = scanner.next(); //
        scanner.nextLine();
        detallesDelPedido.add(cantidad + "(Cantidad que solicita el Cliente:");


        // Ingrese los detalles del pedido
        System.out.println("Ingrese los detalles del pedido (Ej: Agregar 2 cebollas al plato principal): ");
        String detalle = scanner.nextLine();
        detallesDelPedido.add(detalle);


        if(productoSeleccionado > 0 && productoSeleccionado <= menu.size()) {
            System.out.println("Producto seleccionado: " + menu.get(productoSeleccionado - 1));
            System.out.println("Precio del producto: $" + controladorRestaurante.listarMenuPorId(restauranteID).get(productoSeleccionado - 1).split("-")[1]);

            System.out.print("¿Desea confirmar el pedido? (Sí/No): ");
            String respuesta = scanner.nextLine().trim().toLowerCase();

            if (respuesta.equals("sí") || respuesta.equals("si")) {
                System.out.println("Procesando pedido...");
                Pedido pedido = new Pedido( clienteId,restauranteID, detallesDelPedido);
                if (controladorRestaurante.registrarPedidos(clienteId, restauranteID)) {
                System.out.println("Pedido registrado con éxito.");
            } else {
                System.out.println("Pedido cancelado.");
            }
        }



        }
    }

    private static void registrarPedido(Scanner scanner, ControladorRestaurante controladorRestaurante) {
        System.out.print("Ingrese el ID del cliente: ");
        int clienteID = scanner.nextInt();
        System.out.print("Ingrese el ID del restaurante: ");
        int restauranteID = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        if (controladorRestaurante.registrarPedidos(clienteID, restauranteID)) {
            System.out.println("Pedido registrado exitosamente.");
        } else {
            System.out.println("Error al registrar el pedido.");
        }
    }

    private static void consultarPedido(Scanner scanner, ControladorRestaurante controladorRestaurante) {
        System.out.print("Ingrese el ID del pedido: ");
        int pedidoID = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        String resultado = controladorRestaurante.consultarPedido(pedidoID);
        if (resultado != null) {
            System.out.println("\n=== Detalles del Pedido ===");
            System.out.println(resultado);
        } else {
            System.out.println("Error al consultar el pedido.");
        }
    }

    private static void listarPedidos(ControladorRestaurante controladorRestaurante) {
        System.out.println("\n=== Listado de Pedidos ===");
        List<String> pedidos = controladorRestaurante.listarPedidos();

        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos registrados.");
        } else {
            pedidos.forEach(System.out::println);
        }
    }

    private static void eliminarPedido(Scanner scanner, ControladorRestaurante controladorRestaurante) {
        System.out.print("Ingrese el ID del pedido a eliminar: ");
        int pedidoID = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        if (controladorRestaurante.eliminarPedido(pedidoID)) {
            System.out.println("Pedido eliminado exitosamente.");
        } else {
            System.out.println("Error al eliminar el pedido. Verifique el ID e intente nuevamente.");
        }
    }

    private static void actualizarPedido(Scanner scanner, ControladorRestaurante controladorRestaurante) {
        System.out.print("Ingrese el ID del pedido a actualizar: ");
        int pedidoID = scanner.nextInt();
        scanner.nextLine(); // Limpiar el buffer

        System.out.print("Ingrese el nuevo estado del pedido: ");
        String nuevoEstado = scanner.nextLine().trim();

        if (controladorRestaurante.actualizarPedido(pedidoID, nuevoEstado)) {
            System.out.println("Estado del pedido actualizado exitosamente.");
        } else {
            System.out.println("Error al actualizar el estado del pedido. Verifique los datos e intente nuevamente.");
        }
    }

    private static void agregarPlato(Scanner scanner, ControladorRestaurante controladorRestaurante) {
        System.out.print("Ingrese el nombre del plato: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese la descripción del plato: ");
        String descripcion = scanner.nextLine();
        System.out.print("Ingrese el precio del plato: ");
        double precio = scanner.nextDouble();
        scanner.nextLine(); // Limpiar el buffer

        if (controladorRestaurante.agregarPlato(nombre, descripcion, precio)) {
            System.out.println("Plato agregado exitosamente al menú.");
        } else {
            System.out.println("Error al agregar el plato. Intente de nuevo.");
        }
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



