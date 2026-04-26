package com.bibliotech.service;

import com.bibliotech.exception.DniDuplicadoException;
import com.bibliotech.exception.EmailInvalidoException;
import com.bibliotech.model.Socio;
import com.bibliotech.repository.SocioRepository;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class SocioService {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final SocioRepository socioRepository;

    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    public void registrarSocio(Socio socio) throws DniDuplicadoException, EmailInvalidoException {
        if (socioRepository.buscarPorId(socio.dni()).isPresent()) {
            throw new DniDuplicadoException("Ya existe un socio con DNI " + socio.dni());
        }

        if (!EMAIL_PATTERN.matcher(socio.email()).matches()) {
            throw new EmailInvalidoException("Email inválido: " + socio.email());
        }

        socioRepository.guardar(socio);
    }

    public Optional<Socio> buscarPorDni(int dni) {
        return socioRepository.buscarPorId(dni);
    }

    public List<Socio> listarTodos() {
        return socioRepository.buscarTodos();
    }
}