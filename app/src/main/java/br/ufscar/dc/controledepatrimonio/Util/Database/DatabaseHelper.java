package br.ufscar.dc.controledepatrimonio.Util.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "patrimonio";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*
        Por ser poucas tabelas, vou criar aqui mesmo
        Se desejar, modifique a aplicação para ler de um arquivo SQL.
    */
    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder sql;
        sql = new StringBuilder();

        //Departamento dentro da instituição
        sql.append("CREATE TABLE Departamento" +
                "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Sigla TEXT, " +
                "Nome TEXT);");

        //Local dentro de um departamento que um patrimonio pode estar
        sql.append("CREATE TABLE Local " +
                "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "Departamento INTEGER," +
                "Nome TEXT," +
                "Descricao TEXT, " +
                "FOREIGN KEY (departamento) REFERENCES Departamento(_id)" +
                ");");

        //Responsável pelo patriminio
        sql.append("CREATE TABLE Responsavel (" +
                "_id INTERGER PRIMARY KEY AUTOINCREMENT, " +
                "Siape TEXT, " +
                "Nome TEXT, " +
                "Telefone TEXT, " +
                "Funcao TEXT);");

        //Dados do patrimonio
        sql.append("CREATE TABLE Patrimonio" +
                "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Descricao TEXT," +
                "Identificacao TEXT," +
                "Estado TEXT, DataEntrada TEXT," +
                "Local INTEGER," +
                "Responsavel INTEGER," +
                "Ativo INTEGER, " +
                "FOREIGN KEY (Local) REFERENCES Local(_id)," +
                "FOREIGN KEY (Responsavel) REFERENCES Responsavel(_id)" +
                ");");

        db.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        StringBuilder sql;
        sql = new StringBuilder();

        sql.append("DROP TABLE Patrimonio;" +
                "DROP TABLE Responsavel;" +
                "DROP TABLE Local;" +
                "DROP TABLE Departamento;");

        db.execSQL(sql.toString());
        onCreate(db);
    }

}
