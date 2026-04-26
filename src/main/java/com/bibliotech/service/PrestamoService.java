package com.bibliotech.service;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.exception.LimitePrestamosException;
import com.bibliotech.exception.RecursoNoDisponibleException;
import com.bibliotech.exception.RecursoNoEncontradoException;
import com.bibliotech.exception.SocioNoEncontradoException;
import com.bibliotech.model.EBook;
import com.bibliotech.model.LibroFisico;
import com.bibliotech.model.Prestamo;
import com.bibliotech.model.Recurso;
import com.bibliotech.model.Socio;
import com.bibliotech.repository.PrestamoRepository;
import com.bibliotech.repository.RecursoRepository;
import com.bibliotech.repository.SocioRepository;

import java.time.LocalDate;
import java.util.List;

public class PrestamoService {

    private static final int DIAS_PERMITIDOS = 7;

    private final RecursoRepository recursoRepository;
    private final SocioRepository socioRepository;
    private final PrestamoRepository prestamoRepository;

    public PrestamoService(RecursoRepository recursoRepository,
                           SocioRepository socioRepository,
                           PrestamoRepository prestamoRepository) {
        this.recursoRepository = recursoRepository;
        this.socioRepository = socioRepository;
        this.prestamoRepository = prestamoRepository;
    }

    public void realizarPrestamo(String isbn, int dniSocio) throws BibliotecaException {
        Recurso recurso = recursoRepository.buscarPorId(isbn)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe recurso con ISBN " + isbn));

        Socio socio = socioRepository.buscarPorId(dniSocio)
                .orElseThrow(() -> new SocioNoEncontradoException("No existe socio con DNI " + dniSocio));

        if (!recurso.disponible()) {
            throw new RecursoNoDisponibleException("El recurso con ISBN " + isbn + " no está disponible");
        }

        if (!socio.puedePedirPrestamo()) {
            throw new LimitePrestamosException("El socio alcanzó su límite de préstamos");
        }

        Prestamo prestamo = new Prestamo(isbn, dniSocio, LocalDate.now());
        prestamoRepository.guardar(prestamo);

        socio.incrementarPrestamos();

        Recurso recursoActualizado = marcarNoDisponible(recurso);
        recursoRepository.actualizar(recursoActualizado);
    }

    public long devolverRecurso(String isbn) throws BibliotecaException {
        Prestamo prestamo = prestamoRepository.buscarPrestamoActivoPorIsbn(isbn)
                .orElseThrow(() -> new RecursoNoEncontradoException("No hay préstamo activo para ISBN " + isbn));

        Socio socio = socioRepository.buscarPorId(prestamo.dniSocio())
                .orElseThrow(() -> new SocioNoEncontradoException("No existe socio con DNI " + prestamo.dniSocio()));

        Recurso recurso = recursoRepository.buscarPorId(isbn)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe recurso con ISBN " + isbn));

        prestamo.registrarDevolucion(LocalDate.now());

        socio.decrementarPrestamos();

        Recurso recursoActualizado = marcarDisponible(recurso);
        recursoRepository.actualizar(recursoActualizado);

        return prestamo.calcularDiasRetraso(DIAS_PERMITIDOS);
    }

    public List<Prestamo> historialPrestamos() {
        return prestamoRepository.buscarTodos();
    }

    private Recurso marcarNoDisponible(Recurso recurso) {
        if (recurso instanceof LibroFisico libroFisico) {
            return new LibroFisico(
                    libroFisico.isbn(),
                    libroFisico.titulo(),
                    libroFisico.autor(),
                    libroFisico.anio(),
                    libroFisico.categoria(),
                    false,
                    libroFisico.ubicacionEstanteria()
            );
        }

        if (recurso instanceof EBook eBook) {
            return new EBook(
                    eBook.isbn(),
                    eBook.titulo(),
                    eBook.autor(),
                    eBook.anio(),
                    eBook.categoria(),
                    false,
                    eBook.formato()
            );
        }

        return recurso;
    }

    private Recurso marcarDisponible(Recurso recurso) {
        if (recurso instanceof LibroFisico libroFisico) {
            return new LibroFisico(
                    libroFisico.isbn(),
                    libroFisico.titulo(),
                    libroFisico.autor(),
                    libroFisico.anio(),
                    libroFisico.categoria(),
                    true,
                    libroFisico.ubicacionEstanteria()
            );
        }

        if (recurso instanceof EBook eBook) {
            return new EBook(
                    eBook.isbn(),
                    eBook.titulo(),
                    eBook.autor(),
                    eBook.anio(),
                    eBook.categoria(),
                    true,
                    eBook.formato()
            );
        }

        return recurso;
    }
}