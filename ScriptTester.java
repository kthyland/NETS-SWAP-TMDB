public class ScriptTester {
    public static void main(String[] args) {
        ScriptGetter u = new ScriptGetter(
                "https://gameofthronesfanon.fandom.com/wiki/Category:Transcripts");
        u.getEpisodes();
    }

}
