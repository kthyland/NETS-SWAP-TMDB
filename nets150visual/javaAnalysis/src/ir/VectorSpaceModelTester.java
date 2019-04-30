package ir;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * the tester class.
 * @author swapneel
 */
public class VectorSpaceModelTester {

	public static void main(String[] args) throws IOException {
		
		Document e1_1 = new Document("1.1.txt");
		Document e1_2 = new Document("1.2.txt");
        Document e1_3 = new Document("1.3.txt");
        Document e1_4 = new Document("1.4.txt");
        Document e1_5 = new Document("1.5.txt");
        Document e1_6 = new Document("1.6.txt");
        Document e1_7 = new Document("1.7.txt");
        Document e1_8 = new Document("1.8.txt");
        Document e1_9 = new Document("1.9.txt");
        Document e2_1 = new Document("2.1.txt");
        Document e3_9 = new Document("3.9.txt");
        Document e4_4 = new Document("4.4.txt");
        Document e5_1 = new Document("5.1.txt");
        Document e5_10 = new Document("5.10.txt");
        Document e5_2 = new Document("5.2.txt");
        Document e5_3 = new Document("5.3.txt");
        Document e5_4 = new Document("5.4.txt");
        Document e5_5 = new Document("5.5.txt");
        Document e5_6 = new Document("5.6.txt");
        Document e5_7 = new Document("5.7.txt");
        Document e5_8 = new Document("5.8.txt");
        Document e5_9 = new Document("5.9.txt");
        Document e6_1 = new Document("6.1.txt");
        Document e6_2 = new Document("6.2.txt");
        Document e6_4 = new Document("6.4.txt");
        Document e6_5 = new Document("6.5.txt");
        Document e6_6 = new Document("6.6.txt");
        Document e6_7 = new Document("6.7.txt");
        Document e6_8 = new Document("6.8.txt");
        Document e6_9 = new Document("6.9.txt");
        Document e7_1 = new Document("7.1.txt");
        
		ArrayList<Document> documents = new ArrayList<Document>();
		documents.add(e1_1);
        documents.add(e1_2);
        documents.add(e1_3);
        documents.add(e1_4);
        documents.add(e1_5);
        documents.add(e1_6);
        documents.add(e1_7);
        documents.add(e1_8);
        documents.add(e1_9);
        documents.add(e2_1);
        documents.add(e3_9);
        documents.add(e4_4);
        documents.add(e5_1);
        documents.add(e5_10);
        documents.add(e5_2);
        documents.add(e5_3);
        documents.add(e5_4);
        documents.add(e5_5);
        documents.add(e5_6);
        documents.add(e5_7);
        documents.add(e5_8);
        documents.add(e5_9);
        documents.add(e6_1);
        documents.add(e6_2);
        documents.add(e6_4);
        documents.add(e6_5);
        documents.add(e6_6);
        documents.add(e6_7);
        documents.add(e6_8);
        documents.add(e6_9);
        documents.add(e7_1);
		
		Corpus corpus = new Corpus(documents);
		
		VectorSpaceModel vectorSpace = new VectorSpaceModel(corpus);
		
		BufferedWriter outputGuy = new BufferedWriter(new FileWriter("output.csv"));
        outputGuy.write("Document1, Document2, Cosine Similarity\n");
		
		for (int i = 0; i < documents.size(); i++) {
			for (int j = i + 1; j < documents.size(); j++) {
				Document doc1 = documents.get(i);
				Document doc2 = documents.get(j);
				outputGuy.write(doc1 + ", " + doc2 + ", " 
				        + vectorSpace.cosineSimilarity(doc1, doc2) + "\n");
			}
		}
		
		outputGuy.close();
		
//		for(int i = 1; i < documents.size(); i++) {
//			Document doc = documents.get(i);
//			System.out.println("\nComparing to " + doc);
//			System.out.println(vectorSpace.cosineSimilarity(query, doc));
//		}
		
		
		
	}

}