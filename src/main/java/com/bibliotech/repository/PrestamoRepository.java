package main.java.com.bibliotech.repository;

import main.java.com.bibliotech.model.Prestamo;

import java.util.List;
import java.util.Optional;

public interface PrestamoRepository extends Repository<Prestamo, String> {
    Optional<Prestamo> buscarPrestamoActivoPorIsbn(String isbn);
    List<Prestamo> buscarPorDniSocio(int dni);
}
