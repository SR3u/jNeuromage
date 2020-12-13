package org.marasm.neuromage;

import org.marasm.neuromage.math.Vector;
import org.marasm.neuromage.math.VectorMath;
import org.marasm.neuromage.math.VectorMathBuilder;
import org.marasm.neuromage.neural.DataSet;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ImageDataSet extends DataSet {

    private static final VectorMath math = VectorMathBuilder.get();

    protected ImageDataSet(List<Vector> inputs, List<Vector> outputs) {
        super(inputs, outputs);
    }

    public static DataSet of(Image image) {
        return of(buffer(image));
    }

    public static DataSet of(BufferedImage image) {
        List<Vector> outputs = new ArrayList<>(image.getHeight() * image.getWidth());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int clr = image.getRGB(x, y);
                Color color = new Color(clr);
                outputs.add(math.vector(color.getRed(), color.getGreen(), color.getBlue()));
            }
        }
        List<Vector> inputs = IntStream.range(0, image.getHeight())
                .mapToDouble(i -> i)
                .mapToObj(y -> IntStream.range(0, image.getWidth())
                        .mapToDouble(i -> i)
                        .mapToObj(x -> math.vector(x, y)).collect(Collectors.toList()))
                .flatMap(Collection::stream).collect(Collectors.toList());
        return of(inputs, outputs);
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
