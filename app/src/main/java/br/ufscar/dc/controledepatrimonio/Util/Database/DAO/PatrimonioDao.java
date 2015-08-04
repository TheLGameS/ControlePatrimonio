package br.ufscar.dc.controledepatrimonio.Util.Database.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufscar.dc.controledepatrimonio.Entity.Local;
import br.ufscar.dc.controledepatrimonio.Entity.Patrimonio;
import br.ufscar.dc.controledepatrimonio.Entity.Responsavel;

public class PatrimonioDao {
    private SQLiteDatabase db;
    String[] colunas = new String[]{"_id", "dataEntrada", "descricao", "estado", "identificacao", "local", "nome", "responsavel"};

    public PatrimonioDao(SQLiteDatabase db) {
        this.db = db;
    }

    public void inserir(Patrimonio patrimonio) {
        String statusRegistro;
        ContentValues val = new ContentValues();

        val.put("dataEntrada", String.valueOf(patrimonio.getDataEntrada()));
        val.put("descricao", patrimonio.getDescricao());
        val.put("estado", patrimonio.getEstado());
        val.put("identificacao", patrimonio.getIdentificacao());
        val.put("local", patrimonio.getLocal().getId());
        val.put("nome", patrimonio.getNome());
        val.put("responsavel", patrimonio.getResponsavel().getId());

        statusRegistro = patrimonio.getStatusRegistro() ? "0" : "1";

        val.put("statusRegistro", statusRegistro);

        db.insert("Patrimonio", null, val);
    }

    public void atualizar(Patrimonio patrimonio) {
        String statusRegistro;
        ContentValues val = new ContentValues();

        val.put("dataEntrada", String.valueOf(patrimonio.getDataEntrada()));
        val.put("descricao", patrimonio.getDescricao());
        val.put("estado", patrimonio.getEstado());
        val.put("identificacao", patrimonio.getIdentificacao());
        val.put("local", patrimonio.getLocal().getId());
        val.put("nome", patrimonio.getNome());
        val.put("responsavel", patrimonio.getResponsavel().getId());

        statusRegistro = patrimonio.getStatusRegistro() ? "0" : "1";

        val.put("statusRegistro", statusRegistro);

        db.update("Patrimonio", val, "_id=" + patrimonio.getId(), null);
    }

    public void deletar(Patrimonio patrimonio) {
        db.delete("Patrimonio", "_id=" + patrimonio.getId(), null);
    }

    public List<Patrimonio> buscarTodos() {
        LocalDao localDao = new LocalDao(db);
        ResponsavelDao responsavelDao = new ResponsavelDao(db);
        List<Patrimonio> patrimonios = new ArrayList<>();

        Cursor cursor = db.query("Patrimonio", colunas, null, null, null, null, "dataEntrada ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                Patrimonio patrimonio = new Patrimonio();
                Local local;
                Responsavel responsavel;

                patrimonio.setId(cursor.getInt(0));

                DateFormat dataFormato = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    if (cursor.getString(1) == null) {
                        patrimonio.setDataEntrada(null);
                    } else {
                        Date data = dataFormato.parse(cursor.getString(1));
                        patrimonio.setDataEntrada(data);
                    }

                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

                patrimonio.setDescricao(cursor.getString(2));
                patrimonio.setEstado(cursor.getString(3));
                patrimonio.setIdentificacao(cursor.getString(4));
                local = localDao.buscarPorId(cursor.getInt(5));

                patrimonio.setLocal(local);
                patrimonio.setNome(cursor.getString(6));

                responsavel = responsavelDao.buscarPorId(cursor.getInt(7));
                patrimonio.setResponsavel(responsavel);

                patrimonios.add(patrimonio);
            } while (cursor.moveToNext());
        }

        return patrimonios;
    }

    public Patrimonio buscarPorId(int id) {
        Patrimonio patrimonio = new Patrimonio();
        LocalDao localDao = new LocalDao(db);
        ResponsavelDao responsavelDao = new ResponsavelDao(db);

        Cursor cursor = db.query("Departamento", colunas, "_id =" + id, null, null, null, null);

        if (cursor.getCount() == 1) {
            Local local;
            Responsavel responsavel;

            patrimonio.setId(cursor.getInt(0));

            DateFormat dataFormato = new SimpleDateFormat("dd/MM/yyyy");
            try {
                if (cursor.getString(1) == null) {
                    patrimonio.setDataEntrada(null);
                } else {
                    Date data = dataFormato.parse(cursor.getString(1));
                    patrimonio.setDataEntrada(data);
                }

            } catch (ParseException ex) {
                ex.printStackTrace();
            }

            patrimonio.setDescricao(cursor.getString(2));
            patrimonio.setEstado(cursor.getString(3));
            patrimonio.setIdentificacao(cursor.getString(4));
            local = localDao.buscarPorId(cursor.getInt(5));

            patrimonio.setLocal(local);
            patrimonio.setNome(cursor.getString(6));

            responsavel = responsavelDao.buscarPorId(cursor.getInt(7));
            patrimonio.setResponsavel(responsavel);
        } else {
            return null;
        }

        return patrimonio;
    }
}
