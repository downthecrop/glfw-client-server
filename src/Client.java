import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private static Socket socket;
    private static ObjectOutputStream out;

    static {
        try {
            socket = new Socket("localhost", 8080);
            out = new ObjectOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glClearColor(float red, float green, float blue, float alpha) {
        Object[] args = new Object[]{red, green, blue, alpha};
        sendCommand("glClearColor", args);
    }

    private static void sendCommand(String command, Object args) {
        try {
            out.writeUTF(command);
            out.writeObject(args);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        try {
            while (true) {
                glClearColor(1, 0, 0, 1);
                Thread.sleep(1000);
                glClearColor(0, 0, 1, 1);
                Thread.sleep(1000);
                glClearColor(0, 1, 0, 1);
                Thread.sleep(1000);
            }
        } finally {
            close();
        }
    }
}
