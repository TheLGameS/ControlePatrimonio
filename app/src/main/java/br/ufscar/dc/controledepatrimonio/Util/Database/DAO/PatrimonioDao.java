package br.ufscar.dc.controledepatrimonio.Util.Database.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    String[] colunas = new String[]{"_id", "nome", "descricao", "dataentrada", "identificacao", "estado",
            "local", "responsavel", "statusRegistro", "enviarbancoonline"};

    public PatrimonioDao(SQLiteDatabase db) {
        this.db = db;
    }

    public void inserir(Patrimonio patrimonio) {
        String statusRegistro;
        ContentValues val = new ContentValues();

        try {
            //val.put("_id", patrimonio.getId());
            val.put("nome", patrimonio.getNome());
            val.put("dataEntrada", String.valueOf(patrimonio.getDataEntrada()));
            val.put("descricao", patrimonio.getDescricao());
            val.put("estado", patrimonio.getEstado());
            val.put("identificacao", patrimonio.getIdentificacao());
            val.put("local", patrimonio.getLocal().getId());
            val.put("responsavel", patrimonio.getResponsavel().getId());
            val.put("statusRegistro", patrimonio.getStatusRegistro() ? 1 : 0);
            val.put("enviarBancoOnline", patrimonio.isEnviarBancoOnline() ? 1 : 0);

            db.insert("Patrimonio", null, val);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void atualizar(Patrimonio patrimonio) {
        String statusRegistro;
        ContentValues val = new ContentValues();

        val.put("_id", patrimonio.getId());
        val.put("nome", patrimonio.getNome());
        //val.put("dataEntrada", String.valueOf(patrimonio.getDataEntrada()));
        val.put("descricao", patrimonio.getDescricao());
        val.put("estado", patrimonio.getEstado());
        val.put("identificacao", patrimonio.getIdentificacao());
        val.put("local", patrimonio.getLocal().getId());
        val.put("responsavel", patrimonio.getResponsavel().getId());
        val.put("statusRegistro", patrimonio.getStatusRegistro() ? 1 : 0);
        val.put("enviarBancoOnline", patrimonio.isEnviarBancoOnline() ? 1 : 0);

        try {
            db.update("Patrimonio", val, "_id=" + patrimonio.getId(), null);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
                patrimonios.add(iniciarPatrimonio(cursor, true));
            } while (cursor.moveToNext());
        }

        return patrimonios;
    }

    public Patrimonio buscarPorId(int id) {
        Cursor cursor = db.query("Patrimonio", colunas, "_id =" + id, null, null, null, null);

        return iniciarPatrimonio(cursor, true);
    }

    public List<Patrimonio> buscarTodosParaInserir() {
        LocalDao localDao = new LocalDao(db);
        ResponsavelDao responsavelDao = new ResponsavelDao(db);
        List<Patrimonio> patrimonios = new ArrayList<>();

        Cursor cursor = db.query("Patrimonio", colunas, "enviarBancoOnline = 1", null, null, null, "dataEntrada ASC");

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            do {
                patrimonios.add(iniciarPatrimonio(cursor, false));
            } while (cursor.moveToNext());
        }

        return patrimonios;
    }

    public Patrimonio buscarPorTag(String tag) {
        Cursor cursor = db.query("Patrimonio", colunas, "identificacao ='" + tag + "'", null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            return iniciarPatrimonio(cursor, true);
        }
        else {
            return null;
        }
    }

    //region iniciarPatrimonio: DEFINE O CURSOR AO OBJETO PATRIMONIO
    private Patrimonio iniciarPatrimonio(Cursor cursor, boolean adicionarId) {
        Patrimonio patrimonio = new Patrimonio();
        Local local;
        Responsavel responsavel;
        LocalDao localDao = new LocalDao(db);
        ResponsavelDao responsavelDao = new ResponsavelDao(db);

        if (adicionarId)
            patrimonio.setId(cursor.getInt(0));
        patrimonio.setNome(cursor.getString(1));
        patrimonio.setDescricao(cursor.getString(2));

/*        DateFormat dataFormato = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        try {
            if (cursor.getString(3) == null) {
                patrimonio.setDataEntrada(null);
            } else {
                Date data = dataFormato.parse(cursor.getString(3));
                patrimonio.setDataEntrada(data);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }*/


        patrimonio.setIdentificacao(cursor.getString(4));
        patrimonio.setEstado(cursor.getString(5));

        local = localDao.buscarPorId(cursor.getInt(6));

        patrimonio.setLocal(local);

        responsavel = responsavelDao.buscarPorId(cursor.getInt(7));
        patrimonio.setResponsavel(responsavel);

        if (cursor.getString(8) == "0")
            patrimonio.setStatusRegistro(true);
        else
            patrimonio.setStatusRegistro(false);

        patrimonio.setEnviarBancoOnline(true);

        return patrimonio;
    }
    //endregion

    //DELETA TODOS QUE JÁ ESTÃO NA BASE ONLINE
    public void deletarTodos() {
        db.delete("patrimonio", "enviarBancoOnline=0", null);
    }
}
