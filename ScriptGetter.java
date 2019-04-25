import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ScriptGetter {
    private String baseUrl;
    private Document currentDoc;
    private final String BASELINK = "https://gameofthronesfanon.fandom.com";
    private String og = "";

    /**
     * Creates a URL from a string. Opens the connection to be used later.
     * 
     * @param url
     */
    public ScriptGetter(String url) {
        this.baseUrl = url;
        og = url;
        try {
            this.currentDoc = getDOMFromURL(baseUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Setter method for "clicking" a link, updates base URL and document
     * 
     * @param newUrl
     */
    public void newUrl(String newUrl) {
        this.baseUrl = newUrl;
        try {
            this.currentDoc = getDOMFromURL(newUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to get a Document from a String URL
     * 
     * @return a document
     */
    public Document getDOMFromURL(String u) throws IOException {
        URL url = new URL(u);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();
        String curr = in.readLine();
        while (curr != null) {
            sb.append(curr);
            curr = in.readLine();
        }

        return Jsoup.parse(sb.toString());
    }

    /**
     * Method to get all of the links to each individual episode from the home page
     * of the transcript website.
     * 
     */
    public void getEpisodes() {
        ArrayList<String> episodes = new ArrayList<String>();
        Elements links = currentDoc.getElementsByClass("category-page__member");
        Pattern linkPattern = Pattern.compile("</div> <a href=\"(/wiki/.*)\" title=.*</a>");

        for (Element e : links) {
            Matcher linkPatternMatcher = linkPattern.matcher(e.toString());
            while (linkPatternMatcher.find()) {
                episodes.add(BASELINK + linkPatternMatcher.group(1));
            }
        }

        try {
            scriptsToText(episodes);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Method to turn each individual script into its own text file, then write 
     * the lines of the script to that text file. 
     * @param scripts
     * @throws IOException
     */
    public void scriptsToText(ArrayList<String> scripts) throws IOException {
        for (String s : scripts) {
            newUrl(s);
            String fileName = "";
            Pattern name = Pattern.compile(
                    "https://gameofthronesfanon.fandom.com/wiki/(.*)_\\(Fanon\\)/Transcript");
            Matcher nameMatcher = name.matcher(s);
            while (nameMatcher.find()) {
                fileName = nameMatcher.group(1);
                fileName = fileName.replaceAll("_", " ");
                //was getting a glitch replacing apostrophes with %27 so:
                fileName = fileName.replaceAll("%27", "\\'");
            }
            RandomAccessFile raf = new RandomAccessFile(fileName + ".txt", "rw");

            Elements text = currentDoc.getElementsByClass("mw-content-ltr mw-content-text");
            Pattern line = Pattern.compile("<p>(<i>)*(.*)(</i>)*</p>");
            Matcher lineMatcher = line.matcher(text.toString());
            while (lineMatcher.find()) {
                byte[] buff = lineMatcher.group(2).getBytes();
                raf.write(buff);
                raf.write('\n');
            }
            raf.close();
        }

        newUrl(og);
    }
}
