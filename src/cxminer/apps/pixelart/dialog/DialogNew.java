package cxminer.apps.pixelart.dialog;

import cxminer.apps.pixelart.canvas.CanvasSettings;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;

/**
 * Created by erik on 28.2.2017.
 */

/* SINGLETON
 */
public class DialogNew extends JDialog {

    public static CanvasSettings Open(JFrame frame) {
        DialogNew dialog = new DialogNew(frame);
        if (dialog.confirmed) {
            return dialog.settings;
        }
        return null;
    }

    private JLabel labelWidth;
    private JFormattedTextField fieldWidth;
    private JLabel labelWidthNote;
    private JLabel labelHeight;
    private JFormattedTextField fieldHeight;
    private JLabel labelHeightNote;
    private JButton buttonOK;
    private JButton buttonCancel;

    private CanvasSettings settings;

    private boolean confirmed = false;

    private DialogNew(JFrame frame) {
        super(frame, "New", true);

        // Register ENTER key to execute OK action
        getRootPane().registerKeyboardAction(this::doConfirm, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        // Register ESCAPE key to execute Cancel action
        getRootPane().registerKeyboardAction(this::doCancel, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new GridBagLayout());
        setLocationRelativeTo(frame);
        createComponents();
        pack();
        setVisible(true);
    }

    private void createComponents() {
        NumberFormat format = NumberFormat.getIntegerInstance();
        format.setGroupingUsed(false);
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(1);
        formatter.setMaximum(Integer.MAX_VALUE);
        formatter.setAllowsInvalid(false);
        formatter.setCommitsOnValidEdit(true);

        // -----
        // ROW 0
        // -----

        labelWidth = new JLabel("Width:");
        add(labelWidth, 0, 0, GridBagConstraints.LINE_END, 1, 1, 0);

        fieldWidth = new JFormattedTextField(formatter);
        fieldWidth.setPreferredSize(new Dimension(100, (int)fieldWidth.getPreferredSize().getHeight()));
        fieldWidth.setValue(16);
        add(fieldWidth, 1, 0, GridBagConstraints.CENTER, 1, 1, 0);

        labelWidthNote = new JLabel("pixels");
        add(labelWidthNote, 2, 0, GridBagConstraints.LINE_START, 1, 1, 0);

        // -----
        // ROW 1
        // -----

        labelHeight = new JLabel("Height:");
        add(labelHeight, 0, 1, GridBagConstraints.LINE_END, 1, 1, 0);

        fieldHeight = new JFormattedTextField(formatter);
        fieldHeight.setPreferredSize(new Dimension(100, (int)fieldHeight.getPreferredSize().getHeight()));
        fieldHeight.setValue(16);
        add(fieldHeight, 1, 1, GridBagConstraints.CENTER, 1, 1, 0);

        labelHeightNote = new JLabel("pixels");
        add(labelHeightNote, 2, 1, GridBagConstraints.LINE_START, 1, 1, 0);

        // -----
        // ROW 2
        // -----

        JPanel panel = new JPanel();
        add(panel, 0, 2, GridBagConstraints.CENTER, 3, 1, GridBagConstraints.HORIZONTAL);

        buttonOK = new JButton("OK");
        buttonOK.addActionListener(this::doConfirm);
        panel.add(buttonOK);

        buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(this::doCancel);
        panel.add(buttonCancel);
    }

    private void doConfirm(ActionEvent e) {
        confirmed = true;
        settings = new CanvasSettings((int)fieldWidth.getValue(), (int)fieldHeight.getValue());
        dispose();
    }

    private void doCancel(ActionEvent e) {
        confirmed = false;
        dispose();
    }

    private void add(Component component, int gridX, int gridY, int anchor, int gridWidth, int gridHeight, int fill) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = fill;
        constraints.insets = new Insets(3, 3, 3, 3);
        constraints.gridx = gridX;
        constraints.gridy = gridY;
        constraints.anchor = anchor;
        constraints.gridwidth = gridWidth;
        constraints.gridheight = gridHeight;
        add(component, constraints);
    }

}
