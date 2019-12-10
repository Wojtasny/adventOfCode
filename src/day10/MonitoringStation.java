package day10;

import java.util.*;

public class MonitoringStation {

    Integer[][] map;
    private int width;
    private int height;
    private Coordinates currentCoordinate;
    private List<Coordinates> coordinatesList = new ArrayList<>();
    private List<Coordinates> coordinatesToSkip = new ArrayList<>();
    private Map<Coordinates, Integer> asteroidVisibleCount = new HashMap<>();

    void parseMap(String mapString){
        List<String> rows = Arrays.asList(mapString.split("\n"));
        width = rows.get(0).length();
        height = rows.size();
        map = new Integer[height][width];
        int x=0;
        int y=0;
        for(String row: rows){
            Integer[] rowOfOnes = row.chars().mapToObj(c -> (char) c).map(character -> character=='#').map(aBoolean -> aBoolean?1:0).toArray(Integer[]::new);
            map[x++] = rowOfOnes;
        }
        generateCoordinatesList();
        showMap();
        countForAllAsteroids();
    }

    private void countForAllAsteroids() {
        for(Coordinates coordinate: coordinatesList){
            coordinatesToSkip.clear();
            int count = countForAsteroid(coordinate);
            if(count!=0) {
                asteroidVisibleCount.put(coordinate, count);
            }
        }
    }

    private int countForAsteroid(Coordinates coor){
        // 0 means there is no asteroid at this point
        if((map[coor.getY()][coor.getX()]) == 0) return 0;
        currentCoordinate = coor;
        int counter = 0;

        for (Coordinates coordinates: coordinatesList){
            if(coordinates.equals(currentCoordinate)) continue;
            if(map[coordinates.getY()][coordinates.getX()] != 1) continue;
            if(coordinatesToSkip.contains(coordinates)) continue;
            counter+=countOnLine(coordinates);
        }
        return counter;
    }

    private int countOnLine(Coordinates c){
        double a;
        int innerCounter=0;
        Set<Coordinates> onLineAsteroids = new HashSet<>();
        Map<Coordinates, Integer> smaller = new HashMap<>();
        Map<Coordinates, Integer> higher = new HashMap<>();
        if(c.getX()==currentCoordinate.getX()){
            onLineAsteroids = getAsteroidsOnVerticalLine(currentCoordinate.getX());
            for (Coordinates coordinates :
                    onLineAsteroids) {
                int dist = currentCoordinate.getY()-coordinates.getY();
                if(dist<0){
                    higher.put(coordinates,dist);
                } else {
                    smaller.put(coordinates,dist);
                }
            }
        } else {
            nwd()
            int deltaY
            int deltaX
            onLineAsteroids = getAsteroidsOnLine(a, b);

            for (Coordinates coordinates :
                    onLineAsteroids) {
                int dist = currentCoordinate.getX()-coordinates.getX();
                if(dist<0){
                    higher.put(coordinates,dist);
                } else {
                    smaller.put(coordinates,dist);
                }
            }
        }

        if(!smaller.isEmpty()){
            coordinatesToSkip.addAll(smaller.keySet());
            innerCounter++;
        }
        if (!higher.isEmpty()) {
            coordinatesToSkip.addAll(higher.keySet());
            innerCounter++;
        }
        return innerCounter;
    }

    private Set<Coordinates> getAsteroidsOnVerticalLine(int x) {
        Set<Coordinates> asteroidsOnLine = new HashSet<>();
        for(int y=0;y<height;y++){
            if(map[y][x] ==1 && currentCoordinate.getY()!=y){
                asteroidsOnLine.add(new Coordinates(x, (int) y));
            }
        }
        return asteroidsOnLine;
    }


    // TODO: 11.12.2019 DO zmiany
    private Set<Coordinates> getAsteroidsOnLine(double a, double b) {
        Set<Coordinates> asteroidsOnLine = new HashSet<>();
        for(int x=0; x<width; x++){
            double y = a*x+b;
            if(y == Math.floor(y) && y>=0 && y<height && map[(int) y][x] ==1 && currentCoordinate.getX()!=x){
                asteroidsOnLine.add(new Coordinates(x, (int) y));
            }
        }
        return asteroidsOnLine;
    }

    private void showMap() {
        for (Integer[] inty: map) {
            System.out.println(Arrays.toString(inty));
        }
    }

    private class Coordinates{

        int getX() {
            return x;
        }
        int getY() {
            return y;
        }

        int x;

        int y;
        Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof Coordinates)) return false;
            Coordinates toCompare = (Coordinates) obj;
            return this.getX() == toCompare.getX() && this.getY() == toCompare.getY();
        }
    }
    private void generateCoordinatesList() {
        for(int i=0; i<height; i++) {
            for( int n=0;n<width;n++){
                coordinatesList.add(new Coordinates(n, i));
            }
        }
    }
}
