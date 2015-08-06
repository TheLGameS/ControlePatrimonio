package br.ufscar.dc.controledepatrimonio.Forms;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import br.ufscar.dc.controledepatrimonio.R;
import br.ufscar.dc.controledepatrimonio.Util.RFID.Bluetooth;
import br.ufscar.dc.controledepatrimonio.Util.RFID.BluetoothListener;

public class ListarBluetoothActivity extends AppCompatActivity implements BluetoothListener {
    private Bluetooth bluetooth = new Bluetooth(this);
    private final static int REQUEST_ENABLE_BT = 1;
    private List<BluetoothDevice> listaDispositivos = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_bluetooth);

        //region Bluetooth
        switch (bluetooth.verificarEstado()) {
            case LIGADO:
                bluetooth.iniciarBusca(this, this);

                listaDispositivos = bluetooth.getListaDispostivo();
                ListView lstBluetooth = (ListView) findViewById(R.id.lstBluetooth);
                BluetoothAdapter itemBluetooth = new BluetoothAdapter(this, listaDispositivos);

                lstBluetooth.setAdapter(itemBluetooth);
                break;
            case DESLIGADO:
                Intent enableBtIntent = new Intent(bluetooth.getDispositivo().ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
                break;
            case NAO_COMPATIVEL:
                Toast.makeText(getApplicationContext(), R.string.msg_bluetooth_nao_suportado, Toast.LENGTH_SHORT).show();
                break;
        }
        //endregion

        //region Botão Procurar
        Button btnProcurar = (Button) findViewById(R.id.btnProcurar_Bluetooth);
        btnProcurar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

            }
        });
        //endregion
    }

    //region Bluetooth
    @Override
    public void action(String action) {
        if (action.compareTo(ACTION_DISCOVERY_FINISHED) == 0) {
            listaDispositivos = bluetooth.getListaDispostivo();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        if (requestCode == REQUEST_ENABLE_BT) {
            switch (resultCode) {
                case RESULT_CANCELED:
                    //Não habilitou Bluetooth, então fecha a tela.
                    finish();
                    break;
            }
        }
    }
    //endregion

    //region Métodos não utilizados
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_listar_bluetooth, menu);
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
    //endregion
}
