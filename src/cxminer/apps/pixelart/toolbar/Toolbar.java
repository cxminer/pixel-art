package cxminer.apps.pixelart.toolbar;

import javax.swing.*;

/**
 * Created by erik on 1.3.2017.
 */
public class Toolbar extends JInternalFrame {

    public Toolbar() {
        super();
    }

    public Toolbar(String title) {
        super(title);
    }

    public Toolbar(String title, boolean resizable) {
        super(title, resizable);
    }

    public Toolbar(String title, boolean resizable, boolean closable) {
        super(title, resizable, closable);
    }

    public Toolbar(String title, boolean resizable, boolean closable, boolean maximizable) {
        super(title, resizable, closable, maximizable);
    }

    public Toolbar(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        super(title, resizable, closable, maximizable, iconifiable);
    }

}
