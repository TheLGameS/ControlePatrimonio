package br.ufscar.dc.controledepatrimonio.Util.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Database {
    private SQLiteDatabase db;

    public Database (Context ctx) {
        DatabaseHelper cDb = new DatabaseHelper(ctx);
        db = cDb.getWritableDatabase();
    }
}
