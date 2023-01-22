package org.specialistSteak.dataType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

import static org.specialistSteak.utils.CleanString.cleanString;
import static org.specialistSteak.utils.ErrorStringifer.errorMessager;
import static org.specialistSteak.utils.LengthCheckString.checkLength;

public class UserData {
    private String APIKey;
    private String username;
    private boolean isLastUsed;
    private String password;
    private String dateCreated;
    private File tasklistAddress;
    public static final String SystemUsername = System.getProperty("user.name");

    public static ArrayList <UserData> userData = new ArrayList<>();
    public static UserData lastUserData;
    Scanner scanner = new Scanner(System.in);

    /**
     * Constructor for UserData class.
     * @param username the username of the user.
     * @param pword the password of the user.
     * @param isLastUsed whether the user is the last used user.
     */
    @JsonCreator
    public UserData(/*@JsonProperty("APIKey") String APIKey,*/ @JsonProperty("username") String username,
                    @JsonProperty("password") String pword, @JsonProperty("isLastUsed") boolean isLastUsed) {
        this.password = pword;
//        this.APIKey = APIKey;
        this.username = username;
        this.isLastUsed = isLastUsed;
        this.tasklistAddress = new File("/home/" + SystemUsername + "/.Tasker/" + "src/main/resources/" + username + "_tasks.json");

        while(checkUserExist(this.username)) {
            System.out.println("User already exists. Please enter a new username: ");
            this.username = scanner.nextLine();
            this.username = cleanString(this.username);
            this.username = checkLength(this.username);
        }
        this.dateCreated = new Date().toString();
    }

//    public String getAPIKey() {
//        return APIKey;
//    }

    /**
     * Gets the username of the user.
     * @return the username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns if the user is the last used user.
     * @return the boolean value of isLastUsed.
     */
    public boolean isLastUsed() {
        return isLastUsed;
    }

    /**
     * Gets the password of the user.
     * @return the password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets the file address of the user's tasklist.
     * @return the file address of the user's tasklist.
     */
    public File getTasklistAddress() {
        return tasklistAddress;
    }

    /**
     * Gets the date the user was created.
     * @return the date the user was created.
     */
    public String getDateCreated(){
        return dateCreated;
    }

    //setters
//    public void setAPIKey(String APIKey) {
//        this.APIKey = APIKey;
//    }

    /**
     * Sets the username of the user.
     * @param username the username of the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the password of the user.
     * @param lastUsed the boolean value of isLastUsed.
     */
    public void setLastUsed(boolean lastUsed) {
        isLastUsed = lastUsed;
    }

    /**
     * Sets the password of the user.
     * @param password the password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets the file address of the user's tasklist.
     * @param tasklistAddress the file address of the user's tasklist.
     */
    public void setTasklistAddress(File tasklistAddress) {
        this.tasklistAddress = tasklistAddress;
    }

