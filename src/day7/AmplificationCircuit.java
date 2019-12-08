package day7;

import day2.CleverIntcodeProgram;

import java.util.*;

public class AmplificationCircuit extends CleverIntcodeProgram {

    private boolean halted = false;

    private Map<int[], Integer> phaseSettingOutputValueMap = new HashMap<>();
    private Map<int[], Integer> phaseSettingWithFeedbackOutputValueMap = new HashMap<>();

    AmplificationCircuit(List<Integer> program){
        super(program);
        int[] permutationInitArray = {0, 1, 2, 3, 4};
        getAllPermutations(permutationInitArray.length, permutationInitArray, permutationList);
        int[] permutationFeedbackArray = {5, 6, 7, 8, 9};
        getAllPermutations(permutationFeedbackArray.length, permutationFeedbackArray, permutationFeedbackList);
        this.program.addAll(inputProgram);
        marker = 0;
    }

    private List<int[]> permutationList = new ArrayList<>();
    private List<int[]> permutationFeedbackList = new ArrayList<>();

    private void getAllPermutations(int n, int[] elements, List<int[]> whereToSave) {
        if(n == 1) {
            saveElement(elements, whereToSave);
        } else {
            for(int i = 0; i < n-1; i++) {
                getAllPermutations(n - 1, elements, whereToSave);
                if(n % 2 == 0) {
                    swap(elements, i, n-1);
                } else {
                    swap(elements, 0, n-1);
                }
            }
            getAllPermutations(n - 1, elements, whereToSave);
        }
    }

    private void swap(int[] input, int a, int b) {
        int tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }
    private void saveElement(int[] input, List<int[]> whereToSave) {
        int [] tmp = input.clone();
        whereToSave.add(tmp);
    }


    int findGreatestOutput(){
        for (int[] inputs: permutationList){
            int inputSignal = 0;
            for (int phaseSetting :
                    inputs) {
                this.inputQueue.offer(phaseSetting);
                this.inputQueue.offer(inputSignal);
                super.execute();
                inputSignal = this.getResult();
            }
            int finalResult = inputSignal;
            phaseSettingOutputValueMap.put(inputs, finalResult);
        }
        int[] highestOutputPhases = Collections.max(phaseSettingOutputValueMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        System.out.println(Arrays.toString(highestOutputPhases));

        return Collections.max(phaseSettingOutputValueMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getValue();
    }

    int findGreatestOutputWithFeedback(){
        for(int [] phaseSettings: permutationFeedbackList){
            int inputSignal = 0;
            AmplificationCircuit amplifier1 = new AmplificationCircuit(inputProgram);
            AmplificationCircuit amplifier2 = new AmplificationCircuit(inputProgram);
            AmplificationCircuit amplifier3 = new AmplificationCircuit(inputProgram);
            AmplificationCircuit amplifier4 = new AmplificationCircuit(inputProgram);
            AmplificationCircuit amplifier5 = new AmplificationCircuit(inputProgram);

            amplifier1.inputQueue.offer(phaseSettings[0]);
            amplifier2.inputQueue.offer(phaseSettings[1]);
            amplifier3.inputQueue.offer(phaseSettings[2]);
            amplifier4.inputQueue.offer(phaseSettings[3]);
            amplifier5.inputQueue.offer(phaseSettings[4]);

            amplifier1.inputQueue.offer(inputSignal);

            while(!amplifier1.isHalted()){
                int amplifier1Output = amplifier1.executeWithFeedback();
                amplifier2.inputQueue.offer(amplifier1Output);
                int amplifier2Output = amplifier2.executeWithFeedback();
                amplifier3.inputQueue.offer(amplifier2Output);
                int amplifier3Output = amplifier3.executeWithFeedback();
                amplifier4.inputQueue.offer(amplifier3Output);
                int amplifier4Output = amplifier4.executeWithFeedback();
                amplifier5.inputQueue.offer(amplifier4Output);
                int amplifier5Output = amplifier5.executeWithFeedback();
                amplifier1.inputQueue.offer(amplifier5Output);
            }
            phaseSettingWithFeedbackOutputValueMap.put(phaseSettings, amplifier5.getResult());
        }
        int[] highestOutputPhases = Collections.max(phaseSettingWithFeedbackOutputValueMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
        System.out.println(Arrays.toString(highestOutputPhases));

        return Collections.max(phaseSettingWithFeedbackOutputValueMap.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getValue();
    }

    private int executeWithFeedback(){

        OpcodeAndModes instruction = parseInstructionOpcode(program.get(marker));

        while(!TERMINATING_OPCODE.equals(instruction.opcode)){
            List<Integer> argList = getAllArguments(instruction);
            singleInstruction(instruction.opcode, argList, fail());

            if(4 == instruction.opcode){
                return lastOutput();
            }
            instruction = parseInstructionOpcode(program.get(marker));
        }
        halted = true;
        return lastOutput();
    }

    private int lastOutput() {
        return output.get(output.size()-1);
    }

    private boolean isHalted() {
        return halted;
    }
}
