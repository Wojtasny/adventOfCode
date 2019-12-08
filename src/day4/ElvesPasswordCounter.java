package day4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ElvesPasswordCounter {
    private final static int START = 256310;
    private final static int END = 732736;
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

    private boolean validatePossiblePassAdvanced(Integer number) {
        LinkedList<Integer> numberAsList = (LinkedList<Integer>) int2List(number);
        for (int i=0; i<numberAsList.size()-1; i++){
            if(numberAsList.get(i+1).equals(numberAsList.get(i)) &&
                    (Collections.frequency(numberAsList, numberAsList.get(i))) == 2) {
                return true;
            }
        }
        return false;
    }

    int countPossiblePass(){
        int counter = 0;
        for (int n=START; n<= END; n++){
            if(validatePossiblePass(n)) {
                counter++;
                metRegular.add(n);
            }
        }
        return counter;
    }

    int countPossiblePassAdvanced(){
        int counter = 0;
        for (Integer regular :
                metRegular) {
            if (validatePossiblePassAdvanced(regular)) {
                counter ++;
            }
        }
        return counter;
    }
}
