package br.ufscar.dc.controledepatrimonio.Forms;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.List;
import br.ufscar.dc.controledepatrimonio.R;
import br.ufscar.dc.controledepatrimonio.Util.RFID.DotR900.OnBtEventListener;
import br.ufscar.dc.controledepatrimonio.Util.RFID.DotR900.R900;
import br.ufscar.dc.controledepatrimonio.Util.RFID.Leitor;

public class ListarBluetoothActivity extends AppCompatActivity implements OnBtEventListener {
    private ListView lstBluetooth;
    private R900 leitor;
    private BluetoothListItem itemBluetooth;
    private List<BluetoothDevice> listaDispositivo;
    public final int MSG_ENCONTROU_BLUETOOTH = 1;
    private Toolbar mToolbar;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            switch (msg.what) {
                case MSG_ENCONTROU_BLUETOOTH:
                    itemBluetooth.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_bluetooth);

        iniciarTela();

        //region Botão Procurar
        Button btnProcurar = (Button) findViewById(R.id.btnProcurar_Bluetooth);
        btnProcurar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                leitor.buscarBluetooth();
                listaDispositivo = leitor.getListaDispositivo();
            }
        });
        //endregion

        //region ListView
        lstBluetooth.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                leitor.pararBusca();

                //setEnabledLinkCtrl(false);
                leitor.setDispositivo(listaDispositivo.get(position));


                Intent intent = new Intent(ListarBluetoothActivity.this, ListarEtiquetaActivity.class);
                Bundle bundle = new Bundle();

                bundle.putString("addressDispositivo", leitor.getDispositivo().getAddress());
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
        //endregion

        //region Toolbar
        mToolbar = (Toolbar) findViewById(R.id.ToolbarTop);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolbar.findViewById(R.id.btnVoltar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //endregion
    }

    //region Métodos da tela
    private void iniciarTela() {
        leitor = new R900(this, mHandler, this);

        lstBluetooth = (ListView) findViewById(R.id.lstBluetooth);
        itemBluetooth = new BluetoothListItem(this, leitor.getListaDispositivo());
        lstBluetooth.setAdapter(itemBluetooth);

        switch (leitor.getEstado()) {
            case LIGADO:
                listaDispositivo = leitor.getListaDispositivo();
                leitor.buscarBluetooth();
                break;
            case DESLIGADO:
                Intent enableBtIntent = new Intent(Leitor.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, Leitor.REQUEST_ENABLE_BT);
                break;
            case NAO_COMPATIVEL:
                Toast.makeText(getApplicationContext(), R.string.msg_bluetooth_nao_suportado, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == leitor.REQUEST_ENABLE_BT) {
            switch (resultCode) {
                case RESULT_CANCELED:
                    //Não habilitou Bluetooth, então fecha a tela.
                    finish();
                    break;
                case RESULT_OK:
                    listaDispositivo = leitor.getListaDispositivo();
                    leitor.buscarBluetooth();
            }
        }
    }

    public void onDestroy() {
        super.onDestroy();

        leitor.finalize();

    }
    //endregion

    //region Métodos não utilizados
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_listar_bluetooth, menu);
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

    @Override
    public void onNotifyBtDataRecv() {

    }

    @Override
    public void onBtConnected(BluetoothDevice device) {

    }
    //endregion
}
