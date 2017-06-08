package cxminer.apps.pixelart.frame;

import cxminer.apps.pixelart.canvas.CanvasPane;
import cxminer.apps.pixelart.canvas.CanvasSettings;
import cxminer.apps.pixelart.dialog.DialogNew;
import cxminer.apps.pixelart.toolbar.ToolbarColors;
import cxminer.apps.pixelart.toolbar.ToolbarHistory;
import cxminer.apps.pixelart.toolbar.ToolbarTools;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by erik on 28.2.2017.
 */
public class FrameMain extends JFrame {

    private String title = "PixelArt by cxminer";

    private CanvasPane canvasPane;
    //private Canvas canvas;
    private ToolbarTools frameTools;
    private ToolbarColors frameColors;
    private ToolbarHistory frameHistory;

    public FrameMain() {
        // Set frame properties
        setTitle(title);
        setSize(640, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Create main menu
        createMenuBar();
        // Create desktops - tools, colors, history and drawing canvas
        createDesktop();
    }

    private void createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        // ---------
        // FILE MENU
        // ---------
        menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(menu);

        menuItem = new JMenuItem("New");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
        menuItem.addActionListener((ActionEvent e) -> {
            CanvasSettings settings = DialogNew.Open(this);
            if (settings != null) {
                canvasPane.canvasNew(settings);
            }
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Open");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
        menuItem.addActionListener((ActionEvent e) -> {
            ArrayList<String> validExtensions = new ArrayList<>();
            validExtensions.add("png");
            validExtensions.add("jpg");
            validExtensions.add("jpeg");
            validExtensions.add("bmp");
            validExtensions.add("gif");

            JFileChooser chooser = new JFileChooser();
            chooser.addChoosableFileFilter(new FileFilter() {
                @Override
                public boolean accept(File file) {
                    // Do not allow directories
                    if (file.isDirectory()) {
                        return true;
                    }

                    // Get file extension
                    String filename = file.getName();
                    String ext = "";
                    int i = filename.lastIndexOf('.');
                    int p = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));

                    if (i > p) {
                        ext = filename.substring(i + 1);
                    }

                    boolean isValid = false;
                    for (String validExt : validExtensions) {
                        if (ext.toLowerCase().equals(validExt)) {
                            isValid = true;
                        }
                    }
                    return isValid;
                }

                @Override
                public String getDescription() {
                    StringBuilder sb = new StringBuilder("Image Files (");
                    for (String validExt : validExtensions) {
                        sb.append(".");
                        sb.append(validExt.toUpperCase());
                        sb.append(",");
                    }
                    sb.deleteCharAt(sb.length() - 1);
                    sb.append(")");

                    return sb.toString();
                }
            });
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            int result = chooser.showOpenDialog((Component) e.getSource());
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    canvasPane.canvasOpen(ImageIO.read(chooser.getSelectedFile()));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Looks like you chose a bad image file, sorry can't open that!");
                }
            }
        });
        menu.add(menuItem);

        menu.add(new JSeparator(SwingConstants.HORIZONTAL));

        menuItem = new JMenuItem("Save");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        menuItem.addActionListener((ActionEvent e) -> {
            System.out.println(((JMenuItem)e.getSource()).getText());
            // TODO
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Save As...");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK | InputEvent.SHIFT_MASK));
        menuItem.addActionListener((ActionEvent e) -> {
            System.out.println(((JMenuItem)e.getSource()).getText());
            // TODO
        });
        menu.add(menuItem);

        menu.add(new JSeparator(SwingConstants.HORIZONTAL));

        menuItem = new JMenuItem("Print");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        menuItem.addActionListener((ActionEvent e) -> {
            System.out.println(((JMenuItem)e.getSource()).getText());
            // TODO
        });
        menu.add(menuItem);

        menu.add(new JSeparator(SwingConstants.HORIZONTAL));

        menuItem = new JMenuItem("Exit");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        menuItem.addActionListener((ActionEvent e) -> {
            System.out.println(((JMenuItem)e.getSource()).getText());
            System.exit(0);
        });
        menu.add(menuItem);

        // ---------
        // VIEW MENU
        // ---------
        menu = new JMenu("View");
        menu.setMnemonic(KeyEvent.VK_V);
        menuBar.add(menu);

        menuItem = new JMenuItem("Zoom In");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS, InputEvent.CTRL_MASK));
        menuItem.addActionListener((ActionEvent e) -> {
            System.out.println(((JMenuItem)e.getSource()).getText());
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Zoom Out");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, InputEvent.CTRL_MASK));
        menuItem.addActionListener((ActionEvent e) -> {
            System.out.println(((JMenuItem)e.getSource()).getText());
        });
        menu.add(menuItem);

        menu.add(new JSeparator(SwingConstants.HORIZONTAL));

        menuItem = new JMenuItem("Show Tools");
        menuItem.addActionListener((ActionEvent e) -> {
            System.out.println(((JMenuItem)e.getSource()).getText());
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Show Colors");
        menuItem.addActionListener((ActionEvent e) -> {
            System.out.println(((JMenuItem)e.getSource()).getText());
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Show History");
        menuItem.addActionListener((ActionEvent e) -> {
            System.out.println(((JMenuItem)e.getSource()).getText());
        });
        menu.add(menuItem);

        // ----------
        // ABOUT MENU
        // ----------
        menu = new JMenu("About");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        menuItem = new JMenuItem("Program");
        menuItem.setMnemonic(KeyEvent.VK_P);
        menuItem.addActionListener((ActionEvent e) -> {
            // TODO
        });
        menu.add(menuItem);

        menuItem = new JMenuItem("Author");
        menuItem.setMnemonic(KeyEvent.VK_A);
        menuItem.addActionListener((ActionEvent e) -> {
            // TODO
        });
        menu.add(menuItem);

        setJMenuBar(menuBar);
    }

    private void createDesktop() {
        canvasPane = new CanvasPane();
        canvasPane.setBackground(Color.decode("#CFCFCF"));
        setContentPane(canvasPane);
    }

    public void reportZoom(int percentage) {
        setTitle(title + " (Zoom: " + percentage + "%)");
    }
/*
    private void canvasCleanup() {
        if (canvas != null) {
            desktopPane.remove(canvas);
            removeMouseListener(canvas);
            removeMouseWheelListener(canvas);
            canvas = null;
        }
    }

    private void canvasNew(CanvasSettings settings) {
        canvasCleanup();

        canvas = new Canvas(settings);
        addMouseListener(canvas);
        addMouseWheelListener(canvas);
        desktopPane.add(canvas);

        canvas.applyZoom();
        canvas.centerOnParent();
    }

    private void canvasOpen(File file) {
        canvasCleanup();

        BufferedImage image;

        try {
            image = ImageIO.read(file);

            CanvasSettings settings = new CanvasSettings(image.getWidth(), image.getHeight());

            canvas = new Canvas(settings, image);
            addMouseListener(canvas);
            addMouseWheelListener(canvas);
            desktopPane.add(canvas);

            canvas.applyZoom();
            canvas.centerOnParent();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unable to open file!");
        }
    }
*/
}
