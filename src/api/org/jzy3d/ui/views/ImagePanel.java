package org.jzy3d.ui.views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
    public ImagePanel() {
        this((Image)null);
    }
    
	public ImagePanel(String img) {
		this(new ImageIcon(img).getImage());
	}

	public ImagePanel(Image img) {
		this.img = img;
		if(img!=null){
    		Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));
    		setPreferredSize(size);
    		setMinimumSize(size);
    		setMaximumSize(size);
    		setSize(size);
		}
		setLayout(null);
		
		addComponentListener(new ComponentAdapter(){
		    public void componentResized(ComponentEvent e){
		        update();
		    }
		});
	}
	
	public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
        update();
    }

    public void paintComponent(Graphics g) {
        if(simg!=null)
            g.drawImage(simg, 0, 0, null);
	}
	
	protected void update(){
	    if(img!=null){
            width = img.getWidth(null);
            height = img.getHeight(null);

            float w = getWidth();
            float h = w * (height/width);
            
            setPreferredSize(new Dimension((int)w, (int)h));
            repaint();
            
	        simg = img.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
	        swidth = simg.getWidth(null);
	        sheight = simg.getHeight(null);
	    }
	}
	
	protected Image img;
	protected Image simg;
	protected float width;
	protected float height;
    protected float swidth;
    protected float sheight;
	private static final long serialVersionUID = 1L;
}
