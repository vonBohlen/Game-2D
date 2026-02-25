package org.Game2D.engine.graphics.opengl;

import lombok.Getter;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;

import javax.annotation.Nullable;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    @Getter
    private long windowID;

    private void init() {
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(windowID, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowID);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(windowID);
    }

    public Window(String title) {
        if (!GLFWManager.isGLFW_INIT()) return;

        GLFWVidMode vidMode = GLFWManager.getVidMode();
        assert vidMode != null;
        windowID = glfwCreateWindow(vidMode.width(), vidMode.height(), title, NULL, NULL);
        if (windowID == NULL) throw new RuntimeException("Failed to create the GLFW window");

        init();
        center();
    }

    public Window(int width, int height, String title) {
        if (!GLFWManager.isGLFW_INIT()) return;

        windowID = glfwCreateWindow(width, height, title, NULL, NULL);
        if (windowID == NULL) throw new RuntimeException("Failed to create the GLFW window");

        init();
        center();
    }

    public void setTitle(String name) {
        if (!GLFWManager.isGLFW_INIT()) return;
        glfwSetWindowTitle(windowID, name);
    }

    public @Nullable String getTitle() {
        if (!GLFWManager.isGLFW_INIT()) return null;
        return glfwGetWindowTitle(windowID);
    }

    public void setFullscreen() {
        GLFWVidMode vidMode = GLFWManager.getVidMode();
        assert vidMode != null;
        glfwSetWindowSize(windowID, vidMode.width(), vidMode.height());
    }

    public void setSize(int with, int height) {
        if (!GLFWManager.isGLFW_INIT()) return;
        glfwSetWindowSize(windowID, with, height);
    }

    public void setDecorations(boolean enableDecorations) {
        if (!GLFWManager.isGLFW_INIT()) return;
        if (enableDecorations) glfwWindowHint(GLFW_DECORATED, GLFW_TRUE);
        else glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
    }

    public void center() {
        GLFWVidMode vidMode = GLFWManager.getVidMode();
        assert vidMode != null;
        glfwSetWindowPos(windowID, (vidMode.width() - getWidth()) / 2, (vidMode.height() - getHeight()) / 2);
    }

    public int getWidth() {
        if (!GLFWManager.isGLFW_INIT()) return 0;
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        glfwGetFramebufferSize(windowID, width, height);
        return width.get(0);
    }

    public int getHeight() {
        if (!GLFWManager.isGLFW_INIT()) return 0;
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        glfwGetFramebufferSize(windowID, width, height);
        return height.get(0);
    }

    public void destroy() {
        if (windowID == NULL) return;
        glfwFreeCallbacks(windowID);
        glfwDestroyWindow(windowID);
        windowID = NULL;
    }

}
