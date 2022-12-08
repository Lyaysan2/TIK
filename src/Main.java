import java.util.Map;

public class Main {
    public static void main(String[] args) {

        // coder
        Fano fano = new Fano();
        String encoded = fano.encodeAlgorithm();
        System.out.printf("Encoded message: {%s}%n", encoded);

        // decoder
        BWT bwt = new BWT();
        MTF mtf = new MTF();

        Map<String, Integer> mtfDecoded = mtf.decode();
        System.out.printf("after move-to front decoding: %s\n", mtfDecoded.keySet().toArray()[0]);

        String bwtDecoded = bwt.decode(mtfDecoded);
        System.out.printf("after BWT decoding: %s\n", bwtDecoded);
    }
}
