/*
  Copyright 2016, Google, Inc.

  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
*/



// [START language_quickstart]
// Imports the Google Cloud client library
package com.example.helloendpoints;
import com.google.cloud.language.spi.v1.LanguageServiceClient;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Document.Type;
import com.google.cloud.language.v1.Sentiment;

public class getSentimentAboutTopic {
  public static void main(String... args) throws Exception {
    // Instantiates a client
//    LanguageServiceClient language = LanguageServiceClient.create();

    // The text to analyze
//    String text = "Hello, world!";
//    Document doc = Document.newBuilder()
//            .setContent(text).setType(Type.PLAIN_TEXT).build();
//
//    // Detects the sentiment of the text
//    Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();
//
//    System.out.printf("Text: %s%n", text);
//    System.out.printf("Sentiment: %s, %s%n", sentiment.getScore(), sentiment.getMagnitude());
    
	  LanguageServiceClient language;
	try {
		language = LanguageServiceClient.create();
		ArrayList<String> sentenceList = new ArrayList<String>();
	    Scanner sc;
		try {
			sc = new Scanner(new File("C:\\test.txt"));
			System.out.println(sc.toString());
		    while (sc.hasNextLine()) {
		    	String t = sc.nextLine();
		    	sentenceList.add( t);
		    	System.out.println( t);
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    
//	    String text = "Hello, world! .";
//		Document doc = Document.newBuilder()
//		        .setContent(text).setType(Type.PLAIN_TEXT).build();
//		Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();
//		System.out.println("Sentiment: " + sentiment.getScore() + " " + sentiment.getMagnitude());
	    int clearlyPositive = 0; 
	    int clearlyNegative = 0; 
	    int neutral = 0; 
	    int mixed = 0; 
	    float score = (float) 0.0;
	    float magnitude = (float) 0.0;
	    for(int i=0; i < sentenceList.size(); i++ )
	    {
				Document doc = Document.newBuilder()
				        .setContent(sentenceList.get(i)).setType(Type.PLAIN_TEXT).build();
				Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();
				score = sentiment.getScore();
				magnitude = sentiment.getMagnitude();
				System.out.println("Sentiment: " + sentiment.getScore() + " " + sentiment.getMagnitude());
				
				if(score >=0.8 && magnitude >= 3.0 ){
					clearlyPositive++;
				}else if(score <= -0.5 && magnitude >= 4.0 ){
					clearlyNegative++;
				} else if(score >= -0.5 && score <= 0.5 &&magnitude >= 3.0 ){
					neutral++;
				} else{
					mixed++;
				}
	    }
	    System.out.println("clearlyPositive is "+clearlyPositive);
	    System.out.println("clearlyNegative is "+clearlyNegative);
	    System.out.println("neutral is "+neutral);
	    System.out.println("mixed is "+mixed);
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
  
  }
}
// [END language_quickstart]
