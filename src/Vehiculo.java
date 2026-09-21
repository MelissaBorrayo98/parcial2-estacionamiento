import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Clase abstracta que representa un vehiculo generico dentro del estacionamiento.
 * Automovil y Motocicleta heredan de esta clase y sobrescriben calcularCosto().
 */
public abstract class Vehiculo {

    private final String placa;
    private final String propietario;
    private final LocalDateTime horaIngreso;
    private final double horasUtilizadas;

    public Vehiculo(String placa, String propietario, double horasUtilizadas) {
        this.placa = placa;
        this.propietario = propietario;
        this.horaIngreso = LocalDateTime.now();
        this.horasUtilizadas = horasUtilizadas;
    }

    public String getPlaca() {
        return placa;
    }

    public String getPropietario() {
        return propietario;
    }

    public LocalDateTime getHoraIngreso() {
        return horaIngreso;
    }

    public double getHorasUtilizadas() {
        return horasUtilizadas;
    }

    // Metodo abstracto: cada tipo de vehiculo calcula su costo de forma distinta
    public abstract double calcularCosto();

    // Util para el HashMap de totales por tipo y para mostrar informacion
    public abstract String getTipo();

    // Metodo concreto heredado por ambas subclases (no se sobrescribe)
    public void mostrarInformacion() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        System.out.printf(
            "Placa: %-8s | Propietario: %-15s | Tipo: %-11s | Ingreso: %s | Horas: %-5.1f | Costo: Q%.2f%n",
            placa, propietario, getTipo(), horaIngreso.format(fmt), horasUtilizadas, calcularCosto()
        );
    }
}
