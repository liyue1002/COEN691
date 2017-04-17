package com.example.helloendpoints;
import com.google.api.server.spi.Constant;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

/**
 * Contains the client IDs and scopes for allowed clients consuming the helloworld API.
 */
public class Constants {
  public static final String WEB_CLIENT_ID = "";
  public static final String ANDROID_CLIENT_ID = "replace this with your Android client ID";
  public static final String IOS_CLIENT_ID = "replace this with your iOS client ID";
  public static final String ANDROID_AUDIENCE = WEB_CLIENT_ID;
  // according to the udacity conference app add this constant
  //import com.google.api.server.spi.Constant; 来自于
  public static final String API_EXPLORER_CLIENT_ID = Constant.API_EXPLORER_CLIENT_ID;
  public static final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();;


  public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";
}
