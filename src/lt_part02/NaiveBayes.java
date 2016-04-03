package lt_part02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NaiveBayes {

	double totalNumberOfDocuments;
	double spamDocumentCounter = 0;
	double hamDocumentCounter = 0;
	public double spamTotalTokens = 0;
	public double hamTotalTokens = 0;
	double truePositive = 0;
	double falsePositive = 0;
	double falseNegative = 0;
	double trueNegative = 0;
	Set<String> vocabulary = new HashSet<>();
	public Map<String, Double> spamWordsCount = new HashMap<>();
	Map<String, Double> hamWordsCount = new HashMap<>();

	public String directory;
	Set<String> dataSet = new HashSet<>();
	Set<String> testSet = new HashSet<>();
	String test;
	private BufferedReader br;
	private boolean spam = false;
	double precision;
	double recall;
	double fMeasure;
	double accuracy;
	boolean train;

	public NaiveBayes(String directory,  String testSet, int counter, boolean train) {
		this.directory = directory;
		this.test = testSet;
		this.train = train;
		showFiles(counter);
		initializeLearning();
	}

	public void initializeLearning() {	
		for(String s : dataSet) {
			incrementDocCounter(s);
			try {
				br = new BufferedReader(new FileReader( s ));
				String line;
				while((line = br.readLine()) != null) {	
					String[] word = line.replaceAll( "[^a-zA-Z$ ]", "" ).toLowerCase().split( "\\s+" );
					if(word.length!=0){
						if(word[0].equals("subject")){
							for( int i = 0; i < word.length; i++ ){
								vocabulary.add(word[i]);
								if(isItSpam(s)) {
									if(spamWordsCount.containsKey( word[i] )){
										spamWordsCount.put( word[i], spamWordsCount.get(word[i]) + 2.0 );								
									} else {
										spamWordsCount.put( word[i], 2.0 );								
									}
								}
								else {

									if(hamWordsCount.containsKey( word[i] )){
										hamWordsCount.put( word[i], hamWordsCount.get(word[i]) + 2.0 );							
									} else {
										hamWordsCount.put( word[i], 2.0 );							
									}
								}
							}
						}
						else {
							for( int i = 0; i < word.length; i++ ){
								vocabulary.add(word[i]);
								if(isItSpam(s)) {
									if(spamWordsCount.containsKey( word[i] )){
										spamWordsCount.put( word[i], spamWordsCount.get(word[i]) + 1.0 );								
									} else {
										spamWordsCount.put( word[i], 1.0 );								
									}
								}
								else {

									if(hamWordsCount.containsKey( word[i] )){
										hamWordsCount.put( word[i], hamWordsCount.get(word[i]) + 1.0 );							
									} else {
										hamWordsCount.put( word[i], 1.0 );							
									}
								}
							}
						}
					}
					if(spam) {spamTotalTokens += word.length;} else {hamTotalTokens += word.length;}
				}

			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}

	}
	
	private boolean isItSpam( String s ) {
		return s.matches("[a-z/0-9]*spmsg[a-z][0-9]*.txt");
	}
	
	private void incrementDocCounter(String s) {		
		spam = isItSpam(s);
		if(spam) {
			spamDocumentCounter++;
		}
		else {
			hamDocumentCounter++;
		}
	}
	
	public void estimateCategory() {
		for(String s : testSet) {
			try {
				br = new BufferedReader(new FileReader( s ));
				String line;
				String mail = "";
				while((line = br.readLine()) != null) {	
					mail += line;
				}
				String[] word = mail.replaceAll( "[^a-zA-Z$ ]", "" ).toLowerCase().split( "\\s+" );
				
				double spamProb = estimateCategory(word, "spam");
				double hamProb = estimateCategory(word, "ham");
				if(spamProb > hamProb) {
					if( isItSpam(s) ) {
						truePositive += 1;						
					} else {
						falsePositive += 1;
					}
				}
				else {
					if( isItSpam(s) ) {
						falseNegative += 1;
					} else {
						trueNegative += 1;
					}
				}
				
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
		precision = truePositive/( truePositive + falsePositive );
		recall = truePositive/( truePositive + falseNegative);
		fMeasure = 2*precision*recall / (precision+recall);
		
	}
	
	private double estimateCategory(String[] word, String spamOrHam) {
		double probEstimation = 0;
		if(spamOrHam.equals("spam")) {			
			probEstimation += Math.log10(spamDocumentCounter / totalNumberOfDocuments);
			for (int i = 0; i < word.length; i++) {
				double numerator = 1;
				double denominator = spamTotalTokens + vocabulary.size();
				if(spamWordsCount.get(word[i]) != null) {
					if(spamWordsCount.get(word[i])>2){
					numerator += spamWordsCount.get(word[i]);	
					}
				}
				probEstimation += Math.log10(numerator / denominator);
				
			}
		}
		else {
			probEstimation += Math.log10(hamDocumentCounter / totalNumberOfDocuments);
			for (int i = 0; i < word.length; i++) {
				double numerator = 1;
				double denominator = hamTotalTokens + vocabulary.size();
				if(hamWordsCount.get(word[i]) != null) {
					if(hamWordsCount.get(word[i])>2){
					numerator += hamWordsCount.get(word[i]);	
					}
				}
				probEstimation += Math.log10(numerator / denominator);
			}
		}
		return probEstimation;
	}
	
	public void print() {
		System.out.println("--------------------------------------");
		System.out.format("|%12s|%11s|%11s|\n", " ", "correct", "not correct");
		System.out.println("--------------------------------------");
		System.out.format("|%12s", "selected");
		System.out.format("|%11.1f|%11.1f|\n", truePositive, falsePositive);
		System.out.format("|%12s", "not selected");
		System.out.format("|%11.1f|%11.1f|\n", falseNegative, trueNegative);
		System.out.println("--------------------------------------");
		double precision = truePositive/( truePositive + falsePositive );
		System.out.printf("Precision: %.2f\n", precision );
		double recall = truePositive/( truePositive + falseNegative);
		System.out.printf("Recall: %.2f\n", recall);
		double fMeasure = 2*precision*recall / (precision+recall);
		System.out.printf("F-measure: %.2f\n", fMeasure );
		accuracy = (truePositive+trueNegative)/(truePositive+falsePositive+trueNegative+falseNegative);
		System.out.println("Accuracy: "+accuracy);
	}
	
	//Creates dataSet (set of the training files paths )
	public void showFiles(int counter) {

		File[] dir = new File(directory).listFiles();
		for(File d : dir) {
			if(d.getName().equals( test )) {counter++;}
			if(counter>0 || d.getName().equals( test )){
				//System.out.println(d.getName());
				File[] files = new File(directory+"/"+d.getName()).listFiles();
				for(File f : files) {
					if(d.getName().equals( test ) || train){
						testSet.add(directory+"/"+ d.getName() + "/" + f.getName());
						
					}
					else {					
						dataSet.add(directory+"/"+ d.getName() + "/" + f.getName());

					}
				}
				
				counter--;
			}
		}
		totalNumberOfDocuments = dataSet.size();
	}
}