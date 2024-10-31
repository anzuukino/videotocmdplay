package me.yuu;

import javax.sound.sampled.*;
import java.io.File;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class Main {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }
    public static String currentDirectory = System.getProperty("user.dir");
    public static String output_path = currentDirectory + "/output_frames/";
    public static void main(String[] args) throws InterruptedException {
        extract_frame();
        Thread musicThread = new Thread(Main::playMusic);
        musicThread.start();

        Thread printText = new Thread(Main::Image_Process);
        printText.start();
    }
    public static void playMusic(){
        try {
            File file = new File(currentDirectory + "/music.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();

            clip.drain();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void extract_frame() {
        String video_path = currentDirectory + "/BadApple.mp4";
        System.out.println(video_path);
        File outputDir = new File(output_path);
        if (!outputDir.exists()) {
            outputDir.mkdirs(); // Tạo thư mục nếu chưa tồn tại
        }else{
            return;
        }
        VideoCapture capture = new VideoCapture(video_path);
        if (!capture.isOpened()) {
            System.out.println("Video capture is not opened.");
            return;
        }

        Mat frame = new Mat();
        int frame_count = 0;
        while (capture.read(frame)) {
            if (!frame.empty()) {
                String filename = output_path + frame_count + ".png";
                Imgcodecs.imwrite(filename, frame);
                System.out.println("Saved "+ filename);
                frame_count++;
            }else{
                System.out.println("Frame is empty");
                break;
            }
        }
        capture.release();
        System.out.println("Frame saved " + frame_count + " frames");
    }
    private static void Image_Process() {
        for (int i = 0; i <= 6571; i++ ){
            AsciiText temp = new AsciiText(output_path + i + ".png");
            try{
                temp.printAsciiText();
            }catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Image processing was interrupted.");
                break;
            }
            clearConsole();
        }


    }
    private static void clearConsole() {
        System.out.print("\033[H\033[2J"); // ANSI escape code to clear screen and reset cursor
        System.out.flush();
    }
}