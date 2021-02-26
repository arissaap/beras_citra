/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beras_ta;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Arissa
 */
public class ImageController {

    public String path = "";
    public String name = "";
    public BufferedImage image = null;
    public boolean isOpenImage = false;
    public double percentSize = 0.0;
    public int width = 0;
    public int height = 0;
    public SegmentImage segment = new SegmentImage();

    void openImage() throws IOException {
        JFileChooser open = new JFileChooser("C:\\Users\\Arissa\\Pictures\\TA\\TA-Beras");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");
        open.setFileFilter(filter);
        open.setDialogTitle("Pilih Gambar Beras. . .");
        int returnVal = open.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            String buff = open.getSelectedFile().getAbsolutePath();
//            isOpenImage = buff.length() > 0;
            isOpenImage = !buff.isEmpty();
            if (isOpenImage && (this.segment.wht <= 3 || this.segment.wht >= 3)) {
                this.name = open.getSelectedFile().getName();
                this.path = buff;
                image = ImageIO.read(new File(path));
            }
        }
    }

    void setSizePercentResize(double percent) {
        this.percentSize = percent;
        this.width = (int) (this.image.getWidth() * (percent / 100));
        this.height = (int) (this.image.getHeight() * (percent / 100));
    }

    void setSizeWidthHeightResize(int width, int height, SegmentImage segment) {
        this.width = width;
        this.height = height;
        this.segment = segment;
    }

    BufferedImage getImageResize() {
        return this.resize(this.image, this.width, this.height);
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, BufferedImage.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

//    public BufferedImage drawStringFromCordinat(BufferedImage img, int[] x, int[] y, String[] str, int count) {
//        Image tmp = img.getScaledInstance(img.getWidth(), img.getHeight(), image.SCALE_SMOOTH);
//        BufferedImage dimg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
//
//        Graphics2D g2d = dimg.createGraphics();
//        g2d.drawImage(tmp, 0, 0, null);
//        for (int i = 1; i <= count; i++) {
//            g2d.drawString(str[i], x[i], y[i]);
//        }
//        g2d.dispose();
//
//        return dimg;
//    }

    public BufferedImage drawStringFromCordinat(BufferedImage img, int[] x, int[] y, int[] str, int count) {
        Image tmp = img.getScaledInstance(img.getWidth(), img.getHeight(), image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.setColor(Color.red);
        g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        g2d.drawImage(tmp, 0, 0, null);
        for (int i = 1; i <= count; i++) {
            g2d.drawString(String.valueOf(str[i]), x[i], y[i]);
        }
        g2d.dispose();

        return dimg;
    }

//    public BufferedImage drawStringFromCordinat(BufferedImage img, int[] x, int[] y, double[] str, int count) {
//        Image tmp = img.getScaledInstance(img.getWidth(), img.getHeight(), image.SCALE_SMOOTH);
//        BufferedImage dimg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
//
//        Graphics2D g2d = dimg.createGraphics();
//        g2d.drawImage(tmp, 0, 0, null);
//        for (int i = 1; i <= count; i++) {
//            g2d.drawString(String.valueOf(str[i]), x[i], y[i]);
//        }
//        g2d.dispose();
//
//        return dimg;
//    }

//    public BufferedImage drawYesOrNoFromCordinat(BufferedImage img, int[] x, int[] y, double[] str, int minYes, int count) {
//        Image tmp = img.getScaledInstance(img.getWidth(), img.getHeight(), image.SCALE_SMOOTH);
//        BufferedImage dimg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
//
//        Graphics2D g2d = dimg.createGraphics();
//        g2d.drawImage(tmp, 0, 0, null);
//        for (int i = 1; i <= count; i++) {
//            if (str[i] <= minYes) {
//                g2d.drawString("tidak", x[i], y[i]);
//            } else {
//                g2d.drawString("ya", x[i], y[i]);
//            }
//        }
//        g2d.dispose();
//
//        return dimg;
//    }

    public BufferedImage drawYesOrNoFromCordinat(BufferedImage img, int[] x, int[] y, boolean[] trueOrFalse, String true_, String false_,int[] str, int count) {
        Image tmp = img.getScaledInstance(img.getWidth(), img.getHeight(), image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.setFont(new Font("Comic Sans MS", Font.BOLD, 13));
        g2d.drawImage(tmp, 0, 0, null);
        for (int i = 1; i <= count; i++) {
            if (trueOrFalse[i]) {
                g2d.setColor(Color.GREEN);
                g2d.drawString(String.valueOf(str[i]), x[i], y[i]);
            } else {
                g2d.setColor(Color.RED);
                g2d.drawString(String.valueOf(str[i]) , x[i], y[i]);
            }
        }
        g2d.dispose();

        return dimg;
    }

}
