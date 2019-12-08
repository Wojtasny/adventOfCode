package day8;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class SpaceImageFormat {
    private int width = 25;
    private int height = 6;

    private String inputData;
    private List<List<List<Integer>>> image = new LinkedList<>();
    private int pixelsPerLayer;
    private int numberOfLayers;
    private int[][] decodedImage;

    SpaceImageFormat(String inputData) {
        this.inputData = inputData;
        separateToLayers();
    }

    SpaceImageFormat(String inputData, int width, int height) {
        this.inputData = inputData;
        this.width = width;
        this.height = height;
        separateToLayers();
    }

    long corruptDetection(){
        String layerWithFewest0 = getLayerByNumberOf('0');
        long numberOfOnes = layerWithFewest0.chars().filter(ch -> ch == '1').count();
        long numberOfTwos = layerWithFewest0.chars().filter(ch -> ch == '2').count();
        return numberOfOnes*numberOfTwos;
    }

    private String getLayerByNumberOf(Character character){
        List<String> layers = Stream.iterate(0, integer -> integer+1).limit(numberOfLayers)
                .map(integer -> inputData.substring(integer*pixelsPerLayer, integer*pixelsPerLayer+pixelsPerLayer)).collect(Collectors.toList());
        Map<String,Long> layerOccurencesCount = new HashMap<>();
        for (String layer:
             layers) {
            long count = layer.chars().filter(ch -> ch == character).count();
            layerOccurencesCount.put(layer, count);
        }
        return Collections.min(layerOccurencesCount.entrySet(), Comparator.comparingLong(Map.Entry::getValue)).getKey();
    }

    int[][] getDecodedImage() {
        return decodedImage;
    }

    private void separateToLayers(){
        pixelsPerLayer = width * height;
        int totalNumberOfPixels = inputData.length();
        assert totalNumberOfPixels% pixelsPerLayer == 0;
        numberOfLayers = totalNumberOfPixels/ pixelsPerLayer;
        decodedImage = new int[width][height];

        for(int layer = numberOfLayers-1; layer>= 0; layer--){
            String currentLayer = inputData.substring(layer* pixelsPerLayer, pixelsPerLayer *layer + pixelsPerLayer);
            List<List<Integer>> rowList = new ArrayList<>();
            for(int rowNo = 0; rowNo< height; rowNo++){
                String row = currentLayer.substring(rowNo* width, rowNo* width + width);
                List<Integer> pixels = new ArrayList<>();
                for(int character=0; character<row.length(); character++){
                    int pixelValue = Integer.parseInt(String.valueOf(row.charAt(character)));
                    pixels.add(pixelValue);
                }
                rowList.add(pixels);
            }
            image.add(rowList);
        }
        decodedImage = decode();
    }
//
    private int[][] decode() {
        int [][] img = new int[width][height];
        for(List<List<Integer>> layer: image){
            int y=0;
            for(List<Integer> row: layer){
                int x=0;
                for(Integer pix: row){
                    if(pix!=2) img[x][y] = pix;
                    x++;
                }
                y++;
            }
        }
        return img;
    }
}
