package org.jzy3d.plot3d.primitives;

import org.jzy3d.colors.Color;

public interface IWireframeable {

    /**Set the wireframe color.*/
    public void setWireframeColor(Color color);

    /**Set the wireframe display status to on or off.*/
    public void setWireframeDisplayed(boolean status);

    /**Set the wireframe width.*/
    public void setWireframeWidth(float width);

    /**Set the face display status to on or off.*/
    public void setFaceDisplayed(boolean status);

    /**Get the wireframe color.*/
    public Color getWireframeColor();

    /**Get the wireframe display status to on or off.*/
    public boolean getWireframeDisplayed();

    /**Get the wireframe width.*/
    public float getWireframeWidth();

    /**Get the face display status to on or off.*/
    public boolean getFaceDisplayed();

}