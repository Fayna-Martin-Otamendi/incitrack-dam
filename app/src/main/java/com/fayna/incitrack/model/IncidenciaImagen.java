package com.fayna.incitrack.model;


// Clase modelo de IncidenciaImagen.
// Representa una imagen asociada a una incidencia.

public class IncidenciaImagen {

    private int idImagen;
    private int idIncidencia;
    private String rutaImagen;

    // Constructor vacío
    public IncidenciaImagen() {
    }

    // Constructor con todos los campos
    public IncidenciaImagen(int idImagen, int idIncidencia, String rutaImagen) {
        this.idImagen = idImagen;
        this.idIncidencia = idIncidencia;
        this.rutaImagen = rutaImagen;
    }

    // Getters y setters de los atributos

    public int getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public int getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(int idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    // Método para mostrar el objeto en formato texto
    @Override
    public String toString() {
        return "IncidenciaImagen{" +
                "idImagen=" + idImagen +
                ", idIncidencia=" + idIncidencia +
                ", rutaImagen='" + rutaImagen + '\'' +
                '}';
    }
}
