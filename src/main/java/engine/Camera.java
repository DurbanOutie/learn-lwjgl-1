package engine;

import org.joml.Matrix4f;

public class Camera{

    private Matrix4f projectionMatrix, viewMatrix;
    private Vector2f position;

    public Camera(Vector2f position){
        this.position = position;
        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
    }



    
}
