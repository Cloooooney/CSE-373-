import minpq.MinPQ;
import minpq.OptimizedHeapMinPQ;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * Display the most commonly-reported WCAG recommendations.
 */
public class ReportAnalyzer {
    public static void main(String[] args) throws IOException {
        File inputFile = new File("data/wcag.tsv");
        Map<String, String> wcagDefinitions = new LinkedHashMap<>();
        Scanner scanner = new Scanner(inputFile);
        while (scanner.hasNextLine()) {
            String[] line = scanner.nextLine().split("\t", 2);
            String index = "wcag" + line[0].replace(".", "");
            String title = line[1];
            wcagDefinitions.put(index, title);
        }

        Pattern re = Pattern.compile("wcag\\d{3,4}");
        List<String> wcagTags = Files.walk(Paths.get("data/reports"))
                .map(path -> {
                    try {
                        return Files.readString(path);
                    } catch (IOException e) {
                        return "";
                    }
                })
                .flatMap(contents -> re.matcher(contents).results())
                .map(MatchResult::group)
                .toList();

        Map<String, Integer> tagCounts = new LinkedHashMap<>();
        for (String tag : wcagTags) {
            tagCounts.put(tag, tagCounts.getOrDefault(tag, 0) + 1);
        }

        MinPQ<Map.Entry<String, Integer>> minPQ = new OptimizedHeapMinPQ<>();
        for (Map.Entry<String, Integer> entry : tagCounts.entrySet()) {
            minPQ.add(entry, entry.getValue());
            if (minPQ.size() > 3) {
                minPQ.removeMin();
            }
        }

        System.out.println("Top 3 Most Commonly Reported WCAG Recommendations:");
        List<Map.Entry<String, Integer>> topEntries = new ArrayList<>();
        while (!minPQ.isEmpty()) {
            topEntries.add(minPQ.removeMin());
        }
        Collections.reverse(topEntries);

        for (Map.Entry<String, Integer> entry : topEntries) {
            String tag = entry.getKey();
            String definition = wcagDefinitions.get(tag);
            System.out.printf("%s: %d - %s%n", tag, entry.getValue(), definition);
        }
    }
}