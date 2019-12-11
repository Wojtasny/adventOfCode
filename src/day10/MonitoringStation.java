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

    void parseMap(String mapString) {
        List<String> rows = Arrays.asList(mapString.split("\n"));
        width = rows.get(0).length();
        height = rows.size();
        map = new Integer[height][width];
        int x = 0;
        int y = 0;
        for (String row : rows) {
            Integer[] rowOfOnes = row.chars().mapToObj(c -> (char) c).map(character -> character == '#').map(aBoolean -> aBoolean ? 1 : 0).toArray(Integer[]::new);
            map[x++] = rowOfOnes;
        }
        generateCoordinatesList();
//        showMap();
        countForAllAsteroids();
    }

    int getHighestCount(){
        return Collections.max(asteroidVisibleCount.entrySet(),Comparator.comparing(Map.Entry::getValue)).getValue();
    }

    private void countForAllAsteroids() {
        for (Coordinates coordinate : coordinatesList) {
            coordinatesToSkip.clear();
            int count = countForAsteroid(coordinate);
            if (count != 0) {
                asteroidVisibleCount.put(coordinate, count);
            }
        }
    }

    private int countForAsteroid(Coordinates coor) {
        // 0 means there is no asteroid at this point
        if ((map[coor.getY()][coor.getX()]) == 0) return 0;
        currentCoordinate = coor;
        int counter = 0;

        for (Coordinates coordinates : coordinatesList) {
            if (coordinates.equals(currentCoordinate)) continue;
            if (map[coordinates.getY()][coordinates.getX()] != 1) continue;
            if (coordinatesToSkip.contains(coordinates)) continue;
            if (!isSomethingBetween(coordinates)) counter++;
//            counter+=countOnLine(coordinates);
        }
        return counter;
    }

    private boolean isSomethingBetween(Coordinates coordinates) {
        int distX = Math.abs(coordinates.getX() - currentCoordinate.getX());
        int distY = Math.abs(coordinates.getY() - currentCoordinate.getY());
        if (distX == 0) {
            return isBetweenVertical(distY, coordinates);
        }
        if (distY == 0) {
            return isBetweenHorizontal(distX, coordinates);
        }
//        if (isPrime(distX) || isPrime(distY)) {
//            return false;
//        }
        int divisor = findHighestCommonDivisor(distX, distY);
        if (divisor == 1) {
            return false;
        }
        int deltaX = distX / divisor;
        int deltaY = distY / divisor;

        int xStart = Math.min(coordinates.getX(), currentCoordinate.getX());
        int xEnd = Math.max(coordinates.getX(), currentCoordinate.getX());
        int yStart = Math.min(coordinates.getY(), currentCoordinate.getY());
        if(currentCoordinate.getX() == xStart){
            if(yStart!=currentCoordinate.getY()){
                yStart=currentCoordinate.getY();
                deltaY = -deltaY;
            }
        } else{
            if(yStart!=coordinates.getY()){
                yStart=coordinates.getY();
                deltaY =-deltaY;
            }
        }

        for (int m = 1; (m * deltaX + xStart) < xEnd; m += deltaX) {
            if (map[yStart + m * deltaY][xStart + m * deltaX] == 1) {
                return true;
            }
        }
        return false;
    }

    private boolean isBetweenHorizontal(int distX, Coordinates coordinates) {
        int xStart = Math.min(coordinates.getX(), currentCoordinate.getX());
        int xEnd = Math.max(coordinates.getX(), currentCoordinate.getX());
        for (int i = xStart + 1; i < xEnd; i++) {
            if (map[currentCoordinate.getY()][i] == 1) {
                return true;
            }
        }
        return false;
    }

    private boolean isBetweenVertical(Integer distY, Coordinates coordinates) {
        int yStart = Math.min(coordinates.getY(), currentCoordinate.getY());
        int yEnd = Math.max(coordinates.getY(), currentCoordinate.getY());
        for (int i = yStart + 1; i < yEnd; i++) {
            if (map[i][currentCoordinate.getX()] == 1) {
                return true;
            }
        }
        return false;
    }

    private int findHighestCommonDivisor(int n1, int n2) {
        if (n2 == 0) {
            return n1;
        }
        return findHighestCommonDivisor(n2, n1 % n2);
    }

    private void showMap() {
        for (Integer[] inty : map) {
            System.out.println(Arrays.toString(inty));
        }
    }

    private class Coordinates {

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
            if (!(obj instanceof Coordinates)) return false;
            Coordinates toCompare = (Coordinates) obj;
            return this.getX() == toCompare.getX() && this.getY() == toCompare.getY();
        }
    }

    private void generateCoordinatesList() {
        for (int i = 0; i < height; i++) {
            for (int n = 0; n < width; n++) {
                coordinatesList.add(new Coordinates(n, i));
            }
        }
    }
}
