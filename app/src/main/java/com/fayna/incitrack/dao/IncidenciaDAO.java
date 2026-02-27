package com.fayna.incitrack.dao;

import com.fayna.incitrack.db.DatabaseHelper;
import com.fayna.incitrack.model.Incidencia;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;


public class IncidenciaDAO {

    private final DatabaseHelper dbHelper;

    private static final String TABLE_INCIDENCIA = "Incidencia";

    private static final String COL_ID = "idIncidencia";
    private static final String COL_TITULO = "titulo";
    private static final String COL_DESCRIPCION = "descripcion";
    private static final String COL_UBICACION = "ubicacion";
    private static final String COL_FECHA_CREACION = "fechaCreacion";
    private static final String COL_PRIORIDAD_CALCULADA = "prioridadCalculada";
    private static final String COL_ID_USUARIO = "idUsuario";
    private static final String COL_ID_CATEGORIA = "idCategoria";
    private static final String COL_ESTADO = "estado";
    private static final String COL_TIPO = "tipo";

    public IncidenciaDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long insertIncidencia(Incidencia incidencia) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;

        try {

            ContentValues values = new ContentValues();
            values.put(COL_TITULO, incidencia.getTitulo());
            values.put(COL_DESCRIPCION, incidencia.getDescripcion());
            values.put(COL_UBICACION, incidencia.getUbicacion());
            values.put(COL_FECHA_CREACION, incidencia.getFechaCreacion());
            values.put(COL_PRIORIDAD_CALCULADA, incidencia.getPrioridadCalculada());
            values.put(COL_ID_USUARIO, incidencia.getIdUsuario());
            values.put(COL_ID_CATEGORIA, incidencia.getIdCategoria());
            values.put(COL_ESTADO, incidencia.getEstado());
            values.put(COL_TIPO, incidencia.getTipo());

            id = db.insert(TABLE_INCIDENCIA,
                    null,
                    values
            );

        } finally {
            db.close();
        }

        return id;
    }


    public Incidencia getIncidenciaById(int idIncidencia) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        Incidencia incidencia = null;

        try {

            cursor = db.query(
                    TABLE_INCIDENCIA,
                    null,
                    COL_ID + " = ?",
                    new String[]{String.valueOf(idIncidencia)},
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {

                incidencia = new Incidencia();
                incidencia.setIdIncidencia(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                incidencia.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow(COL_TITULO)));
                incidencia.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPCION)));
                incidencia.setUbicacion(cursor.getString(cursor.getColumnIndexOrThrow(COL_UBICACION)));
                incidencia.setFechaCreacion(cursor.getString(cursor.getColumnIndexOrThrow(COL_FECHA_CREACION)));
                incidencia.setPrioridadCalculada(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRIORIDAD_CALCULADA)));
                incidencia.setIdUsuario(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_USUARIO)));
                incidencia.setIdCategoria(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_CATEGORIA)));
                incidencia.setEstado(cursor.getString(cursor.getColumnIndexOrThrow(COL_ESTADO)));
                incidencia.setTipo(cursor.getString(cursor.getColumnIndexOrThrow(COL_TIPO)));
            }

        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return incidencia;
    }

    public List<Incidencia> getAllIncidencias() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        List<Incidencia> lista = new ArrayList<>();

        try {
            cursor = db.query(
                    TABLE_INCIDENCIA,
                    null,
                    null,
                    null,
                    null,
                    null,
                    COL_ID + " ASC"
            );

            while (cursor.moveToNext()) {

                Incidencia incidencia = new Incidencia();

                incidencia.setIdIncidencia(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                incidencia.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow(COL_TITULO)));
                incidencia.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPCION)));
                incidencia.setUbicacion(cursor.getString(cursor.getColumnIndexOrThrow(COL_UBICACION)));
                incidencia.setFechaCreacion(cursor.getString(cursor.getColumnIndexOrThrow(COL_FECHA_CREACION)));
                incidencia.setPrioridadCalculada(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRIORIDAD_CALCULADA)));
                incidencia.setIdUsuario(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_USUARIO)));
                incidencia.setIdCategoria(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_CATEGORIA)));
                incidencia.setEstado(cursor.getString(cursor.getColumnIndexOrThrow(COL_ESTADO)));
                incidencia.setTipo(cursor.getString(cursor.getColumnIndexOrThrow(COL_TIPO)));

                lista.add(incidencia);
            }

        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return lista;
    }

    public List<Incidencia> getIncidenciasPorUsuario(int idUsuario) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        List<Incidencia> lista = new ArrayList<>();

        try {
            cursor = db.query(
                    TABLE_INCIDENCIA,
                    null,
                    COL_ID_USUARIO + "=?",
                    new String[]{String.valueOf(idUsuario)},
                    null,
                    null,
                    COL_ID + " ASC"
            );

            while (cursor.moveToNext()) {

                Incidencia incidencia = new Incidencia();

                incidencia.setIdIncidencia(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                incidencia.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow(COL_TITULO)));
                incidencia.setDescripcion(cursor.getString(cursor.getColumnIndexOrThrow(COL_DESCRIPCION)));
                incidencia.setUbicacion(cursor.getString(cursor.getColumnIndexOrThrow(COL_UBICACION)));
                incidencia.setFechaCreacion(cursor.getString(cursor.getColumnIndexOrThrow(COL_FECHA_CREACION)));
                incidencia.setPrioridadCalculada(cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRIORIDAD_CALCULADA)));
                incidencia.setIdUsuario(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_USUARIO)));
                incidencia.setIdCategoria(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_CATEGORIA)));
                incidencia.setEstado(cursor.getString(cursor.getColumnIndexOrThrow(COL_ESTADO)));
                incidencia.setTipo(cursor.getString(cursor.getColumnIndexOrThrow(COL_TIPO)));

                lista.add(incidencia);
            }

        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return lista;
    }

    public int updateIncidencia(Incidencia incidencia) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = 0;

        try {

            ContentValues values = new ContentValues();
            values.put(COL_TITULO, incidencia.getTitulo());
            values.put(COL_DESCRIPCION, incidencia.getDescripcion());
            values.put(COL_UBICACION, incidencia.getUbicacion());
            values.put(COL_FECHA_CREACION, incidencia.getFechaCreacion());
            values.put(COL_PRIORIDAD_CALCULADA, incidencia.getPrioridadCalculada());
            values.put(COL_ID_USUARIO, incidencia.getIdUsuario());
            values.put(COL_ID_CATEGORIA, incidencia.getIdCategoria());
            values.put(COL_ESTADO, incidencia.getEstado());
            values.put(COL_TIPO, incidencia.getTipo());

            filas =  db.update(
                    TABLE_INCIDENCIA,
                    values,
                    COL_ID + " = ?",
                    new String[]{ String.valueOf(incidencia.getIdIncidencia()) }
            );

        } finally {
            db.close();
        }
        return filas;
    }

    public int deleteIncidencia(Incidencia incidencia) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = 0;

        try {

            filas = db.delete(
                    TABLE_INCIDENCIA,
                    COL_ID + " = ?",
                    new String[]{ String.valueOf(incidencia.getIdIncidencia()) }
            );

        } finally {
            db.close();
        }

        return filas;
    }
}
