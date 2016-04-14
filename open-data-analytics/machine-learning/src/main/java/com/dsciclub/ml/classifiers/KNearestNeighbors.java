package com.dsciclub.ml.classifiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dsciclub.ml.core.Distance;

public class KNearestNeighbors {

	//Number of nearest neighbors
    private int k;
    
    //Dataset to predict
    private double[][] input;
    
    //
    private int[] classes;

    /**
     * Get number of neighbours.
     * @return Number of neighbours.
     */
    public int getK() {
        return k;
    }

    /**
     * Set number of neighbours.
     * @param k Number of neighbours.
     */
    public void setK(int k) {
        this.k = Math.max(1, k);
    }

    /**
     * Initializes a new instance of the KNearestNeighbors class.
     */
    public KNearestNeighbors(){
        this(3);
    }
    
    /**
     * Initializes a new instance of the KNearestNeighbors class.
     * @param k Number of neighbors.
     */
    public KNearestNeighbors(int k){
    	this.k = k;
    }
    
    public void compute(double[][] input, int[] output){
        this.input = input;
        this.classes = output;
    }
    
    /**
     * Compute.
     * @param feature Feature to compute.
     * @return Object.
     */
    public int predict(double[] feature){
        
        int sizeF = input.length;
        double[] dist = new double[sizeF];
        
        //Compute distance.
        for (int i = 0; i < sizeF; i++)
            dist[i] = euclidean(feature, input[i]);
        
        //Sort indexes based on score
//        int[] indexes = Matrix.Indices(0, dist.length);
        List<Distance> lst = new ArrayList<Distance>(dist.length);
        for (int i = 0; i < dist.length; i++) {
            lst.add(new Distance(dist[i], i));
        }
        
        Collections.sort(lst);
        int min = classes[lst.get(0).getIndex()];
        
        //Compute vote majority
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int max = 0;
        for (int i = 0; i < k; i++) {
            int index = lst.get(i).getIndex();
            if(!map.containsKey(classes[index])){
                map.put(classes[index], 1);
            }
            else{
                int x = map.get(classes[index]) + 1;
                map.put(classes[index], x);
                if(x > max) max = x;
            }
        }
        
        for(Map.Entry<Integer,Integer> entry : map.entrySet())
          if(entry.getValue() == max)
              return entry.getKey();
        
        
        return min;
    }
    
    /**
	 * Gets the Euclidean distance between two points.
	 * 
	 * @param a
	 *            A point in space.
	 * @param b
	 *            A point in space.
	 * 
	 * @return The Euclidean distance between a and b.
	 */
	public static double euclidean(double[] a, double[] b) {
		double d = 0.0, u;

		for (int i = 0; i < a.length; i++) {
			u = a[i] - b[i];
			d += u * u;
		}

		return Math.sqrt(d);
	}
//    private class Score implements Comparable<Score> {
//        double score;
//        int index;
//
//        public Score(double score, int index) {
//            this.score = score;
//            this.index = index;
//        }
//
//        @Override
//        public int compareTo(Score o) {
//            return score < o.score ? -1 : score > o.score ? 1 : 0;
//        }
//    }

}
