package carshop.carshop;

// UserSession.java
import javafx.beans.property.SimpleBooleanProperty;

public class UserSession {
    private static UserSession instance;
    private User loggedInUser;
    private final SimpleBooleanProperty loginStatusChanged = new SimpleBooleanProperty(false);

    private UserSession() {
        // Private constructor to enforce singleton pattern
    }

    public static synchronized UserSession getInstance() {
        if (instance == null) {
            instance = new UserSession();
        }
        return instance;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
        loginStatusChanged.set(true);
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public void logout() {
        loggedInUser = null;
        loginStatusChanged.set(true);
    }

    public SimpleBooleanProperty loginStatusChangedProperty() {
        return loginStatusChanged;
    }
}
