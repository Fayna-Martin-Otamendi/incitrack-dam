package com.fayna.incitrack.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fayna.incitrack.db.DatabaseHelper;
import com.fayna.incitrack.model.ComentarioIncidencia;

import java.util.ArrayList;
import java.util.List;


// DAO de ComentarioIncidencia.
// Esta clase se encarga de gestionar los comentarios que el administrador
// añade a una incidencia (insertar comentarios, consultarlos y borrarlos).

public class ComentarioIncidenciaDAO {

    private final DatabaseHelper dbHelper;

    // Nombre de la tabla
    private static final String TABLE_COMENTARIO = "ComentarioIncidencia";

    // Columnas de la tabla
    private static final String COL_ID = "idComentario";
    private static final String COL_ID_INCIDENCIA = "idIncidencia";
    private static final String COL_ID_ADMIN = "idAdmin";
    private static final String COL_TEXTO = "texto";
    private static final String COL_FECHA = "fechaComentario";

    public ComentarioIncidenciaDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }


    // Inserta un comentario nuevo asociado a una incidencia.
    // Devuelve el id generado por SQLite.
    public long insertComentario(ComentarioIncidencia comentario) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;

        try {

            ContentValues values = new ContentValues();
            values.put(COL_ID_INCIDENCIA, comentario.getIdIncidencia());
            values.put(COL_ID_ADMIN, comentario.getIdAdmin());
            values.put(COL_TEXTO, comentario.getTexto());
            values.put(COL_FECHA, comentario.getFechaComentario());

            id = db.insert(TABLE_COMENTARIO, null, values);

        } finally {
            db.close();
        }

        return id;
    }


    // Devuelve todos los comentarios que tiene una incidencia concreta.
    // Se usa cuando abrimos el detalle de una incidencia para ver el seguimiento.
    public List<ComentarioIncidencia> getComentariosPorIncidencia(int idIncidencia) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        List<ComentarioIncidencia> lista = new ArrayList<>();

        try {

            cursor = db.query(
                    TABLE_COMENTARIO,
                    null,
                    COL_ID_INCIDENCIA + " = ?",
                    new String[]{String.valueOf(idIncidencia)},
                    null,
                    null,
                    COL_ID + " ASC"
            );

            // Recorremos los comentarios encontrados y los guardamos en la lista
            while (cursor.moveToNext()) {

                ComentarioIncidencia comentario = new ComentarioIncidencia();

                comentario.setIdComentario(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                comentario.setIdIncidencia(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_INCIDENCIA)));
                comentario.setIdAdmin(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID_ADMIN)));
                comentario.setTexto(cursor.getString(cursor.getColumnIndexOrThrow(COL_TEXTO)));
                comentario.setFechaComentario(cursor.getString(cursor.getColumnIndexOrThrow(COL_FECHA)));

                lista.add(comentario);
            }

        } finally {

            if (cursor != null) cursor.close();
            db.close();
        }

        return lista;
    }


    // Borra todos los comentarios asociados a una incidencia.
    // Esto se usa cuando se elimina una incidencia, para que no queden comentarios sueltos.
    public int deleteComentariosPorIncidencia(int idIncidencia) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = 0;

        try {

            filas = db.delete(
                    TABLE_COMENTARIO,
                    COL_ID_INCIDENCIA + " = ?",
                    new String[]{String.valueOf(idIncidencia)}
            );

        } finally {

            db.close();
        }

        return filas;
    }
}
