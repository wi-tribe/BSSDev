package controllers;

import play.Logger;
import play.libs.Crypto;
import play.mvc.Before;
import play.mvc.Controller;
import play.mvc.Http;

public class Authentication extends Controller {

    @Before(unless={"setConnected", "connected", "isConnected", "logout", "redirectToOriginalURL", "onAuthenticated", "onNotAuthenticated"})
    public static void checkAccess() {
    	Logger.debug("checkAccess");
        if( ! isConnected() ) {
			Logger.debug("keep url : " + request.url);
			// if not connected, save url and move to login page.
			flash.put("url", "GET".equals(request.method) ? request.url : "/"); // seems a good default
			Application.login();
        }
    }

	/**
	 * This method set user's authentication info to session.
	 * @return
	 */
	static String setConnected(String username, boolean remember) {
        // Mark user as connected
        session.put("username", username);
        // Remember if needed
        if(remember) {
            response.setCookie("rememberme", Crypto.sign(username) + "-" + username, "30d");
        }
        return username;
	}

	/**
	 * This method returns the current connected username
	 * @return
	 */
	static String connected() {
		String username = session.get("username");
		if ( username != null ) return username;

    	// if cookie contatins rememberme and rememberme is correct, ok.
        Http.Cookie remember = request.cookies.get("rememberme");
        if(remember != null && remember.value.indexOf("-") > 0) {
            String sign = remember.value.substring(0, remember.value.indexOf("-"));
            username = remember.value.substring(remember.value.indexOf("-") + 1);
            if(Crypto.sign(username).equals(sign)) {
                session.put("username", username);
                return username;
            }
        }
        return null;
	}

	/**
	 * Indicate if a user is currently connected
	 * @return  true if the user is connected
	 */
	static boolean isConnected() {
		return connected() != null;
	}

    public static void logout() {
        session.clear();
        response.removeCookie("rememberme");
        Application.login();
    }
    
    static void redirectToOriginalURL() {
        String url = flash.get("url");
        if(url == null) {
            url = "/";
        }
        else if("/login".equals(url)) {
            url = "/";
        }
        Logger.info("redirect to " + url);
        redirect(url);
    }
}
