import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Fano {

    public static final String fileName = "src/resource/fano.txt";

    public String encodeAlgorithm() {
        Map<String, String> code = createCodeLetter();
        System.out.println(code);

        return encodeMessage(code);
    }

    private Map<String, String> createCodeLetter() {
        Map<String, String> code = new HashMap<>(); // symbol - code
        List<String> alphabet = Arrays.stream(readFromFile(0).split(" ")).collect(Collectors.toList());
        List<Double> probability = Arrays.stream(readFromFile(1).split(" "))
                .map(Double::parseDouble).collect(Collectors.toList());

        Map<String, Double> alphProb = IntStream.range(0, alphabet.size()).boxed()
                .collect(Collectors.toMap(alphabet::get, probability::get));
        // сортировка по вероятностям
        alphProb = alphProb.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        searchTree("", "", 0, alphProb.size() - 1, alphProb, code);
        return code;
    }

    private String encodeMessage(Map<String, String> code){
        Scanner in = new Scanner(System.in);
        String message = in.nextLine();
        StringBuilder encodedMessage = new StringBuilder();
        for (char letter : message.toCharArray()){
            encodedMessage.append(code.get(String.valueOf(letter)));
        }
        return encodedMessage.toString();
    }

    private void searchTree(String branch, String fullBranch, int startPos, int endPos, Map<String, Double> alphProb, Map<String, String> code){
        double dS = 0.0; // среднее значение массива
        int m; // номер средней буквы в последовательности
        double sum = 0.0; // сумма чисел, левой ветки
        String cBranch; // текущая история поворотов по веткам

        // соединяем историю веток
        cBranch = fullBranch + branch;

        // Критерий выхода: если позиции символов совпали, то это конец
        if (startPos == endPos){
            code.put(getLetterFromPosition(alphProb, startPos), cBranch);
            return;
        }

        // Подсчет среднего значения частоты в последовательности
        for (int i = startPos; i < endPos; i++) {
            dS += getProbFromPosition(alphProb, i);
        }
        dS = dS / 2;

        // Поиск середины
        m = startPos;
        while ((sum + getProbFromPosition(alphProb, m) < dS) & (m < endPos)){
            sum += getProbFromPosition(alphProb, m);
            m++;
        }

        // Рекурсия левая ветка дерева
        searchTree("0", cBranch, startPos, m, alphProb, code);

        // Рекурсия правая ветка дерева
        searchTree("1", cBranch, m + 1, endPos, alphProb, code);
    }

    private String readFromFile(int row) {
        try{
            return Files.lines(Paths.get(fileName)).collect(Collectors.toList()).get(row);
        } catch (IOException e){
            throw new IllegalArgumentException(e);
        }
    }

    private String getLetterFromPosition(Map<String, Double> alphProb, int pos){
        return alphProb.keySet().toArray()[pos].toString();
    }

    private Double getProbFromPosition(Map<String, Double> alphProb, int pos){
        return Double.parseDouble(alphProb.values().toArray()[pos].toString());
    }
}
