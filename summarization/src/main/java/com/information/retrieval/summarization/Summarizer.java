package com.information.retrieval.summarization;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.stemmer.PorterStemmer;
import opennlp.tools.tokenize.TokenizerME;

public class Summarizer {
	//Text into sentences
	public static String[] splitToSentences(String content)
	{
		//String[] sent = sentenceDetect.sentDetect(content);
		String[] sent = content.split("(?i)(?<=[.?!])\\S+(?=[a-z])");

		return sent;
	}

	//Text into paragraphs
	public static String[] splitToParagraphs(String content)
	{   String[] mystring = content.split("(?i)(?<=[.?!])\\S+(?=[a-z])");
		int len = mystring.length;
		String[] result = new String[len/4];
		String paragraph = "";
		
		for(int i=0, j=0; i<len; i++) {
			if(i%4 == 0 && i != 0){
				result[j]=paragraph;
				j++;
				paragraph = "";
			}
			paragraph = paragraph.concat(mystring[i]).concat(".");
		}
		return result;
	}

	public static <T> Collection <T> intersect (Collection <? extends T> a, Collection <? extends T> b)
	{
		Collection <T> result = new ArrayList <T> ();

		for (T t: a)
		{
			if (b.remove (t)) result.add (t);
		}

		return result;
	}

	//Computing the intersection(common words) between two sentences
	public static float sentenceIntersection (String sentence1, String sentence2)
	{
		//String[] sent1 = tokenizer.tokenize(sentence1);
		//String[] sent2 = tokenizer.tokenize(sentence2);
		String[] sent1 = sentence1.split("\\s");
		String[] sent2 = sentence1.split("\\s");
		ArrayList<String> stemmedSent1 = new ArrayList<String>();
		ArrayList<String> stemmedSent2 = new ArrayList<String>();

		for(String token : sent1)
		{
			PorterStemmer stemmer = new PorterStemmer();
			stemmedSent1.add(stemmer.stem(token));
		}

		for(String token : sent2)
		{
			PorterStemmer stemmer = new PorterStemmer();
			stemmedSent2.add(stemmer.stem(token));
		}

		if (sent1.length + sent2.length == 0)
			return 0;


		List<String> intersectArray = (List<String>) intersect(stemmedSent1, stemmedSent2);

		float result = ((float)(float)intersectArray.size() / ((float)sent1.length + ((float)sent2.length) / 2));

		return result;
	}

	public static String[] intersection(String[] sent1, String[] sent2)
	{
		if(sent1 == null || sent1.length == 0 || sent2 == null || sent2.length == 0)
			return new String[0];

		List<String> sent1List = new ArrayList<String>(Arrays.asList(sent1));
		List<String> sent2List = new ArrayList<String>(Arrays.asList(sent2));

		sent1List.retainAll(sent2List);

		String[] intersect = sent1List.toArray(new String[0]);

		return intersect;
	}

	public static String formatSentence(String sentence)
	{
		return sentence;
	}

	public static String getBestsentenceFromParagraph(String paragraph)
	{	
		if(paragraph == null)
			return "";
		String[] sentences = splitToSentences(formatSentence(paragraph));
		if(sentences == null)
			return "";

		float[][] intersectionMatrix = getSentenceIntersectionMatrix(sentences);

		float[] sentenceScores = getSentenceScores(sentences, intersectionMatrix);

		return getBestSentence(sentences,  sentenceScores);
	}
	public static float[][] getSentenceIntersectionMatrix(String[] sentences)
	{
		//Split the content in to sentences


		int n = sentences.length;

		float[][] intersectionMatrix= new float[n][n];

		for(int i = 0; i< n; i++)
		{
			for(int j = 0; j< n; j++)
			{

				if(i == j)
					continue;

				intersectionMatrix[i][j] = sentenceIntersection(sentences[i], sentences[j]);	

			}
		}

		//Build the sentence dictionary
		//The score of a sentence is the sum of all its intersections

		return intersectionMatrix;
	}

	public static float[] getSentenceScores(String[] sentences, float[][] scores)
	{
		float[] scoresReturn = new float[sentences.length];

		for(int i=0; i<sentences.length; i++)
		{
			int sentenceScore = 0;
			for(int j=0; j<scores[i].length; j++)
			{
				sentenceScore += scores[i][j];				
			}
			scoresReturn[i] = sentenceScore;
		}

		return scoresReturn;
	}

	public static String getBestSentence(String[] sentences, float[] scores)
	{	

		return sentences[getMaxIndex(scores)];

	}

	public static int getMaxIndex(float[] array)
	{
		int maxIndex = 0;
		float max = -1;
		for(int i=0; i<array.length; i++)
		{
			if(array[i]>max)
			{
				max = array[i];
				maxIndex = i;
			}

		}
		return maxIndex;
	}

	public static TokenizerME tokenizer;
	public static SentenceDetectorME sentenceDetect;
	public static Summarizer Instance;

	public Summarizer()
	{
		initialize();
	}

	public void initialize()
	{

	}

	//TODO: stem all words after tokenization and before extracting most important sentences
	public static String stemTerm (String term) {
		PorterStemmer stemmer = new PorterStemmer();
		return stemmer.stem(term);
	}

	public static StringBuilder summarize(String content) {

		Instance = new Summarizer();	 

		String[] paragraphs = splitToParagraphs(content);
		StringBuilder summary = new StringBuilder();

		for(String p : paragraphs)
		{
			String bestSent = getBestsentenceFromParagraph(p);
			if(bestSent != null && bestSent.length() > 0)
				summary.append(bestSent);
		}

		return summary;
	}
}
