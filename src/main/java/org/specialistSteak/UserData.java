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
import java.util.Scanner;

public class UserData {
    private String APIKey;
    private String username;
    private boolean isLastUsed;
    private String password;

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
        for(UserData userDatum : userData) {
            if(userDatum.getUsername().equals(username)) {
                throw new Error("Username already exists.");
            }
        }
        this.isLastUsed = isLastUsed;
        this.tasklistAddress = new File("src/main/resources/" + username + "_tasks.json");
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

    //takes in the username, and asks for password
    //if the password is correct returns an uname_bool object
    //if the password is incorrect, throws an error
    //if the username is not found, throws an error
    static void login(String username) throws IOException {
        for (int i = 0; i < userData.size(); i++) {
            if (userData.get(i).getUsername().equals(username)) {
                System.out.println("Enter password: ");
                Scanner scanner = new Scanner(System.in);
                String password = scanner.nextLine();
                if (userData.get(i).password.equals(password)) {
                    System.out.println("Login successful.");
                    lastUsed(i);
                    saveLastUserData(userData.get(i));
                } else {
                    throw new Error("Incorrect password.");
                }
            }
        }
        throw new Error("Username not found.");
    }

    //sets all other users to not last used, and the new user to last used
    static void lastUsed(int index) throws IOException {
        if(lastUserData == null){
            loadLastUserData();
        }
        lastUserData = userData.get(index);
        for(int i = 0; i < userData.size(); i++){
            userData.get(i).setLastUsed(i == index);
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
            throw new Error("No user has recently logged in.");
        }
        return lastUserData;
    }

    public static void addUserData(UserData userData) {
        UserData.userData.add(userData);
    }

    //generates necessary files
    @SuppressWarnings("ResultOfMethodCallIgnored")
    static void fileGen(){
        try{
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/userData.json");
            File file2 = new File("src/main/resources/lastUserData.json");
            if(!file.exists()){
                file.createNewFile();
            }
            if(!file2.exists()){
                file2.createNewFile();
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
        ObjectMapper mapper = new ObjectMapper();
        UserData[] udataArray = mapper.readValue(new File("src/main/resources/userData.json"), UserData[].class);
        userData = new ArrayList<>(Arrays.asList(udataArray));
        lastUserData = mapper.readValue(new File("src/main/resources/lastUserData.json"), UserData.class);
    }
    public static void loadLastUserData() throws IOException {
        try{
            ObjectMapper mapper = new ObjectMapper();
            lastUserData = mapper.readValue(new File("src/main/resources/lastUserData.json"), UserData.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        if(lastUserData == null){
            throw new Error("No user has recently logged in. Run the program with the -l flag to login.");
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

//    public static UserData loadLastUserData() throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        UserData[] udataArray = mapper.readValue(new File("src/main/resources/userData.json"), UserData[].class);
//        userData = new ArrayList<>(Arrays.asList(udataArray));
//        UserData udata;
//        for(UserData u : udataArray){
//            if(u.isLastUsed()){
//                udata = u;
//                return udata;
//            }
//        }
//        throw new Error("No user data found");
//    }
}
@Retention(java.lang.annotation.RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@interface Incomplete {
    public String key() default "Method is not finished";
}