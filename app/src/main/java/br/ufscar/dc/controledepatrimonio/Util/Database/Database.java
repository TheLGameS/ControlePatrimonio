package br.ufscar.dc.controledepatrimonio.Util.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import java.util.List;

import br.ufscar.dc.controledepatrimonio.Entity.Departamento;
import br.ufscar.dc.controledepatrimonio.Util.Database.DAO.DepartamentoDao;

public class Database {
    private SQLiteDatabase db;

    public Database(Context ctx) {
        DatabaseHelper cDb = new DatabaseHelper(ctx);
        db = cDb.getWritableDatabase();
    }

    public void inserirDepartamento(Departamento departamento) {
        new DepartamentoDao(db).inserirDepartamento(departamento);
    }

    public void atualizarDepartamento(Departamento departamento) {
        new DepartamentoDao(db).atualizarDepartamento(departamento);
    }

    public void deletarDepartamento(Departamento departamento) {
        new DepartamentoDao(db).deletarDepartamento(departamento);
    }

    public List<Departamento> buscarDepartamentos() {
        return new DepartamentoDao(db).buscarDepartamentos();
    }
}
