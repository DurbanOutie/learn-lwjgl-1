package engine;

public abstract class Scene{


    Camera camera;

    public Scene(){
    }

    public void init(){}

    public abstract void update(float dt);

}
