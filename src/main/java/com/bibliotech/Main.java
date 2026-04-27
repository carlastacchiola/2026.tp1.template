package com.bibliotech;

import com.bibliotech.exception.BibliotecaException;
import com.bibliotech.model.*;
import com.bibliotech.repository.*;
import com.bibliotech.service.*;

import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        RecursoRepository recursoRepo = new RecursoRepositoryInMemory();
        SocioRepository socioRepo = new SocioRepositoryInMemory();
        PrestamoRepository prestamoRepo = new PrestamoRepositoryInMemory();

        RecursoService recursoService = new RecursoService(recursoRepo);
        SocioService socioService = new SocioService(socioRepo);
        PrestamoService prestamoService = new PrestamoService(recursoRepo, socioRepo, prestamoRepo);

        int opcion;

        do {
            System.out.println("\n--- BIBLIOTECH ---");
            System.out.println("1. Registrar libro físico");
            System.out.println("2. Registrar e-book");
            System.out.println("3. Buscar recurso");
            System.out.println("4. Registrar socio");
            System.out.println("5. Realizar préstamo");
            System.out.println("6. Devolver recurso");
            System.out.println("7. Ver historial");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            opcion = Integer.parseInt(sc.nextLine());

            try {
                switch (opcion) {
                    case 1 -> {
                        System.out.print("ISBN: ");
                        String isbn = sc.nextLine();
                        System.out.print("Título: ");
                        String titulo = sc.nextLine();
                        System.out.print("Autor: ");
                        String autor = sc.nextLine();
                        System.out.print("Año: ");
                        int anio = Integer.parseInt(sc.nextLine());

                        recursoService.registrarRecurso(
                                new LibroFisico(isbn, titulo, autor, anio, Categoria.CIENCIA, true, "A1")
                        );

                        System.out.println("Libro físico registrado.");
                    }

                    case 2 -> {
                        System.out.print("ISBN: ");
                        String isbn = sc.nextLine();
                        System.out.print("Título: ");
                        String titulo = sc.nextLine();
                        System.out.print("Autor: ");
                        String autor = sc.nextLine();
                        System.out.print("Año: ");
                        int anio = Integer.parseInt(sc.nextLine());

                        recursoService.registrarRecurso(
                                new EBook(isbn, titulo, autor, anio, Categoria.TECNOLOGIA, true, FormatoEbook.PDF)
                        );

                        System.out.println("E-book registrado.");
                    }

                    case 3 -> {
                        System.out.println("Buscar por: 1. Título | 2. Autor | 3. Categoría");
                        int tipoBusqueda = Integer.parseInt(sc.nextLine());

                        List<Recurso> resultados = switch (tipoBusqueda) {
                            case 1 -> {
                                System.out.print("Título: ");
                                yield recursoService.buscarPorTitulo(sc.nextLine());
                            }
                            case 2 -> {
                                System.out.print("Autor: ");
                                yield recursoService.buscarPorAutor(sc.nextLine());
                            }
                            case 3 -> recursoService.buscarPorCategoria(Categoria.CIENCIA);
                            default -> List.of();
                        };

                        resultados.forEach(r ->
                                System.out.println(r.isbn() + " - " + r.titulo() + " - Disponible: " + r.disponible())
                        );
                    }

                    case 4 -> {
                        System.out.print("DNI: ");
                        int dni = Integer.parseInt(sc.nextLine());
                        System.out.print("Nombre: ");
                        String nombre = sc.nextLine();
                        System.out.print("Email: ");
                        String email = sc.nextLine();

                        System.out.println("Tipo: 1. Estudiante | 2. Docente");
                        int tipo = Integer.parseInt(sc.nextLine());

                        Socio socio = tipo == 2
                                ? new Docente(dni, nombre, email)
                                : new Estudiante(dni, nombre, email);

                        socioService.registrarSocio(socio);
                        System.out.println("Socio registrado.");
                    }

                    case 5 -> {
                        System.out.print("ISBN: ");
                        String isbn = sc.nextLine();
                        System.out.print("DNI socio: ");
                        int dni = Integer.parseInt(sc.nextLine());

                        prestamoService.realizarPrestamo(isbn, dni);
                        System.out.println("Préstamo realizado.");
                    }

                    case 6 -> {
                        System.out.print("ISBN: ");
                        String isbn = sc.nextLine();

                        long diasRetraso = prestamoService.devolverRecurso(isbn);
                        System.out.println("Devolución registrada. Días de retraso: " + diasRetraso);
                    }

                    case 7 -> prestamoService.historialPrestamos().forEach(p ->
                            System.out.println("ISBN: " + p.isbnRecurso()
                                    + " | DNI: " + p.dniSocio()
                                    + " | Devuelto: " + p.estaDevuelto())
                    );

                    case 0 -> System.out.println("Saliendo...");
                    default -> System.out.println("Opción inválida.");
                }

            } catch (BibliotecaException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Dato inválido.");
            }

        } while (opcion != 0);

        sc.close();
    }
}