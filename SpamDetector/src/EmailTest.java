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
	public void testEmailInputStream() 
	{
		File emailFile = new File("email.txt");
		try
		{
			FileInputStream ioStream = new FileInputStream(emailFile);
			Email testEmail = new Email(ioStream);
			assertEquals("jcummings@neumont.edu", testEmail.getTo());
			assertEquals("bob@neumont.edu",testEmail.getFrom());
			assertEquals("Howdy, stranger!",testEmail.getSubject());
			assertEquals("What do you think of this new email system?",testEmail.getContent());
		}
		catch (IOException e)
		{
			fail("File does not exist!");
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
