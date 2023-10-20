import java.util.stream.IntStream;

public class VirtualThread {
    public static void main(String[] args) {
        IntStream.range(1, 10).mapToObj(e -> {
            System.err.println(e);
            return null;
        });
    }
}
