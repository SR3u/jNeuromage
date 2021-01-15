package org.marasm.neuromage;

import org.marasm.neuromage.neural.DataSet;
import sr3u.jvec.JMath;
import sr3u.jvec.Vector;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ImageDataSet extends DataSet {

    private static final JMath math = JMath.get();

    private Dimension size;

    protected ImageDataSet(List<Vector> inputs, List<Vector> outputs) {
        super(inputs, outputs);
    }

    public static ImageDataSet of(int width, int height, List<Vector> inputs, List<Vector> outputs) {
        ImageDataSet imageDataSet = new ImageDataSet(inputs, outputs);
        imageDataSet.size = new Dimension(width, height);
        return imageDataSet;
    }

    public static ImageDataSet of(Image image) {
        return of(buffer(image));
    }

    public static ImageDataSet of(BufferedImage image) {
        List<Vector> outputs = new ArrayList<>(image.getHeight() * image.getWidth());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int clr = image.getRGB(x, y);
                Color color = new Color(clr);
                outputs.add(math.vector(color.getRed() / 255.0, color.getGreen() / 255.0, color.getBlue() / 255.0));
            }
        }
        List<Vector> inputs = IntStream.range(0, image.getHeight())
                .mapToDouble(i -> i)
                .mapToObj(y -> IntStream.range(0, image.getWidth())
                        .mapToDouble(i -> i)
                        .mapToObj(x -> math.vector(x / image.getWidth(), y / image.getHeight())).collect(Collectors.toList()))
                .flatMap(Collection::stream).collect(Collectors.toList());
        return of(image.getWidth(), image.getHeight(), inputs, outputs);
    }

    public BufferedImage toImage() {
        BufferedImage image = new BufferedImage((int) size.getWidth(), (int) size.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        IntStream.range(0, getInputs().size())
                .forEach(i -> {
                    double[] in = getInputs().get(i).calculate().data();
                    double[] out = getOutputs().get(i).calculate().data();
                    int x = (int) (in[0] * size.getWidth())%((int) size.getWidth());
                    int y = (int) (in[1] * size.getHeight())%((int) size.getHeight());
                    int r = (int) (out[0] * 255);
                    int g = (int) (out[1] * 255);
                    int b = (int) (out[2] * 255);
                    image.setRGB(x, y,
                            new Color(r, g, b).getRGB());
                });
        return image;
    }

    private static BufferedImage buffer(Image img) {
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
}
