package com.fayna.incitrack.model;


// Clase modelo de ComentarioIncidencia.
// Representa un comentario que el administrador añade al seguimiento de una incidencia.

public class ComentarioIncidencia {

    private int idComentario;
    private int idIncidencia;
    private int idAdmin;
    private String texto;
    private String fechaComentario;

    // Constructor vacío
    public ComentarioIncidencia() {
    }

    // Getters y setters de los atributos

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public int getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(int idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(String fechaComentario) {
        this.fechaComentario = fechaComentario;
    }
}
