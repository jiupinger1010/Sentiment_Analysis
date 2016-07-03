import java.util.Scanner;

/**
 * Created by t_j_w on 03/07/2016.
 */
public class SentimentAnalysis {

    public static Lexicon lexicon;

    public static void main(String[] args) {
        lexicon = new Lexicon();
        lexicon.Load();
        Input();
    }

    public static void Input() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String in = scanner.nextLine();
            String[] splitIn = in.split(" ");

            switch (splitIn[0]) {
                case "index":
                    System.out.println("Line " + splitIn[1] + ": " + lexicon.Line(Integer.parseInt(splitIn[1])));
                    break;
                case "word":
                    String semantics = lexicon.Word(splitIn[1].toUpperCase(), true);
                    if(semantics == null)
                        System.out.println("Word not found.");
                    else
                        System.out.println("Semantics Data: " + semantics);
                    break;
                case "sentence":
                    System.out.println("Processing Sentence...");
                    lexicon.Positivity(in.substring(splitIn[0].length()+1));
                    break;

            }
        }
    }
}
