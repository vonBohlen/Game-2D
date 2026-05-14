package org.Game2D.engine.graphics;

import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glFlush;

public class OpenGlTest {

    public static void  main(String[] args) {
        GLFWManager.initGLFW();
        Window window = new Window("Test");
        loop(window);
    }

    private static void loop(Window window) {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0.0, 640, 320, 0.0, -1.0, 1.0);
        glMatrixMode(GL_MODELVIEW);


        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        float r = 0.75f, g = 0.25f, b = 0;
        float dc = 0.01f;
//        // Run the rendering loop until the user has attempted to close
//        // the window or has pressed the ESCAPE key.
        while (!glfwWindowShouldClose(window.getWindowID())) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);// clear the framebuffer
            if (r + dc > 1 || g + dc > 1) {
                r = 0;
                g = 0;
            }
            r += dc;
            g += dc;
            glClearColor(r, g, b, 0.0f);


            glBegin(GL_POLYGON);
            glColor3f(r,g,0.5f);
            glVertex2i(100, 100);
            glVertex2i(100, 200);
            //glColor3f(1,0.5f,0);
            glVertex2i(200, 200);
            glVertex2i(200, 100);
            glEnd();
            glFlush();

            glfwSwapBuffers(window.getWindowID()); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

}
