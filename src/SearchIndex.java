//MAIN CLASS

import java.util.HashMap;
import java.util.Map;

public class SearchIndex {//builds index
    public static boolean useHash = true;//uses faster hash sets instead of LL
    public final Crawler crawler;
    private final LinkedList<LinkedList<String>> index;
    private final Map<String, LinkedList<String>> cheatIndex;

    public SearchIndex(Crawler crawler) {
        this.crawler = crawler;
        index = new LinkedList<>();

        cheatIndex = new HashMap<>();

        System.out.println("Building index...");
        for (int i = 0; i < crawler.pages.getCount();i++){//iterates through and indexes
            System.out.println("Indexing page " + (1+i) + " / " + crawler.pages.getCount());
            page page = crawler.pages.get(i);

            for (String word : page.words) {

                if (!cheatIndex.containsKey(word)) {
                    cheatIndex.put(word, new LinkedList<>());
                }
                cheatIndex.get(word).addAtTail(page.urlString);

                if (useHash) {continue;}//if useHash = true uses hash sets

                boolean sawWordInIndex = false;

                for (int k = 0; k < index.getCount(); k++){
                    LinkedList<String> listing = index.get(k);
                    if(listing.get(0).equals(word)){
                        sawWordInIndex = true;
                        listing.addAtTail(page.urlString);
                        break;
                    }
                }

                if (!sawWordInIndex){
                    LinkedList<String> listing = new LinkedList<>();
                    listing.addAtHead(word);
                    listing.addAtTail(page.urlString);
                    index.addAtTail(listing);

                }

            }
        }
    }


    public LinkedList<String> search(String query) {//search method after indexing is completed
        String[] queriedWords = query.toLowerCase().replaceAll("\\W", " ").split(" ");

        LinkedList<String> results = new LinkedList<>();
        for (String word : queriedWords){

            if (useHash) {//using hashsets
                if (cheatIndex.containsKey(word)) {
                    LinkedList<String> listing = cheatIndex.get(word);
                    for (int i = 0; i < listing.getCount(); i++) {
                        results.addAtHead(listing.get(i));
                    }
                }
            } else {

                for (int i = 0; i < index.getCount(); i++) {
                    LinkedList<String> listing = index.get(i);
                    if (listing.get(0).equals(word)) {
                        for (int k = 1; k < listing.getCount(); k++) {
                            results.addAtHead(listing.get(k));
                        }
                        break;
                    }
                }
            }
        }
        return results;
    }
}
