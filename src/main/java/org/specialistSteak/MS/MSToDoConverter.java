package org.specialistSteak.MS;

//import com.microsoft.graph.authentication.IAuthenticationProvider;
//import com.microsoft.graph.models.*;
//import com.microsoft.graph.requests.GraphServiceClient;
//import com.microsoft.graph.requests.LinkedResourceCollectionPage;
//import com.microsoft.graph.requests.LinkedResourceCollectionResponse;
//import okhttp3.Request;

import org.specialistSteak.dataType.Importance;
import org.specialistSteak.dataType.Incomplete;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedList;

import static org.specialistSteak.utils.ErrorStringifer.errorMessager;

public class MSToDoConverter {
    private String id;
//    private ItemBody body;
    private LinkedList<String> categories;
    private ZonedDateTime completedDateTime;
    private ZonedDateTime dueDateTime;
    private Importance importance;
    private boolean isReminderOn;
//    private PatternedRecurrence recurrence;
    private ZonedDateTime reminderDateTime;
//    private TaskStatus status;
    private String title;
    private ZonedDateTime createdDateTime;
    private ZonedDateTime lastModifiedDateTime;
    private ZonedDateTime bodyLastModifiedDateTime;

    /**
     * Formats the task to a usable format for the microsoft graph api
     * @Incomplete: issues connecting to the microsoft graph api, will return to at a later date
     */
    @Incomplete
    public void todomaker(){
//        IAuthenticationProvider authProvider = null;
//        GraphServiceClient<Request> graphClient = GraphServiceClient.builder().authenticationProvider(authProvider).buildClient();
//
//        TodoTask todoTask = new TodoTask();
//        todoTask.title = "A new task";
//        LinkedList<String> categoriesList = new LinkedList<String>();
//        categoriesList.add("Important");
//        todoTask.categories = categoriesList;
//        LinkedList<LinkedResource> linkedResourcesList = new LinkedList<LinkedResource>();
//        LinkedResource linkedResources = new LinkedResource();
//        linkedResources.webUrl = "https://microsoft.com";
//        linkedResources.applicationName = "Microsoft";
//        linkedResources.displayName = "Microsoft";
//        linkedResourcesList.add(linkedResources);
//        LinkedResourceCollectionResponse linkedResourceCollectionResponse = new LinkedResourceCollectionResponse();
//        linkedResourceCollectionResponse.value = linkedResourcesList;
//        todoTask.linkedResources = new LinkedResourceCollectionPage(linkedResourceCollectionResponse, null);
//
//        graphClient.me().todo().lists("AQMkADAwATM0MDAAMS0yMDkyLWVjMzYtM").tasks()
//                .buildRequest()
//                .post(todoTask);
    }

    /**
     * Converts a String in standard date format into the format of a ZonedDateTime (yyyy-MM-dd'T'HH:mm:ss.SSS'Z')
     * @param inputDate the date to convert.
     * @return the converted date as a String.
     */
    public static String convertDateFormat(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        Date date = null;
        try {
            date = inputFormat.parse(inputDate);
        } catch (ParseException e) {
            System.out.println("Error parsing date");
            System.exit(0);
            errorMessager(e);
        }
        return outputFormat.format(date);
    }

    /**
     * Converts a String in standard date format into the format of a ZonedDateTime (yyyy-MM-dd'T'HH:mm:ss.SSS'Z')
     * @param dateTime the date to convert.
     * @return the converted date as a ZonedDateTime.
     */
    private ZonedDateTime parseDateTime(String dateTime) {
        if (dateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            return ZonedDateTime.parse(dateTime, formatter.withZone(ZoneId.of("UTC")));
        }
        return null;
    }
    //getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public ItemBody getBody() {
//        return body;
//    }

//    public void setBody(ItemBody body) {
//        this.body = body;
//    }

    public LinkedList<String> getCategories() {
        return categories;
    }

    public void setCategories(LinkedList<String> categories) {
        this.categories = categories;
    }

    public ZonedDateTime getCompletedDateTime() {
        return completedDateTime;
    }

    public void setCompletedDateTime(ZonedDateTime completedDateTime) {
        this.completedDateTime = completedDateTime;
    }

    public ZonedDateTime getDueDateTime() {
        return dueDateTime;
    }

    public void setDueDateTime(ZonedDateTime dueDateTime) {
        this.dueDateTime = dueDateTime;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public boolean isReminderOn() {
        return isReminderOn;
    }

    public void setReminderOn(boolean reminderOn) {
        isReminderOn = reminderOn;
    }

//    public PatternedRecurrence getRecurrence() {
//        return recurrence;
//    }

//    public void setRecurrence(PatternedRecurrence recurrence) {
//        this.recurrence = recurrence;
//    }

    public ZonedDateTime getReminderDateTime() {
        return reminderDateTime;
    }

    public void setReminderDateTime(ZonedDateTime reminderDateTime) {
        this.reminderDateTime = reminderDateTime;
    }

//    public TaskStatus getStatus() {
//        return status;
//    }

//    public void setStatus(TaskStatus status) {
//        this.status = status;
//    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(ZonedDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public ZonedDateTime getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(ZonedDateTime lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public ZonedDateTime getBodyLastModifiedDateTime() {
        return bodyLastModifiedDateTime;
    }

    public void setBodyLastModifiedDateTime(ZonedDateTime bodyLastModifiedDateTime) {
        this.bodyLastModifiedDateTime = bodyLastModifiedDateTime;
    }
    //end getters and setters
}