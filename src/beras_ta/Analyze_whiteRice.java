/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beras_ta;

/**
 *
 * @author Arissa
 */
public class Analyze_whiteRice {

    public int width = 0;
    public int height = 0;
    public double data[][];
    public double nSize[];
    public double nWhite[];
    public double nTidak[];
    public double nPercent[];
    public double isWhite[];

    public boolean whiteRice[];
    public int nWhiteRice;

    public double percent = 0.0;

    Analyze_cleanRice clean = new Analyze_cleanRice();

    void setSizeWidthHeight(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void analyze_whitePixel(double hsv[][][]) {
        this.data = new double[this.width][this.height];
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (this.isWhite(hsv[i][j][0], hsv[i][j][1], hsv[i][j][2])) {
                    data[i][j] = 255;
                } else if (clean.isClean(hsv[i][j][0], hsv[i][j][1], hsv[i][j][2])) {
                    data[i][j] = 300;
                } else {
                    data[i][j] = 0;
                }
            }
        }
    }

    public boolean isWhite(double hue, double saturation, double value) {

        //boolean sts1 = true ;
//        sts1 = sts1 && ( saturation > 0.1 && saturation < 0.3 );
//        sts1 = sts1 && ( value > 0.7 && value < 0.8 );
        boolean sts2 = true;
        sts2 = sts2 && (hue >= 0.2 && hue <= 0.8);
        sts2 = sts2 && (saturation <= 0.4);
        sts2 = sts2 && (value >= 0.60);

        //boolean sts = sts1 || sts2  ;
        return sts2;
    }

    public void getPercentWhite(double label[][], int counts) {
        this.nSize = new double[counts + 1];
        this.nTidak = new double[counts + 1];
        this.nWhite = new double[counts + 1];
        this.nPercent = new double[counts + 1];

        this.whiteRice = new boolean[counts + 1];
        this.nWhiteRice = 0;

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (label[i][j] >= 1 && label[i][j] <= counts) {
                    this.nSize[(int) label[i][j]]++;
                }
                if (this.data[i][j] > 100 && label[i][j] >= 1 && label[i][j] <= counts) {
                    this.nWhite[(int) label[i][j]]++;
                }
                if (this.data[i][j] == 300 && label[i][j] >= 1 && label[i][j] <= counts) {
                    this.nTidak[(int) label[i][j]]++;
                }
            }
        }
        System.out.println("==> percent white ");
        for (int x = 1; x <= counts; x++) {
            //if (this.nSize[x] <= 451 && this.nSize[x] >=36) {
            this.nSize[x] = Math.abs(this.nSize[x] - this.nTidak[x]);
            this.nPercent[x] = (1 - (Math.abs((this.nWhite[x]) - this.nSize[x]) / this.nSize[x])) * 100;
//           if( this.nPercent[x] > 50 ){
            if (this.nPercent[x] >= 80) {
                this.whiteRice[x] = true;
                this.nWhiteRice++;
            } else {
                this.whiteRice[x] = false;
            }
//            System.out.println("index :" + String.valueOf(x) + " ukuran   :" + String.valueOf(nSize[x]) + " " + " putih  :" + String.valueOf(nWhite[x]) + " " + " percent :" + String.valueOf(nPercent[x]) + "% " + " status : " + String.valueOf(this.whiteRice[x]));
            System.out.println("index :" + String.valueOf(x) + " " + " jumlah pixel putih  :" + String.valueOf(nWhite[x]) + " " + " percent :" + String.valueOf(nPercent[x]) + "% " + " status : " + String.valueOf(this.whiteRice[x]));
            //}else this.whiteRice=null;
        }

        this.percent = (1 - (Math.abs(this.nWhiteRice - (double) counts) / (double) counts)) * 100;
        System.out.println("=====> percent beras putih " + String.valueOf(this.percent));

    }

    public void process(double hsv[][][], double label[][], int counts) {
        this.analyze_whitePixel(hsv);
        this.getPercentWhite(label, counts);
    }
}
