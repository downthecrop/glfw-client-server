import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

public class Server {
    public static void main(String[] args) {
        int level;
        int target;
        int texture;
        int pname;
        int width;
        int border;
        int height;
        int x;
        int y;
        int cap;
        int type;
        int mode;
        int mask;
        int format;
        Object[] bindTextureArgs;
        Object[] oArgs;
        int param;
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

            while (true) {
                GL.createCapabilities();
                try (Socket socket = serverSocket.accept()) {
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

                    while (true) {
                        String command = in.readUTF();
                        Object argsObject = in.readObject();
                        if(!command.equals(""))
                            //System.out.println("Got a command: "+command);
                        switch (command) {
                            case "glClear":
                                oArgs = (Object[]) argsObject;
                                mask = (int) oArgs[0];
                                //System.out.println(mask);
                                GL20.glClear(mask);
                                break;

                            case "glGenTextures":
                                int[] textureIds = new int[1];
                                GL11.glGenTextures(textureIds);
                                out.writeObject(textureIds);
                                out.flush();
                                break;

                            case "glEnable":
                                oArgs = (Object[]) argsObject;
                                GL20.glEnable((int) oArgs[0]);
                                break;

                            case "glGenTexturesNoArgs":
                                out.writeObject(GL11.glGenTextures());
                                out.flush();
                                break;

                            case "glDisable":
                                oArgs = (Object[]) argsObject;
                                //System.out.println(capDisable);
                                GL20.glDisable((int) oArgs[0]);
                                break;

                            case "glMatrixMode":
                                oArgs = (Object[]) argsObject;
                                GL11.glMatrixMode((int) oArgs[0]);
                                break;

                            case "glDepthMask":
                                oArgs = (Object[]) argsObject;
                                GL11.glDepthMask((boolean)oArgs[0]);
                                break;

                            case "glLoadIdentity":
                                GL11.glLoadIdentity();
                                break;

                            case "glPolygonMode":
                                Object[] polyArgs = (Object[]) argsObject;
                                int face = (int) polyArgs[0];
                                mode = (int) polyArgs[1];
                                GL11.glPolygonMode(face, mode);
                                break;

                            case "glCullFace":
                                oArgs =  (Object[]) argsObject;
                                mode = (int) oArgs[0];
                                GL11.glCullFace(mode);
                                break;

                            case "glBlendFunc":
                                Object[] blendArgs = (Object[]) argsObject;
                                int sfactor = (int) blendArgs[0];
                                int dfactor = (int) blendArgs[1];
                                GL11.glBlendFunc(sfactor, dfactor);
                                break;

                            case "glEnableClientState":
                                Object[] enableClientStateArgs = (Object[]) argsObject;
                                cap = (int) enableClientStateArgs[0];
                                GL11.glEnableClientState(cap);
                                break;

                            case "glGetInteger":
                                pname = (int)((Object[]) argsObject)[0];
                                int result = GL11.glGetInteger(pname);
                                out.writeObject(result);
                                out.flush();
                                break;

                            case "glAlphaFunc":
                                Object[] alphaArgs = (Object[]) argsObject;
                                int func = (int) alphaArgs[0];
                                float ref = (float) alphaArgs[1];
                                GL11.glAlphaFunc(func, ref);
                                break;

                            case "glDrawBuffer":
                                oArgs = (Object[]) argsObject;
                                mode = (int) oArgs[0];
                                GL11.glDrawBuffer(mode);
                                break;

                            case "glColor3f":
                                Object[] colorArgs = (Object[]) argsObject;
                                float red = (float) colorArgs[0];
                                float green = (float) colorArgs[1];
                                float blue = (float) colorArgs[2];
                                GL11.glColor3f(red, green, blue);
                                break;

                            case "glActiveTexture":
                                bindTextureArgs = (Object[]) argsObject;
                                GL20.glActiveTexture((int)bindTextureArgs[0]);
                                break;

                            case "glBindTexture":
                                bindTextureArgs = (Object[]) argsObject;
                                target = (int) bindTextureArgs[0];
                                texture = (int) bindTextureArgs[1];
                                GL11.glBindTexture(target, texture);
                                break;

                            case "glTexEnvi":
                                Object[] texEnviArgs = (Object[]) argsObject;
                                target = (int) texEnviArgs[0];
                                pname = (int) texEnviArgs[1];
                                param = (int) texEnviArgs[2];
                                GL11.glTexEnvi(target, pname, param);
                                break;

                            case "glReadBuffer":
                                oArgs = (Object[]) argsObject;
                                mode = (int)oArgs[0];
                                GL11.glReadBuffer(mode);
                                break;

                            case "glScissor":
                                Object[] scissorArgs = (Object[]) argsObject;
                                x = (int) scissorArgs[0];
                                y = (int) scissorArgs[1];
                                width = (int) scissorArgs[2];
                                height = (int) scissorArgs[3];
                                GL11.glScissor(x, y, width, height);
                                break;

                            case "glGenLists":
                                Object[] glGenListsArgs = (Object[]) argsObject;
                                int range = (int) glGenListsArgs[0];
                                int listID = GL11.glGenLists(range);
                                out.write(listID);
                                out.flush();
                                // Handle listID appropriately here (send back to client, etc.)
                                break;

                            case "glTexGeni":
                                Object[] texGeniArgs = (Object[]) argsObject;
                                int coord = (int) texGeniArgs[0];
                                int pnameq = (int) texGeniArgs[1];
                                int paramq = (int) texGeniArgs[2];
                                GL11.glTexGeni(coord, pnameq, paramq);
                                break;


                            case "glCallList":
                                Object[] listArgs = (Object[]) argsObject;
                                int list = (int) listArgs[0];
                                GL11.glCallList(list);
                                break;



                            case "glColor3ub":
                                Object[] colorArgs3 = (Object[]) argsObject;
                                byte red3 = (byte) colorArgs3[0];
                                byte green3 = (byte) colorArgs3[1];
                                byte blue3 = (byte) colorArgs3[2];
                                GL11.glColor3ub(red3, green3, blue3);
                                break;


                            case "glGenBuffers":
                                int bufferID = GL15.glGenBuffers();
                                out.writeObject(bufferID);
                                out.flush();
                                break;

                            case "glGenBuffersInt":
                                IntBuffer serverBuffer = BufferUtils.createIntBuffer(1);
                                GL15.glGenBuffers(serverBuffer);
                                bufferID = serverBuffer.get(0);
                                out.writeObject(new int[]{bufferID});
                                out.flush();
                                break;

                            case "glNewList":
                                Object[] glNewListArgs = (Object[]) argsObject;
                                if (glNewListArgs.length < 2) {
                                    System.out.println("Missing arguments for glNewList");
                                    break;
                                }
                                list = (int) glNewListArgs[0];
                                mode = (int) glNewListArgs[1];
                                GL11.glNewList(list, mode);
                                int errorCode = GL11.glGetError();
                                if (errorCode != GL11.GL_NO_ERROR) {
                                    System.out.println("OpenGL Error: " + errorCode);
                                }
                                break;


                            case "glTexCoord2f":
                                oArgs = (Object[]) argsObject;
                                float s = (float) oArgs[0];
                                float t = (float) oArgs[1];
                                GL11.glTexCoord2f(s, t);
                                break;

                            case "glVertex2f":
                                oArgs = (Object[]) argsObject;
                                float x1 = (float) oArgs[0];
                                float y1 = (float) oArgs[1];
                                GL11.glVertex2f(x1, y1);
                                break;

                            case "glEnd":
                                GL11.glEnd();
                                break;

                            case "glEndList":
                                GL11.glEndList();
                                break;


                            case "glBegin":
                                Object[] glBeginArgs = (Object[]) argsObject;
                                if (glBeginArgs.length < 1) {
                                    System.out.println("Missing arguments for glBegin");
                                    break;
                                }
                                mode = (int) glBeginArgs[0];
                                GL11.glBegin(mode);
                                errorCode = GL11.glGetError();
                                if (errorCode != GL11.GL_NO_ERROR) {
                                    System.out.println("OpenGL Error: " + errorCode);
                                }
                                break;


                            case "glPushAttrib":
                                oArgs = (Object[]) argsObject;
                                mask = (int)oArgs[0];
                                GL11.glPushAttrib(mask);
                                break;

                            case "glRasterPos2i":
                                oArgs = (Object[]) argsObject;
                                x = (int) oArgs[0];
                                y = (int) oArgs[1];
                                GL11.glRasterPos2i(x, y);
                                break;

                            case "glCopyPixels":
                                Object[] copyPixelsArgs = (Object[]) argsObject;
                                x = (int) copyPixelsArgs[0];
                                y = (int) copyPixelsArgs[1];
                                width = (int) copyPixelsArgs[2];
                                height = (int) copyPixelsArgs[3];
                                type = (int) copyPixelsArgs[4];
                                GL11.glCopyPixels(x, y, width, height, type);
                                break;

                            case "glPopAttrib":
                                GL11.glPopAttrib();
                                break;

                            case "glOrtho":
                                Object[] orthoArgs = (Object[]) argsObject;
                                double left = (double) orthoArgs[0];
                                double right = (double) orthoArgs[1];
                                double bottom = (double) orthoArgs[2];
                                double top = (double) orthoArgs[3];
                                double near = (double) orthoArgs[4];
                                double far = (double) orthoArgs[5];
                                GL11.glOrtho(left, right, bottom, top, near, far);
                                break;

                            case "glTranslatef":
                                Object[] translateArgs = (Object[]) argsObject;
                                float x2 = (float) translateArgs[0];
                                float y2 = (float) translateArgs[1];
                                float z2 = (float) translateArgs[2];
                                GL11.glTranslatef(x2, y2, z2);
                                break;

                            case "glDisableClientState":
                                Object[] clientStateArgs = (Object[]) argsObject;
                                cap = (int) clientStateArgs[0];
                                GL11.glDisableClientState(cap);
                                break;

                            case "glDeleteTextures":
                                Object[] textureArgs = (Object[]) argsObject;
                                int[] textures = (int[]) textureArgs[0];
                                GL11.glDeleteTextures(textures);
                                break;

                            case "glTexImage3D":
                                Object[] texImage3DArgs = (Object[]) argsObject;
                                target = (int) texImage3DArgs[0];
                                level = (int) texImage3DArgs[1];
                                int internalFormat = (int) texImage3DArgs[2];
                                width = (int) texImage3DArgs[3];
                                height = (int) texImage3DArgs[4];
                                int depth = (int) texImage3DArgs[5];
                                border = (int) texImage3DArgs[6];
                                format = (int) texImage3DArgs[7];
                                type = (int) texImage3DArgs[8];
                                ByteBuffer pixels = (ByteBuffer) texImage3DArgs[9];

                                GL12.glTexImage3D(target, level, internalFormat, width, height, depth, border, format, type, pixels);
                                break;

                            case "glTexParameteri":
                                Object[] texParamArgs = (Object[]) argsObject;
                                target = (int) texParamArgs[0];
                                pname = (int) texParamArgs[1];
                                param = (int) texParamArgs[2];

                                GL11.glTexParameteri(target, pname, param);
                                break;

                            case "glfwPollEvents":
                                GLFW.glfwPollEvents();
                                break;

                            case "glfwSwapBuffers":
                                GLFW.glfwSwapBuffers(window);
                                break;

                            case "glTexImage2D":
                                Object[] texImage2DArgs = (Object[]) argsObject;
                                target = (int) texImage2DArgs[0];
                                level = (int) texImage2DArgs[1];
                                int internalformat = (int) texImage2DArgs[2];
                                width = (int) texImage2DArgs[3];
                                height = (int) texImage2DArgs[4];
                                border = (int) texImage2DArgs[5];
                                format = (int) texImage2DArgs[6];
                                type = (int) texImage2DArgs[7];
                                Object bufferObject = texImage2DArgs[8];

                                if (bufferObject instanceof byte[]) {
                                    byte[] pixelBytes = (byte[]) bufferObject;
                                    ByteBuffer byteBuffer = ByteBuffer.wrap(pixelBytes);
                                    //GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, byteBuffer);
                                } else if (bufferObject instanceof int[]) {
                                    int[] pixelInts = (int[]) bufferObject;
                                    // Debug print the server buffer after receiving
                                    //System.out.println("Server received byte buffer: " + Arrays.toString(pixelInts));
                                    IntBuffer intBuffer = IntBuffer.wrap(pixelInts);
                                    //GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, intBuffer);
                                } else {
                                    // Handle an unsupported buffer type
                                    System.out.println("Unsupported buffer type");
                                }

                                errorCode = GL11.glGetError();
                                if (errorCode != GL11.GL_NO_ERROR) {
                                    System.out.println("OpenGL Error: " + errorCode);
                                }
                                break;

                            case "glDrawElementsWrapper":
                                Object[] drawElementsArgs = (Object[]) argsObject;
                                mode = (int) drawElementsArgs[0];
                                int count = (int) drawElementsArgs[1];
                                type = (int) drawElementsArgs[2];
                                byte[] byteArray = (byte[]) drawElementsArgs[3];

                                ByteBuffer buffer = MemoryUtil.memAlloc(byteArray.length).put(byteArray).flip();
                                long pointer = MemoryUtil.memAddress(buffer);

                                GL11.glDrawElements(mode, count, type, pointer);

                                MemoryUtil.memFree(buffer);
                                break;

                            case "glClearDepth":
                                oArgs = (Object[]) argsObject;
                                GL11.glClearDepth((double) oArgs[0]);
                                break;

                            case "glLoadMatrixf":
                                Object[] matrixArgs = (Object[]) argsObject;
                                float[] array = (float[]) matrixArgs[0];

                                FloatBuffer buffer2 = ByteBuffer.allocateDirect(array.length * 4).asFloatBuffer();
                                buffer2.put(array);
                                buffer2.flip();

                                GL11.glLoadMatrixf(buffer2);
                                break;

                            case "glRotatef":
                                Object[] rotateArgs = (Object[]) argsObject;
                                float angle = (float) rotateArgs[0];
                                float x3 = (float) rotateArgs[1];
                                float y3 = (float) rotateArgs[2];
                                float z3 = (float) rotateArgs[3];

                                GL11.glRotatef(angle, x3, y3, z3);
                                break;

                            case "glLineWidth":
                                float width3 = (float) ((Object[]) argsObject)[0];
                                GL11.glLineWidth(width3);
                                break;

                            case "glViewport":
                                Object[] viewportArgs = (Object[]) argsObject;
                                x = (int) viewportArgs[0];
                                y = (int) viewportArgs[1];
                                width = (int) viewportArgs[2];
                                height = (int) viewportArgs[3];

                                GL11.glViewport(x, y, width, height);
                                break;

                            case "glDepthFunc":
                                oArgs = (Object[]) argsObject;
                                GL11.glDepthFunc((int) oArgs[0]);
                                break;

                            case "glClearColor":
                                oArgs = (Object[]) argsObject;
                                float red1 = (float) oArgs[0];
                                float green2 = (float) oArgs[1];
                                float blue34 = (float) oArgs[2];
                                float alpha4 = (float) oArgs[3];
                                GL20.glClearColor(red1, green2, blue34, alpha4);
                                break;

                            case "glShadeModel":
                                oArgs = (Object[]) argsObject;
                                GL11.glShadeModel((int)oArgs[0]);
                                break;

                            default:
                                System.out.println("Unrecognized command: " + command);
                        }

                        int error = GL20.glGetError();
                        if(error != 0){
                            System.out.println("Error " + error);
                        }
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
