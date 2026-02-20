package com.fayna.incitrack.model;

public class IncidenciaImagen {
    private int idImagen;
    private int idIncidencia;
    private String rutaImagen;

    public IncidenciaImagen() {
    }

    public IncidenciaImagen(int idImagen, int idIncidencia, String rutaImagen) {
        this.idImagen = idImagen;
        this.idIncidencia = idIncidencia;
        this.rutaImagen = rutaImagen;
    }

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

    @Override
    public String toString() {
        return "IncidenciaImagen{" +
                "idImagen=" + idImagen +
                ", idIncidencia=" + idIncidencia +
                ", rutaImagen='" + rutaImagen + '\'' +
                '}';


    }
}
