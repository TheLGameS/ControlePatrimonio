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
    String[] colunas = new String[]{"_id", "descricao", "dataentrada", "identificacao", "estado",
            "local", "responsavel", "tagrfid","enviarbancoonline"};

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
        val.put("responsavel", patrimonio.getResponsavel().getId());

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
        val.put("responsavel", patrimonio.getResponsavel().getId());

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
                patrimonios.add(iniciarPatrimonio(cursor));
            } while (cursor.moveToNext());
        }

        return patrimonios;
    }

    public Patrimonio buscarPorId(int id) {
        Cursor cursor = db.query("Patrimonio", colunas, "_id =" + id, null, null, null, null);

        return iniciarPatrimonio(cursor);
    }

    public Patrimonio buscarPorTag(String tag) {
        Cursor cursor;

        try {
            cursor = db.query("Patrimonio", colunas, "tagRFID =" + tag, null, null, null, null);
        }
        catch (Exception ex) {
            Log.d("DB:", ex.getMessage());
            return null;
        }

        return iniciarPatrimonio(cursor);
    }

    //region iniciarPatrimonio: DEFINE O CURSOR AO OBJETO PATRIMONIO
    private Patrimonio iniciarPatrimonio(Cursor cursor) {
        Patrimonio patrimonio = new Patrimonio();
        Local local;
        Responsavel responsavel;
        LocalDao localDao = new LocalDao(db);
        ResponsavelDao responsavelDao = new ResponsavelDao(db);

        if (cursor.getCount() == 1) {
            patrimonio.setId(cursor.getInt(0));
            patrimonio.setDescricao(cursor.getString(1));

            DateFormat dataFormato = new SimpleDateFormat("dd/MM/yyyy");
            try {
                if (cursor.getString(2) == null) {
                    patrimonio.setDataEntrada(null);
                } else {
                    Date data = dataFormato.parse(cursor.getString(2));
                    patrimonio.setDataEntrada(data);
                }

            } catch (ParseException ex) {
                ex.printStackTrace();
            }


            patrimonio.setIdentificacao(cursor.getString(3));
            patrimonio.setEstado(cursor.getString(4));

            local = localDao.buscarPorId(cursor.getInt(5));

            patrimonio.setLocal(local);

            responsavel = responsavelDao.buscarPorId(cursor.getInt(6));
            patrimonio.setResponsavel(responsavel);
            patrimonio.setTagRFID(cursor.getString(7));
            patrimonio.setEnviarBancoOnline(true);

            return patrimonio;
        } else {
            return null;
        }
    }
    //endregion
}
