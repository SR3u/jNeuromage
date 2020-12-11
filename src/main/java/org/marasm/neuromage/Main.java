package org.marasm.neuromage;

import org.jetbrains.annotations.NotNull;
import org.marasm.neuromage.gui.ImageFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        System.out.println("Loading");
        final BufferedImage original = ImageIO.read(new File("Lenna.png"));
        imageFrame(original, "Original");
        System.out.println("Creating data set");
        DataSet dataSet = ImageUtility.dataSet(original);
        List<List<Double>> neuralized = ImageUtility.neuralize(original);
        final LearningNetwork learningNetwork = new LearningNetwork(ImageUtility.topology());
        System.out.println("Learning");
        final ImageFrame current = imageFrame(original, "Current");
        learningNetwork.setShowInfoHandler(net -> {
            List<List<Double>> processed = neuralized.stream()
                    .map(net::output)
                    .collect(Collectors.toList());
            Image unneuralized = ImageUtility.unneuralize(processed, original.getWidth(), original.getHeight());
            current.setImage(unneuralized);
            System.out.println("Updated preview");
        });
        learningNetwork.learnBackPropagation(dataSet, 8);
        System.out.println("Applying");
        List<List<Double>> processed = neuralized.stream()
                .map(learningNetwork::output)
                .collect(Collectors.toList());
        System.out.println("Unneuralizing");
        Image unneuralized = ImageUtility.unneuralize(processed, original.getWidth(), original.getHeight());
        imageFrame(unneuralized, "Result");
        System.out.println("Saving");
        //ImageIO.write(ImageUtility.buffer(unneuralized), "png", new File("out.png"));
    }

    @NotNull
    private static ImageFrame imageFrame(Image image, String title) {
        final ImageFrame current = new ImageFrame(title, image);
        current.setSize(192, 192);
        current.setVisible(true);
        return current;
    }
}
