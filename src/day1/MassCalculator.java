package day1;

public class MassCalculator implements ITheTyrannyOfTheRocketEquation{

    private int divider;
    private int substractor;

    public MassCalculator() {
        this.divider = divide;
        this.substractor = subtractor;
    }

    @Override
    public long calculateFor(long mass) {
        if(mass <=0 ) {
            return 0;
        }
        long fuel = Math.floorDiv((int)mass, divider) - substractor;
        if (fuel < 0) return 0;
        return fuel;
    }
}
