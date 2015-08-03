package br.ufscar.dc.controledepatrimonio.Util.Database.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.ufscar.dc.controledepatrimonio.Entity.Departamento;

public class DepartamentoDao {
    private SQLiteDatabase db;

    public DepartamentoDao(SQLiteDatabase db) {
        this.db = db;
    }

    public void inserirDepartamento(Departamento departamento) {
        ContentValues val = new ContentValues();
        val.put("nome", departamento.getNome());
        val.put("sigla", departamento.getSigla());

        db.insert("Departamento", null, val);
    }

    public void atualizarDepartamento(Departamento departamento) {
        ContentValues val = new ContentValues();
        val.put("nome", departamento.getNome());
        val.put("sigla", departamento.getSigla());

        db.update("Departamento", val, "_id=" + departamento.getId(), null);
    }

    public void deletarDepartamento(Departamento departamento) {
        db.delete("Departamento", "_id=" + departamento.getId(), null);
    }

    public List<Departamento> buscarDepartamentos() {
        List<Departamento> departamentos = new ArrayList<>();
        String[] colunas = new String[]{"_id", "nome","sigla"};

        Cursor cursor = db.query("Departamento", colunas, null, null, null, null, "nome ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Departamento dep = new Departamento();

                dep.setId(cursor.getInt(0));
                dep.setNome(cursor.getString(1));
                dep.setSigla(cursor.getString(2));

                departamentos.add(dep);
            }while (cursor.moveToNext());
        }

        return departamentos;
    }
}
