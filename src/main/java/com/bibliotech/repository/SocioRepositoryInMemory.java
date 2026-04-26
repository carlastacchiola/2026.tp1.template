package main.java.com.bibliotech.repository;

import main.java.com.bibliotech.model.Socio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SocioRepositoryInMemory implements SocioRepository {
    private final List<Socio> socios = new ArrayList<>();

    @Override
    public void guardar(Socio entidad) {
        socios.add(entidad);
    }

    @Override
    public Optional<Socio> buscarPorId(Integer dni) {
        return socios.stream()
                .filter(socio -> socio.dni() == dni)
                .findFirst();
    }

    @Override
    public List<Socio> buscarTodos() {
        return new ArrayList<>(socios);
    }

    @Override
    public Optional<Socio> buscarPorEmail(String email) {
        return socios.stream()
                .filter(socio -> socio.email().equalsIgnoreCase(email))
                .findFirst();
    }
}
