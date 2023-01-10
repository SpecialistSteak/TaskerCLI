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

    //TODO: private File tasklistAdress
        //This will be so that each client has their own task file
    static ArrayList <UserData> userData = new ArrayList<>();
    static UserData lastUserData;

    @JsonCreator
    public UserData(@JsonProperty("APIKey") String APIKey, @JsonProperty("username") String username, @JsonProperty("password") String password) {
        this.APIKey = APIKey;
        this.username = username;
        for(UserData userDatum : userData) {
            if(userDatum.getUsername().equals(username)) {
                throw new Error("Username already exists.");
            }
        }
        this.password = password;
        this.isLastUsed = false;
    }

    public String getAPIKey() {
        return APIKey;
    }
    public String getUsername() {
        return username;
    }
    public boolean isLastUsed() {
        return isLastUsed;
    }

    public void setAPIKey(String APIKey) {
        this.APIKey = APIKey;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setLastUsed(boolean lastUsed) {
        isLastUsed = lastUsed;
    }

    static Object login(String username){ //not finished
        for(UserData userDatum : userData){
            if(userDatum.getUsername().equals(username)) {
                System.out.println("Enter the password: ");
                Scanner sc = new Scanner(System.in);
                String password = sc.nextLine();
                if(password.equals(userDatum.password)){
                    System.out.println("Login successful.");
                    userDatum.setLastUsed(true);
                    lastUserData = userDatum;
                    return new uname_bool(username, true);
                } else {
                    System.out.println("Incorrect password.");
                }
            } else {
                System.out.println("Username does not exist.");
            }
        }
        throw new Error("Login failed.");
    }
    static void lastUsed(int index){
        lastUserData = userData.get(index);
        for(int i = 0; i < userData.size(); i++){
            userData.get(i).setLastUsed(i == index);
        }
    }
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

    public static void saveUserData(UserData[] userData) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("src/main/resources/userData.json"), userData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveLastUserData(UserData userData) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("src/main/resources/lastUserData.json"), userData);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadAllUserData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserData[] udataArray = mapper.readValue(new File("src/main/resources/userData.json"), UserData[].class);
        userData = new ArrayList<>(Arrays.asList(udataArray));
        lastUserData = mapper.readValue(new File("src/main/resources/lastUserData.json"), UserData.class);
    }

    public static void printUserList(){
        for(int i = 0; i < userData.size(); i++){
            System.out.println(i + ": " + userData.get(i).getUsername());
        }
    }

    @Deprecated
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

    @Deprecated
    public static Object loadLastUserData() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserData[] udataArray = mapper.readValue(new File("src/main/resources/userData.json"), UserData[].class);
        userData = new ArrayList<>(Arrays.asList(udataArray));
        UserData udata;
        for(UserData u : udataArray){
            if(u.isLastUsed()){
                udata = u;
                return udata;
            }
        }
        return new Error("No user data found");
    }
}
class uname_bool{
    String uname;
    boolean bool;
    public uname_bool(String uname, boolean bool){
        this.uname = uname;
        this.bool = bool;
    }
}
@Retention(java.lang.annotation.RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
@interface Incomplete {
    public String key() default "Method is not finished";
}