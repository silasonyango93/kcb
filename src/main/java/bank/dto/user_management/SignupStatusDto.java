package bank.dto.user_management;

public class SignupStatusDto {
    private boolean isSignupSuccessful;
    private String signupEventMessage;

    public SignupStatusDto(boolean isSignupSuccessful, String signupEventMessage) {
        this.isSignupSuccessful = isSignupSuccessful;
        this.signupEventMessage = signupEventMessage;
    }

    public boolean getIsSignupSuccessful() {
        return isSignupSuccessful;
    }

    public void setIsSignupSuccessful(boolean isSignupSuccessful) {
        this.isSignupSuccessful = isSignupSuccessful;
    }

    public String getSignupEventMessage() {
        return signupEventMessage;
    }

    public void setSignupEventMessage(String signupEventMessage) {
        this.signupEventMessage = signupEventMessage;
    }
}
