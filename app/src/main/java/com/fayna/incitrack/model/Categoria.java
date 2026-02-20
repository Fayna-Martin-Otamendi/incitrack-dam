package com.fayna.incitrack.model;

public class Categoria {

    private int idCategoria;
    private String nombre;
    private int urgenciaBase;

    public Categoria() {
    }

    public Categoria(int idCategoria, String nombre, int urgenciaBase) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.urgenciaBase = urgenciaBase;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getUrgenciaBase() {
        return urgenciaBase;
    }

    public void setUrgenciaBase(int urgenciaBase) {
        this.urgenciaBase = urgenciaBase;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "idCategoria=" + idCategoria +
                ", nombre='" + nombre + '\'' +
                ", urgenciaBase=" + urgenciaBase +
                '}';
    }
}
