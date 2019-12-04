package day3;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class CrossedWires {


    private static HashSet<Intersection> crossings = new HashSet<>();
    private Intersection currentPosition;

    private static class Intersection{
        Integer x;
        Integer y;

        public Intersection(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return (Math.abs(this.x) + Math.abs(this.y));
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Intersection){
                Intersection b = (Intersection) obj;
                return (this.x.equals(b.x)) && (this.y.equals(b.y));
            } else {
                return false;
            }
        }
    }

    private Supplier<RuntimeException> fail(){
        return new Supplier<RuntimeException>() {
            @Override
            public RuntimeException get() {
                return new IllegalArgumentException("Unknown opcode");
            }
        };
    }

    public void parseWire(List<String> wireInstructions){
        List<HashSet<Intersection>> listOfGrids = new ArrayList<>();
        for(String wireInstruction: wireInstructions){
            HashSet<Intersection> grid = new HashSet<>();
            updateCurrentPosition(new Intersection(0, 0));
            String[] instructions = wireInstruction.split(",");
            for(String wireFlow: instructions){
                String direction = wireFlow.substring(0,1);
                int distance = Integer.parseInt(wireFlow.substring(1));
                for(int n=0; n<distance; n++){
                    draw(direction, currentPosition,
                            (Intersection intersection) -> {
                        grid.add(intersection);
//                                if(!grid.add(intersection)){
//                                    crossings.add(intersection);
//                                }
                                updateCurrentPosition(intersection);
                            },
                            fail());
                }
            }
            listOfGrids.add(grid);
        }
        getCrossings(listOfGrids);
    }

    private void getCrossings(List<HashSet<Intersection>> listOfGrids) {
        List<HashSet<Intersection>> tmpList = new ArrayList<>(listOfGrids);
        crossings = tmpList.get(0);
        tmpList.remove(0);
        for (HashSet<Intersection> grid :
                tmpList) {
            crossings.retainAll(grid);
        }
    }

    private void updateCurrentPosition(Intersection intersection) {
        this.currentPosition = intersection;
    }

    Integer getMinDist() {
        IntSummaryStatistics summaryStatistics = crossings.stream()
                .mapToInt(Intersection::hashCode).summaryStatistics();
        return summaryStatistics.getMin();
    }

    private void draw(String marker, Intersection position, Consumer<Intersection> onSuccess, Supplier<RuntimeException> onFailure) {
        Intersection intersection = WireDirection.get(marker).map(line -> line.getIntersection(position)).orElseThrow(onFailure);
        onSuccess.accept(intersection);
    }

    @FunctionalInterface
    interface Line {
        Intersection getIntersection(Intersection start);
    }

    enum WireDirection{
        UP("U", ((start) -> {
            return new Intersection(start.x,start.y+1);
        })),
        DOWN("D", ((start) -> {
            return new Intersection(start.x,start.y-1);
        })),
        LEFT("L", ((start) -> {
            return new Intersection(start.x-1,start.y);
        })),
        RIGHT("R", ((start) -> {
            return new Intersection(start.x+1, start.y);
        }));

        String direction;
        Line line;

        WireDirection(String direction, Line line){
            this.direction = direction;
            this.line = line;
        }

        public static Optional<Line> get(String marker){
            return Arrays.stream(WireDirection.values())
                    .filter(i -> i.direction.equals(marker))
                    .findFirst()
                    .map(i -> i.line);
        }
    }

}
