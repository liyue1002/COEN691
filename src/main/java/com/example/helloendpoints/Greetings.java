
 //[START begin]
package com.example.helloendpoints;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilter;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.api.server.spi.Constant;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.User;
import com.google.cloud.language.spi.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.Sentiment;
import com.google.cloud.language.v1.Document.Type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
//[END begin]
 //[START api_def]

/**
 * Defines v1 of a helloworld API, which provides simple "greeting" methods.
 */
@Api(name = "helloworld",
    version = "v1",
    scopes = {Constants.EMAIL_SCOPE},
    // Constants.ANDROID_CLIENT_ID, Constants.IOS_CLIENT_ID
    clientIds = {Constants.WEB_CLIENT_ID, Constants.API_EXPLORER_CLIENT_ID},
    audiences = {Constants.ANDROID_AUDIENCE}
)
public class Greetings {
	private final DatastoreService datastore;
  public static ArrayList<HelloGreeting> greetings = new ArrayList<HelloGreeting>();

  static {
    greetings.add(new HelloGreeting("hello world!"));
    greetings.add(new HelloGreeting("goodbye world!"));

  }
  public Greetings() {
	  datastore = Constants.datastore;
	
	  }
//[END api_def]
//[START getgreetings]

  //test service
  public HelloGreeting getGreeting(@Named("id") Integer id) throws NotFoundException {
      
//	try {
//		LanguageServiceClient language = LanguageServiceClient.create();
//	      // The text to analyze
//	      String text = "Hello, world! .";
//	      Document doc = Document.newBuilder()
//	              .setContent(text).setType(Type.PLAIN_TEXT).build();
//
//	      // Detects the sentiment of the text
//	      Sentiment sentiment = language.analyzeSentiment(doc).getDocumentSentiment();
//	      
//	      System.out.println("Text " + text);
//	      System.out.println("Sentiment: " + sentiment.getScore() + " " + sentiment.getMagnitude());
//	    
//	} catch (IOException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}

  return greetings.get(id);
  }  

  public HelloGreeting editResume(@Named("userName") String userName, @Named("textResume") String textResume) throws NotFoundException {

		Entity resume = new Entity("Resume", userName);
		resume.setProperty("name", userName);
		resume.setProperty("resume", textResume);
		datastore.put(resume);
		String responding = "";
    	try {
				Entity got = datastore.get(resume.getKey());
				System.out.println(((String) got.getProperty("name")));
				System.out.println(((String) got.getProperty("resume")));
				responding = ((String) got.getProperty("name")) + ((String) got.getProperty("resume"));
		} catch (EntityNotFoundException e) {
				e.printStackTrace();
		}
	  	HelloGreeting respGreeting = new HelloGreeting(responding);
	    return respGreeting;
	  }
  
  public HelloGreeting shareResume(@Named("userName") String userName, @Named("shareName") String shareName) throws NotFoundException {
	  
			Query q =
			            new Query("Resume")
			        .setFilter(new FilterPredicate("name", FilterOperator.EQUAL, userName));
			PreparedQuery pq = datastore.prepare(q);
			Entity resumeEntity  = pq.asSingleEntity();
			String name = (String) resumeEntity.getProperty("name");//username
			String resume = (String) resumeEntity.getProperty("resume");//Resume
			
			Entity shareResume = new Entity("ShareResume");
			shareResume.setProperty("name", userName);
			shareResume.setProperty("shareName", shareName);
			shareResume.setProperty("resume", resume);
			datastore.put(shareResume);
	    	try {
				Entity got = datastore.get(shareResume.getKey());
				System.out.println(((String) got.getProperty("name")));
				System.out.println(((String) got.getProperty("shareName")));
				System.out.println(((String) got.getProperty("resume")));
				
			} catch (EntityNotFoundException e) {
				
				e.printStackTrace();
			}
			 HelloGreeting respGreeting = new HelloGreeting(name + " " + resume + " " +shareName);
			  return respGreeting;
	  }
  
