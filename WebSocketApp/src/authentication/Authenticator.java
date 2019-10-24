package authentication;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class Authenticator {
	
	private static SecureRandom random = new SecureRandom();
	
	private static Cache<String, String> accessTokens = CacheBuilder.newBuilder()
            .expireAfterAccess(15, TimeUnit.SECONDS) // Entries expire in 15 seconds
            .build();
	
	private static Cache<String,Algorithm> algorithms = CacheBuilder.newBuilder()
            .expireAfterAccess(15, TimeUnit.SECONDS) // Entries expire in 15 seconds
            .build();

    private static Map<String, String> users = new HashMap<>();
    
    static {
        users.put("Can", "123");
        users.put("Gizem", "123");
        users.put("Basar", "123");
    }
    
    public static boolean checkCredentials(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
    
    public static String issueAccessToken(String username) throws IllegalArgumentException, UnsupportedEncodingException {
    	String token = "";
    	String randomSecret = new BigInteger(130, random).toString(32);
    	try {
    	    Algorithm algorithm = Algorithm.HMAC256(randomSecret);
    	    token = JWT.create()
    	        .withIssuer("auth0")
    	        .sign(algorithm);
    	    
    	    algorithms.put(token,algorithm);
    	    accessTokens.put(token,username);
    	} catch (JWTCreationException exception){
    	    //Invalid Signing configuration / Couldn't convert Claims.
    	}
        return token;
    }
    
    public static String getUsername(String accessToken) {
    	String username = accessTokens.getIfPresent(accessToken);
    	Optional<String> optionalUsername = Optional.ofNullable(username);
    	return optionalUsername.orElse("");
    }
    
    public static Boolean verifyToken(String accessToken) throws IllegalArgumentException, UnsupportedEncodingException {
    	Algorithm algorithm = algorithms.getIfPresent(accessToken);
        try {
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build(); //Reusable verifier instance
            @SuppressWarnings("unused")
    		DecodedJWT jwt = verifier.verify(accessToken);
            accessTokens.invalidate(accessToken); // The token can be used only once
            return true;
        	    
        } catch (JWTVerificationException exception){
       	    //Invalid signature/claims
       		return false;
       	}
    	

    }

}
