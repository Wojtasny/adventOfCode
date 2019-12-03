package day1;

public class RecurentFuelCalculator extends MassCalculator{
    private int divider;
    private int substractor;


    public RecurentFuelCalculator() {
        this.divider = divide;
        this.substractor = subtractor;
    }

    public long recurrentCalculateFor(long mass) {
        long fuel = calculateFor(mass);
        if (fuel <=0 ){
            return 0;
        } else return fuel + recurrentCalculateFor(fuel);
    }
}
