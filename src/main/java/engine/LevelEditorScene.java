package engine;

import renderer.Shader;
import renderer.Texture;
import util.Time;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import org.joml.Vector2f;


public class LevelEditorScene extends Scene{


    private int vertexID, fragmentID, shaderProgram;
    private float[] vertexArray = {
        // position                              // color           // UV Co-ords
         600.0f,    0.0f, 0.0f,          1.0f, 0.0f, 0.0f, 1.0f,    1, 1, // Bottom right    0
           0.0f,  300.0f, 0.0f,          0.0f, 1.0f, 0.0f, 1.0f,    0, 0, // Top left        1
         600.0f,  300.0f, 0.0f,          1.0f, 0.0f, 1.0f, 1.0f,    1, 0, // Top right       2
           0.0f,    0.0f, 0.0f,          1.0f, 1.0f, 0.0f, 1.0f,    0, 1, // Bottom Left     3
    };

    // IMPORTANT: Counter Clockwise ??? Find out why.
    
    private int[] elementArray = {
        /*
         *    x(1)       y(2)
         *
         *
         *
         *    i(0)       j(3)
         *
         * y->x->i
         * i->j->y
         *
         * 2, 1, 0,
         * 0, 1, 3
         *
         *
         * ???????????
         *
         */
        2, 1, 0,
        0, 3, 1 
        
    };

    private int vaoID, vboID, eboID;
    private Shader defaultShader;

    private Texture testTexture;

    public LevelEditorScene(){
        init();
    }

    @Override
    public void init(){




        camera = new Camera(new Vector2f());

        
        // Compile and link shaders
        
        defaultShader = new Shader("./build/resources/assets/default.glsl");

        defaultShader.compile();
        
        testTexture = new Texture("./build/resources/assets/images/testImage.png");

        // Generate VAO, VBO, EBO and send to GPU

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        // Create a float buffer of vertices
        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
        vertexBuffer.put(vertexArray).flip();
        
        // Create VBO and upload vertex Buffer
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        // Create the indices and upload
        IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length);
        elementBuffer.put(elementArray).flip();
        
        // Create VBO and upload vertex Buffer
        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);

        // Add vertex attrib pointers
        int positionSize = 3;
        int colorSize = 4;
        int uvSize = 2;
        int vertexSizeBytes = (positionSize + colorSize + uvSize) * Float.BYTES;
        glVertexAttribPointer(0, positionSize, GL_FLOAT, false, vertexSizeBytes, 0);
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, colorSize, GL_FLOAT, false, 
                vertexSizeBytes, positionSize * Float.BYTES);
        
        glEnableVertexAttribArray(1);


        glVertexAttribPointer(2, uvSize, GL_FLOAT, false, 
                vertexSizeBytes, (positionSize + colorSize) * Float.BYTES);

        glEnableVertexAttribArray(2);
    }

    @Override
    public void update(float dt){
        camera.position.x -= dt * 50;
        camera.position.y -= dt * 20;

        defaultShader.use();

        defaultShader.uploadTexture("TEX_SAMPLER", 0);
        glActiveTexture(GL_TEXTURE0);
        testTexture.bind();



        defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
        defaultShader.uploadMat4f("uView", camera.getViewMatrix());
        defaultShader.uploadFloat("uTime", Time.getTime());


        // Bind VAO
        glBindVertexArray(vaoID);

        // Enable vertex attrib pointers
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);

        // Unbind everything

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);

        glBindVertexArray(0);

        defaultShader.detach();



    }

}








