package engine;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener{
    private static MouseListener instance;
    private static double xScroll, yScroll;
    private static double xPos, yPos, xLast, yLast;
    private static boolean mouseButtonPressed[] = new boolean[3];
    private static boolean isDragging;

    private MouseListener(){
        this.xScroll = 0.0;
        this.yScroll = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.xLast = 0.0;
        this.yLast = 0.0;
    }
    
    public static MouseListener get(){
        if(instance == null)
            instance = new MouseListener();
        return instance;
    }

    public static void mousePosCallback(long window, double xPos, double yPos){
        xLast = instance.xPos;
        yLast = instance.yPos;
        instance.xPos = xPos;
        instance.yPos = yPos;
        //TODO Better drag checking
        isDragging = mouseButtonPressed[0] || mouseButtonPressed[1] || mouseButtonPressed[2];
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods){
        if(button < mouseButtonPressed.length){
            if(action == GLFW_PRESS)
                mouseButtonPressed[button] = true;
            else if(action == GLFW_RELEASE){
                mouseButtonPressed[button] = false;
                isDragging = false;
            }
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset){
        xScroll = xOffset;
        yScroll = yOffset;
    }

    public static void endFrame(){
        xScroll = 0;
        yScroll = 0;
        xLast = xPos;
        yLast = yPos;
    }

    public static float getX(){
        return (float)xPos;
    }

    public static float getY(){
        return (float)yPos;
    }

    public static float getDx(){
        return (float)(xLast - xPos);
    }

    public static float getDy(){
        return (float)(yLast - yPos);
    }
    
    public static float getXScroll(){
        return (float)xScroll;
    }

    public static float getYScroll(){
        return (float)yScroll;
    }

    public static boolean isDragging(){
        return isDragging;
    }

    public static boolean mouseButtonDown(int button){
        if(button < mouseButtonPressed.length)
            return mouseButtonPressed[button];
        return false;
    }
}
