package renderer;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.io.File;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glUseProgram;

public class Shader{

    private int shaderProgramID;
    private String vertexSource;
    private String fragmentSource;
    private String filepath;



    public Shader(String filepath){
        this.filepath = filepath;
        try{


            String source = new String(Files.readAllBytes(Paths.get(filepath)));

        
            // TODO - Find Type names, find better way to do
            String[] split = source.split("(#type)( )+([a-zA-Z]+)");

            int index = source.indexOf("#type") + 6;
            int eol = source.indexOf("\n", index);
            String firstPattern = source.substring(index, eol).trim();

            index = source.indexOf("#type", eol) + 6;
            eol = source.indexOf("\n", index);
            String secondPattern = source.substring(index, eol).trim();

            if(firstPattern.equals("vertex")){
                vertexSource = split[1];
            }else if (firstPattern.equals("fragment")){
                fragmentSource = split[1];
            }else {
                throw new IOException("Unexpected Token");
            }
            
            if(secondPattern.equals("vertex")){
                vertexSource = split[2];
            }else if (secondPattern.equals("fragment")){
                fragmentSource = split[2];
            }else {
                throw new IOException("Unexpected Token");
            }



        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Failed to open shader file");
        }
        System.out.println("VertexSource: [" + vertexSource + "]");
        System.out.println("FragmentSource: [" + fragmentSource + "]");
    }

    public void compile(){

        int vertexID, fragmentID;

        vertexID = glCreateShader(GL_VERTEX_SHADER);
        //pass shader src code
        glShaderSource(vertexID, vertexSource);
        glCompileShader(vertexID);

        // Check for errors
        int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);

        if(success == GL_FALSE){
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            System.out.println("gl vertex shader compile broke!");
            System.out.println(glGetShaderInfoLog(vertexID, len));
            System.exit(success);
        }
        
        // load and compile fragment shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        //pass shader src code
        glShaderSource(fragmentID, fragmentSource);
        glCompileShader(fragmentID);

        // Check for errors
        success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);

        if(success == GL_FALSE){
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            System.out.println("gl fragment shader compile broke!");
            System.out.println(glGetShaderInfoLog(fragmentID, len));
            System.exit(success);
        }
        // link shaders and check for errors
        shaderProgramID = glCreateProgram();
        glAttachShader(shaderProgramID, vertexID);
        glAttachShader(shaderProgramID, fragmentID);
        glLinkProgram(shaderProgramID);

        // Check for errors
        success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);

        if(success == GL_FALSE){
            int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
            System.out.println("gl linking shader compile broke!");
            System.out.println(glGetProgramInfoLog(shaderProgramID, len));
            System.exit(success);
        }
    }





    public void use(){
        glUseProgram(shaderProgramID);
    }
    
    public void detach(){
        glUseProgram(0);
    }
}
