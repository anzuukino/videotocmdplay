package me.yuu;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageProcessor {
    private final String imageType = "png";
    private String imagePath;
    public ImageProcessor(String imageFullPath){
        imagePath = imageFullPath;
    }
    public int[][] getBrightness(){
        BufferedImage image = getImage(imagePath);
        int [][] pixels = getPixels(image);
        return setBrightness(pixels);
    }
    private BufferedImage getImage(String imageFullPath){
        BufferedImage image = null;
        try{
            if (imageFullPath == null) {
                throw new NullPointerException("Image full path cannot be null or empty");
            }
            String tempImagePath = imageFullPath;
            image = ImageIO.read(new File(tempImagePath));

        }catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }
    private static int[][] getPixels(BufferedImage image){
        if (image == null) {
            throw new IllegalArgumentException();
        }
        int h = image.getHeight();
        int w = image.getWidth();
        int[][] pixels = new int[h][w];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                pixels[y][x] = image.getRGB(x, y);
            }
        }
        return pixels;
    }

    private static int[][] setBrightness(int[][] pixels){
        int r,g,b;
        int average;
        int [][] avg = new int[pixels.length][pixels[0].length];
        for (int x = 0; x < pixels.length; x++) {
            for (int y = 0; y < pixels[0].length; y++) {
                r = (pixels[x][y]>>16) & 0xff;
                g = (pixels[x][y]>>8) & 0xff;
                b = pixels[x][y] & 0xff;
                average = (r+g+b)/3;
                avg[x][y] = average;
            }
        }
        return avg;
    }
    
}
