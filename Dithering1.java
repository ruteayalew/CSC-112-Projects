import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;
import java.util.Random;
import java.awt.Color;

public class Dithering1 {

    static int h = 512;
    static int w = 512;
    static int[][] pixels = new int[512][512];
    static byte[][] pixels2 = new byte[512][512];

    static int[][] mask = {{1, 7, 4}, {5, 8, 3}, {6, 7, 9}};           //mask array

    static int maskValue = 0;
    static int p = 0;
    static int q = 0;

    static int error = 0;
    static int d = 0;


    static long Millis = 0;
    public static void main(String[] args) throws IOException {


        File f = new File("Addie.raw");
        FileInputStream inputStream = new FileInputStream("Addie.raw"); //reads the raw data
        FileOutputStream textStream = new FileOutputStream("report.txt"); //to write raw bytes to a file (unreadable)
        PrintWriter outText = new PrintWriter(textStream);   //this writes a text file to open and read

        //loops store raw bytes as read by inputStream into an array called pixels
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                pixels[i][j] = inputStream.read();
            }
        }
        inputStream.close();

        File f2 = new File("IanColor1.raw");
        FileInputStream inputStream2 = new FileInputStream("IanColor1.raw"); //reads the raw data
        FileOutputStream textStream2 = new FileOutputStream("report.txt"); //to write raw bytes to a file (unreadable)
        PrintWriter outText2 = new PrintWriter(textStream);   //this writes a text file to open and read

        //loops store raw bytes as read by inputStream into an array called pixels
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                pixels2[i][j] = (byte) inputStream2.read();
            }
        }
        inputStream2.close();


        Scanner scnr = new Scanner(System.in);
        //prompt the user for their desired dithering process
        System.out.println("Select how you would like to process the image:");
        System.out.println("1. Thresholding");
        System.out.println("2. Random Dithering");
        System.out.println("3. Pattern Dithering");
        System.out.println("4. Error Diffusion");
        System.out.println("5. Color to Black & White");

        long start = 0;
        long end = 0;

        start= getStartTime();



        //reads the user's entry & performs selected dithering
        while(d==0) { //while loop holding the user response assignment and switch statement allow the user to be
                    //continuosly prompted to submit a valid selection or to exit
            String response = scnr.nextLine();
            switch (response) {

                case "1":
                    threshold(pixels);
                    //streams the post editted array into a new file that is saved
                    FileOutputStream outputStream1 = new FileOutputStream("AddieThreshold.raw"); //Writes new file of dithered image
                    for (int i = 0; i < h; i++) {
                        for (int j = 0; j < w; j++) {
                            outputStream1.write(pixels[i][j]);
                        }
                    }
                    outputStream1.close();
                    outText.close();
                    end = getEndTime();
                    Millis = getMillis(end, start);
                    System.out.println("Time of Completion= " + Millis + "Milliseconds");
                    break;
                case "2":
                    randomDith(pixels);
                    //streams the post editted array into a new file that is saved
                    FileOutputStream outputStream2 = new FileOutputStream("AddieRandomDith.raw"); //Writes new file of dithered image
                    for (int i = 0; i < h; i++) {
                        for (int j = 0; j < w; j++) {
                            outputStream2.write(pixels[i][j]);
                        }
                    }
                    outputStream2.close();
                    outText.close();
                    getEndTime();
                    getMillis(end, start);
                    System.out.println("Time of Completion= " + Millis + "Milliseconds");
                    break;
                case "3":
                    patternDith(pixels);
                    //streams the post editted array into a new file that is saved
                    FileOutputStream outputStream3 = new FileOutputStream("AddiePatternDith.raw"); //Writes new file of dithered image
                    for (int i = 0; i < h; i++) {
                        for (int j = 0; j < w; j++) {
                            outputStream3.write(pixels[i][j]);
                        }
                    }
                    outputStream3.close();
                    outText.close();
                    getEndTime();
                    getMillis(end, start);
                    System.out.println("Time of Completion= " + Millis + "Milliseconds");
                    break;
                case "4":
                    errorDiff(pixels);
                    //streams the post editted array into a new file that is saved
                    FileOutputStream outputStream4 = new FileOutputStream("AddieErrorDiff.raw"); //Writes new file of dithered image
                    for (int i = 0; i < h; i++) {
                        for (int j = 0; j < w; j++) {
                            outputStream4.write(pixels[i][j]);
                        }
                    }
                    outputStream4.close();
                    outText.close();
                    getEndTime();
                    getMillis(end, start);
                    System.out.println("Time of Completion= " + Millis + "Milliseconds");
                    break;
                case "5":
                    int r=0;
                    int g=0;
                    int b=0;
                    Color col = new Color( r, g, b );
                    colorToBW(pixels2, r,g,b); //this selection uses the second array which contains the color raw file
                    //streams the post editted array into a new file that is saved
                    FileOutputStream outputStream5 = new FileOutputStream("IanB&W.raw"); //Writes new file of dithered image
                    for (int x = 0; x < h; x++) {
                        for (int y = 0; y < w; y++) {
                            outputStream5.write(pixels2[x][y]);
                        }
                    }
                    outputStream5.close();
                    outText2.close();
                    getEndTime();
                    getMillis(end, start);
                    System.out.println("Time of Completion= " + Millis + "Milliseconds");
                    break;
                case "EXIT":
                    System.out.println("Have a nice day!");
                    d++;
                    break;
                default:
                    System.out.println("Invalid response: Please enter the number of your desired selection or type EXIT");
                    break;
            }
        }
    }

    private static long getStartTime() {
        long start = System.currentTimeMillis();
        return start;
    }
    private static long getEndTime() {
        long end  =  System.currentTimeMillis();
        return end;
    }
    private static long getMillis(long end, long start) {
        long Millis = end-start;
        return Millis;
    }

    //Start of my methods!--------------------------------------------------
    private static void threshold(int[][] pixels) {
        System.out.println("You chose thresholding!");

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (pixels[i][j] < 128) {
                    pixels[i][j] = 0;
                } else if (pixels[i][j] >= 128) {
                    pixels[i][j] = 255;
                }
            }
        }
    }

    private static void randomDith(int[][] pixels) {
        System.out.println("You chose random dithering!");

        Random rand = new Random();
        int randomNum = rand.nextInt(128);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (pixels[i][j] < randomNum) {
                    pixels[i][j] = 0;
                } else if (pixels[i][j] >= randomNum) {
                    pixels[i][j] = 255;
                }
            }
        }
    }

    private static void patternDith(int[][] pixels) {
        System.out.println("You chose pattern dithering!");
        //loop compares the value of pixel/25.6 to the corresponding mask number to assign 0 or 255 value
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                getMaskValue(p, q);  //calls method to return the value of the int in mask & passes p & q aka index values
                int temp = (int) (pixels[i][j] / 25.6);
                if (temp < maskValue) {
                    pixels[i][j] = 0;
                } else if (temp >= maskValue) {
                    pixels[i][j] = 255;
                }
                q++;  //the row number in the mask array is increased by 1 and reset to zero once it hits 2
                if (q > 2) {
                    q = 0;
                }
            }
            p++;   //the column number in the mask array is increased by 1 and reset to zero once it hits 2
            if (p > 2) {
                p = 0;
            }
        }
    }

    //method used for pattern dithering to return the value in the mask array with p and q aka the indexes
    private static int getMaskValue(int p, int q) {
        maskValue = mask[p][q];
        return maskValue;
    }

    private static void errorDiff(int[][] pixels) {
        System.out.println("You chose error diffusion!");
        //the two nested for loops increment by 2 to avoid editing an already touched pixel
        for (int i = 1; i < h-1; ++i) {
            for (int j = 0; j < w-1; ++i) {
                if (pixels[i][j] < 128) {
                    getError(pixels[i][j], 0); //calls getError method to calculate error passing value and 0
                    pixels[i][j] = 0;
                    rippleErrorAdd(i, j, error); //calls another method to increment surrounding pixels up
                } else if (pixels[i][j] >= 128) {
                    getError(pixels[i][j], 255);//calls getError method to calculate error passing value and 255
                    pixels[i][j] = 255;
                    rippleErrorSub(i, j, error); //calls another method to increment surrounding pixels down
                }
            }
        }
    }

    //this method calculates the error for error diffusion method
    private static int getError(int t, int r) {
        error = Math.abs(t - r); //t is the pixel value and r is either 0 or 255
        return error;
    }

    //this method disperses the calculated error to increase the surrounding pixels
    private static void rippleErrorAdd(int i, int j, int error) {
        if((i<h)&&(j<w)) {
            pixels[i][j + 1] = pixels[i][j + 1] + ((7 / 16) * error);

            pixels[i + 1][j + 1] = pixels[i + 1][j + 1] + ((1 / 16) * error);

            pixels[i + 1][j] = pixels[i + 1][j] + ((5 / 16) * error);
        }
        if((i<h)&&(j>0)){ //to prevent the column number from going to -1
            pixels[i + 1][j - 1] = pixels[i + 1][j - 1] + ((3 / 16) * error);
        }
    }

    //this method disperses the calculated error to decrease the surrounding pixels

    private static void rippleErrorSub(int i, int j, int error) {
        if((i<h)&&(j<w)){
        pixels[i][j + 1] = pixels[i][j + 1] - ((7 / 16) * error);

        pixels[i + 1][j + 1] = pixels[i + 1][j + 1] - ((1 / 16) * error);

        pixels[i + 1][j] = pixels[i + 1][j] - ((5 / 16) * error);
        }
        if((i<h)&&(j>0)){  //to prevent the column number from going to -1
            pixels[i + 1][j - 1] = pixels[i + 1][j - 1] - ((3 / 16) * error);
        }
    }

    private static void colorToBW(byte[][] pixels2, int r, int g, int b) {
        System.out.println("You chose color to black & white!");
        Color color = new Color(r, g, b);
        for (int x = 0; x < h; x++) {
            for (int y = 0; y < w; y++) {
                //Retrieving the R G B values
                int red = (int) color.getRed() ;
                int green = (int) color.getGreen();
                int blue = (int) color.getBlue() ;

                System.out.println(red);

                pixels2[x][y]= (byte) ((red+blue+green)/3);
            }
        }
    }



}