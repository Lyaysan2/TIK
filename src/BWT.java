import java.util.*;
import java.util.stream.Collectors;

public class BWT {

    public String decode(Map<String, Integer> input){
        List<String> encoded = Arrays.stream(input.keySet().toArray()[0].toString().split("")).collect(Collectors.toList());
        List<String> sorted = encoded.stream().sorted().collect(Collectors.toList());
        int position = (int) input.values().toArray()[0];

        StringBuilder output = new StringBuilder();
        int a; // кол-во повторяющихся букв
        int pos;

        for (int i = 0; i < encoded.size(); i++){
            String letter = sorted.get(position);
            output.append(letter);
            // ищем количество нужной буквы в сете повторяющихся букв (если есть)
            a = 0;
            pos = position;
            while ((pos >= 0) && (sorted.get(pos).equals(letter))){
                a++;
                pos--;
            }

            position = getIndex(encoded, a, letter);
        }
        return output.toString();
    }

    private int getIndex(List<String> encoded, int a, String letter) {
        int k = 0;
        for (int j = 0; j < encoded.size(); j++){
            if (encoded.toArray()[j].toString().equals(letter)) {
                k++;
                if (k == a) {
                    return j;
                }
            }
        }
        return encoded.size() - 1;
    }
}
