package br.ufscar.dc.controledepatrimonio.Forms;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import br.ufscar.dc.controledepatrimonio.Entity.Departamento;
import br.ufscar.dc.controledepatrimonio.R;
import br.ufscar.dc.controledepatrimonio.Util.Database.Database;

public class ListarDepartamentoActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Database db = new Database(this);

        List<Departamento> list = db.buscarDepartamentos();
        setListAdapter(new DepartamentoAdapter(this, list));
    }
}
