package com.fayna.incitrack.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


// Clase que gestiona la base de datos de la aplicación.
// Aquí se crean las tablas y se inicializan algunos datos básicos.

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "incitrack.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Creación de la tabla Usuario
        String CREATE_TABLE_Usuario = "CREATE TABLE Usuario (" +
                "idUsuario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "email TEXT NOT NULL, " +
                "telefono TEXT, " +
                "propiedad TEXT, " +
                "rol TEXT NOT NULL, " +
                "password TEXT NOT NULL" +
                ");";

        // Creación de la tabla Categoria
        String CREATE_TABLE_Categoria = "CREATE TABLE Categoria (" +
                "idCategoria INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "urgenciaBase INTEGER NOT NULL" +
                ");";

        // Creación de la tabla Incidencia
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

        // Creación de la tabla Tablon (avisos para los vecinos)
        String CREATE_TABLE_Tablon = "CREATE TABLE Tablon (" +
                "idAviso INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                "texto TEXT NOT NULL, " +
                "fechaPublicacion TEXT NOT NULL, " +
                "idAdminPublicador INTEGER NOT NULL" +
                ");";

        // Tabla donde se guardan las imágenes asociadas a incidencias
        String CREATE_TABLE_IncidenciaImagen = "CREATE TABLE IncidenciaImagen (" +
                "idImagen INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idIncidencia INTEGER NOT NULL, " +
                "rutaImagen TEXT NOT NULL" +
                ");";

        // Tabla de comentarios que el administrador añade a las incidencias
        String CREATE_TABLE_ComentarioIncidencia = "CREATE TABLE ComentarioIncidencia (" +
                "idComentario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idIncidencia INTEGER NOT NULL, " +
                "idAdmin INTEGER NOT NULL, " +
                "texto TEXT NOT NULL, " +
                "fechaComentario TEXT NOT NULL" +
                ");";

        // Ejecución de las sentencias SQL para crear las tablas
        db.execSQL(CREATE_TABLE_Usuario);
        db.execSQL(CREATE_TABLE_Categoria);
        db.execSQL(CREATE_TABLE_Incidencia);
        db.execSQL(CREATE_TABLE_Tablon);
        db.execSQL(CREATE_TABLE_IncidenciaImagen);
        db.execSQL(CREATE_TABLE_ComentarioIncidencia);

        // Inserción de usuarios iniciales para poder probar la aplicación
        db.execSQL("INSERT INTO Usuario (nombre, email, telefono, propiedad, rol, password) " +
                "VALUES ('Admin', 'admin@incitrack.com', '600000001', '1A', 'ADMIN', '1234')");

        db.execSQL("INSERT INTO Usuario (nombre, email, telefono, propiedad, rol, password) " +
                "VALUES ('Vecino', 'vecino@incitrack.com', '600000002', '2B', 'VECINO', '1234')");

        // Categorías iniciales de incidencias
        db.execSQL("INSERT INTO Categoria (nombre, urgenciaBase) VALUES ('Agua', 5)");
        db.execSQL("INSERT INTO Categoria (nombre, urgenciaBase) VALUES ('Ascensor', 5)");
        db.execSQL("INSERT INTO Categoria (nombre, urgenciaBase) VALUES ('Electricidad', 5)");
        db.execSQL("INSERT INTO Categoria (nombre, urgenciaBase) VALUES ('Limpieza', 2)");
        db.execSQL("INSERT INTO Categoria (nombre, urgenciaBase) VALUES ('Ruidos', 2)");
        db.execSQL("INSERT INTO Categoria (nombre, urgenciaBase) VALUES ('Puerta garaje', 4)");
        db.execSQL("INSERT INTO Categoria (nombre, urgenciaBase) VALUES ('Iluminación', 3)");
        db.execSQL("INSERT INTO Categoria (nombre, urgenciaBase) VALUES ('Humedades', 4)");
        db.execSQL("INSERT INTO Categoria (nombre, urgenciaBase) VALUES ('Otros', 1)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Si la base de datos se actualiza desde una versión anterior a la 2,
        // se crea la tabla de comentarios de incidencias.
        if (oldVersion < 2) {
            String CREATE_TABLE_ComentarioIncidencia = "CREATE TABLE ComentarioIncidencia (" +
                    "idComentario INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "idIncidencia INTEGER NOT NULL, " +
                    "idAdmin INTEGER NOT NULL, " +
                    "texto TEXT NOT NULL, " +
                    "fechaComentario TEXT NOT NULL" +
                    ");";

            db.execSQL(CREATE_TABLE_ComentarioIncidencia);
        }
    }
}