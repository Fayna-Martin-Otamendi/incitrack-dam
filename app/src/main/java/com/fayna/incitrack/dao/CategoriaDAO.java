package com.fayna.incitrack.dao;

import com.fayna.incitrack.db.DatabaseHelper;
import com.fayna.incitrack.model.Categoria;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;


// DAO de la entidad Categoria.
// Aquí están todas las operaciones que hacemos sobre la tabla Categoria
// de la base de datos (insertar, consultar, actualizar y borrar).

public class CategoriaDAO {

    // Helper de la base de datos que usamos para abrir la conexión
    private final DatabaseHelper dbHelper;

    // Nombre de la tabla
    private static final String TABLE_CATEGORIA = "Categoria";

    // Columnas de la tabla
    private static final String COL_ID = "idCategoria";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_URGENCIA_BASE = "urgenciaBase";

    public CategoriaDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }


    // Inserta una nueva categoría en la base de datos.
    // Devuelve el id que genera SQLite al hacer el insert.
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


    // Busca una categoría concreta usando su id.
    // Si la encuentra, crea el objeto Categoria con los datos de la tabla.
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


    // Devuelve la lista completa de categorías que hay en la base de datos.
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


    // Actualiza los datos de una categoría existente.
    // Devuelve cuántas filas se han modificado.
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


    // Borra una categoría de la base de datos usando su id.
    // Devuelve cuántas filas se han eliminado.
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
