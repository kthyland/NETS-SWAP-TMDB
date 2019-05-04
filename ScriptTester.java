import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ScriptTester {
    public static void main(String[] args) {
        ArrayList<String> episodes = new ArrayList<String>();
        ScriptGetter u = new ScriptGetter(
                "https://gameofthronesfanon.fandom.com/wiki/Category:Transcripts");
        episodes = u.getEpisodes();
        
    }

}
