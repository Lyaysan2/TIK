import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MTF {

    public static final String fileName = "src/resource/mtf.txt";

    public Map<String, Integer> decode() {
        Scanner in = new Scanner(System.in);
        String message = in.nextLine();
        Integer pos = in.nextInt();
        List<String> alphabet = Arrays.stream(readFromFile().split(" ")).collect(Collectors.toList());
        // нумеруем буквы алфавита десятичными числами
        List<String> code = IntStream.range(0, alphabet.size()).boxed().map(Object::toString).collect(Collectors.toList());

        StringBuilder output = new StringBuilder();
        String str = "";

        for (char letter : message.toCharArray()) {
            str = str + letter;
            // если код (послед-ть кодов) содержится в массиве кодов
            if (code.contains(str)) {
                int ind = code.indexOf(str);
                String let = alphabet.get(ind);
                output.append(let); // записываем букву
                alphabet.remove(ind);
                alphabet.add(0, let); // переносим букву наверх
                str = "";
            }
        }
        return Map.of(output.toString(), pos);
    }

    private String readFromFile() {
        try {
            return Files.lines(Paths.get(fileName)).collect(Collectors.toList()).get(0);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
