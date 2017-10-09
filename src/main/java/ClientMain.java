import client.UdpClient;

/**
 * Created by LJT on 17-10-9.
 * email: ljt1343@gmail.com
 */
public class ClientMain {
    public static void main(String []args) throws InterruptedException {
        UdpClient client = new UdpClient();
        client.init();
        while (true) {
            client.send();
            Thread.sleep(10000);
        }
    }
}
