package org.marasm.neuromage;

import org.marasm.neuromage.math.Vector;
import org.marasm.neuromage.math.VectorMath;
import org.marasm.neuromage.math.VectorMathBuilder;
import org.marasm.neuromage.neural.DataSet;
import org.marasm.neuromage.neural.Layers;
import org.marasm.neuromage.neural.NeuralNetwork;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Main {

    public static void main(String[] args) throws IOException {
        Image ima = ImageIO.read(new File("Lenna.png"));
        DataSet dataSet = ImageDataSet.of(ima);
        VectorMath math = VectorMathBuilder.get();
        Layers layers = new Layers(math);
        NeuralNetwork neuralNetwork = new NeuralNetwork(
                layers.sum(2, 2),
                layers.sigmoid(128, 2).learning(),
                layers.sigmoid(1024, 128).learning(),
                layers.sigmoid(1024, 1024).learning(),
                layers.sigmoid(1024, 1024).learning(),
                layers.sigmoid(128, 1024).learning(),
                layers.sigmoid(3, 128).learning()
        );
        neuralNetwork.learn(3, 0.01, dataSet);

        /*long time = benchmark(math, neuralNetwork);
        System.out.println(time + "ms");*/
    }

    private static long benchmark(VectorMath math, NeuralNetwork neuralNetwork) {
        Vector output = neuralNetwork.output(math.vector(1, 1));
        long neurons = neuralNetwork.summary().getNeurons();
        System.out.println(neurons);
        long N = 10;
        if (neurons < 10000) {
            N = neurons / 3000;
        }
        for (long i = 0; i < N; i++) { // waiting for JIT
            output = neuralNetwork.output(math.vector(1, 1));
        }
        long start = new Date().getTime();
        for (long i = 0; i < N; i++) {
            output = neuralNetwork.output(math.vector(1, 1));
        }
        long end = new Date().getTime();
        System.out.println(output);
        return (end - start) / N;
    }

   /* @NotNull
    private static ImageFrame imageFrame(Image image, String title) {
        final ImageFrame current = new ImageFrame(title, image);
        current.setSize(192, 192);
        current.setVisible(true);
        return current;
    }*/
}
