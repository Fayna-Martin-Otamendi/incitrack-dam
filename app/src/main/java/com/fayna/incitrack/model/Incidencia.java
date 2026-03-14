package com.fayna.incitrack.model;


// Clase modelo de Incidencia.
// Representa una incidencia creada por un vecino dentro de la aplicación.

public class Incidencia {

    private int idIncidencia;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private int idUsuario;
    private int idCategoria;
    private String estado;
    private String tipo;
    private int prioridadCalculada;
    private String fechaCreacion;


    // Constructor vacío
    public Incidencia() {
    }

    // Constructor con todos los campos
    public Incidencia(int idIncidencia, String titulo, String descripcion, String ubicacion, int idUsuario, int idCategoria, String estado, String tipo,  int prioridadCalculada, String fechaCreacion) {
        this.idIncidencia = idIncidencia;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.idUsuario = idUsuario;
        this.idCategoria = idCategoria;
        this.estado = estado;
        this.tipo = tipo;
        this.prioridadCalculada = prioridadCalculada;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y setters de los atributos

    public int getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(int idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPrioridadCalculada() {
        return prioridadCalculada;
    }

    public void setPrioridadCalculada(int prioridadCalculada) {
        this.prioridadCalculada = prioridadCalculada;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    // Método para mostrar el contenido del objeto en texto
    @Override
    public String toString() {
        return "Incidencia{" +
                "idIncidencia=" + idIncidencia +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", usuario='" + idUsuario + '\'' +
                ", categoria='" + idCategoria + '\'' +
                ", estado='" + estado + '\'' +
                ", tipo='" + tipo + '\'' +
                ", prioridadCalculada=" + prioridadCalculada +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                '}';
    }
}