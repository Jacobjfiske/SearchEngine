import java.util.HashSet;
import java.util.Set;

public class Crawler {
    public final int maxVisits;
    public final String startUrl;
    public final Set<String> visited;
    public final LinkedList<page> pages;
    public final LinkedList<String> toVisit;
    public final Set<String> blacklist;

    public Crawler(String startUrl, int maxVisits, Set<String> blacklist) {

        this.maxVisits = maxVisits;
        this.startUrl = startUrl;
        this.blacklist = blacklist;
        visited = new HashSet<>();
        toVisit = new LinkedList<>();
        pages = new LinkedList<>();

        toVisit.addAtHead(startUrl);
    }//constructor

    public void crawl() {//uses page to go through and find urls from visited pages
        while (visited.size() < maxVisits && toVisit.getCount() > 0) {

            String url = toVisit.removeHead();
            if(visited.contains(url)){
                continue;
            }
            visited.add(url);

            System.out.println("visiting " + url);
            page page = new page(url, blacklist);

            pages.addAtHead(page);

            for(String link : page.links) {//adds toVisit if not visited and has link
                if(!visited.contains(link)){
                    toVisit.addAtTail(link);
                }
            }
        }

        System.out.println("visited " + visited.size() + " pages with " + toVisit.getCount() + " remaining.");
    }
}
