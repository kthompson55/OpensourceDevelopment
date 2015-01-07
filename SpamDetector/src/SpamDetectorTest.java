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
			
			Email spam = new Email("bob@neumont.edu","jcummings@neumont.edu","specializing,10264,391,4022,graciously,583326,2065,380","specializing,10264,391,4022,graciously,583326,2065,380");
			
			assertEquals(detector.isSpam(spam),true);
		}
		catch (IOException e)
		{
			fail("Not valid file!");
		}
	}

	@Test
	public void testIsSpam() {
		fail("Not yet implemented");
	}

}