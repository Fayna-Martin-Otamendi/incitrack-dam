package com.fayna.incitrack.model;

public class Incidencia {

    private int idIncidencia;
    private String titulo;
    private String descripcion;
    private String ubicacion;
    private Usuario usuario;
    private Categoria categoria;
    private EstadoIncidencia estado;
    private TipoIncidencia tipo;
    private int prioridadCalculada;
    private String fechaCreacion;



    public Incidencia() {
    }

    public Incidencia(int idIncidencia, String titulo, String descripcion, String ubicacion, Usuario usuario, Categoria categoria, EstadoIncidencia estado, TipoIncidencia tipo,  int prioridadCalculada, String fechaCreacion) {
        this.idIncidencia = idIncidencia;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.usuario = usuario;
        this.categoria = categoria;
        this.estado = estado;
        this.tipo = tipo;
        this.prioridadCalculada = prioridadCalculada;
        this.fechaCreacion = fechaCreacion;
    }

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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public EstadoIncidencia getEstado() {
        return estado;
    }

    public void setEstado(EstadoIncidencia estado) {
        this.estado = estado;
    }

    public TipoIncidencia getTipo() {
        return tipo;
    }

    public void setTipo(TipoIncidencia tipo) {
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

    @Override
    public String toString() {
        return "Incidencia{" +
                "idIncidencia=" + idIncidencia +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", usuario='" + usuario + '\'' +
                ", categoria='" + categoria + '\'' +
                ", estado='" + estado + '\'' +
                ", tipo='" + tipo + '\'' +
                ", prioridadCalculada=" + prioridadCalculada +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                '}';
    }
}
