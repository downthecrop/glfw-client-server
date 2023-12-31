import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;

public class Client {

    private static Socket socket;
    private static ObjectOutputStream out;

    private static ObjectInputStream in;

    static {
        try {
            socket = new Socket("localhost", 8080);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
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

    public static void glPointParameterfv(int pname, float[] params) {
        Object[] args = new Object[]{pname, params};
        try {
            sendCommand("glPointParameterfv", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static float[] glGetFloatv(int pname) {
        float[] result = null;
        Object[] args = new Object[]{pname};
        try {
            result = (float[]) sendCommandWithReturn("glGetFloatv", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static void glGetFloatv(int pname, FloatBuffer buffer) {
        Object[] args = new Object[]{pname};
        try {
            float[] result = (float[]) sendCommandWithReturn("glGetFloatv", args);
            buffer.put(result);
            buffer.flip(); // Don't forget to flip the buffer to be able to read it
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void glPointParameterf(int pname, float param) {
        Object[] args = new Object[]{pname, param};
        try {
            sendCommand("glPointParameterf", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glTexStorage2D(int target, int levels, int internalformat, int width, int height) {
        Object[] args = new Object[]{target, levels, internalformat, width, height};
        try {
            sendCommand("glTexStorage2D", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, ByteBuffer pixels) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(pixels.array());

            byte[] pixelBytes = byteArrayOutputStream.toByteArray();

            Object[] args = new Object[]{target, level, xoffset, yoffset, width, height, format, type, pixelBytes};
            sendCommand("glTexSubImage2D", args);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void glBindBuffer(int target, int buffer) {
        Object[] args = new Object[]{target, buffer};
        try {
            sendCommand("glBindBuffer", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void glGenerateMipmap(int target) {
        Object[] args = new Object[]{target};
        try {
            sendCommand("glGenerateMipmap", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
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


    public static int glGenTextures() {
        Integer textureID = null;
        Object[] args = new Object[]{};
        try {
            textureID = (Integer) sendCommandWithReturn("glGenTexturesNoArgs", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textureID != null ? textureID : -1; // Return -1 if null, indicating an error
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
            byte[] pixelBytes = new byte[pixels.remaining()];
            pixels.get(pixelBytes);

            Object[] args = new Object[]{target, level, internalformat, width, height, border, format, type, pixelBytes};
            sendCommand("glTexImage2D", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, IntBuffer pixels) {
        try {
            int[] pixelInts = new int[pixels.remaining()];
            pixels.get(pixelInts);

            Object[] args = new Object[]{target, level, internalformat, width, height, border, format, type, pixelInts};
            sendCommand("glTexImage2D", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static int glGenLists(int range) {
        int listID = -1;
        Object[] args = new Object[]{range};
        try {
            listID = (int) sendCommandWithReturn("glGenLists", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listID;
    }

    public static void glNewList(int list, int mode) {
        Object[] args = new Object[]{list, mode};
        sendCommand("glNewList", args);
    }

    public static void glBegin(int mode) {
        Object[] args = new Object[]{mode};
        sendCommand("glBegin", args);
    }

    public static void glColor3f(float red, float green, float blue) {
        Object[] args = new Object[]{red, green, blue};
        try {
            sendCommand("glColor3f", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void glTexCoord2f(float s, float t) {
        Object[] args = new Object[]{s, t};
        sendCommand("glTexCoord2f", args);
    }

    public static void glEndList() {
        sendCommand("glEndList", null);
    }

    public static void glEnd() {
        sendCommand("glEnd", null);
    }
    public static void glVertex2f(float x, float y) {
        Object[] args = new Object[]{x, y};
        sendCommand("glVertex2f", args);
    }

    private static Object sendCommandWithReturn(String command, Object args) {
        try {
            synchronized (out) {
                out.writeUTF(command);
                out.writeObject(args);
                out.flush();
            }

            Object returnValue;
            synchronized (in) {
                returnValue = in.readObject();
            }

            return returnValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void glActiveTexture(int texture) {
        Object[] args = new Object[]{texture};
        sendCommand("glActiveTexture", args);
    }

    public static void glfwSwapBuffers(long window){
        Object[] args = new Object[]{window};
        sendCommand("glfwSwapBuffers", args);
    }

    public static void glfwPollEvents(){
        Object[] args = new Object[]{};
        sendCommand("glfwPollEvents", args);
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

    public static void glTexGeni(int coord, int pname, int param) {
        Object[] args = new Object[]{coord, pname, param};
        try {
            sendCommand("glTexGeni", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int glGenBuffers() {
        int bufferID = -1;
        Object[] args = new Object[]{};
        try {
            bufferID = (int) sendCommandWithReturn("glGenBuffers", args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bufferID;
    }

    public static void glGenBuffers(IntBuffer buffer) {
        Object[] args = new Object[]{};
        try {
            int[] result = (int[]) sendCommandWithReturn("glGenBuffersInt", args);
            if (result != null && result.length > 0) {
                buffer.put(result[0]);
                buffer.flip();  // Don't forget to flip the buffer to make it readable.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void glScissor(int x, int y, int width, int height) {
        Object[] args = new Object[]{x, y, width, height};
        try {
            sendCommand("glScissor", args);
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
            long l = 100;
            float x = 0, y = 0;
            float dx = 0.01f, dy = 0.01f;
            Random random = new Random();
            while (true) {
                    glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

                    if (x >= 1 || x <= -1) dx = -dx;
                    if (y >= 1 || y <= -1) dy = -dy;

                    x += dx;
                    y += dy;

                    glColor3f(random.nextFloat(), random.nextFloat(), random.nextFloat());
                    glBegin(GL11.GL_QUADS);

                    glVertex2f(x - 0.1f, y - 0.1f);
                    glVertex2f(x + 0.1f, y - 0.1f);
                    glVertex2f(x + 0.1f, y + 0.1f);
                    glVertex2f(x - 0.1f, y + 0.1f);

                    glEnd();

                    glfwSwapBuffers(l);
                    glfwPollEvents();
            }
        } finally {
            close();
        }
    }
}
