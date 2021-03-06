package FillerPackage;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;


public class EmailTest 
{

	@Test
	public void testEmailStringStringStringString() 
	{
		Email testEmail = new Email("testreceiver@example.com","testsender@example.com","Test","Such test");
		assertEquals("testreceiver@example.com",testEmail.getTo());
		assertEquals("testsender@example.com",testEmail.getFrom());
		assertEquals("Test",testEmail.getSubject());
		assertEquals("Such test",testEmail.getContent());
	}
	
	@Test
	public void testBadEmail()
	{
		try
		{
			File attemptedEmail = new File("bogus-email.txt");
			FileInputStream ioStream = new FileInputStream(attemptedEmail);
			Email testEmail = new Email(ioStream);
		}
		catch(IOException e)
		{
			fail("File does not exist!");
		}
		catch(IllegalArgumentException e)
		{
			// email is breaking, which is what we want!
		}
	}

	@Test
	public void testEmailInputStream() 
	{
		try
		{
			File emailFile = new File("email.txt");
			FileInputStream ioStream = new FileInputStream(emailFile);
			Email testEmail = new Email(ioStream);
			// broken regex??
			assertEquals("jcummings@neumont", testEmail.getTo());
			assertEquals("bob@neumont",testEmail.getFrom());
			assertEquals("Howdy",testEmail.getSubject());
			assertEquals("What do you think of this new email system?",testEmail.getContent());
		}
		catch (IOException e)
		{
			fail("File does not exist!");
		}
	}
	
	@Test
	public void testBadFileInputStream()
	{
		try
		{
			File fakeFile = new File("fake.txt");
			FileInputStream ioStream = new FileInputStream(fakeFile);
			Email testEmail = new Email(ioStream);
			
			fail("File exists, apparently");
		}
		catch (IOException e)
		{
			// nothing needs to be here!
		}
	}

	@Test
	public void testGetTo() 
	{
		Email testEmail = new Email("testreceiver@example.com","testsender@example.com","Test","Such test");
		assertEquals("testreceiver@example.com",testEmail.getTo());
	}

	@Test
	public void testGetFrom() 
	{
		Email testEmail = new Email("testreceiver@example.com","testsender@example.com","Test","Such test");
		assertEquals("testsender@example.com",testEmail.getFrom());
	}

	@Test
	public void testGetSubject() 
	{
		Email testEmail = new Email("testreceiver@example.com","testsender@example.com","Test","Such test");
		assertEquals("Test",testEmail.getSubject());
	}

	@Test
	public void testGetContent() 
	{
		Email testEmail = new Email("testreceiver@example.com","testsender@example.com","Test","Such test");
		assertEquals("Such test",testEmail.getContent());
	}
}
