import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {

            if (!GLFW.glfwInit()) {
                System.err.println("Failed to initialize GLFW!");
                System.exit(1);
            }

            long window = GLFW.glfwCreateWindow(800, 600, "Basic GLFW Window", 0, 0);
            if (window == 0) {
                System.err.println("Failed to create a GLFW window!");
                GLFW.glfwTerminate();
                System.exit(1);
            }

            GLFW.glfwMakeContextCurrent(window);
            GL.createCapabilities();

            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                    while (true) {
                        String command = in.readUTF();
                        Object argsObject = in.readObject();

                        switch (command) {
                            case "glClear":
                                int mask = (int) argsObject;
                                GL20.glClear(mask);
                                break;

                            case "glClearColor":
                                Object[] colorArgs = (Object[]) argsObject;
                                float red = (float) colorArgs[0];
                                float green = (float) colorArgs[1];
                                float blue = (float) colorArgs[2];
                                float alpha = (float) colorArgs[3];
                                GL20.glClearColor(red, green, blue, alpha);
                                break;

                            default:
                                System.out.println("Unrecognized command: " + command);
                        }

                        GLFW.glfwPollEvents();
                        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
                        GLFW.glfwSwapBuffers(window);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Connection closed or error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
