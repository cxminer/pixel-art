package cxminer.apps.pixelart.canvas;

import cxminer.apps.pixelart.canvas.action.CanvasTool;
import cxminer.apps.pixelart.canvas.action.CanvasToolPencil;
import cxminer.apps.pixelart.canvas.action.ICanvasActionDrawable;
import cxminer.apps.pixelart.frame.FrameMain;
import cxminer.apps.pixelart.toolbar.ToolbarColors;
import cxminer.apps.pixelart.toolbar.ToolbarHistory;
import cxminer.apps.pixelart.toolbar.ToolbarTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Erik on 1. 03. 2017.
 */
public class CanvasPane extends JDesktopPane implements MouseWheelListener, MouseListener, MouseMotionListener {

    private double scale = 1;

    private CanvasTool action = null;
    private ArrayList<CanvasTool> history = new ArrayList<>();
    private CanvasSettings settings;
    private BufferedImage image;

    private ToolbarTools toolbarTools;
    private ToolbarColors toolbarColors;
    private ToolbarHistory toolbarHistory;

    public CanvasPane() {
        super();
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        createToolbars();
    }

    private void createToolbars() {
        toolbarTools = new ToolbarTools(this);
        toolbarTools.setLocation(0, 0);
        add(toolbarTools);

        toolbarColors = new ToolbarColors();
        toolbarColors.setLocation(0, 0);
        add(toolbarColors);

        toolbarHistory = new ToolbarHistory();
        toolbarHistory.setLocation(0, 0);
        add(toolbarHistory);
    }

    private void cleanupCanvas() {
        action = null;
        history.clear();
        settings = null;
        image = null;
    }

    public void canvasNew(CanvasSettings settings) {
        cleanupCanvas();
        this.settings = settings;
        determineScale();
        repaint();
    }

    public void canvasOpen(BufferedImage image) {
        cleanupCanvas();
        this.settings = new CanvasSettings(image.getWidth(), image.getHeight());
        this.image = image;
        determineScale();
        repaint();
    }

    public void setActiveTools() {

    }

    private void determineScale() {
        int maxw = getWidth() - (int)(getWidth() * 0.1);
        int maxh = getHeight() - (int)(getHeight() * 0.1);

        if (maxw > maxh) {
            while(getScaledHeight() < maxh) {
                scale += 1;
            }
        } else {
            while(getScaledWidth() < maxw) {
                scale += 1;
            }
        }
    }

    private int getScaledWidth() {
        return (int)(settings.getWidth() * scale);
    }

    private int getScaledHeight() {
        return (int)(settings.getHeight() * scale);
    }

    private int getScaledCenterX() {
        return (getWidth() / 2) - (getScaledWidth() / 2);
    }

    private int getScaledCenterY() {
        return (getHeight() / 2) - (getScaledHeight() / 2);
    }

    private int getPixelWidth() {
        return getScaledWidth() / settings.getWidth();
    }

