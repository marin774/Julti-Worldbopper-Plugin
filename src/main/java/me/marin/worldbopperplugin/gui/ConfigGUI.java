package me.marin.worldbopperplugin.gui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import me.marin.worldbopperplugin.io.WorldBopperSettings;
import org.apache.logging.log4j.Level;
import xyz.duncanruns.julti.Julti;
import xyz.duncanruns.julti.gui.JultiGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;

public class ConfigGUI extends JFrame {

    private boolean isClosed = false;

    private JCheckBox enableWorldbopper;
    private JFormattedTextField savesBuffer;
    private JCheckBox keepWorldsWithNetherCheckBox;
    private JButton saveButton;
    private JPanel mainPanel;

    public ConfigGUI() {
        $$$setupUI$$$();

        this.setContentPane(mainPanel);
        this.setTitle("World Bopper Plugin Config");
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
        this.setLocation(JultiGUI.getJultiGUI().getLocation());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                isClosed = true;
            }
        });
        updateGUI();

        enableWorldbopper.addActionListener(e -> {
            WorldBopperSettings settings = WorldBopperSettings.getInstance();
            settings.worldbopperEnabled = enableWorldbopper.isSelected();
            WorldBopperSettings.save();
            Julti.log(Level.INFO, settings.worldbopperEnabled ? "WorldBopper is now active." : "WorldBopper is no longer active.");
        });

        keepWorldsWithNetherCheckBox.addActionListener(e -> {
            WorldBopperSettings settings = WorldBopperSettings.getInstance();
            settings.keepNetherWorlds = keepWorldsWithNetherCheckBox.isSelected();
            WorldBopperSettings.save();
            Julti.log(Level.INFO, settings.keepNetherWorlds ? "WorldBopper will now be keeping worlds with nether enters." : "WorldBopper will now be clearing worlds with nether enters.");
        });

        saveButton.addActionListener(e -> {
            Long number = (Long) savesBuffer.getValue();

            WorldBopperSettings settings = WorldBopperSettings.getInstance();

            if (number == null) {
                savesBuffer.setValue(settings.savesBuffer);
                JOptionPane.showMessageDialog(null, "Invalid number: '" + savesBuffer.getText() + "'.");
                return;
            }
            // number has to be between 5-5000
            number = Math.min(5000, number);
            number = Math.max(5, number);

            settings.savesBuffer = (int) number.longValue();
            WorldBopperSettings.save();

            // update visually if number was too small/big
            savesBuffer.setValue(settings.savesBuffer);

            JOptionPane.showMessageDialog(null, "Set world buffer to " + number + ".");
        });
    }

    public boolean isClosed() {
        return this.isClosed;
    }

    public void updateGUI() {
        enableWorldbopper.setSelected(WorldBopperSettings.getInstance().worldbopperEnabled);
        keepWorldsWithNetherCheckBox.setSelected(WorldBopperSettings.getInstance().keepNetherWorlds);
        savesBuffer.setValue(WorldBopperSettings.getInstance().savesBuffer);
    }

    private void createUIComponents() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setParseIntegerOnly(true);
        savesBuffer = new JFormattedTextField(numberFormat);
    }

    // I run this to force intellij to generate code for components
    public static void main(String[] args) {
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(5, 2, new Insets(5, 5, 5, 5), -1, -1));
        enableWorldbopper = new JCheckBox();
        enableWorldbopper.setText("Enable WorldBopper?");
        mainPanel.add(enableWorldbopper, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        mainPanel.add(spacer1, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        keepWorldsWithNetherCheckBox = new JCheckBox();
        keepWorldsWithNetherCheckBox.setText("Keep worlds with nether enters?");
        mainPanel.add(keepWorldsWithNetherCheckBox, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        mainPanel.add(savesBuffer, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        saveButton = new JButton();
        saveButton.setText("Save");
        mainPanel.add(saveButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Max worlds folder size:");
        mainPanel.add(label1, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
