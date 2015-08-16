package br.ufscar.dc.controledepatrimonio.Util.Webservice;

import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import br.ufscar.dc.controledepatrimonio.Entity.Local;
import br.ufscar.dc.controledepatrimonio.Util.Database.Database;

public class LocalTask extends AsyncTask<Void, Void, String> {
    private Context ctx;
    private String retorno = null;

    public LocalTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected String doInBackground(Void... params) {
        Webservice webservice = new Webservice("http://192.168.0.10:8080/Patrimonio/local/index.json");
        String retorno = webservice.getJSON();
        return retorno;
    }

    @Override
    protected void onPostExecute(String json) {
        Database db = new Database(ctx);
        Gson gson = new Gson();

        TypeToken<List<Local>> token = new TypeToken<List<Local>>() {};
        List<Local> listaLocal = gson.fromJson(json, token.getType());

        for (Local local : listaLocal) {
            db.inserirLocal(local);
        }
    }


}
