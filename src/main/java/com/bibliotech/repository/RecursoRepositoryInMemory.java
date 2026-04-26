package main.java.com.bibliotech.repository;

import main.java.com.bibliotech.model.Categoria;
import main.java.com.bibliotech.model.Recurso;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RecursoRepositoryInMemory implements RecursoRepository {
    private final List<Recurso> recursos = new ArrayList<>();

    @Override
    public void guardar(Recurso entidad) {
        recursos.add(entidad);
    }

    @Override
    public Optional<Recurso> buscarPorId(String isbn) {
        return recursos.stream()
                .filter(recurso -> recurso.isbn().equalsIgnoreCase(isbn))
                .findFirst();
    }

    @Override
    public List<Recurso> buscarTodos() {
        return new ArrayList<>(recursos);
    }

    @Override
    public List<Recurso> buscarPorTitulo(String titulo) {
        return recursos.stream()
                .filter(recurso -> recurso.titulo().toLowerCase().contains(titulo.toLowerCase()))
                .toList();
    }

    @Override
    public List<Recurso> buscarPorAutor(String autor) {
        return recursos.stream()
                .filter(recurso -> recurso.autor().toLowerCase().contains(autor.toLowerCase()))
                .toList();
    }

    @Override
    public List<Recurso> buscarPorCategoria(Categoria categoria) {
        return recursos.stream()
                .filter(recurso -> recurso.categoria().equals(categoria))
                .toList();
    }

    @Override
    public void actualizar(Recurso recursoActualizado) {
        for (int i = 0; i < recursos.size(); i++) {
            if (recursos.get(i).isbn().equalsIgnoreCase(recursoActualizado.isbn())) {
                recursos.set(i, recursoActualizado);
                return;
            }
        }
    }
}
