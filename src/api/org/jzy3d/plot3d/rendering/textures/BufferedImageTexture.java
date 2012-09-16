package org.jzy3d.plot3d.rendering.textures;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.media.opengl.GL;
import javax.media.opengl.GLException;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureCoords;
import com.sun.opengl.util.texture.TextureIO;

public class BufferedImageTexture extends SharedTexture {
    public BufferedImageTexture(BufferedImage image) {
        super();
        this.image = image;
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
            load(image);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        coords = texture.getImageTexCoords();
        halfWidth = texture.getWidth() / 2;
        halfHeight = texture.getHeight() / 2;
        // System.out.println("mount texture: " + file + " halfWidth=" +
        // halfWidth + " halfHeight=" + halfHeight);
    }

    protected void load(BufferedImage image) throws GLException, IOException {
        texture = TextureIO.newTexture(image, false);
        texture.setTexParameteri(GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR); // different from shared texture!
        texture.setTexParameteri(GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR);
    }

    /** returns null*/
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

    protected Texture texture;
    protected BufferedImage image;
    protected TextureCoords coords;
    protected float halfWidth;
    protected float halfHeight;
}
