import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Runner {
    public static final String[] COMMONS = new String[] {
            "a", "an", "and", "at", "if", "is", "it", "in", "of", "on", "to", "the", "this",
            "that", "which", "who","what","why","when","where"
    };

    public static void main(String[] args) {
        Set<String> blacklist = new HashSet<>();//creates blacklist to use as parameter in crawler
        for (String s : COMMONS) { blacklist.add(s); }

        Crawler crawler = new Crawler("https://en.wikipedia.org/wiki/World_War_II",1000
                , blacklist);
        crawler.crawl();

        SearchIndex index = new SearchIndex(crawler);

        guiMain(index);
        //cmdMain(index);

    }

    private static void guiMain(SearchIndex index) {//creates gui
        GUI gui = new GUI();
        gui.index = index;

    }

    private static void cmdMain(SearchIndex index) {//previous test code for reference
        Scanner sc = new Scanner(System.in);
        System.out.println("Ready for queries!");
        while(true) {
            System.out.print("\n\nEnter query or blank to quit: ");
            String input = sc.nextLine();
            if (input.equals("")) {
                System.out.println("Okay bye.");
                break;
            }
            LinkedList<String> results = index.search(input);

            if (results.getCount() == 0) {
                System.out.println("\nNo Results!");
            }
            else {
                for (int i = 0; i < results.getCount(); i++) {
                    System.out.println("Result " + (1+i) + ": " + results.get(i));
                }
            }
        }
    }


}
