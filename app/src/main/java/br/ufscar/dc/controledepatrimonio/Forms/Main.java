package br.ufscar.dc.controledepatrimonio.Forms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import br.ufscar.dc.controledepatrimonio.R;
import br.ufscar.dc.controledepatrimonio.Util.Webservice.DepartamentoTask;
import br.ufscar.dc.controledepatrimonio.Util.Webservice.LocalTask;
import br.ufscar.dc.controledepatrimonio.Util.Webservice.PatrimonioTask;
import br.ufscar.dc.controledepatrimonio.Util.Webservice.ResponsavelTask;

public class Main extends AppCompatActivity {
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region Botão Departamento
        Button btnDepartamento = (Button) findViewById(R.id.btnPatrimonio);
        btnDepartamento.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Main.this, PatrimonioActivity.class);
                startActivity(intent);
            }
        });
        //endregion

        //region Botão RFID
        Button btnIniciarRFID = (Button) findViewById(R.id.btnIniciarRFID);
        btnIniciarRFID.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Main.this, ListarBluetoothActivity.class);
                startActivity(intent);
            }
        });
        //endregion
    }

    //region MÉTODOS DA TELA
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_carregar) {
            DepartamentoTask departamentoTask = new DepartamentoTask(Main.this);
            departamentoTask.execute();

            ResponsavelTask responsavelTask = new ResponsavelTask(Main.this);
            responsavelTask.execute();

            LocalTask localTask = new LocalTask(Main.this);
            localTask.execute();

            PatrimonioTask patrimonioTask = new PatrimonioTask(Main.this);
            patrimonioTask.execute();
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion


}
