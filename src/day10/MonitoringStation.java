package day10;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MonitoringStation {

    Integer[][] map;
    private int width;
    private int height;

    void parseMap(String mapString){
        List<String> rows = Arrays.asList(mapString.split("\n"));
        width = rows.get(0).length();
        height = rows.size();
        map = new Integer[width][height];
        int x=0;
        int y=0;
        for(String row: rows){
            Integer[] rowOfOnes = row.chars().mapToObj(c -> (char) c).map(character -> character=='#').map(aBoolean -> aBoolean?1:0).toArray(Integer[]::new);
            map[x++] = rowOfOnes;
        }
        showMap();
    }

    private int countForAsteroid(Integer x, Integer y){
        // 0 means there is no asteroid at this point
        if(map[x][y]==0) return 0;

    }

    private boolean canSeeEachOher(Integer x, Integer y, Integer x1, Integer y1){
        int distX = Math.abs(x1-x);
        int distY = Math.abs(y1-y);
        double a = (y1-y)/(x1-x);
        double b = a*x-y;
        Set<Integer[]> onLineAsteroids = getAsteroidOnLine(a, b);
    }

    private Set<Integer[]> getAsteroidOnLine(double a, double b) {

    }

    private void showMap() {
        for (Integer[] inty: map) {
            System.out.println(Arrays.toString(inty));
        }
    }
}
