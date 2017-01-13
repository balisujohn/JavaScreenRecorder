package com.company;

import javax.imageio.ImageIO;
import javax.media.MediaLocator;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.SampleModel;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by John on 12/26/2016.
 */
public class Recorder implements Runnable{

    Vector v;
    int frameRate;
    private double startTime;
    File root;
    private ImageQueue imageQueue;
    private boolean terminationFlag = false;

    public  Recorder(int frameRate) {
        v = new Vector();
        root = new File("sourceimages");
        this.frameRate = frameRate;
    }


    @Override
    public void run() {
        int runCounter = 0;
        startTime = System.currentTimeMillis();
        imageQueue = new ImageQueue( v, startTime);
        new Thread(imageQueue).start();
        while (!terminationFlag){
            try {
                runCounter += 1;
                Thread.sleep(1000/frameRate);
                Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                BufferedImage capture = new Robot().createScreenCapture(screenRect);

                Image scaled = capture.getScaledInstance(854, 480,
                        BufferedImage.SCALE_AREA_AVERAGING);
                BufferedImage bufferedScaled = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null), BufferedImage.TYPE_INT_RGB);

                // Draw the image on to the buffered image
                Graphics2D bGr = bufferedScaled.createGraphics();
                bGr.drawImage(scaled, 0, 0, null);
                bGr.dispose();



                if (!root.exists()) {
                    root.mkdir();
                }

                File image = new File(root.getPath() + "//" + runCounter + ".jpg");
              //  ImageIO.write(capture, "jpg", image);
                v.add(image.getPath());
                ImageQueueItem item = new ImageQueueItem(image, bufferedScaled);


                    imageQueue.enqueue(item);


              /*  JFrame frame = new JFrame();
                frame.getContentPane().setLayout(new FlowLayout());
                frame.getContentPane().add(new JLabel(new ImageIcon(capture)));
                frame.pack();
                frame.setVisible(true);*/
            } catch (AWTException e) {
                System.out.println("AN AWT EXCEPTION OCCURRED");
            }catch (InterruptedException e)
            {
                System.out.println("THREAD INTERRUPTED");
            }catch (OutOfMemoryError e)
            {
                System.out.println("OUT OF MEMORY");

            }

        }
        imageQueue.terminate();
    /*
        JpegImagesToMovie outputSystem = new JpegImagesToMovie();

        MediaLocator m = JpegImagesToMovie.createMediaLocator("File\\test.avi");

        outputSystem.doIt(1920,1080,frameRate, v ,m);
        */
    }


    public void terminate()
    {
        terminationFlag = true;
    }
}
