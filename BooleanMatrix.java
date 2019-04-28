import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BooleanMatrix {

//    ScriptGetter u = new ScriptGetter(
//            "https://gameofthronesfanon.fandom.com/wiki/Category:Transcripts");
//    ArrayList<String> episodes = new ArrayList<String>();
    Map<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
    ScriptGetter u;

    public BooleanMatrix(ScriptGetter u) {
        this.u = u;

    }

    public int[][] createBooleanMatrix() {
        List<String> episodes = u.getEpisodes();
        List<String> all = u.getAllCharacters();
        int[][] matrix = new int[episodes.size()][all.size()];

        try {
            for (int i = 0; i < matrix.length; i++) {
                Set<String> c = u.getCharactersInEpisode(episodes.get(i));
                List<String> characters = new ArrayList<>(c);
                characters.remove("");

                for (int j = 0; j < characters.size(); j++) {
                    int characterIndex = all.indexOf(characters.get(j));
                    matrix[i][characterIndex] = 1;
                }
            }

        } catch (FileNotFoundException e1) {
            System.out.print("Error");
        }
        return matrix;
    }

}
