package org.marasm.neuromage;

import org.jetbrains.annotations.NotNull;
import org.marasm.neuromage.gui.ImageFrame;
import org.marasm.neuromage.neural.DataSet;
import org.marasm.neuromage.neural.Layers;
import org.marasm.neuromage.neural.NeuralNetwork;
import org.marasm.neuromage.neural.NeuralNetworkObj;
import sr3u.jvec.JMath;
import sr3u.jvec.Vector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    //public static final File IMAGE_PATH = new File("Lenna.png");
    public static final File IMAGE_PATH = new File("ChristmasTree.png");

    public static void main(String[] args) throws IOException {
        Image ima = ImageIO.read(IMAGE_PATH);
        ImageDataSet dataSet = ImageDataSet.of(ima);
        imageFrame(dataSet.toImage(), "Original");

        NeuralNetwork neuralNetwork = getNeuralNetwork();
        //long start = new Date().getTime();
        final long[] last = {0};
        final int[] lastE = {0};

        imageFrame(process(ima.getWidth(null), ima.getHeight(null), neuralNetwork).toImage(), "Processed before");

        neuralNetwork.setHandler((e, i, t) -> {
            long current = new Date().getTime();
            if (e != lastE[0]) {
                saveNeuralNetwork(neuralNetwork);
                lastE[0] = e;
                ImageDataSet processed = process(ima.getWidth(null), ima.getHeight(null), neuralNetwork);
                BufferedImage out = processed.toImage();
                imageFrame(out, "Processed " + e);
                System.out.println("Learning: epoch: " + e + " iteraion: " + i + "/" + t);
                last[0] = current;
            }
            if (current - last[0] > 30000) {
                System.out.println("Learning: epoch: " + e + " iteraion: " + i + "/" + t);
                last[0] = current;
            }
        });
        neuralNetwork.learn(1000, 1E-4, dataSet);
        saveNeuralNetwork(neuralNetwork);

        ImageDataSet processed = process(ima.getWidth(null), ima.getHeight(null), neuralNetwork);
        imageFrame(ima, "Original");
        BufferedImage out = processed.toImage();
        imageFrame(out, "Processed");

        /*long time = benchmark(math, neuralNetwork);
        System.out.println(time + "ms");*/
    }

    @NotNull
    private static NeuralNetwork getNeuralNetwork() {
        try {
            FileInputStream fi = new FileInputStream("nn.nn");
            ObjectInputStream oi = new ObjectInputStream(fi);
            return NeuralNetwork.withSerializable((NeuralNetworkObj) oi.readObject(), true);
        } catch (Exception e) {
            NeuralNetwork neuralNetwork = new NeuralNetwork(
                    Layers.sigmoid(2).learning(),
                    Layers.sigmoid(1024).learning(),
                    Layers.sigmoid(1024).learning(),
                    Layers.sigmoid(3).learning()
            );

            /*NeuralNetwork neuralNetwork = new NeuralNetwork(
                    Layers.sum(2, 2),
                    Layers.sigmoid(2, 2).learning(),
                    Layers.sigmoid(2, 2).learning(),
                    Layers.sigmoid(3, 2).learning()
            );*/
            /*NeuralNetwork neuralNetwork = new NeuralNetwork(
                    Layers.sum(2).learning(),
                    Layers.sigmoid(16).learning(),
                    Layers.sigmoid(128).learning(),
                    Layers.sigmoid(128).learning(),
                    Layers.sigmoid(16).learning(),
                    Layers.sigmoid(3).learning()
            );*/
           /* NeuralNetwork neuralNetwork = new NeuralNetwork(
                    Layers.sum(2),
                    Layers.sigmoid(2).learning(),
                    Layers.sigmoid(2).learning(),
                    Layers.sigmoid(3).learning()
            );*/
            saveNeuralNetwork(neuralNetwork);
            return neuralNetwork;
        }
    }

    private static void saveNeuralNetwork(NeuralNetwork neuralNetwork) {
        try {

            FileOutputStream f = new FileOutputStream(new File("nn.nn"));
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(neuralNetwork.serializable());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static ImageDataSet process(int width, int height, NeuralNetwork neuralNetwork) {
        JMath math = JMath.get();
        List<Vector> inputs = IntStream.range(0, width)
                .mapToDouble(i -> i)
                .mapToObj(y -> IntStream.range(0, height)
                        .mapToDouble(i -> i)
                        .mapToObj(x -> math.vector(((double) x) / width, ((double) y) / height)).collect(Collectors.toList()))
                .flatMap(Collection::stream).collect(Collectors.toList());

        List<Vector> outputs = new ArrayList<>(width * height);
        inputs.forEach(i -> outputs.add(neuralNetwork.output(i)));
        return ImageDataSet.of(width, height, inputs, outputs);
    }

    private static long benchmark(JMath math, NeuralNetwork neuralNetwork) {
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

    @NotNull
    private static ImageFrame imageFrame(Image image, String title) {
        final ImageFrame current = new ImageFrame(title, image);
        current.setSize(192, 192);
        current.setVisible(true);
        return current;
    }
}
