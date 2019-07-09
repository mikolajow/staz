package src;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import static src.Constants.WEBSITE_PASSWORD;
import static src.Constants.WEBSITE_USERNAME;

public class CustomAuthenticator extends Authenticator {

    @Override
    protected PasswordAuthentication getPasswordAuthentication(){
        String username = WEBSITE_USERNAME;
        String password = WEBSITE_PASSWORD;

        return new PasswordAuthentication(username, password.toCharArray());
    }
}
