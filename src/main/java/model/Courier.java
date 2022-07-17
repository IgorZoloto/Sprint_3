package model;

import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
   private String login;
    private String password;
    private String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier getRandom(){
        String login = RandomStringUtils.randomAlphabetic(13);
         String password = RandomStringUtils.randomAlphabetic(13);
       String firstName = RandomStringUtils.randomAlphabetic(13);
       return new Courier(login, password, firstName);
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
