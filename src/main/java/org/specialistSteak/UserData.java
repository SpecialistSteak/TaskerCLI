package org.specialistSteak;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

//TODO: Rewrite the methods for consistency (i.e. make sure they all either do or do not ask for user input, either run that here or in the
// UserCommand class, etc.)(check as well for making sure that the right method (eg. setting someone as the last user boolean
// or setting the last user to the current user) is being called in the right place and when it should be in the driver class)

public class UserData {
    private String APIKey;
    private String username;
    private boolean isLastUsed;
    private String password;
    private String dateCreated;
    private File tasklistAddress;
    static ArrayList <UserData> userData = new ArrayList<>();
    static UserData lastUserData;

    //constructor
    @JsonCreator
    public UserData(@JsonProperty("APIKey") String APIKey, @JsonProperty("username") String username,
                    @JsonProperty("password") String pword, @JsonProperty("isLastUsed") boolean isLastUsed) {
        this.password = pword;
        this.APIKey = APIKey;
        this.username = username;
        this.isLastUsed = isLastUsed;
        this.tasklistAddress = new File("src/main/resources/" + username + "_tasks.json");
        Scanner scanner = new Scanner(System.in);
        while(checkUserExist(this.username)) {
            System.out.println("User already exists. Please enter a new username: ");
            this.username = scanner.nextLine();
        }
        this.dateCreated = new Date().toString();
    }

    static boolean checkUserExist(String username) {
        for (UserData userDatum : userData) {
            if (userDatum.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    //getters
    public String getAPIKey() {
        return APIKey;
    }
    public String getUsername() {
        return username;
    }
    public boolean isLastUsed() {
        return isLastUsed;
    }
    public String getPassword() {
        return password;
    }
    public File getTasklistAddress() {
        return tasklistAddress;
    }
    public String getDateCreated(){
        return dateCreated;
    }

    //setters
    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setLastUsed(boolean lastUsed) {
        isLastUsed = lastUsed;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setTasklistAddress(File tasklistAddress) {
        this.tasklistAddress = tasklistAddress;
    }
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    static void login(String username) throws IOException {
        for (int i = 0; i < userData.size(); i++) {
            if (userData.get(i).getUsername().equals(username)) {
                if(!(userData.get(i).password == null)) {
                    System.out.println("Enter password: ");
                    Scanner scanner = new Scanner(System.in);
                    String password = scanner.nextLine();
                    if (userData.get(i).password.equals(password)) {
                        System.out.println("Login successful.");
                        lastUsed(i);
                        saveLastUserData(userData.get(i));
                        return;
                    } else {
                        throw new IllegalArgumentException("Incorrect password.");
                    }
                } else {
                    System.out.println("Login successful.");
                    lastUsed(i);
                    saveLastUserData(userData.get(i));
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Username not found.");
    }

    static void logout() throws IOException {
        File file = new File("src/main/resources/lastUserData.json");
        if (file.delete()) {
            System.out.println("File deleted successfully.");
        } else {
            System.out.println("Failed to delete the file.");
        }
        if(file.createNewFile()) {
            System.out.println("File created successfully.");
        }
        lastUserData = null;
    }

    //sets all other users to not last used, and the new user to last used
    static void lastUsed(int index) throws IOException {
        lastUserData = userData.get(index);
        for(int i = 0; i < userData.size(); i++){
            userData.get(i).setLastUsed(i == index);
        }
    }

    static void noLastUsed() throws IOException{
        for(int i = 0; i < userData.size(); i++){
            userData.get(i).setLastUsed(false);
        }
    }

    //returns the last user's data
    static UserData getLastUsed(){
        for(UserData userDatum : userData) {
            if (userDatum.isLastUsed()) {
                lastUserData = userDatum;
            }
        }
        if(lastUserData == null){
            return null;
        }
        return lastUserData;
    }

    public static void addUserData(UserData userData) {
        UserData.userData.add(userData);
    }

    //generates necessary files
    static void fileGen(){
        try{
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/userData.json");
            File file2 = new File("src/main/resources/lastUserData.json");
            if(!file.exists()){
                if(file.createNewFile()){
                    System.out.println("File created successfully.");
                } else {
                    System.out.println("Failed to create file.");
                }
            }
            if(!file2.exists()){
                if(file2.createNewFile()){
                    System.out.println("File created successfully.");
                } else {
                    System.out.println("Failed to create file.");
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    //saves all user data to a file
    public static void saveUserData(UserData[] userData) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("src/main/resources/userData.json"), userData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //saves the last user data to a file
    public static void saveLastUserData(UserData userData) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("src/main/resources/lastUserData.json"), userData);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    //loads all user data
    public static void loadAllUserData() throws IOException {
        loadLastUserData(); //it won't work properly without this being called separately
        ObjectMapper mapper = new ObjectMapper();
        UserData[] udataArray = mapper.readValue(new File("src/main/resources/userData.json"), UserData[].class);
        userData = new ArrayList<>(Arrays.asList(udataArray));
    }
    public static void loadLastUserData() throws IOException {
        File lastUserDataFile = new File("src/main/resources/lastUserData.json");
        if (lastUserDataFile.exists() && lastUserDataFile.length() > 0) {
            ObjectMapper lmapper = new ObjectMapper();
            lastUserData = lmapper.readValue(lastUserDataFile, UserData.class);
        } else {
            System.out.println("No user has recently logged in. Please log in.");
        }
    }


    //prints list of users
    public static void printUserList(){
        for(int i = 0; i < userData.size(); i++){
            System.out.println(i + ": " + userData.get(i).getUsername());
        }
    }

    @Deprecated //not the best way to do this
    public static Object loadUserDataFromName(String uname) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserData[] udataArray = mapper.readValue(new File("src/main/resources/userData.json"), UserData[].class);
        UserData udata;
        for(UserData userData : udataArray){
            if(userData.getUsername().equals(uname)){
                udata = userData;
                udata.setLastUsed(true);
                lastUsed(Arrays.asList(udataArray).indexOf(userData));
                return udata;
            }
        }
        userData = new ArrayList<>(Arrays.asList(udataArray));
        throw new Error("User not found");
    }

}
@Retention(java.lang.annotation.RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@interface Incomplete {
    public String key() default "Method is not finished";
}