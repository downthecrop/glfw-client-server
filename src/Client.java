import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

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

    public static void glEnable(int cap) {
        sendCommand("glEnable", cap);
    }

    public static void glDisable(int cap) {
        sendCommand("glDisable", cap);
    }

    public static void glViewport(int x, int y, int width, int height) {
        Object[] args = new Object[]{x, y, width, height};
        sendCommand("glViewport", args);
    }

    public static void glClear(int mask) {
        Object[] args = new Object[]{mask};
        sendCommand("glClear", args);
    }

    public static void glLoadMatrixf(FloatBuffer matrix) {
        float[] array = new float[matrix.remaining()];
        matrix.get(array);
        Object[] args = new Object[]{array};
        sendCommand("glLoadMatrixf", args);
    }

    public static void glLineWidth(float width) {
        Object[] args = new Object[]{width};
        sendCommand("glLineWidth", args);
    }

    public static void glRotatef(float angle, float x, float y, float z) {
        Object[] args = new Object[]{angle, x, y, z};
        sendCommand("glRotatef", args);
    }

    public static void glMatrixMode(int mode) {
        sendCommand("glMatrixMode", mode);
    }


    public static void glReadBuffer(int mode) {
        sendCommand("glReadBuffer", mode);
    }


    public static void glDrawBuffer(int mode) {
        sendCommand("glDrawBuffer", mode);
    }

    public static void glTexEnvi(int target, int pname, int param) {
        Object[] args = new Object[]{target, pname, param};
        sendCommand("glTexEnvi", args);
    }

    public static void glBindTexture(int target, int texture) {
        Object[] args = new Object[]{target, texture};
        sendCommand("glBindTexture", args);
    }

    public static void glShadeModel(int mode) {
        Object[] args = new Object[]{mode};
        sendCommand("glShadeModel", args);
    }

    public static void glClearDepth(double depth) {
        Object[] args = new Object[]{depth};
        sendCommand("glClearDepth", args);
    }

    public static void glDepthFunc(int func) {
        Object[] args = new Object[]{func};
        sendCommand("glDepthFunc", args);
    }


    public static void glDepthMask(boolean flag) {
        Object[] args = new Object[]{flag};
        sendCommand("glDepthMask", args);
    }

    public static void glLoadIdentity() {
        Object[] args = new Object[]{};
        sendCommand("glLoadIdentity", args);
    }

    public static void glPolygonMode(int face, int mode) {
        Object[] args = new Object[]{face, mode};
        sendCommand("glPolygonMode", args);
    }

    public static void glCullFace(int mode) {
        Object[] args = new Object[]{mode};
        sendCommand("glCullFace", args);
    }


    public static void glBlendFunc(int sfactor, int dfactor) {
        Object[] args = new Object[]{sfactor, dfactor};
        sendCommand("glBlendFunc", args);
    }

    public static void glAlphaFunc(int func, float ref) {
        Object[] args = new Object[]{func, ref};
        sendCommand("glAlphaFunc", args);
    }

    public static void glEnableClientState(int cap) {
        Object[] args = new Object[]{cap};
        sendCommand("glEnableClientState", args);
    }

    public static int glGetInteger(int pname) {
        Object[] args = new Object[]{pname};
        return (int) sendCommandWithReturn("glGetInteger", args);
    }


    public static int[] glGenTextures(int n) {
        int[] textureIDs = null;
        Object[] args = new Object[]{n};
        try {
            textureIDs = (int[]) sendCommandWithReturn("glGenTextures", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textureIDs;
    }



    public static void glPushAttrib(int mask) {
        Object[] args = new Object[]{mask};
        sendCommand("glPushAttrib", args);
    }

    public static void glRasterPos2i(int x, int y) {
        Object[] args = new Object[]{x, y};
        sendCommand("glRasterPos2i", args);
    }

    public static void glCopyPixels(int x, int y, int width, int height, int type) {
        Object[] args = new Object[]{x, y, width, height, type};
        sendCommand("glCopyPixels", args);
    }

    public static void glPopAttrib() {
        sendCommand("glPopAttrib", null);
    }

    public static void glOrtho(double left, double right, double bottom, double top, double near, double far) {
        Object[] args = new Object[]{left, right, bottom, top, near, far};
        sendCommand("glOrtho", args);
    }

    public static void glTranslatef(float x, float y, float z) {
        Object[] args = new Object[]{x, y, z};
        sendCommand("glTranslatef", args);
    }

    public static void glDisableClientState(int cap) {
        Object[] args = new Object[]{cap};
        sendCommand("glDisableClientState", args);
    }

    public static void glDeleteTextures(int[] textures) {
        Object[] args = new Object[]{textures};
        sendCommand("glDeleteTextures", args);
    }

    public static void glTexImage3D(int target, int level, int internalFormat, int width, int height, int depth, int border, int format, int type, ByteBuffer pixels) {
        Object[] args = new Object[]{target, level, internalFormat, width, height, depth, border, format, type, pixels};
        sendCommand("glTexImage3D", args);
    }


    public static void glTexParameteri(int target, int pname, int param) {
        Object[] args = new Object[]{target, pname, param};
        sendCommand("glTexParameteri", args);
    }

    public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, ByteBuffer pixels) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(pixels.array());

            byte[] pixelBytes = byteArrayOutputStream.toByteArray();

            Object[] args = new Object[]{target, level, internalformat, width, height, border, format, type, pixelBytes};
            sendCommand("glTexImage2D", args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Object sendCommandWithReturn(String command, Object args) {
        try (Socket socket = new Socket("localhost", 8080)) {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeUTF(command);
            out.writeObject(args);
            out.flush();

            return in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void glActiveTexture(int texture) {
        Object[] args = new Object[]{texture};
        sendCommand("glActiveTexture", args);
    }

    public static void glDrawElementsWrapper(int mode, int count, int type, ByteBuffer buffer) {
        byte[] byteArray = new byte[buffer.remaining()];
        buffer.get(byteArray);

        Object[] args = new Object[] { mode, count, type, byteArray };
        sendCommand("glDrawElementsWrapper", args);
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
                glLineWidth(1000f);
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
