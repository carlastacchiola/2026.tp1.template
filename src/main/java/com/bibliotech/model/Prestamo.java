package com.bibliotech.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Prestamo {
    private final String isbnRecurso;
    private final int dniSocio;
    private final LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;

    public Prestamo(String isbnRecurso, int dniSocio, LocalDate fechaPrestamo) {
        this.isbnRecurso = isbnRecurso;
        this.dniSocio = dniSocio;
        this.fechaPrestamo = fechaPrestamo;
    }

    public String isbnRecurso() {
        return isbnRecurso;
    }

    public int dniSocio() {
        return dniSocio;
    }

    public LocalDate fechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate fechaDevolucion() {
        return fechaDevolucion;
    }

    public void registrarDevolucion(LocalDate fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public boolean estaDevuelto() {
        return fechaDevolucion != null;
    }

    public long calcularDiasRetraso(int diasPermitidos) {
        LocalDate fechaLimite = fechaPrestamo.plusDays(diasPermitidos);
        LocalDate fechaFinal = (fechaDevolucion != null) ? fechaDevolucion : LocalDate.now();

        if (fechaFinal.isAfter(fechaLimite)) {
            return ChronoUnit.DAYS.between(fechaLimite, fechaFinal);
        }

        return 0;
    }

}
