package cxminer.apps.pixelart.canvas;

import cxminer.apps.pixelart.canvas.action.ICanvasActionDrawable;
import cxminer.apps.pixelart.frame.FrameMain;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

/**
 * Created by erik on 1.3.2017.
 */
public class Canvas extends JPanel implements MouseListener, MouseWheelListener {

    private BufferedImage image;
    private int zoom = 1;
    private CanvasSettings settings;
    private ArrayList<ICanvasActionDrawable> history = new ArrayList<>();

    public Canvas(CanvasSettings settings) {
        this(settings, null);
    }

    public Canvas(CanvasSettings settings, BufferedImage image) {
        super();
        this.settings = settings;

        // Create image
        if (image == null) {
            image = new BufferedImage(settings.getWidth(), settings.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = image.createGraphics();
            //clear
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
            g2.fillRect(0,0,settings.getWidth(),settings.getHeight());
            //reset composite
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        }
        // Save image
        this.image = image;
        // Set black line border
        setBorder(BorderFactory.createEmptyBorder());
        // Set bounds - required when in JDesktopPane
        setBounds(0, 0, settings.getWidth(), settings.getHeight());
        // Show it!
        setVisible(true);
    }

    private int getCenterX() {
        return (getParent().getWidth() / 2) - (getWidth() / 2);
    }

    private int getCenterY() {
        return (getParent().getHeight() / 2) - (getHeight() / 2);
    }

    public void centerOnParent() {
        setLocation(getCenterX(), getCenterY());
    }

    public void applyZoom() {
        setBounds(getCenterX(), getCenterY(), settings.getWidth() * zoom, settings.getHeight() * zoom);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawBorder(g);
        drawImage(g);
        drawGrid(g);
    }

    private void drawBackground(Graphics g) {
        int w = 4;
        int h = 4;
        int blocksX = (int)Math.ceil(getWidth() / (double)w);
        int blocksY = (int)Math.ceil(getHeight() / (double)h);
        Color c1 = Color.decode("#BFBFBF");
        Color c2 = Color.decode("#FFFFFF");
        Color c3 = c1;

        for (int x = 0; x < blocksX; x++) {
            c3 = c3 == c1 ? c2 : c1;
            for (int y = 0; y < blocksY; y++) {
                c3 = c3 == c1 ? c2 : c1;
                g.setColor(c3);
                g.fillRect(x * w, y * h, w, h);
            }
        }
    }

    private void drawGrid(Graphics g) {
        if (getWidth() == settings.getWidth() || getHeight() == settings.getHeight()) {
            return;
        }

        Graphics2D g2 = (Graphics2D)g;

        int w = getWidth() / settings.getWidth();
        int h = getHeight() / settings.getHeight();
        int blocksX = getWidth() / w; // Same as settings.getWidth() but hey, what not :P
        int blocksY = getHeight() / h; // whoops I did it again... do you hear the song?!?! :D

        Color c = Color.decode("#7F7F7F");
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{ 1 }, 0);

        g2.setColor(c);
        g2.setStroke(dashed);

        // Draw vertical lines
        for (int x = 0; x < blocksX; x++) {
            g2.drawLine(x * w, 0, x * w, h * blocksY);
        }

        // Draw horizontal lines
        for (int y = 0; y < blocksY; y++) {
            g2.drawLine(0, y * h, w * blocksX, y * h);
            //g.drawLine(0, y * h, w * blocksX, y * h);
        }

        //g2.dispose();
    }

    private void drawBorder(Graphics g) {
        //g.setColor(Color.BLACK);
        //g.drawRect(0, 0, getWidth(), getHeight());
    }

    private void drawImage(Graphics g) {
        if (image == null)
            return;
/*
        BufferedImage imageClone = deepCopy(image);
        Graphics gi = imageClone.createGraphics();

        for (ICanvasAction action : history) {
            action.paint(gi);
        }

        gi.dispose();

        try {
            ImageIO.write(imageClone, "png", new File("C:\\Users\\erik\\Downloads\\test.png"));
        } catch (Exception e) {

        }
        */

        //g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
    }

    private BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    private FrameMain getFrameMain() {
        return (FrameMain) SwingUtilities.getWindowAncestor(this);
    }

    private Point getPoint(Point point) {
        return new Point(point.x / zoom, point.y / zoom);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        double amount = e.getUnitsToScroll();
        if (zoom - amount < 1)
            return;
        zoom -= amount;
        getFrameMain().reportZoom((int) Math.ceil(zoom * 100));
        applyZoom();
        centerOnParent();
        revalidate();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //history.add(new CanvasActionPencil(getPoint(getMousePosition()), Color.RED));
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
