package org.jzy3d.plot3d.rendering.textures;

import java.io.File;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GLException;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureCoords;
import com.sun.opengl.util.texture.TextureIO;

public class SharedTexture {
    protected SharedTexture() {
        this.texture = null;
    }
    
    public SharedTexture(String file) {
        this.texture = null;
        this.file = file;
    }

    public Texture getTexture(GL gl) {
        if (texture == null)
            mount();
        else { // execute onmount even if we did not mount
               // if( action != null )
               // action.execute();
        }
        return texture;
    }

    /** A GL context MUST be current. */
    public void mount() {
        try {
            load(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        coords = texture.getImageTexCoords();
        halfWidth = texture.getWidth() / 2;
        halfHeight = texture.getHeight() / 2;
        // System.out.println("mount texture: " + file + " halfWidth=" +
        // halfWidth + " halfHeight=" + halfHeight);
    }

    protected void load(String fileName) throws GLException, IOException {
        texture = TextureIO.newTexture(new File(fileName), false);
        texture.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_NEAREST);
        texture.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_NEAREST);
    }

    public String getFile() {
        return file;
    }

    public TextureCoords getCoords() {
        return coords;
    }

    public float getHalfWidth() {
        return halfWidth;
    }

    public float getHalfHeight() {
        return halfHeight;
    }

    /*
     * public BoundingBox3d getBounds(PlaneAxis plane){
     * 
     * }
     */

    protected Texture texture;
    protected String file;
    protected TextureCoords coords;
    protected float halfWidth;
    protected float halfHeight;
}
