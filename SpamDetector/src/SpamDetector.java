

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpamDetector {
	private Map<String, WordFeature> features = new HashMap<String, WordFeature>();
	private static final Pattern FEATURE_LINE = Pattern.compile("(\\w+),\\d+,([\\d\\.]+),([\\d\\.]+)");
	
	public SpamDetector(InputStream is) {
		Scanner s = new Scanner(is);
		while ( s.hasNextLine() ) {
			String line = s.nextLine();
			Matcher m = FEATURE_LINE.matcher(line);
			if ( m.find() ) {
				WordFeature wf = new WordFeature(m.group(1), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
				features.put(wf.getWord(), wf);
			}
		}
	}
	
	public boolean isSpam(Email email) {
		String subjectAndContent = email.getSubject() + " " + email.getContent();
		String[] split = subjectAndContent.toLowerCase().replaceAll("[!\\?\\.,]", "").split("\\s");
		
		List<Double> probabilities = new ArrayList<Double>();
		
		for ( String s : split ) {
			WordFeature wf = features.get(s);
			if ( wf == null ) {
				probabilities.add(0.005);
			} else {
				Double probability = wf.getSpamCount() / ( wf.getSpamCount() + wf.getHamCount() ) + 0.005;
				probabilities.add(probability);
			}
		}
		
		Double product = 1.0;
		Double inverseProduct = 1.0;
		
		for ( Double prob : probabilities ) {
			product *= prob;
			inverseProduct *= ( 1 - prob );
		}
		
		double total = product / ( product + inverseProduct );
		
		return total > .8;
	}
}