package br.ufscar.dc.controledepatrimonio.Forms;

import android.app.ProgressDialog;
import android.content.Intent;
import android.preference.PreferenceActivity;
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

public class DepartamentoActivity extends AppCompatActivity{
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departamento);

        Button btnObter = (Button) findViewById(R.id.btnObterJson);
        progressDialog = new ProgressDialog(DepartamentoActivity.this);


        btnObter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                DepartamentoTask departamentoTask = new DepartamentoTask(DepartamentoActivity.this);
                departamentoTask.execute();

                LocalTask localTask = new LocalTask(DepartamentoActivity.this);
                localTask.execute();


                PatrimonioTask patrimonioTask = new PatrimonioTask(DepartamentoActivity.this);
                patrimonioTask.execute();
            }
        });

        Button btnListar_Departamento = (Button) findViewById(R.id.btnActivity_Listar_Departamento);
        btnListar_Departamento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(DepartamentoActivity.this, ListarDepartamentoActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_departamento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
