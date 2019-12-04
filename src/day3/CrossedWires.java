package day3;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CrossedWires {

    private static HashSet<Intersection> grid = new HashSet<>();
    private static ArrayList<Intersection> crossings = new ArrayList<>();
    private static class Intersection{
        Integer x;
        Integer y;

        public Intersection(Integer x, Integer y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return (int)(Math.abs(this.x) + Math.abs(this.y));
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Intersection){
                Intersection b = (Intersection) obj;
                return (this.x == b.x) && (this.y == b.y);
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
        AtomicReference<Intersection> currentPosition = new AtomicReference<>(new Intersection(0, 0));

        for(String wireInstruction: wireInstructions){
            String[] instructions = wireInstruction.split(",");
            for(String wireFlow: instructions){
                String direction = wireFlow.substring(0,1);
                int distance = Integer.parseInt(wireFlow.substring(1));
                for(int n=0; n<=distance; n++){
                    draw(direction, currentPosition.get(),
                            (Intersection intersection) -> {
                                if(!grid.add(intersection)){
                                    crossings.add(intersection);
                                }
                                currentPosition.set(intersection);
                            },
                            fail());
                }
            }
        }
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
