import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Set;

public class ScriptTester {
    public static void main(String[] args) {
        ArrayList<String> episodes = new ArrayList<String>();
        ScriptGetter u = new ScriptGetter(
                "https://gameofthronesfanon.fandom.com/wiki/Category:Transcripts");
        episodes = u.getEpisodes();
        BooleanMatrix b = new BooleanMatrix(u);
        int[][] mat = b.createBooleanMatrix();
        
//       testing if all the episodes happened
        for(int i = 0; i < episodes.size(); i++) {
            System.out.println(episodes.get(i));
        }
        
       //testing out if the getCharactersInEpisode method works 
        try {
            Set<String> charactersEp1 = u.getCharactersInEpisode(episodes.get(0));
            for(String s : charactersEp1) {
                System.out.println(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }

}
