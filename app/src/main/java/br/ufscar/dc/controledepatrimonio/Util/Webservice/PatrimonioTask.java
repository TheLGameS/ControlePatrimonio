package br.ufscar.dc.controledepatrimonio.Util.Webservice;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import br.ufscar.dc.controledepatrimonio.Entity.Patrimonio;
import br.ufscar.dc.controledepatrimonio.Util.Database.Database;

public class PatrimonioTask extends AsyncTask<Void, Void, String> {
    private Context ctx;
    private String retorno = null;

    public PatrimonioTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(Void... params) {
        Webservice webservice = new Webservice("patrimonio/index.json");
        String retorno = webservice.getJSON();
        return retorno;
    }

    @Override
    protected void onPostExecute(String json) {
        Database db = new Database(ctx);
        Gson gson = new Gson();

        TypeToken<List<Patrimonio>> token = new TypeToken<List<Patrimonio>>() {
        };
        List<Patrimonio> listaPatrimonio = gson.fromJson(json, token.getType());

        if (listaPatrimonio.size() > 0)
            db.deletarTodosPatrimonios();


        for (Patrimonio patrimonio : listaPatrimonio) {
            patrimonio.setEnviarBancoOnline(false);
            db.inserirPatrimonio(patrimonio);
        }
    }


}
