package cxminer.apps.pixelart;

import javax.swing.*;

/**
 * Created by Erik on 28. 02. 2017.
 */
public class Resource {

    public static final ImageIcon PENCIL = getImageIcon("pencil.png", "Pencil");
    public static final ImageIcon COLOR_PICKER = getImageIcon("color_picker.png", "Color Picker");
    public static final ImageIcon BUCKET = getImageIcon("bucket.png", "Bucket");
    public static final ImageIcon ERASER = getImageIcon("eraser.png", "Eraser");

    static ImageIcon getImageIcon(String filename, String description) {
        return new ImageIcon(Resource.class.getResource("/resources/assets/images/" + filename), description);
    }

}
