package cxminer.apps.pixelart.canvas.action;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Erik on 1. 03. 2017.
 */
public class CanvasTool {

    private ArrayList<Point> points = new ArrayList<>();
    private ArrayList<Color> colors = new ArrayList<>();

    public void savePoint(Point point, Color color) {
        for (int i = 0, max = points.size(); i < max; i++) {
            Point p = points.get(i);
            if (p.x == point.x && p.y == point.y) {
                return;
            }
        }
        points.add(point);
        colors.add(color);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    public void drawPixel(Graphics2D g2, int startx, int starty, int basew, int baseh, int x, int y, Color color) {
        g2.setPaint(color);
        g2.fillRect(startx + x * basew, starty + y * baseh, basew, baseh);
    }

}
