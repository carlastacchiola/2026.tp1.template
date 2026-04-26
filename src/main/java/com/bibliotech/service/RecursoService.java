package com.bibliotech.service;

import com.bibliotech.model.Categoria;
import com.bibliotech.model.Recurso;
import com.bibliotech.repository.RecursoRepository;

import java.util.List;
import java.util.Optional;

public class RecursoService {

    private final RecursoRepository recursoRepository;

    public RecursoService(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    public void registrarRecurso(Recurso recurso) {
        recursoRepository.guardar(recurso);
    }

    public Optional<Recurso> buscarPorIsbn(String isbn) {
        return recursoRepository.buscarPorId(isbn);
    }

    public List<Recurso> buscarPorTitulo(String titulo) {
        return recursoRepository.buscarPorTitulo(titulo);
    }

    public List<Recurso> buscarPorAutor(String autor) {
        return recursoRepository.buscarPorAutor(autor);
    }

    public List<Recurso> buscarPorCategoria(Categoria categoria) {
        return recursoRepository.buscarPorCategoria(categoria);
    }

    public List<Recurso> listarTodos() {
        return recursoRepository.buscarTodos();
    }
}