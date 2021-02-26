/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beras_ta;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Arissa
 */
public class ImageArray {

    double rgb[][][];
    double hsv[][][];
    double gray[][];
    double bin[][];
    public int width = 0;
    public int height = 0;

    void setSizeWidthHeight(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public double[][][] BufferedImageIntoArray(BufferedImage image) {
        double buff[][][] = new double[this.width][this.height][3];
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                int rgb = image.getRGB(i, j);
                int red = (rgb >> 16) & 0x0FF;
                int green = (rgb >> 8) & 0x0FF;
                int blue = (rgb) & 0x0FF;
                buff[i][j][0] = red;
                buff[i][j][1] = green;
                buff[i][j][2] = blue;
            }
        }
        return buff;
    }

    public BufferedImage rgbArray2Image(double[][][] rgb) {
        BufferedImage buffer = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                buffer.setRGB(i, j, new Color((int) rgb[i][j][0], (int) rgb[i][j][1], (int) rgb[i][j][2]).getRGB());
            }
        }
        return buffer;
    }
    
    public BufferedImage grayArray2Image(double[][] gray) {
        BufferedImage buffer = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                buffer.setRGB(i, j, new Color((int) gray[i][j], (int) gray[i][j], (int) gray[i][j]).getRGB());
            }
        }
        return buffer;
    }

    public BufferedImage binArray2Image(double[][] bin) {
        BufferedImage buffer = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (bin[i][j] == 0) {
                    buffer.setRGB(i, j, new Color(0, 0, 0).getRGB());
                } else {
                    buffer.setRGB(i, j, new Color(255, 255, 255).getRGB());
                }
            }
        }
        return buffer;
    }

    public BufferedImage segArray2Image(double[][] bin) {
        BufferedImage buffer = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (bin[i][j] == 0) {
                    buffer.setRGB(i, j, new Color(0, 0, 0).getRGB());
                } else {
                    buffer.setRGB(i, j, new Color(255, 255, 255).getRGB());
                }
            }
        }
        return buffer;
    }

    public BufferedImage cekArray2Image(double[][] bin) {
        BufferedImage buffer = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (bin[i][j] == 0) {
                    buffer.setRGB(i, j, new Color(0, 0, 0).getRGB());
                } else if (bin[i][j] == 1) {
                    buffer.setRGB(i, j, new Color(255, 255, 0).getRGB());
                }  
                else {
                    buffer.setRGB(i, j, new Color(255, 0, 0).getRGB());
                }
            }
        }
        return buffer;
    }
    
    public BufferedImage cekBersih2Image(double[][] bin) {
        BufferedImage buffer = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (bin[i][j] == 0) {
                    buffer.setRGB(i, j, new Color(255, 0, 0).getRGB());
                }  
                else {
                    buffer.setRGB(i, j, new Color(0, 0, 0).getRGB());
                }
            }
        }
        return buffer;
    }

    public double[][] rgbArray2Gray(double[][][] rgb) {
        double[][] buffer = new double[this.width][this.height];
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                double gray = (rgb[i][j][0] + rgb[i][j][1] + rgb[i][j][2]) / 3.0;
                buffer[i][j] = gray;
            }
        }
        return buffer;
    }

    public double[][] grayArray2Bin(double[][] gray) {
        double tresh = getTreshold(gray);
//        double tresh = 112;
        double[][] buffer = new double[this.width][this.height];
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (gray[i][j] < tresh) {
                    buffer[i][j] = 0;
                } else {
                    buffer[i][j] = 1;
                }
            }
        }
        return buffer;
    }

    public double[][][] rgbArray2HSV(double[][][] rgb) {
        double[][][] buffer = new double[this.width][this.height][(int) 3];
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {

                float[] hsv = new float[3];
                Color.RGBtoHSB((int) rgb[i][j][0], (int) rgb[i][j][1], (int) rgb[i][j][2], hsv);
                buffer[i][j][0] = hsv[0];
                buffer[i][j][1] = hsv[1];
                buffer[i][j][2] = hsv[2];
//                System.out.println("indeks 0" +String.valueOf(i)+":"+String.valueOf(buffer[i][j][0]));
//                System.out.println("indeks 1" +String.valueOf(i)+":"+String.valueOf(buffer[i][j][1]));
//                System.out.println("indeks 2" +String.valueOf(i)+":"+String.valueOf(buffer[i][j][2]));
            }
        }
        return buffer;
    }

//    private double getMaxFrom3Value(double val_0, double val_1, double val_2) {
//        //System.out.println("====> " + String.valueOf(val_0) + " " + String.valueOf(val_1) + "  " + String.valueOf(val_2));
//        if (val_0 >= val_1 && val_0 >= val_2) {
//            return val_0;
//        } else if (val_1 >= val_0 && val_1 >= val_2) {
//            return val_1;
//        } else if (val_2 >= val_1 && val_2 >= val_0) {
//            return val_2;
//        }
//        return 0;
//    }
//
//    private double getMinFrom3Value(double val_0, double val_1, double val_2) {
//        if (val_0 <= val_1 && val_0 <= val_2) {
//            return val_0;
//        } else if (val_1 <= val_0 && val_1 <= val_2) {
//            return val_1;
//        } else {
//            return val_2;
//        }
//    }

    public double getTreshold(double[][] gray) {
        double min = 1000;
        double max = 0;
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {

                if (gray[i][j] < min) {
                    min = gray[i][j];
                }
                if (gray[i][j] > max) {
                    max = gray[i][j];
                }
            }
        }
        min = min + ((max - min) / 2);
        return min - (min * 0.35);
    }

}
