package day1;

class RecurentFuelCalculator extends MassCalculator{
    private int divider;
    private int substractor;


    RecurentFuelCalculator() {
        this.divider = divide;
        this.substractor = subtractor;
    }

    long recurrentCalculateFor(long mass) {
        long fuel = calculateFor(mass);
        if (fuel <=0 ){
            return 0;
        } else return fuel + recurrentCalculateFor(fuel);
    }
}
