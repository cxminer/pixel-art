package cxminer.apps.pixelart.canvas.action;

import java.awt.*;

/**
 * Created by erik on 1.3.2017.
 */
public interface ICanvasActionDrawable {

    void paint(Graphics2D g2, int startx, int starty, int basew, int baseh);

}
