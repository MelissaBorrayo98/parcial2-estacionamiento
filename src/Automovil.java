/**
 * Vehiculo tipo Automovil. Tarifa: Q10.00 por hora.
 * Si horasUtilizadas > 5, se aplica 10% de descuento sobre el total.
 */
public class Automovil extends Vehiculo {

    private static final double TARIFA_HORA = 10.0;

    public Automovil(String placa, String propietario, double horasUtilizadas) {
        super(placa, propietario, horasUtilizadas);
    }

    @Override
    public double calcularCosto() {
        double costo = getHorasUtilizadas() * TARIFA_HORA;
        if (getHorasUtilizadas() > 5) {
            costo *= 0.90; // 10% de descuento
        }
        return costo;
    }

    @Override
    public String getTipo() {
        return "Automovil";
    }
}
