package cxminer.apps.pixelart.toolbar;

/**
 * Created by Erik on 01/03/2017.
 */
public class ToolbarHistory extends Toolbar {

    public ToolbarHistory() {
        super("History");

        setFocusable(false);
        setMaximizable(false);
        setIconifiable(false);
        setResizable(false);
        pack();
        setVisible(true);
    }

}
