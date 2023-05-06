import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class GraphTest {
	
	public static void print(String gfn) {
		System.out.println(gfn);
		
		Graph myGraph = new Graph(gfn);
		
		// test toString()
		System.out.println(myGraph);
		
		
		// test getVerts()
		System.out.println();
		System.out.println(myGraph.getVerts());
		
		// test nVerts and vEdges
		System.out.println("Number of Verts: " + myGraph.nVerts());
		System.out.println("Number of Edges: " + myGraph.nEdges());
		
		// test getMat()
		System.out.println("\n" + Arrays.deepToString(myGraph.getMat()));
		
		// test getVertex()
		
		for (int i = 0; i < myGraph.nVerts(); i++) {
			Vertex currV = myGraph.getVertex(i);
			System.out.println("\n" + currV);
			System.out.println(myGraph.getVertex(currV.getLabel()));
		}
		
		// test getEdge() vertex
		System.out.println();
		int numEdge = 0;
		for (int i = 0; i < myGraph.nVerts(); i++) {
			Vertex src = myGraph.getVertex(i);
			
			for (int j = 0; j < myGraph.nVerts(); j++) {
				Vertex dst = myGraph.getVertex(j);
				Edge currE = myGraph.getEdge(src, dst);
				if (currE != null) {
					System.out.print(currE + "\t");
					numEdge++;
				}
			}
			System.out.println();
		}
		
		System.out.println(numEdge);
		
		// test getEdge() string
				System.out.println();
				numEdge = 0;
				for (int i = 0; i < myGraph.nVerts(); i++) {
					Vertex src = myGraph.getVertex(i);
					
					for (int j = 0; j < myGraph.nVerts(); j++) {
						Vertex dst = myGraph.getVertex(j);
						Edge currE = myGraph.getEdge(src.getLabel(), dst.getLabel());
						if (currE != null) {
							System.out.print(currE + "\t");
							numEdge++;
						}
					}
					System.out.println();
				}
				
				System.out.println(numEdge);
	}
	
	public static void main(String[] args) {
		
//		for (int i = 1; i < 24; i++) {
//			File graph = new File("src/graphs/g" + i);
//			String gfn = "";
//			try {
//				Scanner in = new Scanner(graph);
//				while (in.hasNextLine()) {
//					gfn += in.nextLine() + "\n";
//				}
//				in.close();
//
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			}
//			System.out.println("--------------------Graph" + i + "--------------------");
//			print(gfn);
//		}
//		
//		System.out.println("\n\n\n\n\n");
//		
//		GraphUtil.testGraph("g1");
		GraphUtil.testGraphs(1,23);
	}
}
