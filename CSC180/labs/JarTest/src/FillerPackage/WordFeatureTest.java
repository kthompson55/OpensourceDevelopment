package FillerPackage;
import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;


public class WordFeatureTest 
{
	@Test
	public void testGetSpamCount() 
	{
		WordFeature feature = new WordFeature("testWord",10,13);
		assertEquals(13.0,feature.getSpamCount(),0);
	}

	@Test
	public void testGetHamCount() 
	{
		WordFeature feature = new WordFeature("testWord",10,13);
		assertEquals(10.0,feature.getHamCount(),0);
	}

	@Test
	public void testGetWord() 
	{
		WordFeature feature = new WordFeature("testWord",10,13);
		assertEquals("testWord",feature.getWord());
	}

}
