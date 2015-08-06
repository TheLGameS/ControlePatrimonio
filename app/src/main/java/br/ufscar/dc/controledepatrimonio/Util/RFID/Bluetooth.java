package br.ufscar.dc.controledepatrimonio.Util.RFID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Bluetooth extends BroadcastReceiver {
    private BluetoothListener listener;
    private BluetoothAdapter dispositivo;
    private List<BluetoothDevice> listaDispostivo = new ArrayList<>();

    public enum Estado {
        LIGADO, DESLIGADO, NAO_COMPATIVEL
    }

    public Bluetooth (BluetoothListener listener) {
        dispositivo = BluetoothAdapter.getDefaultAdapter();
        this.listener = listener;
        getDispositivosPareados();
    }

    public BluetoothAdapter getDispositivo() {
        return dispositivo;
    }

    public List<BluetoothDevice> getListaDispostivo(){
        return listaDispostivo;
    }

    //region getDispositivosPareados: Retorna todos dispositivos que já estão pareados com o aparelho
    private void getDispositivosPareados() {
        Set<BluetoothDevice> pairedDevicesList = dispositivo.getBondedDevices();

        if (pairedDevicesList.size() > 0) {
            for (BluetoothDevice device : pairedDevicesList) {
                listaDispostivo.add(device);
            }
        }
    }
    //endregion

    //region verificarEstado: Verifica se o recurso do Bluetooth está ativo
    public Estado verificarEstado() {
        if (dispositivo == null) {
            return Estado.NAO_COMPATIVEL;
        }
        if (!dispositivo.isEnabled()) {
            return Estado.DESLIGADO;
        }

        return Estado.LIGADO;
    }
    //endregion

    //region iniciarBusca: Inicia a busca de dispositivos que não estão pareados
    public void iniciarBusca(Context context, BluetoothListener listener) {
        Bluetooth bluetooth = new Bluetooth(listener);

        // Registro os Broadcast necessarios para a busca de dispositivos
        IntentFilter filter = new IntentFilter(BluetoothListener.ACTION_FOUND);
        IntentFilter filter2 = new IntentFilter(BluetoothListener.ACTION_DISCOVERY_FINISHED);
        IntentFilter filter3 = new IntentFilter(BluetoothListener.ACTION_DISCOVERY_STARTED);
        context.registerReceiver(bluetooth, filter);
        context.registerReceiver(bluetooth, filter2);
        context.registerReceiver(bluetooth, filter3);

        // inicio a busca e retorno a classe instanciada.
        dispositivo.startDiscovery();
    }
    //endregion

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        //caso encontre um dispositivo:
        if (action.compareTo(BluetoothDevice.ACTION_FOUND) == 0) {

            //pega as o dispositivo encontrado:
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

            //se a lista já tiver esse dispositivo eu retorno para o proximo
            //isso permite que sejá mostrado somente uma vez meu dispositivo
            //problema muito comum em exemplos
            if (listaDispostivo.contains(device)) {
                return;
            }

            //adiciono o dispositivo na minha lista:
            listaDispostivo.add(device);

        } else if (action.compareTo(BluetoothAdapter.ACTION_DISCOVERY_FINISHED) == 0) {
            //caso o discovery chegue ao fim eu desregistro meus broadcasts
            //SEMPRE FAÇA ISSO quando terminar de usar um broadcast
            context.unregisterReceiver(this);
        }

        if (listener != null) {
            //se foi definido o listener eu aviso a quem ta gerenciando.
            listener.action(action);
        }
    }
}
