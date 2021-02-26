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
public class Analyze_cleanRice {
    public int width = 0 ;
    public int height = 0 ;
    public double data[][] ;
    
    public double nSize[];
    public double nClean[];
    public double nPercent[];
    public double isClean[];
    
    public boolean cleanRice[];
    public int nCleanRice ;
    
    public double percent = 0.0 ;
    
    void setSizeWidthHeight( int width , int height ){
        this.width = width ;
        this.height = height;
    }
    
    public void analyze_cleanPixel( double hsv[][][]){
       this.data = new double[this.width][this.height];
       for( int i = 0 ; i <  this.width ; i++  )
           for( int j = 0 ; j <  this.height ; j++  )
               if( this.isClean( hsv[i][j][0], hsv[i][j][1], hsv[i][j][2]) )
                   data[i][j] = 255  ;
               else
                   data[i][j] = 0 ;
    }
    
    public boolean isClean(double hue , double saturation , double value){
        boolean sts1 = true ;
        sts1 = sts1 && ( hue < 0.10 );
        sts1 = sts1 && ( saturation > 0.20 );

        return sts1 ;
    }
    
    public void getPercentClean(double label[][] , int counts){
        this.nSize = new double[counts+1];
        this.nClean = new double[counts+1];
        this.nPercent = new double[counts+1];
        
        this.cleanRice = new boolean[counts+1];
        this.nCleanRice = 0 ;
        
        for( int i = 0 ; i <  this.width ; i++  )
           for( int j = 0 ; j <  this.height ; j++  ){
               if( label[i][j] >= 0 && label[i][j] <= counts)
                   this.nSize[(int)label[i][j]]++ ;
               if( this.data[i][j] < 255 && label[i][j] >= 1 && label[i][j] <= counts) 
                   this.nClean[(int)label[i][j]]++ ;
           }
        System.out.println("==> percent Bersih ");
        for( int x = 1 ; x <= counts ; x++  ){
           this.nPercent[x] = ( 1- ( Math.abs( this.nClean[x] - this.nSize[x] ) / this.nSize[x] )) * 100 ;
           //this.nPercent[x] = ( this.nSize[x] - this.nClean[x]) ;
           if( this.nPercent[x] >= 97 ){
               this.cleanRice[x] = true ;
               this.nCleanRice++ ;
           }else{
               this.cleanRice[x] = false ;
           }
//           System.out.println( "index :" + String.valueOf(x) + " ukuran   :" + String.valueOf(nSize[x]) + " " +  " jumlah pixel bersih  :" + String.valueOf(nClean[x]) + " " + " percent :" + String.valueOf(nPercent[x]) + "% " + " status : " + String.valueOf(this.cleanRice[x]));
           System.out.println( "index :" + String.valueOf(x) + " " +  " jumlah pixel bersih  :" + String.valueOf(nClean[x]) + " " + " percent :" + String.valueOf(nPercent[x]) + "% " + " status : " + String.valueOf(this.cleanRice[x]));
        }
        
        this.percent = ( 1 - ( Math.abs(this.nCleanRice - (double)counts) / (double)counts ) ) * 100 ;
        System.out.println( "=====> percent beras Bersih " + String.valueOf(this.percent) );
   
    }
    
    public void process( double hsv[][][], double label[][] , int counts ){
        this.analyze_cleanPixel(hsv);
        this.getPercentClean(label, counts);
    }
}