    /**
     * Sets the date the user was created.
     * @param dateCreated the date the user was created.
     */
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Attempts to set the user as the last used user, and login to the user's account.
     * @param username the username of the user.
     * @throws IOException if the user's tasklist cannot be found.
     * @throws IllegalArgumentException if the user's tasklist cannot be found.
     */
    public static void login(String username) throws IOException {
        for (int i = 0; i < userData.size(); i++) {
            if (userData.get(i).getUsername().equals(username)) {
                if(!(userData.get(i).getPassword() == null)) {
                    System.out.println("Enter password: ");
                    Scanner scanner = new Scanner(System.in);
                    String password = scanner.nextLine();
                    cleanString(password);
                    checkLength(password);
                    if (userData.get(i).getPassword().equals(password)) {
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

    /**
     * Attempts to remove the user as the last used user, and logout of the user's account.
     * @throws IOException if the user's tasklist cannot be found.
     */
    public static void logout() throws IOException {
        File file = new File("/home/" + SystemUsername + "/.Tasker/" + "src/main/resources/lastUserData.json");
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

    /**
     * Checks if the user exists.
     * @param username the username of the user.
     * @return true if the user exists, false otherwise.
     */
    public static boolean checkUserExist(String username) {
        for (UserData userDatum : userData) {
            if (userDatum.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * sets all other users to not last used, and the new user to last used
     * @param index the index of the user in the userData arraylist.
     * @throws IOException if the user's tasklist cannot be found.
     */
    public static void lastUsed(int index) throws IOException {
        lastUserData = userData.get(index);
        for(int i = 0; i < userData.size(); i++){
            userData.get(i).setLastUsed(i == index);
        }
    }

    /**
     * Sets all users to not last used.
     */
    public static void noLastUsed(){
        for (UserData userDatum : userData) {
            userDatum.setLastUsed(false);
        }
    }

    /**
     * @return the userdata of the last used user.
     */
    public static UserData getLastUsed(){
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

    /**
     * adds a user to the userData arraylist.
     * @param userData
     */
    public static void addUserData(UserData userData) {
        UserData.userData.add(userData);
    }

    /**
     * Generates all necessary files for the user.
     * This method is usually called to better handle errors, and to ensure there are no errors while starting the program.
     */
    public static void fileGen(){
        try{
            File file = new File("/home/" + SystemUsername + "/.Tasker/" + "src/main/resources/lastUserData.json");
            File file2 = new File("/home/" + SystemUsername + "/.Tasker/" + "src/main/resources/userData.json");
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
            File dir = new File("src/main/resources");
            if (!dir.exists()) {
                if(dir.mkdir()){
                    System.out.println("Directory created successfully.");
                } else {
                    System.out.println("Failed to create directory.");
                }
            }
        } catch(Exception e){
            System.out.println("An error occurred while generating files.");
            errorMessager(e);
        }
    }

    /**
     * Saves the user's data to the userData.json file.
     * @param userData the user's data.
     */
    public static void saveUserData(UserData[] userData) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("/home/" + SystemUsername + "/.Tasker/" + "src/main/resources/userData.json"), userData);
        } catch (IOException e) {
            errorMessager(e);
            System.exit(0);
        }
    }

    /**
     * Saves the last used user's data to the lastUserData.json file.
     * @param userData the last used user's data.
     */
    public static void saveLastUserData(UserData userData) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            mapper.writeValue(new File("/home/" + SystemUsername + "/.Tasker/" + "src/main/resources/lastUserData.json"), userData);
        } catch(IOException e) {
            errorMessager(e);
            System.exit(0);
        }
    }

    /**
     * Loads all the users data from the userData.json file.
     */
    public static void loadAllUserData() throws IOException {
        loadLastUserData(); //it won't work properly without this being called separately
        ObjectMapper mapper = new ObjectMapper();
        try{
            UserData[] udataArray = mapper.readValue(new File("/home/" + SystemUsername + "/.Tasker/" + "src/main/resources/userData.json"), UserData[].class);
            userData = new ArrayList<>(Arrays.asList(udataArray));
        } catch (IOException ex) { //checks to see if the file works
            if (ex.getMessage().indexOf("cannot find the file") <= 0) {
                errorMessager(ex);
            }
        }
    }

    /**
     * Loads the last used user's data from the lastUserData.json file.
     */
    public static void loadLastUserData() {
        File lastUserDataFile = new File("/home/" + SystemUsername + "/.Tasker/" + "src/main/resources/lastUserData.json");
        if (lastUserDataFile.exists() && lastUserDataFile.length() > 0) {
            try{
                ObjectMapper lmapper = new ObjectMapper();
                lastUserData = lmapper.readValue(lastUserDataFile, UserData.class);
            } catch (IOException ex) { //checks to see if the file works
                errorMessager(ex);
            }
        } else {
            System.out.println("No user has recently logged in. Please log in.");
//            System.out.println("Rerun the program as `tasker.ps1 user -nc`.");
        }
    }

    /**
     * Prints all the users in the userData arraylist.
     */
    public static void printUserList(){
        for(int i = 0; i < userData.size(); i++){
            System.out.println(i + ": " + userData.get(i).getUsername());
        }
    }

    /**
     * Loads a user with a username matching the given username.
     * @param uname the username of the user to be loaded.
     * @return the user's data.
     * @throws IOException if the user's tasklist cannot be found.
     */
    @Deprecated //not the best way to do this
    public static Object loadUserDataFromName(String uname) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        UserData[] udataArray = mapper.readValue(new File("/home/" + SystemUsername + "/.Tasker/" + "src/main/resources/userData.json"), UserData[].class);
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