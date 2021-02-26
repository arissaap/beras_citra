/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beras_ta;

import java.awt.geom.Rectangle2D;

/**
 *
 * @author Arissa
 */
public class SizeObject {

    //==> parameter lebar gambar

    public int width = 0;
    public int height = 0;

    public int[] size = null;

    public int maxSize = 0;

    public double[] sizeObjectFromMax;

    public void setSizeWidthHeight(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int[] getSizeObject(double[][] label, int count) {
        int[] sizeArea = new int[count + 1];
        System.out.println("Ukuran Beras : ");
        for (int x = 1; x <= count; x++) {
            for (int i = 2; i < width - 2; i++) {
                for (int j = 2; j < height - 2; j++) {
                    if (label[i][j] == x) {
                        sizeArea[x] = sizeArea[x] + 1;
                    }
                }
            }
            System.out.println("Index : " + String.valueOf(x) + " ukuran : " + String.valueOf(sizeArea[x]));
        }
        return sizeArea;
    }

    public int getMaxSize(int[] size, int count) {
        int max = 0;
        System.out.println("Ukuran Beras : ");
        for (int x = 1; x <= count; x++) {
            if (max <= size[x]) {
                max = size[x];
            }
            System.out.println("Index : " + String.valueOf(x) + " ukuran : " + String.valueOf(size[x]));
        }
        return max;
    }

    public double[] getSizeObjectFromMaxObject(int[] size, int max, int count) {
        double[] buff = new double[count + 1];
        for (int x = 1; x <= count; x++) {
            buff[x] = ((Math.abs((double) size[x] - (double) max) / (double) max)) * 100;
            buff[x] = Math.floor(buff[x]);
            buff[x] = 100 - buff[x];//100 - Math.floor(buff[x]);'
        }
        return buff;
    }

    public void processMaxObject(double[][] label, int count) {
        this.size = this.getSizeObject(label, count);
        this.maxSize = 364;
//        this.maxSize = 319 ;
        this.sizeObjectFromMax = getSizeObjectFromMaxObject(this.size, this.maxSize, count);
    }
}
