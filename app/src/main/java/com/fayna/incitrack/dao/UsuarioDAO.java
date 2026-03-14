package com.fayna.incitrack.dao;

import com.fayna.incitrack.db.DatabaseHelper;
import com.fayna.incitrack.model.Usuario;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;


// DAO de la entidad Usuario.
// Gestiona los usuarios de la aplicación: vecinos y administrador.

public class UsuarioDAO {

    private final DatabaseHelper dbHelper;

    // Nombre de la tabla
    private static final String TABLE_USUARIO = "Usuario";

    // Columnas de la tabla
    private static final String COL_ID = "idUsuario";
    private static final String COL_NOMBRE = "nombre";
    private static final String COL_EMAIL = "email";
    private static final String COL_TELEFONO = "telefono";
    private static final String COL_PROPIEDAD = "propiedad";
    private static final String COL_ROL = "rol";
    private static final String COL_PASSWORD = "password";

    public UsuarioDAO(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }


    // Inserta un usuario nuevo en la base de datos.
    // Devuelve el id generado por SQLite.
    public long insertUsuario(Usuario usuario) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id = -1;

        try {

            ContentValues values = new ContentValues();
            values.put(COL_NOMBRE, usuario.getNombre());
            values.put(COL_EMAIL, usuario.getEmail());
            values.put(COL_TELEFONO, usuario.getTelefono());
            values.put(COL_PROPIEDAD, usuario.getPropiedad());
            values.put(COL_ROL, usuario.getRol());
            values.put(COL_PASSWORD, usuario.getPassword());

            id = db.insert(TABLE_USUARIO,
                    null,
                    values
            );

        } finally {
            db.close();
        }

        return id;
    }


    // Busca un usuario usando su email.
    // Se utiliza en el proceso de login.
    public Usuario getUsuarioByEmail(String email) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        Usuario usuario = null;

        try {

            cursor = db.query(
                    TABLE_USUARIO,
                    null,
                    COL_EMAIL + " = ?",
                    new String[]{email},
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {

                usuario = new Usuario();
                usuario.setIdUsuario(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                usuario.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE)));
                usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)));
                usuario.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(COL_TELEFONO)));
                usuario.setPropiedad(cursor.getString(cursor.getColumnIndexOrThrow(COL_PROPIEDAD)));
                usuario.setRol(cursor.getString(cursor.getColumnIndexOrThrow(COL_ROL)));
                usuario.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD)));
            }

        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return usuario;
    }


    // Busca un usuario usando su id.
    public Usuario getUsuarioById(int idUsuario) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        Usuario usuario = null;

        try {

            cursor = db.query(
                    TABLE_USUARIO,
                    null,
                    COL_ID + " = ?",
                    new String[]{String.valueOf(idUsuario)},
                    null,
                    null,
                    null
            );

            if (cursor.moveToFirst()) {

                usuario = new Usuario();
                usuario.setIdUsuario(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                usuario.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE)));
                usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)));
                usuario.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(COL_TELEFONO)));
                usuario.setPropiedad(cursor.getString(cursor.getColumnIndexOrThrow(COL_PROPIEDAD)));
                usuario.setRol(cursor.getString(cursor.getColumnIndexOrThrow(COL_ROL)));
                usuario.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD)));
            }

        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return usuario;
    }


    // Devuelve todos los usuarios guardados en la base de datos.
    public List<Usuario> getAllUsuarios() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        List<Usuario> lista = new ArrayList<>();

        try {
            cursor = db.query(
                    TABLE_USUARIO,
                    null,
                    null,
                    null,
                    null,
                    null,
                    COL_ID + " ASC"
            );

            // Se recorren los resultados y se convierten en objetos Usuario
            while (cursor.moveToNext()) {

                Usuario usuario = new Usuario();

                usuario.setIdUsuario(cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID)));
                usuario.setNombre(cursor.getString(cursor.getColumnIndexOrThrow(COL_NOMBRE)));
                usuario.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(COL_EMAIL)));
                usuario.setTelefono(cursor.getString(cursor.getColumnIndexOrThrow(COL_TELEFONO)));
                usuario.setPropiedad(cursor.getString(cursor.getColumnIndexOrThrow(COL_PROPIEDAD)));
                usuario.setRol(cursor.getString(cursor.getColumnIndexOrThrow(COL_ROL)));
                usuario.setPassword(cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD)));

                lista.add(usuario);
            }

        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return lista;
    }


    // Actualiza los datos de un usuario existente.
    public int updateUsuario(Usuario usuario) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = 0;

        try {

            ContentValues values = new ContentValues();
            values.put(COL_NOMBRE, usuario.getNombre());
            values.put(COL_EMAIL, usuario.getEmail());
            values.put(COL_TELEFONO, usuario.getTelefono());
            values.put(COL_PROPIEDAD, usuario.getPropiedad());
            values.put(COL_ROL, usuario.getRol());
            values.put(COL_PASSWORD, usuario.getPassword());

            filas =  db.update(
                    TABLE_USUARIO,
                    values,
                    COL_ID + " = ?",
                    new String[]{ String.valueOf(usuario.getIdUsuario()) }
            );

        } finally {
            db.close();
        }
        return filas;
    }


    // Elimina un usuario de la base de datos usando su id.
    public int deleteUsuario(Usuario usuario) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int filas = 0;

        try {

            filas = db.delete(
                    TABLE_USUARIO,
                    COL_ID + " = ?",
                    new String[]{ String.valueOf(usuario.getIdUsuario()) }
            );

        } finally {
            db.close();
        }

        return filas;
    }
}
