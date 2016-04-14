package com.dsciclub.ml;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dsciclub.ml.classifiers.KNearestNeighbors;

public class KNearestNeighborsTest {

	double[][] data;
	int[] classes;
	
	@Before
	public void setUp() throws Exception {
		data = new double[10][1];
		data[0][0] = 0.0;
		data[1][0] = 40.0;
		data[2][0] = 80.0;
		data[3][0] = 60.0;
		data[4][0] = 30.0;
		data[5][0] = 255.0;
		data[6][0] = 205.0;
		data[7][0] = 235.0;
		data[8][0] = 155.0;
		data[9][0] = 185.0;
		
		classes = new int[]{0,0,0,0,0,1,1,1,1,1};
		
	}

	@Test
	public void test() {
		KNearestNeighbors knn = new KNearestNeighbors(3);
		knn.compute(data, classes);
		
		int class1 = knn.predict(new double[]{0.0});
		int class2 = knn.predict(new double[]{255.0});
		System.out.println("Classe de 0.0: " + class1);
		System.out.println("Classe de 255.0: " + class2);
		Assert.assertEquals(class1, 0);
		Assert.assertEquals(class2, 1);
	}

}
