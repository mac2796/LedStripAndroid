package xyz.mateusztarnowski.homecontrol;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by mac2796 on 24.02.18.
 */

public class FindLedStripsTask extends AsyncTask<ArrayAdapter, Void, ArrayList<InetAddress>> {
    private ArrayAdapter adapter;

    @Override
    protected ArrayList<InetAddress> doInBackground(ArrayAdapter[] arrayAdapters) {
        adapter = arrayAdapters[0];

        DatagramSocket socket;
        ArrayList<InetAddress> addressesArrayList = new ArrayList<>();

        String dataString = "FIND";
        byte[] dataArray = dataString.getBytes();
        DatagramPacket packet = new DatagramPacket(dataArray, dataArray.length);

        try {
            socket = new DatagramSocket();

            packet.setPort(1851);
            ArrayList<InetAddress> broadcastAddresses = getBroadcastAddresses();
            for (InetAddress address : broadcastAddresses) {
                packet.setAddress(address);
                socket.send(packet);
            }

            socket.setSoTimeout(500);
            for (int i = 0; i < 10; i++) {
                socket.receive(packet);
                addressesArrayList.add(packet.getAddress());
                Log.d("PACKET", packet.getAddress() + " " + packet.getPort() + " " + Arrays.toString(packet.getData()));
            }

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }

        return addressesArrayList;
    }

    @Override
    protected void onPostExecute(ArrayList<InetAddress> addresses) {
        for (InetAddress address : addresses) {
            adapter.add(address.toString());
        }
    }

    private ArrayList<InetAddress> getBroadcastAddresses() {
        ArrayList<InetAddress> broadcastAddresses = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> networkInterfacesEnumerator = NetworkInterface.getNetworkInterfaces();
            while (networkInterfacesEnumerator.hasMoreElements()) {
                List<InterfaceAddress> addresses = networkInterfacesEnumerator.nextElement().getInterfaceAddresses();
                for (InterfaceAddress address : addresses) {
                    broadcastAddresses.add(address.getBroadcast());
                }
            }
        } catch (SocketException e) {

        }
        return broadcastAddresses;
    }
}
