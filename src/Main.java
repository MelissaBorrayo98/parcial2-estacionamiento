import java.util.HashMap;
import java.util.Optional;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final Estacionamiento estacionamiento = new Estacionamiento();

    public static void main(String[] args) {
        boolean salir = false;

        while (!salir) {
            mostrarMenu();
            int opcion = leerOpcionMenu();

            switch (opcion) {
                case 1 -> registrarVehiculo();
                case 2 -> estacionamiento.mostrarTodos();
                case 3 -> buscarVehiculo();
                case 4 -> mostrarMayorCosto();
                case 5 -> mostrarTotalGeneral();
                case 6 -> mostrarTotalPorTipo();
                case 7 -> {
                    salir = true;
                    System.out.println("Saliendo del sistema. Hasta luego.");
                }
                default -> System.out.println("Opcion invalida. Intente nuevamente.");
            }
        }
        sc.close();
    }

    private static void mostrarMenu() {
        System.out.println("\n===== ESTACIONAMIENTO =====");
        System.out.println("1. Registrar vehiculo");
        System.out.println("2. Mostrar todos los vehiculos");
        System.out.println("3. Buscar vehiculo por placa");
        System.out.println("4. Mostrar vehiculo con mayor costo");
        System.out.println("5. Mostrar total general recaudado");
        System.out.println("6. Mostrar total recaudado por tipo");
        System.out.println("7. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    // Validacion de opcion de menu: si no es un numero, no debe tronar el programa
    private static int leerOpcionMenu() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Fuerza el caso "default" del switch (opcion invalida)
        }
    }

    private static void registrarVehiculo() {
        System.out.print("Placa: ");
        String placa = sc.nextLine().trim();
        if (placa.isEmpty()) {
            System.out.println("Error: la placa no puede estar vacia.");
            return;
        }

        System.out.print("Propietario: ");
        String propietario = sc.nextLine().trim();
        if (propietario.isEmpty()) {
            System.out.println("Error: el propietario no puede estar vacio.");
            return;
        }

        System.out.print("Tipo de vehiculo (1=Automovil, 2=Motocicleta): ");
        String tipoOpcion = sc.nextLine().trim();
        if (!tipoOpcion.equals("1") && !tipoOpcion.equals("2")) {
            System.out.println("Error: tipo de vehiculo invalido.");
            return;
        }

        // Manejo de excepciones: el usuario puede escribir texto en vez de un numero
        double horas;
        System.out.print("Horas utilizadas: ");
        try {
            horas = Double.parseDouble(sc.nextLine().trim());
            if (horas <= 0) {
                System.out.println("Error: las horas utilizadas deben ser mayores que cero.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: debe ingresar un valor numerico valido para las horas.");
            return;
        } finally {
            // Funcion observable: se deja constancia de que el intento de registro se proceso
            System.out.println("(Intento de registro procesado: " + placa + ")");
        }

        Vehiculo vehiculo = tipoOpcion.equals("1")
                ? new Automovil(placa, propietario, horas)
                : new Motocicleta(placa, propietario, horas);

        boolean registrado = estacionamiento.registrarVehiculo(vehiculo);
        if (registrado) {
            System.out.println("Vehiculo registrado correctamente. Costo calculado: Q"
                    + String.format("%.2f", vehiculo.calcularCosto()));
        } else {
            System.out.println("Error: ya existe un vehiculo registrado con la placa " + placa.toUpperCase());
        }
    }

    private static void buscarVehiculo() {
        System.out.print("Ingrese la placa a buscar: ");
        String placa = sc.nextLine().trim();
        Optional<Vehiculo> resultado = estacionamiento.buscarPorPlaca(placa);
        if (resultado.isPresent()) {
            resultado.get().mostrarInformacion();
        } else {
            System.out.println("No se encontro ningun vehiculo con esa placa.");
        }
    }

    private static void mostrarMayorCosto() {
        Optional<Vehiculo> mayor = estacionamiento.obtenerMayorCosto();
        if (mayor.isPresent()) {
            System.out.println("Vehiculo con mayor costo generado:");
            mayor.get().mostrarInformacion();
        } else {
            System.out.println("No hay vehiculos registrados todavia.");
        }
    }

    private static void mostrarTotalGeneral() {
        System.out.printf("Total general recaudado: Q%.2f%n", estacionamiento.totalGeneralRecaudado());
    }

    private static void mostrarTotalPorTipo() {
        HashMap<String, Double> totales = estacionamiento.totalPorTipo();
        if (totales.isEmpty()) {
            System.out.println("No hay vehiculos registrados todavia.");
            return;
        }
        System.out.println("Total recaudado por tipo de vehiculo:");
        for (var entrada : totales.entrySet()) {
            System.out.printf("%-12s Q%.2f%n", entrada.getKey() + ":", entrada.getValue());
        }
    }
}
