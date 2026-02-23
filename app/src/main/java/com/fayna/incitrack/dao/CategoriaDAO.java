package com.fayna.incitrack.dao;

import com.fayna.incitrack.db.DatabaseHelper;
import com.fayna.incitrack.model.Categoria;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;


public class CategoriaDAO {

    private final DatabaseHelper dbHelper;

    private static final String TABLE_CATEGORIA = "Categoria";

    private static final String COL_ID = "idCategoria";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_URGENCIA_BASE = "urgenciaBase";

    public CategoriaDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public long insertCategoria(Categoria categoria) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;

        try {

            ContentValues values = new ContentValues();
            values.put(COL_NOMBRE, categoria.getNombre());
            values.put(COL_URGENCIA_BASE, categoria.getUrgenciaBase());

            id = db.insert(TABLE_CATEGORIA,
                    null,
                    values
            );

        } finally {
            db.close();
        }

        return id;
    }

    public Categoria getCategoriaById(int idCategoria) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        Categoria categoria = null;

        try {

            cursor = db.query(
                    TABLE_CATEGORIA,
                    null,
                    COL_ID + " = ?",
                    new String[]{String.valueOf(idCategoria)},
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {

                categoria = new Categoria();
                categoria.setIdCategoria(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                categoria.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE)));
                categoria.setUrgenciaBase(cursor.getInt(cursor.getColumnIndexOrThrow(COL_URGENCIA_BASE)));
            }

        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return categoria;
    }

    public List<Categoria> getAllCategorias() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        List<Categoria> lista = new ArrayList<>();

        try {
            cursor = db.query(
                    TABLE_CATEGORIA,
                    null,
                    null,
                    null,
                    null,
                    null,
                    COL_ID + " ASC"
            );

            while (cursor.moveToNext()) {

                Categoria categoria = new Categoria();

                categoria.setIdCategoria(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                categoria.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE)));
                categoria.setUrgenciaBase(cursor.getInt(cursor.getColumnIndexOrThrow(COL_URGENCIA_BASE)));

                lista.add(categoria);
            }

        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return lista;
    }

    public int updateCategoria (Categoria categoria) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = 0;

        try {

            ContentValues values = new ContentValues();
            values.put(COL_NOMBRE, categoria.getNombre());
            values.put(COL_URGENCIA_BASE, categoria.getUrgenciaBase());

            filas =  db.update(
                    TABLE_CATEGORIA,
                    values,
                    COL_ID + " = ?",
                    new String[]{ String.valueOf(categoria.getIdCategoria()) }
            );

        } finally {
            db.close();
        }
        return filas;
    }

    public int deleteCategoria(Categoria categoria) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = 0;

        try {

            filas = db.delete(
                    TABLE_CATEGORIA,
                    COL_ID + " = ?",
                    new String[]{ String.valueOf(categoria.getIdCategoria()) }
            );

        } finally {
            db.close();
        }

        return filas;
    }
}
