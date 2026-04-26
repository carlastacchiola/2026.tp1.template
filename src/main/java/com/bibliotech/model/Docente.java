package main.java.com.bibliotech.model;

public class Docente extends Socio {

    public Docente(int dni, String nombre, String email) {
        super(dni, nombre, email):
    }
    @Override
    public int maxPrestamos() {
        return 5;
    }
}
