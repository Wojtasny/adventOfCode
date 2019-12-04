package day2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CleverIntcodeProgram {
    private static final Integer TERMINATING_OPCODE = 99;
    private static final Integer MARKER_INDEX = 0;
    private static final Integer WHERE_TO_PUT_RESULT_INDEX = 3;
    private static final Integer ARG_1 = 1;
    private static final Integer ARG_2 = 2;

    List<Integer> inputProgram = new ArrayList<>();
    List<Integer> promgram = new ArrayList<>();

    CleverIntcodeProgram(List<Integer> program){
        this.inputProgram.addAll(program);
    }
    public List<Integer> execute() {
        promgram.addAll(inputProgram);
        assert promgram.size() > 0;
        int iterator = 0;
        while(!TERMINATING_OPCODE.equals(promgram.get(iterator))){
            assert promgram.size() >= iterator + 4;
            List<Integer> subList = promgram.subList(iterator, iterator +4);
            Integer marker = subList.get(MARKER_INDEX);
            Integer whereToPlaceResult = subList.get(WHERE_TO_PUT_RESULT_INDEX);

            Integer arg1Index = subList.get(ARG_1);
            Integer arg2Index = subList.get(ARG_2);
            Integer arg1 = promgram.get(arg1Index);
            Integer arg2 = promgram.get(arg2Index);

            singleLoop(marker, arg1, arg2,
                    (Integer result) -> promgram.set(whereToPlaceResult, result),
                    fail());
            iterator += 4;
        }
        return promgram;
    }

    private void singleLoop(Integer marker, Integer arg1, Integer arg2, Consumer<Integer> onSuccess, Supplier<RuntimeException> onFailure) {
        Integer toSave = Opcodes.get(marker).map(opcode -> opcode.run(arg1, arg2)).orElseThrow(onFailure);
        onSuccess.accept(toSave);
    }

    enum Opcodes {
        SUM(1, Integer::sum),
        MULTIPLY(2, (Integer arg1, Integer arg2) -> arg1 * arg2);

        Integer marker;
        Opcode opcode;

        Opcodes(int marker, Opcode opcode){
            this.marker = marker;
            this.opcode = opcode;
        }

        public static Optional<Opcode> get(Integer marker){
            return Arrays.stream(Opcodes.values())
                    .filter(i -> i.marker.equals(marker))
                    .findFirst()
                    .map(i -> i.opcode);
        }
    }

    @FunctionalInterface
    interface Opcode {
        Integer run(Integer arg1, Integer arg2);
    }

    private Supplier<RuntimeException> fail(){
        return new Supplier<RuntimeException>() {
            @Override
            public RuntimeException get() {
                return new IllegalArgumentException("Unknown opcode");
            }
        };
    }
}
