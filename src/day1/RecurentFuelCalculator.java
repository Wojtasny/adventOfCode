package day1;

class RecurentFuelCalculator extends MassCalculator{



    long recurrentCalculateFor(long mass) {
        long fuel = calculateFor(mass);
        if (fuel <=0 ){
            return 0;
        } else return fuel + recurrentCalculateFor(fuel);
    }
}