  public HelloGreeting viewShareResume(@Named("selfName") String selfName, @Named("nameOfResume") String nameOfResume) throws NotFoundException {
			    System.out.println(selfName +" "+ nameOfResume);
				Filter selfNameFilter =
				    new FilterPredicate("shareName", FilterOperator.EQUAL, selfName);
				Filter nameOfResumeFilter =
				    new FilterPredicate("name", FilterOperator.EQUAL, nameOfResume);
				// Use CompositeFilter to combine multiple filters
				CompositeFilter findResumeFilter =  CompositeFilterOperator.and(selfNameFilter, nameOfResumeFilter);
				
				// Use class Query to assemble a query
				Query q = new Query("ShareResume").setFilter(findResumeFilter);
				
				// Use PreparedQuery interface to retrieve results
				PreparedQuery pq = datastore.prepare(q);
				Entity shareResume  = pq.asSingleEntity();
				String resume = (String) shareResume.getProperty("resume");//Resume
			    HelloGreeting respGreeting = new HelloGreeting(resume);
			    return respGreeting;
	  }
  
  public HelloGreeting editTopic(@Named("topicTitle") String topicTitle, @Named("content") String content, User user) throws NotFoundException {

		Entity topic = new Entity("Topic", topicTitle);
		topic.setProperty("topicTitle", topicTitle);
		topic.setProperty("content", content);
		String ue = "example@example.com";
		topic.setProperty("userEmail", ue);
		datastore.put(topic);
		String responding = "";
  	try {
			Entity got = datastore.get(topic.getKey());
			System.out.println(((String) got.getProperty("topicTitle")));
			System.out.println(((String) got.getProperty("content")));
			System.out.println((String) got.getProperty("userEmail"));
			responding = ((String) got.getProperty("topicTitle")) + ((String) got.getProperty("content")+((String) got.getProperty("userEmail")));
		} catch (EntityNotFoundException e) {
			e.printStackTrace();
		}
	  	HelloGreeting respGreeting = new HelloGreeting(responding);
	    return respGreeting;
	  }
  
  public ArrayList<TopicPass> showTopic() {
	  			Query q3 = new Query("Topic");
			    PreparedQuery pq3 = datastore.prepare(q3);
			    List<Entity> topicAndEmailList = pq3.asList(FetchOptions.Builder.withLimit(5));
			    
			    for( int i = 0 ; i<topicAndEmailList.size() ; i++ ){
			    	System.out.println((String) topicAndEmailList.get(i).getProperty("topicTitle"));
			    	System.out.println((String) topicAndEmailList.get(i).getProperty("userEmail"));
			    }
			    
			    ArrayList<TopicPass> TopicList = new ArrayList<TopicPass>();
		    	String to = "abc";
		    	String ue = "example@example.com";
		    	TopicPass topicPass1 = new TopicPass(to, ue);
		    	TopicList.add(topicPass1);
			    
			    for( int i = 0 ; i<topicAndEmailList.size() ; i++ ){
			    	String topicTitle = (String) topicAndEmailList.get(i).getProperty("topicTitle");
			    	String userEmail = (String) topicAndEmailList.get(i).getProperty("userEmail");
			    	TopicPass topicPass2 = new TopicPass(topicTitle, userEmail);
			    	TopicList.add(topicPass2);
			    }
			    return TopicList;
	  }
//[END getgreetings]
//[START multiplygreetings]

