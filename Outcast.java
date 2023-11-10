
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;



public class Outcast {
    private WordNet net;
    public Outcast(WordNet wordnet){
        net = wordnet;

    }         // constructor takes a WordNet object
    public String outcast(String[] nouns) {
        int[] lis = new int[nouns.length];
        int j = 0;
        int min = Integer.MIN_VALUE;
        String anc = null;
        for (String i : nouns) {
            for (String jt : nouns) {
                try {
                    int x = net.distance(i, jt);
                    lis[j] += x;

                } catch (IllegalArgumentException e) {

                }
            }
            j++;
        }
        for(int jt=0;jt<lis.length;jt++){
            if(lis[jt]>min){
                min = lis[jt];
                anc = nouns[jt];
            }
        }
        return anc;

    }   // given an array of WordNet nouns, return an outcast
    public static void main(String[] args) {
            WordNet wordnet = new WordNet(args[0], args[1]);
            Outcast outcast = new Outcast(wordnet);
            for (int t = 2; t < args.length; t++) {
                In in = new In(args[t]);
                String[] nouns = in.readAllStrings();
                StdOut.println(args[t] + ": " + outcast.outcast(nouns));
            }

    }// see test client below
}