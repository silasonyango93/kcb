package bank.dto.user_management;

import bank.entity.user_management.AuthenticationStatus;

import java.util.List;

public class AuthenticationObject {
    private boolean isAuthenticationSuccessful;
    private String accessToken;
    private String firstName;
    private String userEmail;
    private AuthenticationStatus authenticationStatus;

    public AuthenticationObject(boolean isAuthenticationSuccessful, String accessToken, String firstName, String userEmail, AuthenticationStatus authenticationStatus) {
        this.isAuthenticationSuccessful = isAuthenticationSuccessful;
        this.accessToken = accessToken;
        this.firstName = firstName;
        this.userEmail = userEmail;
        this.authenticationStatus = authenticationStatus;
    }

    public boolean isAuthenticationSuccessful() {
        return isAuthenticationSuccessful;
    }

    public void setAuthenticationSuccessful(boolean authenticationSuccessful) {
        isAuthenticationSuccessful = authenticationSuccessful;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public AuthenticationStatus getAuthenticationStatus() {
        return authenticationStatus;
    }

    public void setAuthenticationStatus(AuthenticationStatus authenticationStatus) {
        this.authenticationStatus = authenticationStatus;
    }
}
