package org.marasm.neuromage;

import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageUtility {

    public static Image scale(Image img, int divisior) {
        BufferedImage buffer = buffer(img);
        return buffer(buffer.getScaledInstance(buffer.getWidth() / divisior,
                buffer.getHeight() / divisior, Image.SCALE_SMOOTH));
    }

    public static Topology topology() {
        return new Topology(2,
                colorComponents(Color.WHITE).size(),
                128, 128, 128, 128);
    }

    public static DataSet dataSet(Image original) {
        BufferedImage buffer = buffer(original);
        List<DataSet.Entry> entries = new ArrayList<>(buffer.getHeight() * buffer.getWidth());
        for (int y = 0; y < buffer.getHeight(); y++) {
            for (int x = 0; x < buffer.getHeight(); x++) {
                DataSet.Entry entry = new DataSet.Entry(inputsAt(x, y),
                        outputsAt(x, y, buffer));
                entries.add(entry);
            }
        }
        return new DataSet(entries);
    }

    public static List<List<Double>> neuralize(Image img) {
        return neuralize(img.getHeight(null), img.getWidth(null));
    }

    @NotNull
    private static List<List<Double>> neuralize(int height, int width) {
        List<List<Double>> inputs = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                inputs.add(inputsAt(x, y));
            }
        }
        return inputs;
    }

    private static List<Double> outputsAt(int x, int y, BufferedImage original) {
        return colorComponents(new Color(original.getRGB(x, y)));
    }

    private static List<Double> inputsAt(int x, int y) {
        return Arrays.asList((double) x, (double) y);
    }

    private static List<Double> colorComponents(Color color) {
        return Arrays.asList(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0);
    }

    private static Color color(double r, double g, double b) {
        return new Color((int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

    public static BufferedImage buffer(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static Image unneuralize(List<List<Double>> processed, int width, int height) {
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < height - 1; y++) {
            int syS = y * width;
            for (int x = 0; x < width - 1; x++) {
                unneuralize(x, y, processed.get(x + syS), result);
            }
        }
        return result;
    }

    private static void unneuralize(int x, int y, List<Double> doubles, BufferedImage result) {
        result.setRGB(x, y, color(doubles.get(0), doubles.get(1), doubles.get(2)).getRGB());
    }
}
