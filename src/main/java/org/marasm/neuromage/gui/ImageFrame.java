package org.marasm.neuromage.gui;

import javax.swing.*;
import java.awt.*;

public class ImageFrame extends JFrame {
    private Image image;
    private final JLabel imageLabel;

    public ImageFrame(Image image) throws HeadlessException {
        super();
        this.image = image;
        imageLabel = new JLabel(new ImageIcon(image));
        this.add(imageLabel);
    }

    public ImageFrame(String title, Image image) throws HeadlessException {
        super(title);
        this.image = image;
        imageLabel = new JLabel(new ImageIcon(image));
        this.add(imageLabel);
    }

    public void setImage(Image image) {
        this.image = image;
        imageLabel.setIcon(new ImageIcon(image));
        invalidate();
        imageLabel.invalidate();
        repaint();
        imageLabel.repaint();
    }
}