    private int getPixelHeight() {
        return getScaledHeight() / settings.getHeight();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (settings == null) {
            System.out.println("settings==null");
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();

        try {
            drawBackground(g2);
            drawContainer(g2);
            drawBorder(g2);
            drawImage(g2);
            drawModifications(g2);
            drawGrid(g2);
        } finally {
            g2.dispose();
        }
    }

    private void drawBackground(Graphics g) {
        int size = 10;
        Color c1 = Color.decode("#BFBFBF");
        Color c2 = Color.decode("#FFFFFF");
        Color c;

        for (int y = 0; y <= getHeight() + size; y += size) {
            c = y % (size * 2) == 0 ? c1 : c2;
            for (int x = 0; x <= getWidth() + size; x += size) {
                g.setColor(c);
                g.fillRect(x, y, size, size);
                c = c == c1 ? c2 : c1;
            }
        }
    }

    private void drawContainer(Graphics2D g2) {
        Color c = Color.decode("#CFCFCF");

        g2.setPaint(c);
        // Above
        g2.fillRect(0, 0, getWidth(), getScaledCenterY());
        // Bellow
        g2.fillRect(0, getScaledCenterY() + getScaledHeight(), getWidth(), getHeight() - getScaledCenterY() - getScaledHeight());
        // Leftmost
        g2.fillRect(0, 0, getScaledCenterX(), getHeight());
        // Rightmost
        g2.fillRect(getScaledCenterX() + getScaledWidth(), 0, getWidth() - getScaledCenterX() - getScaledWidth(), getHeight());
    }

    private void drawBorder(Graphics2D g2) {
        g2.setPaint(Color.BLACK);
        g2.drawRect(getScaledCenterX() - 1, getScaledCenterY() - 1, getScaledWidth() + 1, getScaledHeight() + 1);
    }

    private void drawImage(Graphics2D g2) {
        if (image != null) {
            g2.drawImage(image, getScaledCenterX(), getScaledCenterY(), getScaledWidth(), getScaledHeight(), null);
        }
    }

    private void drawGrid(Graphics2D g2) {
        if (settings == null || getWidth() == settings.getWidth() || getHeight() == settings.getHeight()) {
            return;
        }

        int w = getScaledWidth() / settings.getWidth();
        int h = getScaledHeight() / settings.getHeight();
        int blocksX = getScaledWidth() / w; // Same as settings.getWidth() but hey, what not :P
        int blocksY = getScaledHeight() / h; // whoops I did it again... do you hear the song?!?! :D

        Color c = Color.decode("#7F7F7F");
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{ 1 }, 0);

        g2.setColor(c);
        g2.setStroke(dashed);

        // Draw vertical lines
        for (int x = 0; x < blocksX; x++) {
            g2.drawLine(
                    x * w + getScaledCenterX(),
                    getScaledCenterY(),
                    x * w + getScaledCenterX(),
                    h * blocksY + getScaledCenterY()
            );
        }

        // Draw horizontal lines
        for (int y = 0; y < blocksY; y++) {
            g2.drawLine(
                    getScaledCenterX(),
                    y * h + getScaledCenterY(),
                    w * blocksX + getScaledCenterX(),
                    y * h + getScaledCenterY()
            );
        }
    }

    private void drawModifications(Graphics2D g2) {
        for (CanvasTool action : history) {
            if (action instanceof ICanvasActionDrawable) {
                ((ICanvasActionDrawable) action).paint(g2, getScaledCenterX(), getScaledCenterY(), getPixelWidth(), getPixelHeight());
            }
        }

        if (action != null && action instanceof ICanvasActionDrawable) {
            ((ICanvasActionDrawable) action).paint(g2, getScaledCenterX(), getScaledCenterY(), getPixelWidth(), getPixelHeight());
        }
    }

    private FrameMain getFrameMain() {
        return (FrameMain) SwingUtilities.getWindowAncestor(this);
    }

    private Point decodePixel() {
        Point point = getMousePosition();
        point.x -= getScaledCenterX();
        point.y -= getScaledCenterY();
        return new Point(point.x / (int)scale, point.y / (int)scale);
    }

    private boolean isMouseClickValid() {
        Point point = getMousePosition();
        return settings != null &&
                point.x > getScaledCenterX() &&
                point.x < getScaledCenterX() + getScaledWidth() &&
                point.y > getScaledCenterY() &&
                point.y < getScaledCenterY() + getScaledWidth();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double amount = -e.getPreciseWheelRotation();
        if (scale + amount < 1) {
            scale -= 0.1;
        } else {
            scale += amount;
            scale = Math.round(scale);
        }
        repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!isMouseClickValid())
            return;

        System.out.println("mouseDragged -> " + decodePixel());
        action.savePoint(decodePixel(), Color.RED);
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!isMouseClickValid())
            return;

        System.out.println("mousePressed -> " + decodePixel());
        action = new CanvasToolPencil();
        action.savePoint(decodePixel(), Color.RED);
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!isMouseClickValid())
            return;

        System.out.println("mouseReleased -> " + decodePixel());
        history.add(action);
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
