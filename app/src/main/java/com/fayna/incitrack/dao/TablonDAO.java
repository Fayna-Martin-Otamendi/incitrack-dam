package com.fayna.incitrack.dao;

import com.fayna.incitrack.db.DatabaseHelper;
import com.fayna.incitrack.model.Tablon;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;


// DAO de la entidad Tablon.
// Aquí se gestionan los avisos que publica el administrador para los vecinos.

public class TablonDAO {

    private final DatabaseHelper dbHelper;

    // Nombre de la tabla
    private static final String TABLE_TABLON = "Tablon";

    // Columnas de la tabla
    private static final String COL_ID = "idAviso";
    private static final String COL_TITULO = "titulo";
    private static final String COL_TEXTO = "texto";
    private static final String COL_FECHA_PUBLICACION = "fechaPublicacion";
    private static final String COL_ID_ADMIN_PUBLICADOR = "idAdminPublicador";

    public TablonDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }


    // Inserta un aviso nuevo en el tablón.
    // Devuelve el id que genera SQLite al guardar el aviso.
    public long insertTablon(Tablon tablon) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;

        try {

            ContentValues values = new ContentValues();
            values.put(COL_TITULO, tablon.getTitulo());
            values.put(COL_TEXTO, tablon.getTexto());
            values.put(COL_FECHA_PUBLICACION, tablon.getFechaPublicacion());
            values.put(COL_ID_ADMIN_PUBLICADOR, tablon.getIdAdminPublicador());

            id = db.insert(TABLE_TABLON,
                    null,
                    values
            );

        } finally {
            db.close();
        }

        return id;
    }


    // Busca un aviso concreto usando su id.
    public Tablon getTablonById(int idAviso) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        Tablon tablon = null;

        try {

            cursor = db.query(
                    TABLE_TABLON,
                    null,
                    COL_ID + " = ?",
                    new String[]{String.valueOf(idAviso)},
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {

                tablon = new Tablon();
                tablon.setIdAviso(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                tablon.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow(COL_TITULO)));
                tablon.setTexto(cursor.getString(cursor.getColumnIndexOrThrow(COL_TEXTO)));
                tablon.setFechaPublicacion(cursor.getString(cursor.getColumnIndexOrThrow(COL_FECHA_PUBLICACION)));
                tablon.setIdAdminPublicador(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_ADMIN_PUBLICADOR)));
            }

        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return tablon;
    }


    // Devuelve todos los avisos publicados en el tablón.
    public List<Tablon> getAllAvisos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        List<Tablon> lista = new ArrayList<>();

        try {
            cursor = db.query(
                    TABLE_TABLON,
                    null,
                    null,
                    null,
                    null,
                    null,
                    COL_ID + " ASC"
            );

            // Se recorren los resultados y se convierten en objetos Tablon
            while (cursor.moveToNext()) {

                Tablon tablon = new Tablon();

                tablon.setIdAviso(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                tablon.setTitulo(cursor.getString(cursor.getColumnIndexOrThrow(COL_TITULO)));
                tablon.setTexto(cursor.getString(cursor.getColumnIndexOrThrow(COL_TEXTO)));
                tablon.setFechaPublicacion(cursor.getString(cursor.getColumnIndexOrThrow(COL_FECHA_PUBLICACION)));
                tablon.setIdAdminPublicador(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_ADMIN_PUBLICADOR)));

                lista.add(tablon);
            }

        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return lista;
    }


    // Actualiza los datos de un aviso que ya existe.
    public int updateTablon (Tablon tablon) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = 0;

        try {

            ContentValues values = new ContentValues();
            values.put(COL_TITULO, tablon.getTitulo());
            values.put(COL_TEXTO, tablon.getTexto());
            values.put(COL_FECHA_PUBLICACION, tablon.getFechaPublicacion());
            values.put(COL_ID_ADMIN_PUBLICADOR, tablon.getIdAdminPublicador());

            filas =  db.update(
                    TABLE_TABLON,
                    values,
                    COL_ID + " = ?",
                    new String[]{ String.valueOf(tablon.getIdAviso()) }
            );

        } finally {
            db.close();
        }
        return filas;
    }


    // Elimina un aviso del tablón usando su id.
    public int deleteTablon(Tablon tablon) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = 0;

        try {

            filas = db.delete(
                    TABLE_TABLON,
                    COL_ID + " = ?",
                    new String[]{ String.valueOf(tablon.getIdAviso()) }
            );

        } finally {
            db.close();
        }

        return filas;
    }
}
