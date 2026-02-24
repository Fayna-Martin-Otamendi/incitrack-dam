package com.fayna.incitrack.dao;

import com.fayna.incitrack.db.DatabaseHelper;
import com.fayna.incitrack.model.Tablon;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;


public class TablonDAO {

    private final DatabaseHelper dbHelper;

    private static final String TABLE_TABLON = "Tablon";

    private static final String COL_ID = "idAviso";
    private static final String COL_TITULO = "titulo";
    private static final String COL_TEXTO = "texto";
    private static final String COL_FECHA_PUBLICACION = "fechaPublicacion";
    private static final String COL_ID_ADMIN_PUBLICADOR = "idAdminPublicador";

    public TablonDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

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
