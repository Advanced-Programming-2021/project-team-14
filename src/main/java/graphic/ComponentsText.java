package graphic;

public enum ComponentsText {
    DONT_HAVE_ACCOUNT("Don't have an account?"),
    DO_HAVE_ACCOUNT("Already have an account?"),
    LOGIN_TITLE("LOGIN"),
    SIGNUP_TITLE("SIGN UP"),
    LOGIN_BUTTON("Login"),
    SIGNUP_BUTTON("Sign up");
    private String content;
    ComponentsText(String content){
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
