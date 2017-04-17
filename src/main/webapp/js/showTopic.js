/**
 * @fileoverview
 * Provides methods for the Hello Endpoints sample UI and interaction with the
 * Hello Endpoints API.
 *
 * @author danielholevoet@google.com (Dan Holevoet)
 */

/** google global namespace for Google projects. */
var google = google || {};

/** devrel namespace for Google Developer Relations projects. */
google.devrel = google.devrel || {};

/** samples namespace for DevRel sample code. */
google.devrel.samples = google.devrel.samples || {};

/** hello namespace for this sample. */
google.devrel.samples.hello = google.devrel.samples.hello || {};

/**
 * Client ID of the application (from the APIs Console).
 * @type {string}
 */
google.devrel.samples.hello.CLIENT_ID = ' '

/**
 * Scopes used by the application.
 * @type {string}
 */
google.devrel.samples.hello.SCOPES =
    'https://www.googleapis.com/auth/userinfo.email';

/**
 * Whether or not the user is signed in.
 * @type {boolean}
 */
google.devrel.samples.hello.signedIn = false;

/**
 * Loads the application UI after the user has completed auth.
 */
google.devrel.samples.hello.userAuthed = function() {
	
  var request = gapi.client.oauth2.userinfo.get().execute(function(resp) {
	  console.log("executine autherization");
    if (!resp.code) {
      google.devrel.samples.hello.signedIn = true;
      document.getElementById('signinButton').innerHTML = 'Sign out';
    }
  });
};

/**
 * Handles the auth flow, with the given value for immediate mode.
 * @param {boolean} mode Whether or not to use immediate mode.
 * @param {Function} callback Callback to call on completion.
 */
google.devrel.samples.hello.signin = function(mode, callback) {
  gapi.auth.authorize({client_id: google.devrel.samples.hello.CLIENT_ID,
      scope: google.devrel.samples.hello.SCOPES, immediate: mode},
      callback);
  console.log("after authorize the file");
};

/**
 * Presents the user with the authorization popup.
 */
google.devrel.samples.hello.auth = function() {
  if (!google.devrel.samples.hello.signedIn) {
    google.devrel.samples.hello.signin(false,
        google.devrel.samples.hello.userAuthed);
  } else {
    google.devrel.samples.hello.signedIn = false;
    document.getElementById('signinButton').innerHTML = 'Sign in';
//    document.getElementById('authedGreeting').disabled = true;
  }
};

/**
 * Prints a greeting to the greeting log.
 * param {Object} greeting Greeting to print.
 */
google.devrel.samples.hello.print = function(greeting) {
  var element = document.createElement('div');
  element.classList.add('row');
  element.innerHTML = greeting.topicPass;
  element.setAttribute("data-a",greeting.userEmail);
  element.className = "top"
  document.getElementById('outputLog').appendChild(element);
};

google.devrel.samples.hello.print2 = function(greeting) {
	  var element = document.createElement('div');
	  element.classList.add('row');
	  element.innerHTML = greeting.message;
	  document.getElementById('addcomment').appendChild(element);
	};
	
/**
 * Lists greetings via the API.
 */
google.devrel.samples.hello.listGreeting = function() {
  gapi.client.helloworld.greetings.showTopic().execute(
      function(resp) {
        if (!resp.code) {
          resp.items = resp.items || [];
          for (var i = 0; i < resp.items.length; i++) {
            google.devrel.samples.hello.print(resp.items[i]);
          }
        }
      });
};

google.devrel.samples.hello.editComment = function(topicTitle, comment ) {
	   
	   var topicNodes = document.getElementsByClassName("top")  || [];
	   var editorEmail ;
       for (var i = 0; i < topicNodes.length; i++) {
           
           if(topicNodes[i].innerHTML === topicTitle)
               {
	        	   editorEmail = topicNodes[i].getAttribute("data-a")
	               console.log(topicNodes[i].innerHTML);
	        	   console.log(editorEmail);
               }
       }
	  gapi.client.helloworld.greetings.editComment({'topicTitle': topicTitle,  'commentContent': comment, 'topicEditorEmail': editorEmail}).execute(
	      function(resp) {
	        if (!resp.code) {
	        	console.log(resp);
	          google.devrel.samples.hello.print2(resp);
	        }
	      });
};
/**
 * Enables the button callbacks in the UI.
 */
google.devrel.samples.hello.enableButtons = function() {

	google.devrel.samples.hello.listGreeting();
	
    document.getElementById('editComment').onclick = function() {
	    google.devrel.samples.hello.editComment(
	    		// create a api editResume
	        document.getElementById('topicTitle').value,
	        document.getElementById('comment').value);
	  }
	  
  document.getElementById('signinButton').onclick = function() {
    google.devrel.samples.hello.auth();
  }
};

/**
 * Initializes the application.
 * @param {string} apiRoot Root of the API's path.
 */
google.devrel.samples.hello.init = function(apiRoot) {
  // Loads the OAuth and helloworld APIs asynchronously, and triggers login
  // when they have completed.
  var apisToLoad;
  var callback = function() {
    if (--apisToLoad == 0) {
      google.devrel.samples.hello.enableButtons();
      console.log("after enable the button");
      google.devrel.samples.hello.signin(true,
          google.devrel.samples.hello.userAuthed);
    }
  }

  apisToLoad = 2; // must match number of calls to gapi.client.load()
  gapi.client.load('helloworld', 'v1', callback, apiRoot);
  gapi.client.load('oauth2', 'v2', callback);
};
