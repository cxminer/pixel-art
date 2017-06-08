package cxminer.apps.pixelart;

import cxminer.apps.pixelart.frame.FrameMain;

import javax.swing.*;
import java.awt.*;

public class PixelArt {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }

        EventQueue.invokeLater(() -> {
            new FrameMain().setVisible(true);
        });
    }

}
