package main.java.com.bibliotech.repository;

import main.java.com.bibliotech.model.Socio;
import java.util.Optional;

public interface SocioRepository extends Repository<Socio, Integer> {
    Optional<Socio> buscarPorEmail(String email);
}
