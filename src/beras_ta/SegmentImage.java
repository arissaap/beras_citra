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
public class SegmentImage {

    public int wht = 0;
    public int count = 0;

    public int width = 0;
    public int height = 0;

    public int cordinat_x[] = null;
    public int cordinat_y[] = null;

    public double[][] img = null;

    void setSizeWidthHeight(int width, int height) {
        this.wht = 0;
        this.width = width;
        this.height = height;
    }

    public double[][] zeros(int width, int height) {
        this.wht++;
        double[][] buffer = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                buffer[i][j] = 0;
            }
        }
        return buffer;
    }

    public double[][] label(double[][] bin) {
        count = 0;
        double[][] buffer = this.zeros(width, height);
        for (int i = 2; i < width - 2; i++) {
            for (int j = 2; j < height - 2; j++) {

                //===> chek apakah ini zona baru dan belum di beri label
                if (bin[i][j] == 1 && buffer[i][j] == 0) {
                    count++; //==> count jumlah data objeck yang tertangkap
                    buffer[i][j] = count; //==> beri tanda pada pixel pertama
                    for (int xc = 0; xc < 10; xc++) { //==> lakukan perulangan beberapa kali untuk memastikan objek sudah benar di segmentasi utuh
                        //===> routing seluruh gambar untuk mencari koneksi objeck tiap pixel
                        for (int i_ = 5; i_ < width - 5; i_++) {
                            for (int j_ = 5; j_ < height - 5; j_++) {
                                //===> jika terdeksi pixel putih , chek objek gabungan sekitar
                                if (bin[i_][j_] == 1 && buffer[i_][j_] == 0) {
                                    boolean isConnect = false;
                                    //==> chek objek di sekitar dengan ukuran kernel 4x4
                                    for (int i__ = (i_ - 4); i__ < (i_ + 4); i__++) {
                                        for (int j__ = (j_ - 4); j__ < (j_ + 4); j__++) {
                                            isConnect = bin[i__][j__] == 1 && buffer[i__][j__] == count;
                                            if (isConnect) {
                                                buffer[i_][j_] = count;
                                                break;
                                            }
                                        }
                                        if (isConnect) {
                                            break;
                                        }
                                    }
                                }

                            }

                        }
                    }
                }

            }
        }
        return buffer;
    }

    public double[][] noiseCorrectionLabel(double label[][], int count) {
        double[][] buffer = this.zeros(width, height);
        int counter = 0;
        for (int x = 1; x <= count; x++) {
            int count_ = 0;
            for (int i = 2; i < width - 2; i++) {
                for (int j = 2; j < height - 2; j++) {
                    if (label[i][j] == x) {
                        count_++;
                    }
                }
            }
            if (count_ <= 451 && count_ >= 36) {
                counter++;
                for (int i = 2; i < width - 2; i++) {
                    for (int j = 2; j < height - 2; j++) {
                        if (label[i][j] == x) {
                            buffer[i][j] = counter;
                        }
                    }
                }
            }
        }
        this.count = counter;
        return buffer;

    }

    public void getCordinat(double[][] label, int count) {
        this.cordinat_x = new int[count + 1];
        this.cordinat_y = new int[count + 1];
        for (int x = 1; x <= count; x++) {
            boolean isBreakNow = false;
            for (int i = 0; i < this.width; i++) {
                for (int j = 0; j < this.height; j++) {
                    isBreakNow = label[i][j] == x;
                    if (isBreakNow) {
                        this.cordinat_x[x] = i;
                        this.cordinat_y[x] = j;
                        break;
                    }
                }
                if (isBreakNow) {
                    break;
                }
            }
        }
    }

    public BufferedImage label2ColorImage(double labeling[][]) {
        BufferedImage buffer = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 1; x <= this.count; x++) {
            for (int i = 0; i < this.width; i++) {
                for (int j = 0; j < this.height; j++) {
                    if (labeling[i][j] == x) {
                        buffer.setRGB(i, j, new Color(255, 255, 255).getRGB());
                    } else if (labeling[i][j] == 0) {
                        buffer.setRGB(i, j, new Color(0, 0, 0).getRGB());
                    }
                }
            }
        }
        return buffer;
    }

    public BufferedImage seg2RgbImage(double[][][] rgb, double labeling[][]) {
        BufferedImage buffer = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        int[] size = new int[count + 1];
        for (int x = 1; x <= count; x++) {
            for (int i = 0; i < this.width; i++) {
                for (int j = 0; j < this.height; j++) {
                    if (labeling[i][j] == x) {
                        size[x] = size[x] + 1;

                        if (size[x] > 451 && size[x] < 36) {
                            buffer.setRGB(i, j, new Color(0, 0, 0).getRGB());
                        } else {
                            buffer.setRGB(i, j, new Color((int) rgb[i][j][0], (int) rgb[i][j][1], (int) rgb[i][j][2]).getRGB());
                        }
                    } else if (labeling[i][j] == 0){
                            buffer.setRGB(i, j, new Color(0, 0, 0).getRGB());
                    }
                }
            }
        }
        return buffer;
    }
}
