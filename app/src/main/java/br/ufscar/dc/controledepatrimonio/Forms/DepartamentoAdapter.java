package br.ufscar.dc.controledepatrimonio.Forms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.ufscar.dc.controledepatrimonio.Entity.Departamento;
import br.ufscar.dc.controledepatrimonio.R;
import br.ufscar.dc.controledepatrimonio.Util.Database.Database;

public class DepartamentoAdapter extends BaseAdapter {
    private Context ctx;
    private List<Departamento> departamentos;

    public DepartamentoAdapter(Context ctx, List<Departamento> departamentos) {
        this.ctx = ctx;
        this.departamentos = departamentos;
    }

    @Override
    public int getCount() {
        return departamentos.size();
    }

    @Override
    public Object getItem(int position) {
        return departamentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int auxPosition = position;

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_listar_departamento, null);

        Departamento departamento = departamentos.get(position);

        TextView lblDepartamento = (TextView) layout.findViewById(R.id.lblDepartamento);
        lblDepartamento.setText(departamento.getNome());

        Button btnExcluir = (Button) layout.findViewById(R.id.btnExcluir_Departamento);
        btnExcluir.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                Database db = new Database(ctx);
                db.deletarDepartamento(departamentos.get(auxPosition));

                layout.setVisibility(View.GONE);
            }
        });

        return layout;
    }
}
