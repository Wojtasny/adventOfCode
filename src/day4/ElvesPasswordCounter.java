package day4;

import java.util.LinkedList;
import java.util.List;

public class ElvesPasswordCounter {
    private final static int START = 256310;
    private final static int END = 732736;

    public List<Integer> int2List(Integer number){
        LinkedList<Integer> stack = new LinkedList<>();

        while (number > 0 ){
            stack.push(number%10);
            number = number / 10;
        }
        return stack;
    }

    public boolean validatePossiblePass(Integer numer){
        LinkedList<Integer> numberAsList = (LinkedList<Integer>) int2List(numer);
        boolean wasDouble = false;
        for (int i=0; i<numberAsList.size()-1; i++){
            if(numberAsList.get(i+1) < numberAsList.get(i)) return false;
            if(numberAsList.get(i+1).equals(numberAsList.get(i))) wasDouble = true;
        }
        return wasDouble;
    }

    public int countPossiblePass(){
        int counter = 0;
        for (int n=START; n<= END; n++){
            if(validatePossiblePass(n)) counter++;
        }
        return counter;
    }
}
