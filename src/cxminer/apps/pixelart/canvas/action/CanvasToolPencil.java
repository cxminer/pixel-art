package cxminer.apps.pixelart.canvas.action;

import java.awt.*;

/**
 * Created by erik on 1.3.2017.
 */
public class CanvasToolPencil extends CanvasTool implements ICanvasActionDrawable {

    @Override
    public void paint(Graphics2D g2, int startx, int starty, int basew, int baseh) {
        for (int i = 0, max = getPoints().size(); i < max; i++) {
            Point point = getPoints().get(i);
            Color color = getColors().get(i);

            drawPixel(g2, startx, starty, basew, baseh, point.x, point.y, color);
        }
    }

}
