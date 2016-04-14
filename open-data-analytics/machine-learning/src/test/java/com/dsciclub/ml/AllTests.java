package com.dsciclub.ml;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import junit.framework.Assert;

@RunWith(Suite.class)
@SuiteClasses({ KMeans.class })
public class AllTests {
	
	@Test
    public void AppTest( )
    {
		System.out.println("Test 1");
		Assert.assertTrue(true);
    }
}
