package main.java.com.bibliotech.repository;

import main.java.com.bibliotech.model.Prestamo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PrestamoRepositoryInMemory implements PrestamoRepository {
    private final List<Prestamo> prestamos = new ArrayList<>();

    @Override
    public void guardar(Prestamo entidad) {
        prestamos.add(entidad);
    }

    @Override
    public Optional<Prestamo> buscarPorId(String id) {
        return prestamos.stream()
                .filter(prestamo -> (prestamo.isbnRecurso() + "-" + prestamo.dniSocio()).equalsIgnoreCase(id))
                .findFirst();
    }

    @Override
    public List<Prestamo> buscarTodos() {
        return new ArrayList<>(prestamos);
    }

    @Override
    public Optional<Prestamo> buscarPrestamoActivoPorIsbn(String isbn) {
        return prestamos.stream()
                .filter(prestamo -> prestamo.isbnRecurso().equalsIgnoreCase(isbn) && !prestamo.estaDevuelto())
                .findFirst();
    }

    @Override
    public List<Prestamo> buscarPorDniSocio(int dni) {
        return prestamos.stream()
                .filter(prestamo -> prestamo.dniSocio() == dni)
                .toList();
    }
}
