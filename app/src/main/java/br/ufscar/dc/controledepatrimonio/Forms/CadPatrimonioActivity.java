package br.ufscar.dc.controledepatrimonio.Forms;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private EditText txtNome;
    private Spinner cboLocal;
    private Spinner cboResponsavel;
    private CheckBox chkAtivo;
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

        txtNome = (EditText) findViewById(R.id.txtNomePatrimonio);
        txtDescricao = (EditText) findViewById(R.id.txtDescricaoPatrimonio);
        txtIdentificacao = (EditText) findViewById(R.id.txtIdentificacaoPatrimonio);
        txtEstado = (EditText) findViewById(R.id.txtEstadoPatrimonio);
        cboLocal = (Spinner) findViewById(R.id.cboLocalPatrimonio);
        cboResponsavel = (Spinner) findViewById(R.id.cboResponsavel);
        chkAtivo = (CheckBox) findViewById(R.id.chkAtivoPatrimonio);
        chkAtivo.setChecked(true);

        ArrayAdapter<Local> adapterLocal = new ArrayAdapter<Local>(this, android.R.layout.simple_spinner_item, listaLocal);
        cboLocal.setAdapter(adapterLocal);

        ArrayAdapter<Responsavel> adapterResponsavel = new ArrayAdapter<Responsavel>(this,
                android.R.layout.simple_spinner_item, listaResponsavel);

        cboResponsavel.setAdapter(adapterResponsavel);

        if (getIntent().hasExtra("patrimonio")) {
            patrimonio = (Patrimonio) getIntent().getExtras().getSerializable("patrimonio");

            txtNome.setText(patrimonio.getNome());
            txtDescricao.setText(patrimonio.getDescricao());
            txtIdentificacao.setText(patrimonio.getIdentificacao());
            txtEstado.setText(patrimonio.getEstado());
            chkAtivo.setChecked(patrimonio.getStatusRegistro());

            cboLocal.setId(patrimonio.getLocal().getId());
            cboResponsavel.setId(patrimonio.getResponsavel().getId());
        }
        else if (getIntent().hasExtra("tag")) {
            txtIdentificacao.setText(getIntent().getExtras().getString("tag").toString());
        }


        final Button btnGravar = (Button) findViewById(R.id.btnGravarPatrimonio);
        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (patrimonio == null)
                    patrimonio = new Patrimonio();
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();

                patrimonio.setNome(txtNome.getText().toString());
                patrimonio.setIdentificacao(txtIdentificacao.getText().toString());
                patrimonio.setDescricao(txtDescricao.getText().toString());
                patrimonio.setEstado(txtEstado.getText().toString());

                patrimonio.setDataEntrada(date);
                if (chkAtivo.isChecked())
                    patrimonio.setStatusRegistro(true);
                else
                    patrimonio.setStatusRegistro(false)
                            ;
                patrimonio.setEnviarBancoOnline(true);

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
