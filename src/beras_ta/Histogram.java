/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beras_ta;

/**
 *
 * @author Arissa
 */
public class Histogram {

    //==> array untuk menampung hitogram grayscale
    double hist[][] = null;

    //====> array untuk histogram hsv
    //==> index 0 ==> Berisi nilai Histogram 0 sampai 255
    //==> index 1 ==> Berisi nilai index gambar
    //==> index 2 ==> Berisi nilai index hsv ( 0 => hue , 1 => saturation , 2 => value ) 
    double hist_hsv[][][] = null;

    //====> array untuk menampung min - max histogram
    double minMaxHist[][][] = null;

    double maxHist[] = null;

    public int width = 0;
    public int height = 0;

    public int whiteLimit = 240;
    public boolean whiteEachObject[] = null;
    public int countWhiteObject;

    void setSizeWidthHeight(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public double[][] zeros(int width, int height) {
        double[][] buffer = new double[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                buffer[i][j] = 0;
            }
        }
        return buffer;
    }

    public double[][][] zeros(int width, int height, int x_) {
        double[][][] buffer = new double[width][height][x_];
        for (int x = 0; x < 3; x++) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    buffer[i][j][x] = 0;
                }
            }
        }
        return buffer;
    }

    public double[][] getHistogram(double[][] gray, double[][] label, int counts) {
        double[][] buff = this.zeros(255 + 1, counts + 1);
        for (int x = 1; x <= counts; x++) {
            for (int i = 2; i < width - 2; i++) {
                for (int j = 2; j < height - 2; j++) {
                    if (label[i][j] == x) {
                        buff[(int) gray[i][j]][x] = buff[(int) gray[i][j]][x] + 1;
                    }
                }
            }
        }
        return buff;
    }

    public double[] maxEachsObjeck(double[][] hist_, int counts) {
        double[] buff = new double[counts + 1];
        for (int x = 0; x <= counts; x++) {
            double[] buff_ = new double[255];
            for (int i = 0; i < 255; i++) {
                buff_[i] = hist_[i][x];
            }
            buff[x] = this.maxEachObjeck(buff_);
        }
        return buff;
    }

    public double maxEachObjeck(double[] hist_) {
        double buffer = 0;
        double index = 0;
        for (int x = 0; x < hist_.length; x++) {
            if (buffer < hist_[x]) {
                buffer = hist_[x];
                index = x;
            }
        }
        return index;
    }

    public boolean[] getWhiteOBject(double[] indexMaxEachObject, int count) {
        this.countWhiteObject = 0;
        boolean[] buff = new boolean[count + 1];
        for (int i = 0; i <= count; i++) {
            buff[i] = false;
        }
        for (int x = 1; x <= count; x++) {
            if (indexMaxEachObject[x] >= this.whiteLimit) {
                buff[x] = true;
                countWhiteObject++;
            }
        }
        return buff;
    }

    //=> 0 : hist => 1 : no label => 2 : index hsv
    public double[][][] getHistogram_hsv(double[][][] hsv, double[][] label, int counts) {
        double[][][] buff = this.zeros(255 + 1, counts + 1, 3);
        for (int z = 0; z < 3; z++) {
            for (int x = 0; x <= counts; x++) {
                for (int i = 1; i <= width - 2; i++) {
                    for (int j = 2; j < height - 2; j++) {
                        if (label[i][j] == x) {
                            buff[(int) hsv[i][j][z]][x][z] = buff[(int) hsv[i][j][z]][x][z] + 1;
                        }
                    }
                }
            }
        }
        return buff;
    }

    public double[][][] getMinMaxHistogram_hsv(double[][][] histHsv, int counts) {
        double[][][] buff = this.zeros(2, counts + 1, 3);
        for (int z = 0; z < 3; z++) {
            for (int x = 1; x <= counts; x++) {
                buff[0][x][z] = -1;
                buff[1][x][z] = -1;
                for (int i = 0; i < 256; i++) {
                    if (buff[0][x][z] == -1 && histHsv[i][x][z] > 2) {
                        buff[0][x][z] = i;
                    } else if (histHsv[i][x][z] > 2) {
                        buff[1][x][z] = i;
                    }
                }
            }
        }
        return buff;
    }

    public void getDataMaxEachObjeckOnLabelImage(double[][] gray, double[][] label, int counts) {
        this.hist = this.getHistogram(gray, label, counts);
        this.maxHist = this.maxEachsObjeck(this.hist, counts);
        this.whiteEachObject = this.getWhiteOBject(this.maxHist, counts);
    }
}
