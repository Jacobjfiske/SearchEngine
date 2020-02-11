import java.net.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.io.*;

import javax.swing.text.html.parser.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.*;

/** Simpler page class that reads pages and tracks links/words on the page */
public class page {
    /** Blacklist words that will not be catalogued. */
    public final Set<String> blacklist;
    /** Links read from page */
    public final Set<String> links;
    /** Words read from page */
    public final Set<String> words;
    /** String URL where this page was GET'd from */
    public final String urlString;
    /** URL where this page was GET'd from */
    // Really should be able to be final, but java.
    public /*final*/ URL url;

    public page(String urlString, Set<String> blacklist) {
        this.urlString = urlString;
        this.links = new HashSet<>();
        this.words = new HashSet<>();
        this.blacklist = blacklist;
        try {
            this.url = new URL(urlString);
            // Note from tutor:
            // I really dislike doing network traffic in a constructor, but the example code also does this.
            String content = loadPage(url);
            StringReader reader = new StringReader(content);

            // Turns out there is a builtin HTML parser in java.
            ParserDelegator pd = new ParserDelegator();

            // This is an internal class (Defined below, within 'Page2') that recieves callbacks from parsing.
            ParserReciever pr = new ParserReciever();
            try {
                pd.parse(reader, pr, true);
            } catch (Exception e) {
                System.out.println("Oops when parsing HTML: " + e);
            }
        } catch (Exception e) {
            System.out.println("Invalid url: " + urlString + "\n\nexception: " + e);
        }
    }

    /** Goes out to the internet and gets the webpage at the given URL */
    public static String loadPage(URL url) {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line;
            StringBuilder str = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                str.append(line);

                str.append("\n");
            }
            reader.close();
            return str.toString();
        } catch (Exception e) {
            System.out.println("Oops when loading page: " + e);
        }
        return "";
    }

    /** Little class to recieve callbacks from the parser */
    // Note, unlike the LinkedList.Node class, this one is not static
    // that lets us reach back into the 'Page2' instance that was used to create one of these,
    // and check its blacklist or add links/words to its lists.
    private class ParserReciever extends HTMLEditorKit.ParserCallback {
        /** This method gets called by the parser when it intercepts an HTML tag. */
        public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
            // Did we see an <a> tag?
            if (t.equals(HTML.Tag.A)) {
                // Get the 'href' property
                String href = (String) a.getAttribute(HTML.Attribute.HREF);
                // May be null if tag doesn't have it
                if (href != null) {
                    //System.out.println("href=" + href);

                    // Ignore empty, javascript and self-page links
                    if (href.length() == 0
                            || (href.length() > 11 && href.substring(0, 11).equals("javascript:"))
                            || (href.length() > 0 && href.charAt(0) == '#')) {
                        // do nothing...
                    } else {
                        // Modify link so it is an absolute href if it is not already...
                        if (href.length() > 0 && href.charAt(0) == '/') {
                            //System.out.println("hi. " + url.getProtocol() +"://"+ url.getHost() + href);
                            href = url.getProtocol() + "://" + url.getHost() + href;
                        }

                        // Check to see if we already have it...
                        if (!links.contains(href)) {

                            // Add it to the links
                            links.add(href);
                        }
                    }
                }
            }
        }

        /** This method gets called by the parser when it intercepts plain text sitting in the HTML. */
        public void handleText(char[] data, int pos) {
            // lowercase all text, and replace any non-word characters with whitespaces so they get skipped.
            String str = new String(data).toLowerCase().replaceAll("\\W", " ");
            Scanner sc = new Scanner(str);
            while (sc.hasNext()) {
                // We will be seeing a lot of the same strings, so we will intern them where we can to save space.
                // Interning makes a 'canonical' copy of the string, which will be returned by subsequent calls to intern
                String word = sc.next().intern();
                if (word.length() > 0 && !blacklist.contains(word)) {
                    words.add(word);
                }
            }
            sc.close();


        }

    }
}