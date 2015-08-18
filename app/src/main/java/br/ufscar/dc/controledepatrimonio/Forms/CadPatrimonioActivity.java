package br.ufscar.dc.controledepatrimonio.Forms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufscar.dc.controledepatrimonio.Entity.Local;
import br.ufscar.dc.controledepatrimonio.Entity.Patrimonio;
import br.ufscar.dc.controledepatrimonio.Entity.Responsavel;
import br.ufscar.dc.controledepatrimonio.R;
import br.ufscar.dc.controledepatrimonio.Util.Database.DAO.LocalDao;
import br.ufscar.dc.controledepatrimonio.Util.Database.Database;

public class CadPatrimonioActivity extends AppCompatActivity {
    private EditText txtDescricao;
    private EditText txtIdentificacao;
    private EditText txtEstado;
    private Spinner cboLocal;
    private Spinner cboResponsavel;
    private Database db;
    private Patrimonio patrimonio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new Database(CadPatrimonioActivity.this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_patrimonio);

        List<Local> listaLocal;
        List<Responsavel> listaResponsavel;

        listaLocal = db.buscarLocais();
        listaResponsavel = db.buscarResponsaveis();

        txtDescricao = (EditText) findViewById(R.id.txtDescricaoPatrimonio);
        txtIdentificacao = (EditText) findViewById(R.id.txtIdentificacaoPatrimonio);
        txtEstado = (EditText) findViewById(R.id.txtEstadoPatrimonio);
        cboLocal = (Spinner) findViewById(R.id.cboLocalPatrimonio);
        cboResponsavel = (Spinner) findViewById(R.id.cboResponsavel);


        ArrayAdapter<Local> adapterLocal = new ArrayAdapter<Local>(this, android.R.layout.simple_spinner_item, listaLocal);
        cboLocal.setAdapter(adapterLocal);

        ArrayAdapter<Responsavel> adapterResponsavel = new ArrayAdapter<Responsavel>(this,
                android.R.layout.simple_spinner_item, listaResponsavel);

        cboResponsavel.setAdapter(adapterResponsavel);

        if (getIntent().hasExtra("patrimonio")) {
            patrimonio = (Patrimonio) getIntent().getExtras().getSerializable("patrimonio");

            txtDescricao.setText(patrimonio.getDescricao());
            txtIdentificacao.setText(patrimonio.getIdentificacao());
            txtEstado.setText(patrimonio.getEstado());

            cboLocal.setId(patrimonio.getLocal().getId());
            cboResponsavel.setId(patrimonio.getResponsavel().getId());
        }

        final Button btnGravar = (Button) findViewById(R.id.btnGravarPatrimonio);
        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (patrimonio.getId() == 0)
                    patrimonio = new Patrimonio();
                Date d = new Date();

                patrimonio.setEnviarBancoOnline(true);
                patrimonio.setIdentificacao(txtIdentificacao.getText().toString());
                patrimonio.setDescricao(txtDescricao.getText().toString());
                patrimonio.setEstado(txtEstado.getText().toString());
                patrimonio.setDataEntrada(d);

                Local local = (Local) cboLocal.getSelectedItem();

                patrimonio.setLocal(local);

                Responsavel responsavel = (Responsavel) cboResponsavel.getSelectedItem();

                patrimonio.setResponsavel(responsavel);

                if (patrimonio.getId() == 0)
                    db.inserirPatrimonio(patrimonio);
                else
                    db.atualizarPatrimonio(patrimonio);

            }
        });
    }

    //region MÉTODOS NÃO UTILIZADOS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cad_patrimonio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //endregion
}
