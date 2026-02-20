package com.fayna.incitrack.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "incitrack.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_Usuario = "CREATE TABLE Usuario (" +
                "idUsuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "telefono TEXT, " +
                "propiedad TEXT, " +
                "rol TEXT NOT NULL, " +
                "password TEXT NOT NULL" +
                ");";

        String CREATE_TABLE_Categoria = "CREATE TABLE Categoria (" +
                "idCategoria INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "urgenciaBase INTEGER NOT NULL" +
                ");";

        String CREATE_TABLE_Incidencia = "CREATE TABLE Incidencia (" +
                "idIncidencia INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                "descripcion TEXT NOT NULL, " +
                "ubicacion TEXT, " +
                "fechaCreacion TEXT NOT NULL, " +
                "prioridadCalculada INTEGER, " +
                "idUsuario INTEGER NOT NULL, " +
                "idCategoria INTEGER NOT NULL, " +
                "estado TEXT NOT NULL, " +
                "tipo TEXT NOT NULL" +
                ");";

        String CREATE_TABLE_Tablon = "CREATE TABLE Tablon (" +
                "idAviso INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                "texto TEXT NOT NULL, " +
                "fechaPublicacion TEXT NOT NULL, " +
                "idAdminPublicador INTEGER NOT NULL" +
                ");";

        String CREATE_TABLE_IncidenciaImagen = "CREATE TABLE IncidenciaImagen (" +
                "idImagen INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idIncidencia INTEGER NOT NULL, " +
                "rutaImagen TEXT NOT NULL" +
                ");";

        db.execSQL(CREATE_TABLE_Usuario);
        db.execSQL(CREATE_TABLE_Categoria);
        db.execSQL(CREATE_TABLE_Incidencia);
        db.execSQL(CREATE_TABLE_Tablon);
        db.execSQL(CREATE_TABLE_IncidenciaImagen);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
