package cxminer.apps.pixelart;

import java.awt.*;

/**
 * Created by erik on 1.3.2017.
 */
public class Utils {

    public static GridBagConstraints createConstraints(int gridX, int gridY, int anchor, int gridWidth, int gridHeight, int fill) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = fill;
        constraints.insets = new Insets(3, 3, 3, 3);
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.anchor = anchor;
        constraints.gridwidth = gridWidth;
        constraints.gridheight = gridHeight;
        return constraints;
    }

}
