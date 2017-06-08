package cxminer.apps.pixelart.toolbar;

import cxminer.apps.pixelart.Resource;
import cxminer.apps.pixelart.canvas.CanvasPane;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Erik on 01/03/2017.
 */
public class ToolbarTools extends Toolbar {

    private CanvasPane canvasPane;

    private int row = 0;
    private int col = 0;
    private ButtonGroup group = new ButtonGroup();

    public ToolbarTools(CanvasPane canvasPane) {
        super("Tools");
        this.canvasPane = canvasPane;

        setFocusable(false);
        setResizable(false);
        setMaximizable(false);
        setIconifiable(false);
        getContentPane().setLayout(new GridBagLayout());
        createTools();
        pack();
        setVisible(true);
    }

    private void createTools() {
        JToggleButton button;

        addButton(Resource.PENCIL);
        addButton(Resource.BUCKET);
        addButton(Resource.COLOR_PICKER);
        addButton(Resource.ERASER);
    }

    private void addButton(ImageIcon imageIcon) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = col;
        c.gridy = row;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 1;
        c.gridheight = 1;

        int width = imageIcon.getIconWidth() * 2;
        int height = imageIcon.getIconHeight() * 2;
        Dimension dimension = new Dimension(width, height);

        JToggleButton button = new JToggleButton(imageIcon);
        button.setSize(dimension);
        button.setPreferredSize(dimension);
        button.setMinimumSize(dimension);
        button.setMaximumSize(dimension);
        getContentPane().add(button, c);
        group.add(button);

        col++;
        if (col == 2) {
            col = 0;
            row++;
        }
    }

}
