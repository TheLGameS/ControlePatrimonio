package br.ufscar.dc.controledepatrimonio.Forms;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;

import br.ufscar.dc.controledepatrimonio.Entity.Departamento;
import br.ufscar.dc.controledepatrimonio.R;
import br.ufscar.dc.controledepatrimonio.Util.Database.Database;

public class CadDepartamentoActivity extends AppCompatActivity {
    private Departamento departamento = new Departamento();
    private EditText txtNome;
    private EditText txtSigla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_departamento);

        Button btnGravar = (Button) findViewById(R.id.btnInserir_Departamento);
        txtNome = (EditText) findViewById(R.id.txtNome);
        txtSigla = (EditText) findViewById(R.id.txtSigla);

        btnGravar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                gravarDepartamento();
                finish();
            }
        });

    }

    public void gravarDepartamento() {
        try {

            departamento.setNome(txtNome.getText().toString());
            departamento.setSigla(txtSigla.getText().toString());

            Database db = new Database(this);
            db.inserirDepartamento(departamento);

            Toast.makeText(this, "Departamento inserido com sucesso!", Toast.LENGTH_SHORT).show();
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cad_departamento, menu);
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
