import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

/**
 * Administra el registro de vehiculos usando las colecciones requeridas:
 * - ArrayList<Vehiculo>: almacena todos los vehiculos registrados.
 * - HashSet<String>: evita placas duplicadas.
 * - HashMap<String, Double>: totales recaudados por tipo (se recalcula, no se hardcodea).
 */
public class Estacionamiento {

    private final ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    private final HashSet<String> placasRegistradas = new HashSet<>();

    /**
     * Intenta registrar un vehiculo. Devuelve false si la placa ya existe.
     */
    public boolean registrarVehiculo(Vehiculo vehiculo) {
        String placaNormalizada = vehiculo.getPlaca().trim().toUpperCase();
        if (placasRegistradas.contains(placaNormalizada)) {
            return false; // placa duplicada
        }
        placasRegistradas.add(placaNormalizada);
        vehiculos.add(vehiculo);
        return true;
    }

    public void mostrarTodos() {
        if (vehiculos.isEmpty()) {
            System.out.println("No hay vehiculos registrados.");
            return;
        }
        for (Vehiculo v : vehiculos) {
            v.mostrarInformacion(); // polimorfismo: cada uno calcula su propio costo
        }
    }

    public Optional<Vehiculo> buscarPorPlaca(String placa) {
        String placaNormalizada = placa.trim().toUpperCase();
        for (Vehiculo v : vehiculos) {
            if (v.getPlaca().trim().toUpperCase().equals(placaNormalizada)) {
                return Optional.of(v);
            }
        }
        return Optional.empty();
    }

    public Optional<Vehiculo> obtenerMayorCosto() {
        Vehiculo mayor = null;
        for (Vehiculo v : vehiculos) {
            if (mayor == null || v.calcularCosto() > mayor.calcularCosto()) {
                mayor = v;
            }
        }
        return Optional.ofNullable(mayor);
    }

    public double totalGeneralRecaudado() {
        double total = 0;
        for (Vehiculo v : vehiculos) {
            total += v.calcularCosto();
        }
        return total;
    }

    /**
     * Calcula el total recaudado por tipo de vehiculo recorriendo el ArrayList.
     * No se escriben los totales manualmente: se acumulan aqui.
     */
    public HashMap<String, Double> totalPorTipo() {
        HashMap<String, Double> totales = new HashMap<>();
        for (Vehiculo v : vehiculos) {
            String tipo = v.getTipo();
            double acumulado = totales.getOrDefault(tipo, 0.0);
            totales.put(tipo, acumulado + v.calcularCosto());
        }
        return totales;
    }

    public boolean estaVacio() {
        return vehiculos.isEmpty();
    }
}
