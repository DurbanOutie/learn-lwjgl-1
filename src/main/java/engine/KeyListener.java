package engine;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyListener{
    private static KeyListener instance;
    private static boolean keyPressed[] = new boolean[350];

    private KeyListener(){

    }

    public static void keyCallback(long window, int key, int scanccode, int action, int mods){
        if(action == GLFW_PRESS)
            keyPressed[key] = true; 
        else if(action == GLFW_RELEASE)
            keyPressed[key] = false;
    }

    public static KeyListener get(){
        if(instance == null)
            instance  = new KeyListener();
        return instance;
    }

    public static boolean isKeyPressed(int key){
        if(key < keyPressed.length)
            return keyPressed[key];
        return false;
    }

}
