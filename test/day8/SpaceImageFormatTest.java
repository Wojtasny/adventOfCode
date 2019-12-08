package day8;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SpaceImageFormatTest {
    @Test
    void validateLayerListCreation(){
        String image = "123456789012";
        int width = 3;
        int height = 2;
        SpaceImageFormat sif = new SpaceImageFormat(image, width, height);
    }

    @Test
    void validateInputCorruption() throws IOException {
        File file = new File("C:\\Users\\PTM867\\Documents\\adventOfCode\\resources\\inputday8.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String image = br.readLine();
        SpaceImageFormat sif = new SpaceImageFormat(image);
        assertEquals(2048, sif.corruptDetection());
    }

    @Test
    void decodeSample(){
        String image = "0222112222120000";
        int width = 2;
        int height = 2;
        SpaceImageFormat sif = new SpaceImageFormat(image, width, height);
        int[][] decodedImage = sif.getDecodedImage();
        for(int y=0; y<height;y++){
            for(int x=0; x<width;x++){
                if(decodedImage[x][y]==0){
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    @Test
    void decodeInputImage() throws IOException {
        File file = new File("C:\\Users\\PTM867\\Documents\\adventOfCode\\resources\\inputday8.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String image = br.readLine();
        SpaceImageFormat sif = new SpaceImageFormat(image);
        int[][] decodedImage = sif.getDecodedImage();
        for(int y=0; y<6;y++){
            for(int x=0; x<25;x++){
                if(decodedImage[x][y]==0){
                    System.out.print(" ");
                } else {
                    System.out.print("@");
                }
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println(Arrays.toString(sif.getDecodedImage()));
    }
}