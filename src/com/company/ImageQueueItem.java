package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.Buffer;

/**
 * Created by John on 1/2/2017.
 */
public class ImageQueueItem {

    private File file;
    private BufferedImage image;


    public ImageQueueItem(File file, BufferedImage image)
    {
        if(image==null) throw new IllegalArgumentException();
        this.file = file;
        this.image = image;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
