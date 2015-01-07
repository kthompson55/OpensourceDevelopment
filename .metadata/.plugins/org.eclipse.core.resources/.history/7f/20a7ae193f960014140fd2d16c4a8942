

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {
	private final String to;
	private final String from;
	private final String subject;
	private final String content;

	private static final Pattern FROM_PATTERN = Pattern.compile("From:\\s*(\\w+@\\w+)");
	private static final Pattern TO_PATTERN = Pattern.compile("To:\\s*(\\w+@\\w+)");
	private static final Pattern SUBJECT_PATTERN = Pattern.compile("Subject:\\s*(\\w+)");
	
	public Email(String to, String from, String subject, String content) {
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.content = content;
	}

	public Email(InputStream is) throws IOException {
		// wrap the InputStream in a scanner
		Scanner s = new Scanner(is);

		from = findProperty(s.nextLine(), FROM_PATTERN);
		
		to = findProperty(s.nextLine(), TO_PATTERN);
		
		subject = findProperty(s.nextLine(), SUBJECT_PATTERN);

		StringBuilder sb = new StringBuilder();
		while ( s.hasNextLine() ) {
			sb.append(s.nextLine());
		}
		content = sb.toString();
	}
	
	private String findProperty(String line, Pattern pattern) {
		Matcher m = pattern.matcher(line);
		if ( m.find() ) {
			return m.group(1);
		} else {
			throw new IllegalArgumentException("From not correctly formatted.");
		}
	}
	
	public String getTo() {
		return to;
	}

	public String getFrom() {
		return from;
	}

	public String getSubject() {
		return subject;
	}

	public String getContent() {
		return content;
	}
}
