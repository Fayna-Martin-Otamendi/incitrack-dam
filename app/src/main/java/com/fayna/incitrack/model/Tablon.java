package com.fayna.incitrack.model;

public class Tablon {

    private int idAviso;
    private String titulo;
    private String texto;
    private String fechaPublicacion;
    private int idAdminPublicador;

    public Tablon(){
    }

    public Tablon(int idAviso, String titulo, String texto, String fechaPublicacion, int idAdminPublicador) {
        this.idAviso = idAviso;
        this.titulo = titulo;
        this.texto = texto;
        this.fechaPublicacion = fechaPublicacion;
        this.idAdminPublicador = idAdminPublicador;
    }

    public int getIdAviso() {
        return idAviso;
    }

    public void setIdAviso(int idAviso) {
        this.idAviso = idAviso;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public int getIdAdminPublicador() {
        return idAdminPublicador;
    }

    public void setIdAdminPublicador(int idAdminPublicador) {
        this.idAdminPublicador = idAdminPublicador;
    }

    @Override
    public String toString() {
        return "Tablon{" +
                "idAviso=" + idAviso +
                ", titulo='" + titulo + '\'' +
                ", texto='" + texto + '\'' +
                ", fechaPublicacion='" + fechaPublicacion + '\'' +
                ", idAdminPublicador=" + idAdminPublicador +
                '}';

    }
}
