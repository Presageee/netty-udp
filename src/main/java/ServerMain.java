import server.UdpServer;

/**
 * Created by LJT on 17-10-9.
 * email: ljt1343@gmail.com
 */
public class ServerMain {
    public static Object object = new Object();
    public static void main(String[] args) throws InterruptedException {
        UdpServer server = UdpServer.getInstance();
        server.init(6666);
        synchronized (object) {
            object.wait();
        }
    }
}
