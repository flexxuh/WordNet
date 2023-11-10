import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Topological;

import java.util.ArrayList;
import java.util.TreeMap;

public class WordNet {
    private TreeMap<String, Bag<Integer>> map;
    private Digraph graph;
    private ArrayList<String> str;
    private ArrayList<String> syn;
    private int nou;


    private SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        syn = new ArrayList<>();
        In in = new In(synsets);
        map = new TreeMap<>();
        str = new ArrayList<>();
        int count =0;
        while(in.hasNextLine()) {
            String[] line = in.readLine().split(",");
            String syns = line[1];
            String[] lr = syns.split(" ");
            for (String i : lr) {
                if (map.get(i) == null) {
                    map.put(i, new Bag<>());
                    count++;
                    syn.add(i);
                }
                map.get(i).add(Integer.parseInt(line[0]));
            }
            str.add(Integer.parseInt(line[0]),syns);
        }
        in = new In(hypernyms);
        graph = new Digraph(count);
        while(in.hasNextLine()){
            String[] line = in.readLine().split(",");
            int v = Integer.parseInt(line[0]);
            for(int i=1;i<line.length;i++){
                int w = Integer.parseInt(line[i]);
                graph.addEdge(v,w);
            }
        }
        sap = new SAP(graph);
        Topological tt = new Topological(graph);
        if(!tt.hasOrder()){
            throw new IllegalArgumentException("Not a DAG");
        }
        int v = graph.V();
        int countt =0;
        for (int i=0;i<v;i++){
            if(graph.outdegree(i)==0&&graph.indegree(i)!=0){
                countt++;
            }
        }
        if(countt>1){
            throw new IllegalArgumentException("Not a DAG");
        }
        nou = syn.size();

    }

    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return syn;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if(word != null) {
            return map.containsKey(word);
        }
        throw new IllegalArgumentException("Word is Null");
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if(nounA!=null&&nounB!=null) {
            Bag a = map.get(nounA);
            Bag b = map.get(nounB);
            return sap.length(a, b);
        }
        throw new IllegalArgumentException("Item was null");
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if(nounA!=null&&nounB!=null) {
            Bag a = map.get(nounA);
            Bag b = map.get(nounB);
            int t = sap.ancestor(a, b);
            return str.get(t);
        }
        throw new IllegalArgumentException("Item was null");
    }

    // do unit testing of this class
    public static void main(String[] args){
        WordNet net = new WordNet("synsets.txt","hypernyms.txt");
        System.out.println(net.nou);
    }
}