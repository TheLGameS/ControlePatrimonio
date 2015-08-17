package br.ufscar.dc.controledepatrimonio.Forms;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.ufscar.dc.controledepatrimonio.Entity.Patrimonio;
import br.ufscar.dc.controledepatrimonio.R;

public class TagAdapter extends BaseAdapter {
    Context ctx;
    List<Patrimonio> listaPatrimonio;

    public TagAdapter(Context ctx, List<Patrimonio> listaPatrimonio) {
        this.listaPatrimonio = listaPatrimonio;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return listaPatrimonio.size();
    }

    @Override
    public Object getItem(int position) {
        return listaPatrimonio.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            final int auxPosition = position;

            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.adapter_etiqueta, null);

            Patrimonio patrimonio = listaPatrimonio.get(position);

            TextView lblPatrimonio = (TextView) layout.findViewById(R.id.lblPatrimonio);
            TextView lblLocalizacao = (TextView) layout.findViewById(R.id.lblLocalizacao);

            if (patrimonio.getDescricao() == null) {
                lblPatrimonio.setText(patrimonio.getIdentificacao());
                lblLocalizacao.setText("");
            } else {
                lblPatrimonio.setText(patrimonio.getDescricao());
                lblLocalizacao.setText(patrimonio.getLocal().getNome());
            }

            return layout;
        } catch (Exception ex) {
            Log.d("ERRO", ex.getMessage());
            return null;
        }
    }
}
