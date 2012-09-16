package org.jzy3d.plot3d.rendering.canvas;



import java.awt.event.ComponentListener;
import java.awt.event.FocusListener;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyListener;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;

import javax.media.opengl.GL;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLContext;
import javax.media.opengl.GLDrawableFactory;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLException;
import javax.media.opengl.GLPbuffer;

import org.jzy3d.plot3d.rendering.scene.Scene;
import org.jzy3d.plot3d.rendering.view.Renderer3d;
import org.jzy3d.plot3d.rendering.view.View;

/**
 * Offscreen canvas for use in headless environments.
 *
 * Currently, no axes are drawn and output size is either
 * set using the specific constructor, or has a default size
 * of 800x800 pixels.
 *
 * @author Martin Pernollet
 * @author Nils Hoffmann
 */
public class OffscreenCanvas implements ICanvas, GLPbuffer {

    /** Initialize a Canvas3d attached to a {@link Scene}, with a given rendering {@link Quality}.*/
    public OffscreenCanvas(Scene scene, Quality quality, int width, int height) {
        if (!GLDrawableFactory.getFactory().canCreateGLPbuffer()) 
            throw new RuntimeException("Could not create GLPBuffer");
        this.scene = scene;
        this.quality = quality;
        this.defaultWidth = width;
        this.defaultHeight = height;
        view = scene.newView(this, this.quality);
        initPBuffer(width, height);
    }

    /** Initialize a Canvas3d attached to a {@link Scene}, with a given rendering {@link Quality}.*/
    public OffscreenCanvas(Scene scene, Quality quality) {
        this(scene, quality, 800, 600);
    }

    protected void initPBuffer(int width, int height) {
        if (glpBuffer != null) {
            dispose();
        }
        GLCapabilities caps = org.jzy3d.global.Settings.getInstance().getGLCapabilities();
        caps.setDoubleBuffered(false);
        glpBuffer = GLDrawableFactory.getFactory().createGLPbuffer(caps, null, width, height, null);
        renderer = new Renderer3d(view);
        glpBuffer.addGLEventListener(renderer);
        renderer.reshape(null, 0, 0, width, height);
        view.updateBounds();
    }

    public void swapBuffers() throws GLException {
        glpBuffer.swapBuffers();
    }

    public void setSize(int i, int i1) {
        throw new UnsupportedOperationException("Please create a new OffscreenCanvas with different size attributes!");
        //resizing does not work on JOGL side, so reinitialize
        //glpBuffer.setSize(i, i1);
//        view = scene.newView(this, quality);
//        initPBuffer(i, i1);
    }

    public void setRealized(boolean bln) {
        glpBuffer.setRealized(bln);
    }

    public int getWidth() {
        return glpBuffer.getWidth();
    }

    public int getHeight() {
        return glpBuffer.getHeight();
    }

    public GLCapabilities getChosenGLCapabilities() {
        return glpBuffer.getChosenGLCapabilities();
    }

    public GLContext createContext(GLContext glc) {
        return glpBuffer.createContext(glc);
    }

