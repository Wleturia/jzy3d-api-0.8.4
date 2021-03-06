package net.letskit.redbook.second;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.ByteBuffer;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLJPanel;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import net.letskit.redbook.glskeleton;

import com.sun.opengl.util.BufferUtil;

/**
 * This program texture maps a checkerboard image onto two rectangles. This
 * program clamps the texture, if the texture coordinates fall outside 0.0 and
 * 1.0. If the s key is pressed, a texture subimage is used to alter the
 * original texture. If the r key is pressed, the original texture is restored.
 * 
 * @author Kiet Le (Java port)
 */
public class texsub extends glskeleton//
        implements GLEventListener//
        , KeyListener//
{
    private GLU glu;
    private KeyEvent key;
    //
    private static final int rgba = 4;
    private static final int checkImageWidth = 64;
    private static final int checkImageHeight = 64;
    private static final int subImageWidth = 16;
    private static final int subImageHeight = 16;
    // private byte checkImage[][][];
    // private byte otherImage[][][];
    private ByteBuffer checkImageBuf = //
    BufferUtil.newByteBuffer(checkImageWidth * checkImageHeight * rgba);
    private ByteBuffer subImageBuf = //
    BufferUtil.newByteBuffer(subImageWidth * subImageHeight * rgba);
    private int[] texName = new int[2];

    public texsub() {
    }

    public static void main(String[] args) {
        GLCapabilities caps = new GLCapabilities();
        GLJPanel canvas = new GLJPanel(caps);

        texsub demo = new texsub();
        canvas.addGLEventListener(demo);
        demo.setCanvas(canvas);
        demo.setDefaultListeners(demo);

//        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("texsub");
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(canvas);
        frame.setVisible(true);
        canvas.requestFocusInWindow();
    }

    public void init(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        glu = new GLU();
        //
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glShadeModel(GL.GL_FLAT);
        gl.glEnable(GL.GL_DEPTH_TEST);

        makeCheckImages();

        gl.glPixelStorei(GL.GL_UNPACK_ALIGNMENT, 1);

        gl.glGenTextures(2, texName, 0);
        gl.glBindTexture(GL.GL_TEXTURE_2D, texName[0]);
        gl
                .glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S,
                        GL.GL_REPEAT);
        gl
                .glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T,
                        GL.GL_REPEAT);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER,
                GL.GL_NEAREST);
        gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER,
                GL.GL_NEAREST);
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA, checkImageWidth,
                checkImageHeight, 0, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE,
                checkImageBuf);
    }

    public void display(GLAutoDrawable drawable) {
        GL gl = drawable.getGL();
        //
        if (key != null) {
            switch (key.getKeyCode()) {
            case KeyEvent.VK_S:
                gl.glBindTexture(GL.GL_TEXTURE_2D, texName[0]);
                gl.glTexSubImage2D(GL.GL_TEXTURE_2D, 0, 12, 44, subImageWidth,
                        subImageHeight, GL.GL_RGBA, GL.GL_UNSIGNED_BYTE,
                        subImageBuf);
                break;
            case KeyEvent.VK_R:
                gl.glBindTexture(GL.GL_TEXTURE_2D, texName[0]);
                gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGBA,
                        checkImageWidth, checkImageHeight, 0, GL.GL_RGBA,
                        GL.GL_UNSIGNED_BYTE, checkImageBuf);
                break;

            default:
                break;
            }
            key = null;
        }

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glEnable(GL.GL_TEXTURE_2D);
        gl.glTexEnvf(GL.GL_TEXTURE_ENV, GL.GL_TEXTURE_ENV_MODE, GL.GL_DECAL);
        gl.glBindTexture(GL.GL_TEXTURE_2D, texName[0]);

        gl.glBegin(GL.GL_QUADS);
        gl.glTexCoord2d(0.0, 0.0);
        gl.glVertex3d(-2.0, -1.0, 0.0);
        gl.glTexCoord2d(0.0, 1.0);
        gl.glVertex3d(-2.0, 1.0, 0.0);
        gl.glTexCoord2d(1.0, 1.0);
        gl.glVertex3d(0.0, 1.0, 0.0);
        gl.glTexCoord2d(1.0, 0.0);
        gl.glVertex3d(0.0, -1.0, 0.0);

        gl.glTexCoord2d(0.0, 0.0);
        gl.glVertex3d(1.0, -1.0, 0.0);
        gl.glTexCoord2d(0.0, 1.0);
        gl.glVertex3d(1.0, 1.0, 0.0);
        gl.glTexCoord2d(1.0, 1.0);
        gl.glVertex3d(2.41421, 1.0, -1.41421);
        gl.glTexCoord2d(1.0, 0.0);
        gl.glVertex3d(2.41421, -1.0, -1.41421);
        gl.glEnd();
        gl.glFlush();

        gl.glDisable(GL.GL_TEXTURE_2D);
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
        GL gl = drawable.getGL();
        //
        gl.glViewport(0, 0, w, h);
        gl.glMatrixMode(GL.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60.0, (float) w / (float) h, 1.0, 30.0);
        gl.glMatrixMode(GL.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glTranslated(0.0, 0.0, -3.6);
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
            boolean deviceChanged) {
    }

    private void makeCheckImages() {
        byte c = 0x00;

        for (int i = 0; i < checkImageWidth; i++) {
            for (int j = 0; j < checkImageHeight; j++) {

                c = (byte) ((((byte) ((i & 0x8) == 0 ? 0x00 : 0xff)//
                ^ (byte) ((j & 0x8) == 0 ? 0x00 : 0xff))));
                // checkImage[i][j][0] = (byte) c;
                // checkImage[i][j][1] = (byte) c;
                // checkImage[i][j][2] = (byte) c;
                checkImageBuf.put((byte) c);
                checkImageBuf.put((byte) c);
                checkImageBuf.put((byte) c);
                checkImageBuf.put((byte) 0xff);
            }
        }

        for (int i = 0; i < subImageHeight; i++) {
            for (int j = 0; j < subImageWidth; j++) {
                c = (byte) ((((byte) ((i & 0x4) == 0 ? 0x00 : 0xff)//
                ^ (byte) ((j & 0x4) == 0 ? 0x00 : 0xff))));
                subImageBuf.put((byte) c);
                subImageBuf.put((byte) 0);
                subImageBuf.put((byte) 0);
                subImageBuf.put((byte) 0xff);
            }
        }
        checkImageBuf.rewind();
        subImageBuf.rewind();
    }

    public void keyTyped(KeyEvent key) {
    }

    public void keyPressed(KeyEvent key) {
        this.key = key;
        switch (key.getKeyCode()) {
        case KeyEvent.VK_ESCAPE:
            System.exit(0);
        default:
            break;
        }
        super.refresh();
    }

    public void keyReleased(KeyEvent key) {
    }
}
