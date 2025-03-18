import tinyb.*;

public class BLEScanner {
    public static void main(String[] args) {
        BluetoothManager manager = BluetoothManager.getBluetoothManager();

        System.out.println("Scanning for devices...");
        for (int i = 0; i < 10; i++) {
            for (BluetoothDevice device : manager.getDevices()) {
                System.out.println("Found device: " + device.getName() + " [" + device.getAddress() + "]");
            }
            try {
                Thread.sleep(1000); // 每秒扫描一次
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
