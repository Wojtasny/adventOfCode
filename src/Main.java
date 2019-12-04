import java.util.HashSet;

public class Main {

    public static void main(String[] args) {
        HashSet<Integer> hs = new HashSet<>();
        System.out.println(hs.add(1));
        System.out.println(hs.add(1));
        String s = "A35";
        System.out.println(s.substring(0,1));
        Integer dist = Integer.parseInt(s.substring(1));
        System.out.println(dist);
    }
}
