package main.java.com.bibliotech.model;

public abstract class Socio {
    private final int dni;
    private final String nombre;
    private final String email;
    private int prestamosActivos;

    public Socio(int dni, String nombre, String email) {
        this.dni = dni;
        this.nombre = nombre;
        this.email = email;
        this.prestamosActivos = 0;
    }

    public int dni() {
        return dni;
    }
    public String nombre() {
        return nombre;
    }
    public String email() {
        return email;
    }
    public int prestamosActivos() {
        return prestamosActivos;
    }
    public void incrementarPrestamos() {
        prestamosActivos++;
    }
    public void decrementarPrestamos() {
        if (prestamosActivos > 0) {
            prestamosActivos--;
        }
    }
    public boolean puedePedirPrestamo() {
        return prestamosActivos < maxPrestamos();
    }
    public abstract int maxPrestamos();

}

