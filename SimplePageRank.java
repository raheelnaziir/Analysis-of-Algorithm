import java.util.*;

public class SimplePageRank {
	
    static final int N = 4; // number of web pages
    static final double DAMPING = 0.85;
    static final double THRESHOLD = 0.0001;
    static final int MAX_ITER = 100;

    public static void main(String[] args) {
    	
        // Adjacency matrix (who links to whom)
        int[][] links = {
            {0, 1, 1, 0}, // A -> B, C
            {0, 0, 1, 0}, // B -> C
            {1, 0, 0, 0}, // C -> A
            {0, 0, 1, 0}  // D -> C
        };

        double[] rank = new double[N];
        
        Arrays.fill(rank, 1.0 / N); // initial rank

        for (int iter = 0; iter < MAX_ITER; iter++) {
        	
            double[] newRank = new double[N];

            for (int i = 0; i < N; i++) {
                double sum = 0.0;
                for (int j = 0; j < N; j++) {
                    int outLinks = 0;
                    for (int k = 0; k < N; k++) {
                        if (links[j][k] == 1) outLinks++;
                    }

                    if (links[j][i] == 1 && outLinks > 0) {
                        sum += rank[j] / outLinks;
                    }
                }
                newRank[i] = (1 - DAMPING) / N + DAMPING * sum;
            }

            // Check convergence
            double diff = 0.0;
            for (int i = 0; i < N; i++) {
                diff += Math.abs(rank[i] - newRank[i]);
            }

            rank = newRank;

            if (diff < THRESHOLD)
            	break;
        }

        // Print final ranks
        char page = 'A';
        
        for (double r : rank) {
            System.out.printf("Page %c: %.4f%n", page++, r);
        }
    }
}
