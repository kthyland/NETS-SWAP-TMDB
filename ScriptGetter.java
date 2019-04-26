import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
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
     * @return episodes: a list of all the episodes that can be accessed from the home page
     */
    public ArrayList<String> getEpisodes() {
        ArrayList<String> episodeLinks = new ArrayList<String>();
        ArrayList<String> episodes = new ArrayList<String>();
        Elements links = currentDoc.getElementsByClass("category-page__member");
        Pattern linkPattern = Pattern.compile("</div> <a href=\"(/wiki/.*)\" title=.*</a>");

        for (Element e : links) {
            //System.out.println(e);
            Matcher linkPatternMatcher = linkPattern.matcher(e.toString());
            while (linkPatternMatcher.find()) {
                episodeLinks.add(BASELINK + linkPatternMatcher.group(1));
            }
        }

        try {
            episodes = scriptsToText(episodeLinks);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return episodes;

    }

    /**
     * Method to turn each individual script into its own text file, then write the
     * lines of the script to that text file.
     * 
     * @param scripts: the script links from the home page
     * @throws IOException
     */
    public ArrayList<String> scriptsToText(ArrayList<String> scripts) throws IOException {
        ArrayList<String> episodes = new ArrayList<String>();
        for (String s : scripts) {
            //System.out.println(s);
            newUrl(s);
            String fileName = "";
            Pattern name = Pattern
                    .compile("https://gameofthronesfanon.fandom.com/wiki/(.*)/Transcript");
            Matcher nameMatcher = name.matcher(s);

            while (nameMatcher.find()) {
                fileName = nameMatcher.group(1);
                fileName = fileName.replaceAll("_", " ");
                // replace %27 with apostrophes:
                fileName = fileName.replaceAll("%27", "\\'");
                // cut out unnecessary information
                fileName = fileName.replaceAll("\\(Fanon\\)", "");
                fileName = fileName.replaceAll("\\(episode\\)", "");
                fileName = fileName.trim();

            }

            // i need to hard code the names of the websites that give invalid results so
            // that's what this is
            if (!fileName.equals("Hear Me Roar") && !fileName.equals("Salt and Smoke")
                    && !fileName.equals("Storm's End") && !fileName.equals("The Black Dread")
                    && !fileName.equals("The Kingslayer")
                    && !fileName.equals("The Lords of Winterfell")
                    && !fileName.equals("The Neverborn") && !fileName.equals("A Time for Wolves")
                    && !fileName.equals("Fire and Blood") && !fileName.equals("Episode 7.05")
                    && !fileName.equals("Episode 7.06")) {
                episodes.add(fileName);
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
        }

        newUrl(og);
        return episodes;
    }

    /**
     * Method to get all of the characters in an episode
     * 
     * @param episode: the episode you want to find the chars for 
     * @return characters: set of characters in the episode
     * @throws FileNotFoundException
     */
    public Set<String> getCharactersInEpisode(String episode) throws FileNotFoundException {
        Set<String> characters = new HashSet<String>();
        String fileName = episode+".txt";
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            Pattern getChar = Pattern.compile("(([A-Z])*).*");
            
            String line;
            while ((line = br.readLine()) != null)
            {
                Matcher charMatcher = getChar.matcher(line);
                while(charMatcher.find()) {
                    characters.add(charMatcher.group(1));
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return characters;
    }

}
