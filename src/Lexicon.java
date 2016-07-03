import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by t_j_w on 03/07/2016.
 */
public class Lexicon {

    List lexicon;

    public Lexicon() {
    }

    public void Load() {
        if(lexicon == null)
            lexicon = new ArrayList<String>();

        try {
            File file = new File("Harvard Semantic Lexicon (optimised).txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line;
            int lineIter = 0;
            while((line = reader.readLine()) != null) {
                lexicon.add(line);
                lineIter++;
                System.out.println("Line: " + lineIter);
            }
            System.out.println(lexicon.size() + " entries loaded.");

        } catch (IOException e) {
            System.out.println("IO Exception");
            e.printStackTrace();
        }
    }

    public String Line(int line) {
        return (String)lexicon.get(line);
    }

    public String Word(String word, boolean verbose) {

        long startTime = System.nanoTime();

        for(int i = 0; i < lexicon.size(); i++) {
            String entry = (String)lexicon.get(i);

            //System.out.print("\r                                               \r");

            //System.out.print("Index: " + i + "Word: " + entry.split(" ")[0]);


            if(entry.startsWith(word)) {
                //TODO: Read additional words if entry.split(" ")[0].contains("#")

                long endTime = System.nanoTime();
                if(verbose)
                    System.out.println("Found. Time Taken: " + ((double)(endTime - startTime)/1000000000.0) + " seconds.");
                return entry;
            }
            //System.out.flush();
        }
        return null;
    }

    public float Positivity(String sentence) {
        String stripPunctuation = sentence.replace(".", ""); //TODO: all punctuation
        String[] words = stripPunctuation.split(" ");
        int[] values = new int[words.length];
        int semanticWordCount = 0; // Num. of semantic words
        int positiveWords = 0;
        for(int i = 0; i < words.length; i++) {
            String entry = Word(words[i].toUpperCase(), false);
            if(entry != null) {
                semanticWordCount++;
                values[i] = GetSemanticValue(entry, "positivity");
                if(values[i] > 0)
                    positiveWords++;
            }
        }
        float positivePercentage = (positiveWords/words.length) * 100;
        float negativePercentage = ((semanticWordCount-positiveWords)/words.length) * 100.0f;
        float neutralPercentage = ((words.length - semanticWordCount)/words.length) * 100.0f;

        System.out.println("String is " + positivePercentage + "% Positive, "
                + negativePercentage + "% Negative(" + (semanticWordCount-positiveWords) + "), "
                + neutralPercentage + "% Neutral.");
        System.out.println("String is " + ((positivePercentage > negativePercentage)? "Positive" : "Negative") + " Overall." );

        return positiveWords - (semanticWordCount-positiveWords);
    }

    public int GetSemanticValue(String entry, String semanticism) {
        switch (semanticism) {
            case "positivity":
                if(entry.contains("Positiv"))
                    return 1;
                else if(entry.contains("Negativ"))
                    return -1;
                break;
        }
        return 0;
    }
}
