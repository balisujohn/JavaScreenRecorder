package com.company;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.media.MediaLocator;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by John on 12/31/2016.
 */
    public class ImageQueue implements Runnable {

    ArrayList<ImageQueueItem> a;
    double startTime;
    private int frameRate;
    private int frameCount;
    private Vector imageVect;
    public boolean terminate = false;


    public ImageQueue( Vector v, double startTime) {
        a = new ArrayList<>();
        this.frameRate = frameRate;
        imageVect = v;
        this.startTime = startTime;
    }

    public void enqueue(ImageQueueItem b) {
        a.add(b);
    }

    public ImageQueueItem dequeue() {
        if (!isEmpty()) return a.remove(0);

        throw new EmptyStackException();

    }

    public boolean isEmpty() {
        return a.isEmpty();
    }

    public void terminate() {
        terminate = true;
    }

    public void run() {
        while (!(terminate && isEmpty())) {
            try {

                ImageQueueItem item = dequeue();
                System.out.println(a.size());



                Iterator iter = ImageIO.getImageWritersByFormatName("JPG");
                if (iter.hasNext()) {
                    ImageWriter writer = (ImageWriter) iter.next();
                    ImageWriteParam iwp = writer.getDefaultWriteParam();
                    iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                    iwp.setCompressionQuality(1f);


                    FileImageOutputStream output = new FileImageOutputStream(item.getFile());
                    writer.setOutput(output);
                    IIOImage iioImage = new IIOImage(item.getImage(), null, null);
                    writer.write(null, iioImage, iwp);
                }
                item.getImage().getGraphics().dispose();
                frameCount += 1;
            } catch (IOException e) {
                System.out.println("AN IO EXCEPTION OCCURRED");
            }
            catch (EmptyStackException e)
            {
                System.out.println("NO QUEUED IMAGES");
            }
        }

        double timeChangeMillis = System.currentTimeMillis() - startTime;
        frameRate = (int)((frameCount * 1000) / timeChangeMillis);
        JpegImagesToMovie outputSystem = new JpegImagesToMovie();

        MediaLocator m = JpegImagesToMovie.createMediaLocator("File\\test.avi");

        outputSystem.doIt(854,480,frameRate, imageVect ,m);
    }
}