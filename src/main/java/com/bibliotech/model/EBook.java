package main.java.com.bibliotech.model;

public record EBook(
        String isbn,
        String titulo,
        String autor,
        int anio,
        Categoria categoria,
        boolean disponible,
        FormatoEbook formato
) implements Recurso {
}