    public void removePropertyChangeListener(String string, PropertyChangeListener pl) {
        glpBuffer.removePropertyChangeListener(string, pl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pl) {
        glpBuffer.removePropertyChangeListener(pl);
    }

    public void removeMouseWheelListener(MouseWheelListener ml) {
        glpBuffer.removeMouseWheelListener(ml);
    }

    public void removeMouseMotionListener(MouseMotionListener ml) {
        glpBuffer.removeMouseMotionListener(ml);
    }

    public void removeMouseListener(MouseListener ml) {
        glpBuffer.removeMouseListener(ml);
    }

    public void removeKeyListener(KeyListener kl) {
        glpBuffer.removeKeyListener(kl);
    }

    public void removeInputMethodListener(InputMethodListener il) {
        glpBuffer.removeInputMethodListener(il);
    }

    public void removeHierarchyListener(HierarchyListener hl) {
        glpBuffer.removeHierarchyListener(hl);
    }

    public void removeHierarchyBoundsListener(HierarchyBoundsListener hl) {
        glpBuffer.removeHierarchyBoundsListener(hl);
    }

    public void removeFocusListener(FocusListener fl) {
        glpBuffer.removeFocusListener(fl);
    }

    public void removeComponentListener(ComponentListener cl) {
        glpBuffer.removeComponentListener(cl);
    }

    public void addPropertyChangeListener(String string, PropertyChangeListener pl) {
        glpBuffer.addPropertyChangeListener(string, pl);
    }

    public void addPropertyChangeListener(PropertyChangeListener pl) {
        glpBuffer.addPropertyChangeListener(pl);
    }

    public void addMouseWheelListener(MouseWheelListener ml) {
        glpBuffer.addMouseWheelListener(ml);
    }

    public void addMouseMotionListener(MouseMotionListener ml) {
        glpBuffer.addMouseMotionListener(ml);
    }

    public void addMouseListener(MouseListener ml) {
        glpBuffer.addMouseListener(ml);
    }

    public void addKeyListener(KeyListener kl) {
        glpBuffer.addKeyListener(kl);
    }

    public void addInputMethodListener(InputMethodListener il) {
        glpBuffer.addInputMethodListener(il);
    }

    public void addHierarchyListener(HierarchyListener hl) {
        glpBuffer.addHierarchyListener(hl);
    }

    public void addHierarchyBoundsListener(HierarchyBoundsListener hl) {
        glpBuffer.addHierarchyBoundsListener(hl);
    }

    public void addFocusListener(FocusListener fl) {
        glpBuffer.addFocusListener(fl);
    }

    public void addComponentListener(ComponentListener cl) {
        glpBuffer.addComponentListener(cl);
    }

    public void setGL(GL gl) {
        glpBuffer.setGL(gl);
    }

    public void setAutoSwapBufferMode(boolean bln) {
        glpBuffer.setAutoSwapBufferMode(bln);
    }

    public void repaint() {
        glpBuffer.repaint();
    }

    public void removeGLEventListener(GLEventListener gl) {
        glpBuffer.removeGLEventListener(gl);
    }

    public GL getGL() {
        return glpBuffer.getGL();
    }

    public GLContext getContext() {
        return glpBuffer.getContext();
    }

    public boolean getAutoSwapBufferMode() {
        return glpBuffer.getAutoSwapBufferMode();
    }

    public void display() {
        glpBuffer.display();
    }

    public void addGLEventListener(GLEventListener gl) {
        glpBuffer.addGLEventListener(gl);
    }

    public void releaseTexture() {
        glpBuffer.releaseTexture();
    }

    public int getFloatingPointMode() {
        return glpBuffer.getFloatingPointMode();
    }

    public void destroy() {
        glpBuffer.destroy();
    }

    public void bindTexture() {
        glpBuffer.bindTexture();
    }

    @Override
    public void dispose() {
        glpBuffer.getContext().destroy();
        glpBuffer.destroy();
    }

    @Override
    public void forceRepaint() {
        glpBuffer.display();
    }

    @Override
    public BufferedImage screenshot() {
        renderer.nextDisplayUpdateScreenshot();
        glpBuffer.display();
        return renderer.getLastScreenshot();
    }

    /*********************************************************/
    /** Provide a reference to the View that renders into this canvas.*/
    public View getView() {
        return view;
    }

    /** Provide the actual renderer width for the open gl camera settings,
     * which is obtained after a resize event.*/
    public int getRendererWidth() {
        return (renderer != null ? renderer.getWidth() : defaultWidth);
    }

    /** Provide the actual renderer height for the open gl camera settings,
     * which is obtained after a resize event.*/
    public int getRendererHeight() {
        return (renderer != null ? renderer.getHeight() : defaultHeight);
    }
    /*********************************************************/
    protected View view;
    protected Renderer3d renderer;
    protected GLPbuffer glpBuffer;
    protected final Scene scene;
    protected final Quality quality;
    protected int defaultWidth = 800;
    protected int defaultHeight = 800;
    //private GLContext glContext;
}
