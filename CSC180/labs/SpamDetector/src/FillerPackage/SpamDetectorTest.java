package FillerPackage;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;


public class SpamDetectorTest {

	@Test
	public void testSpamDetector() 
	{
		try
		{
			File spamFile = new File("antispam-table.txt");
			InputStream ioStream = new FileInputStream(spamFile);
			SpamDetector detector = new SpamDetector(ioStream);
			
			// spam
			Email spam = new Email("bob@neumont.edu","jcummings@neumont.edu","specializing","specializing");
			assertEquals(true, detector.isSpam(spam));
			
			// not spam
			Email notSpam = new Email("bob@neumont.edu","jcummings@neumont.edu","Hello","You know something, I don't think this message is spam, but will it be marked as such?");
			assertEquals(false, detector.isSpam(notSpam));
		}
		catch (IOException e)
		{
			fail("Not valid file!");
		}
	}

	@Test
	public void testIsSpam() 
	{
		try
		{
			File spamFile = new File("antispam-table.txt");
			InputStream ioStream = new FileInputStream(spamFile);
			SpamDetector detector = new SpamDetector(ioStream);
			
			// spam
			Email spam = new Email("bob@neumont.edu","jcummings@neumont.edu","specializing","specializing");
			assertEquals(true, detector.isSpam(spam));
		}
		catch (IOException e)
		{
			fail("Not valid file!");
		}
	}
	
	@Test
	public void testIsNotSpam()
	{
		try
		{
			File spamFile = new File("antispam-table.txt");
			InputStream ioStream = new FileInputStream(spamFile);
			SpamDetector detector = new SpamDetector(ioStream);
			
			// not spam
			Email notSpam = new Email("bob@neumont.edu","jcummings@neumont.edu","Hello","You know something, I don't think this message is spam, but will it be marked as such?");
			assertEquals(false, detector.isSpam(notSpam));
		}
		catch (IOException e)
		{
			fail("Not valid file!");
		}
	}
}
