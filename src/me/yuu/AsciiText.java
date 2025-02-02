package me.yuu;

public class AsciiText {
    private ImageProcessor processor;
    private static int SCALE = 4;
    private static final String ASCIICHARS = " .*:o&8#@";
    public AsciiText(String fullPath){
        processor = new ImageProcessor(fullPath);
    }
    public void printAsciiText() throws InterruptedException {
        char[][] pixels = setAsciiText(downscaleMatrix(processor.getBrightness(), SCALE));
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[i].length; j++) {
                for (int k = 0; k < 3; k++) {
                    output.append(pixels[i][j]);
                }
            }
            output.append("\n"); // Add a newline after each row
        }

        System.out.print(output.toString());
        System.out.flush();
        Thread.sleep(25);

    }
    private static char[][] setAsciiText(int[][] BrightnessMatrix){
        char [][] asciiMatrix = new char[BrightnessMatrix.length][BrightnessMatrix[0].length];
        for (int i = 0; i < BrightnessMatrix.length; i++) {
            for (int j = 0; j < BrightnessMatrix[0].length; j++) {
                asciiMatrix[i][j] = Decode(BrightnessMatrix[i][j]);
            }
        }
        return asciiMatrix;
    }
    private static int[][] downscaleMatrix(int[][] matrix, int scaleFactor) {
        int newHeight = matrix.length / scaleFactor;
        int newWidth = matrix[0].length / scaleFactor;
        int[][] scaledMatrix = new int[newHeight][newWidth];

        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth; j++) {
                scaledMatrix[i][j] = matrix[i * scaleFactor][j * scaleFactor];
            }
        }
        return scaledMatrix;
    }
    private static char Decode(int brightness){
        final char str;
        if (brightness >= 230.0) {
            str = ' ';
        } else if (brightness >= 200.0) {
            str = '.';
        } else if (brightness >= 180.0) {
            str = '*';
        } else if (brightness >= 160.0) {
            str = ':';
        } else if (brightness >= 130.0) {
            str = 'o';
        } else if (brightness >= 100.0) {
            str = '&';
        } else if (brightness >= 70.0) {
            str = '8';
        } else if (brightness >= 50.0) {
            str = '#';
        } else {
            str = '@';
        }
        return str;
    }
}
