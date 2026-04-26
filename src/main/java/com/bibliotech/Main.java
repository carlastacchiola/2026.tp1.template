package com.bibliotech;

import com.bibliotech.model.*;
import com.bibliotech.repository.*;
import com.bibliotech.service.*;
import com.bibliotech.exception.*;

public class Main {

    public static void main(String[] args) {

        try {


            RecursoRepository recursoRepo = new RecursoRepositoryInMemory();
            SocioRepository socioRepo = new SocioRepositoryInMemory();
            PrestamoRepository prestamoRepo = new PrestamoRepositoryInMemory();


            RecursoService recursoService = new RecursoService(recursoRepo);
            SocioService socioService = new SocioService(socioRepo);
            PrestamoService prestamoService = new PrestamoService(recursoRepo, socioRepo, prestamoRepo);


            Recurso libro = new LibroFisico("123", "El Quijote", "Cervantes", 1605, Categoria.CIENCIA, true, "A1");
            recursoService.registrarRecurso(libro);


            Socio socio = new Estudiante(12345678, "Carla", "carla@mail.com");
            socioService.registrarSocio(socio);


            prestamoService.realizarPrestamo("123", 12345678);


            long diasRetraso = prestamoService.devolverRecurso("123");

            System.out.println("Días de retraso: " + diasRetraso);

        } catch (BibliotecaException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}