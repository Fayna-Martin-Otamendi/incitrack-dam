package com.fayna.incitrack.dao;

import com.fayna.incitrack.db.DatabaseHelper;
import com.fayna.incitrack.model.IncidenciaImagen;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;


public class IncidenciaImagenDAO {

    private final DatabaseHelper dbHelper;

    private static final String TABLE_INCIDENCIA_IMAGEN = "IncidenciaImagen";

    private static final String COL_ID = "idImagen";
    private static final String COL_ID_INCIDENCIA = "idIncidencia";
    private static final String COL_RUTA_IMAGEN = "rutaImagen";

    public IncidenciaImagenDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long insertImagen(IncidenciaImagen incidenciaImagen) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;

        try {

            ContentValues values = new ContentValues();
            values.put(COL_ID_INCIDENCIA, incidenciaImagen.getIdIncidencia());
            values.put(COL_RUTA_IMAGEN, incidenciaImagen.getRutaImagen());

            id = db.insert(TABLE_INCIDENCIA_IMAGEN,
                    null,
                    values
            );

        } finally {
            db.close();
        }

        return id;
    }

    public IncidenciaImagen getImagenById(int idImagen) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        IncidenciaImagen incidenciaImagen = null;

        try {

            cursor = db.query(
                    TABLE_INCIDENCIA_IMAGEN,
                    null,
                    COL_ID + " = ?",
                    new String[]{String.valueOf(idImagen)},
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {

                incidenciaImagen = new IncidenciaImagen();
                incidenciaImagen.setIdImagen(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                incidenciaImagen.setIdIncidencia(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_INCIDENCIA)));
                incidenciaImagen.setRutaImagen(cursor.getString(cursor.getColumnIndexOrThrow(COL_RUTA_IMAGEN)));
            }

        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return incidenciaImagen;
    }

    public List<IncidenciaImagen> getAllImagenes() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        List<IncidenciaImagen> lista = new ArrayList<>();

        try {
            cursor = db.query(
                    TABLE_INCIDENCIA_IMAGEN,
                    null,
                    null,
                    null,
                    null,
                    null,
                    COL_ID + " ASC"
            );

            while (cursor.moveToNext()) {

                IncidenciaImagen incidenciaImagen = new IncidenciaImagen();

                incidenciaImagen.setIdImagen(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                incidenciaImagen.setIdIncidencia(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_INCIDENCIA)));
                incidenciaImagen.setRutaImagen(cursor.getString(cursor.getColumnIndexOrThrow(COL_RUTA_IMAGEN)));

                lista.add(incidenciaImagen);
            }

        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return lista;
    }

    public int updateImagen (IncidenciaImagen incidenciaImagen) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = 0;

        try {

            ContentValues values = new ContentValues();
            values.put(COL_ID_INCIDENCIA, incidenciaImagen.getIdIncidencia());
            values.put(COL_RUTA_IMAGEN, incidenciaImagen.getRutaImagen());

            filas =  db.update(
                    TABLE_INCIDENCIA_IMAGEN,
                    values,
                    COL_ID + " = ?",
                    new String[]{ String.valueOf(incidenciaImagen.getIdImagen()) }
            );

        } finally {
            db.close();
        }
        return filas;
    }

    public int deleteImagen(IncidenciaImagen incidenciaImagen) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = 0;

        try {

            filas = db.delete(
                    TABLE_INCIDENCIA_IMAGEN,
                    COL_ID + " = ?",
                    new String[]{ String.valueOf(incidenciaImagen.getIdImagen()) }
            );

        } finally {
            db.close();
        }

        return filas;
    }
}
