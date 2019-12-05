package day4;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.LinkedList;
import java.util.List;

public class ElvesPasswordCounter {
    private final static int START = 256310;
    private final static int END = 732736;
    private ArrayList<Integer> notMet = new ArrayList<>();
    private ArrayList<Integer> met = new ArrayList<>();
    private ArrayList<Integer> metRegular = new ArrayList<>();

    public static List<Integer> int2List(Integer number){
        LinkedList<Integer> stack = new LinkedList<>();

        while (number > 0 ){
            stack.push(number%10);
            number = number / 10;
        }
        return stack;
    }

    private boolean validatePossiblePass(Integer numer){
        LinkedList<Integer> numberAsList = (LinkedList<Integer>) int2List(numer);
        boolean wasDouble = false;
        for (int i=0; i<numberAsList.size()-1; i++){
            if(numberAsList.get(i+1) < numberAsList.get(i)) return false;
            if(numberAsList.get(i+1).equals(numberAsList.get(i))) wasDouble = true;
        }
        return wasDouble;
    }

    public boolean validatePossiblePassAdvanced(Integer number) {
        LinkedList<Integer> numberAsList = (LinkedList<Integer>) int2List(number);
        int doubleDigit = 0;

        for (int i=0; i<numberAsList.size()-1; i++){
            if(numberAsList.get(i+1) < numberAsList.get(i)) return false;
            if(numberAsList.get(i+1).equals(numberAsList.get(i))) doubleDigit = numberAsList.get(i);
        }
        return !isDoubleDigitPartOfLargerGroup(numberAsList, doubleDigit);
    }

    private boolean isDoubleDigitPartOfLargerGroup(LinkedList<Integer> numberAsList, int doubleDigit){
        long count = numberAsList.stream().filter(n -> n.equals(doubleDigit)).count();
        return count > 2;
    }

    public int countPossiblePass(){
        int counter = 0;
        for (int n=START; n<= END; n++){
            if(validatePossiblePass(n)) {
                counter++;
                metRegular.add(n);
            }
        }
        return counter;
    }

    public int countPossiblePassAdvanced(){
        int counter = 0;
        for (Integer regular :
                metRegular) {
            if (validatePossiblePassAdvanced(regular)) {
                counter ++;
            }
        }
//        for (int n=START; n<= END; n++){
//            if(validatePossiblePassAdvanced(n)) {
//                counter++;
//                met.add(n);
//            } else {
//                notMet.add(n);
//            }
//        }
//        System.out.println(notMet.size() + 517);
//        System.out.println(met.size());
//        met.forEach(System.out::println);
        return counter;
    }
}
