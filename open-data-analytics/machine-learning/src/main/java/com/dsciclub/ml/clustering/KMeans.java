package com.dsciclub.ml.clustering;

public class KMeans {

	/**
	 * The centroids of each cluster.
	 */
	double[][] centroids;

	int k = 2;

	/**
	 * Returns the centroids.
	 */
	double[][] getCentroids() {
		return centroids;
	}
	
	public void setCentroids(double[][] centroids) {
		this.centroids = centroids;
	}

	public KMeans() {
		this(3);
	}

	public KMeans(int k) {
		this.k = k;
	}

	/**
	 * Cluster a new instance.
	 * 
	 * @param x
	 *            a new instance.
	 * @return the cluster label, which is the index of nearest centroid.
	 */
	public int predict(double[] x) {
		double minDist = Double.MAX_VALUE;
		int bestCluster = 0;

		for (int i = 0; i < k; i++) {
			double dist = euclidean(x, centroids[i]);
			if (dist < minDist) {
				minDist = dist;
				bestCluster = i;
			}
		}

		return bestCluster;
	}

	/**
	 * Clustering data into k clusters. Run the algorithm for given times and
	 * return the best one with smallest distortion.
	 * 
	 * @param data
	 *            the input data of which each row is a sample.
	 * @param k
	 *            the number of clusters.
	 * @param maxIteration
	 *            the maximum number of iterations for each running.
	 * @param runs
	 *            the number of runs of K-Means algorithm.
	 */
	void compute(double[][] input) {
		// First step, find the centroids
		int n = input.length;// lines
		int d = input[0].length;// dimension
		double[][] lastCentroids = new double[k][d];

		//For a unsupervised problem
		if(centroids == null) {
			// k clusters and d dimension
			centroids = new double[k][d];
			// Utilizado apenas para comparar se o movimento dos centros se
			// estabilizaram
	
			int[] randomCentroidsIndex = new int[k];
			for (int i = 0; i < k; i++) {
				int randomSi = (int) (Math.random() * n);
				centroids[i] = input[randomSi];
				randomCentroidsIndex[i] = randomSi;
			}
		}

		// 0 - input(1) => centroid 1 data
		// 1 - input(2) => centroid 2 data
		// 2 - input(2) => centroid 3 data
		double[][][] dataCentroidSeparated = associateClusters(input, n, d);
		int iteractions = 0;
		boolean isFinished = false;
		do {

			for (int c = 0; c < k; c++) {
				int sizeElements = dataCentroidSeparated[c].length;

				double sumItems[] = new double[d];
				double meanItems[] = new double[d];

				// Summarize all lines in one
				for (int l = 0; l < sizeElements; l++) {
					for (int p = 0; p < d; p++) {
						sumItems[p] += dataCentroidSeparated[c][l][p];
					}
				}

				// Calculate the mean for all attributes
				for (int l = 0; l < sizeElements; l++) {
					for (int p = 0; p < d; p++) {
						meanItems[p] = sumItems[p] / sizeElements;
					}
				}

				// Update the centroid with mean values for all attributes
				for (int b = 0; b < d; b++) {
					lastCentroids[c][b] = centroids[c][b];// Keep prior
															// information to
															// calcula wheather
															// the centroids is
															// stabilized
					centroids[c][b] = meanItems[b];
				}

			}
			dataCentroidSeparated = associateClusters(input, n, d);
			iteractions++;

			isFinished = isCentroidStabilized(centroids, lastCentroids);

			for (int i = 0; i < centroids.length; i++) {
				System.out.println("Centroid[" + iteractions + "] " + i + ": ");
				for (int j = 0; j < centroids[i].length; j++) {
					System.out.print(getCentroids()[i][j] + " - ");
				}
			}
		} while (isFinished == false);

	}

	/**
	 * Associate each input to a cluster.
	 * 
	 * @param table
	 *            of data, a double matrix
	 * @param number
	 *            of records
	 * @param dimension
	 *            or number of atributes
	 * @return Results in a extra dimentional space to associate each input in a
	 *         cluster.
	 */
	public double[][][] associateClusters(double[][] input, int n, int d) {
		double[][][] clusterMap = new double[k][n][d];
		// Distance of each point to centroid
		double[][] dists = new double[n][k];
		int[] centroidSum = new int[k];
		// For each centroid
		for (int l = 0; l < n; l++) {
			// And for each input line calculates the distances
			for (int c = 0; c < k; c++) {
				// Lines X Centroids
				dists[l][c] = euclidean(input[l], centroids[c]);
			}

			// Assign the centroids labels
			int label = 0;
			for (int i = 0; i < (k - 1); i++) {
				if (dists[l][i] < dists[l][i + 1]) {
					label = i;
				} else {
					label = i + 1;
				}
			}
			clusterMap[label][centroidSum[label]++] = input[l];
		}

		return clusterMap;
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

	/**
	 * Indicates if the centroid is stabilized comparing the current centroids
	 * with the older.
	 * 
	 * @param currentCentroids
	 *            is the centroids got at last iteraction
	 * @param priorCentroids
	 *            is the centroids got at the prior iteraction
	 **/
	private boolean isCentroidStabilized(double[][] currentCentroids, double[][] priorCentroids) {
		boolean isStabilized = false;
		for (int i = 0; i < priorCentroids.length; i++) {
			double dist = euclidean(priorCentroids[i], currentCentroids[i]);

			System.out.println("isFinish: " + dist);

			if (dist != 0.0) {
				isStabilized = false;
				break;
			} else {
				isStabilized = true;

			}
		}

		return isStabilized;
	}
}