  //editComment
  public HelloGreeting editComment(@Named("topicTitle") String topicTitle, @Named("commentContent") String commentContent,@Named("topicEditorEmail") String topicEditorEmail ) throws NotFoundException {
  
		Entity comment = new Entity("Comment");
		comment.setProperty("topicTitle", topicTitle);
		comment.setProperty("commentContent", commentContent);
		comment.setProperty("userEmail", topicEditorEmail);
		datastore.put(comment);
		String responding = "";
	try {
			Entity got = datastore.get(comment.getKey());
			System.out.println((String) got.getProperty("topicTitle"));
			System.out.println((String) got.getProperty("commentContent"));
			System.out.println((String) got.getProperty("userEmail"));
			responding = (String) got.getProperty("commentContent");
		} catch (EntityNotFoundException e) {
		
			e.printStackTrace();
		}
	  	HelloGreeting respGreeting = new HelloGreeting(responding);
	    return respGreeting;
	  }
  
//topicTItle
  public ArrayList<HelloGreeting> getCommentList(@Named("topicTitle") String topicTitle) {
		System.out.println("In the comment list");
		Filter propertyFilter =
			    new FilterPredicate("topicTitle", FilterOperator.EQUAL, topicTitle);
		Query q5 = new Query("Comment").setFilter(propertyFilter);
	    PreparedQuery pq5 = datastore.prepare(q5);
	    List<Entity> commentList = pq5.asList(FetchOptions.Builder.withDefaults());

	    ArrayList<HelloGreeting> commentListOutput = new ArrayList<HelloGreeting>();
	    for( int i = 0 ; i<commentList.size() ; i++ ){
	    	System.out.println((String) commentList.get(i).getProperty("commentContent"));
	    	commentListOutput.add(new HelloGreeting((String) commentList.get(i).getProperty("commentContent")));
	    }
	    
	    return commentListOutput;
}
  //showPercentage
//  public ArrayList<SentimentPercentage> showPercentage(@Named("topicTitle") String topicTitle, User user ) throws NotFoundException {
//
////		String userEmail = user.getEmail();
//	  String userEmail ="example@example.com";
//		Filter propertyFilter =
//			    new FilterPredicate("userEmail", FilterOperator.EQUAL, userEmail);
//		Query q4 = new Query("Comment").setFilter(propertyFilter);
//	    PreparedQuery pq3 = datastore.prepare(q4);
//	    List<Entity> a = pq3.asList(FetchOptions.Builder.withDefaults());
//	    //user.getEmail()
//	    for( int i = 0 ; i<a.size() ; i++ ){
//	    	System.out.println( a.get(i).getProperty("topicTitle")+" " + i +"call natural language processing");
//	    }
//	    
//	    ArrayList<Sentimenta> senti = new ArrayList<Sentimenta>();
//	    double score =  0.8;
//	    double magnitude =  3.0;
//	    Sentimenta senti1 = new Sentimenta(score,magnitude);//Clearly Positive
//	    senti.add(senti1);
//	     score =  -0.6;
//	     magnitude = 4.0;
//	    Sentimenta senti2 = new Sentimenta(score,magnitude);
//	    senti.add(senti2); //Clearly Negative
//	     score =   0.1;
//	     magnitude = 0.0;
//	    Sentimenta senti3 = new Sentimenta(score,magnitude);
//	    senti.add(senti3); //Neutral
//	     score =    0.0;
//	     magnitude = 4.0;
//	    Sentimenta senti4 = new Sentimenta(score,magnitude);
//	    senti.add(senti4); //Mixed
//	    ArrayList<SentimentPercentage> SentiList =  new  ArrayList<SentimentPercentage>();
//	    Integer SentiNum = 10;
//		String SentiName ="Positive";
//		SentimentPercentage Posi = new SentimentPercentage(SentiNum, SentiName);
//	    SentiNum = 20;
//		SentiName ="Mixed";
//		SentimentPercentage Mixe = new SentimentPercentage(SentiNum, SentiName);
//		SentiList.add(Posi);
//		SentiList.add(Mixe);
//	    return SentiList;
//	  }
  
  @ApiMethod(name = "greetings.multiply", httpMethod = "post")
  public HelloGreeting insertGreeting(@Named("times") Integer times, HelloGreeting greeting) {
    HelloGreeting response = new HelloGreeting();
    StringBuilder responseBuilder = new StringBuilder();
    for (int i = 0; i < times; i++) {
      responseBuilder.append(greeting.getMessage());
    }
    response.setMessage(responseBuilder.toString());
    return response;
  }
//[END multiplygreetings]
//[START auth] 

  @ApiMethod(name = "greetings.authed", path = "hellogreeting/authed")
  public HelloGreeting authedGreeting(User user) {
    HelloGreeting response = new HelloGreeting("hello " + user.getEmail());
    return response;
  }
//[END auth]
  public ArrayList<HelloGreeting> listGreeting() {
	  
	    return greetings;
	  }
}
