package ar.edu.utn.frba.dds.dominio.estadistica;

public class Provincia {
  private final double latitud;
  private final double longitud;

  public Provincia(double latitud, double longitud) {
    this.latitud = latitud;
    this.longitud = longitud;
  }

  public double getLatitud() {
    return latitud;
  }
  public double getLongitud() {
    return longitud;
  }
}
