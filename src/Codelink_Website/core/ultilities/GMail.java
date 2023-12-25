package ultilities;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.Thread;
import common.GlobalConstants;
import io.restassured.path.json.JsonPath;

import java.io.*;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GMail {
    private static final String APPLICATION_NAME = "Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String USER_ID = "me";
    private static List<String> allURLS;

    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM);
    private static final String CREDENTIALS_FILE_PATH =
            GlobalConstants.PROJECT_PATH +
                    File.separator + "src" +
                    File.separator + "main" +
                    File.separator + "resources" +
                    File.separator + "credentials" +
                    File.separator + "credentials_new.json";

    private static final String TOKENS_DIRECTORY_PATH = GlobalConstants.PROJECT_PATH +
            File.separator + "src" +
            File.separator + "main" +
            File.separator + "resources" +
            File.separator + "credentials";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = new FileInputStream(new File(CREDENTIALS_FILE_PATH));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(9999).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }

    private static Gmail getService() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        return service;
    }

    private static List<Message> listMessagesMatchingQuery(Gmail service, String userId,
                                                          String query) throws IOException {
        ListMessagesResponse response = service.users().messages().list(userId).setQ(query).execute();
        List<Message> messages = new ArrayList<Message>();
        while (response.getMessages() != null) {
            messages.addAll(response.getMessages());
            if (response.getNextPageToken() != null) {
                String pageToken = response.getNextPageToken();
                response = service.users().messages().list(userId).setQ(query)
                        .setPageToken(pageToken).execute();
            } else {
                break;
            }
        }
        return messages;
    }

    private static Message getMessage(Gmail service, String userId, List<Message> messages, int index)
            throws IOException {
        if (index < 0 || index >= messages.size()) {
            log.error("Index of message is out of bound");
        }
        Message message = service.users().messages().get(userId, messages.get(index).getId()).execute();
        return message;
    }

    public static HashMap<String, String> getGmailData(String query) {

        try {
            log.info("Getting Gmail data for query: " + query);
            Gmail service = getService();
            List<Message> messages = listMessagesMatchingQuery(service, USER_ID, query);
            if (messages.isEmpty()) {
                log.info("No messages found for the query: " + query);
                return null;
            } else {
                Message message = getMessage(service, USER_ID, messages, 0);
                JsonPath jsonPath = new JsonPath(message.toString());
                String subject = jsonPath.getString("payload.headers.find { it.name == 'Subject' }.value");
                String bodyEncoded = jsonPath.getString("payload.parts[0].body.data");

                Base64.Decoder decoder = Base64.getUrlDecoder();
                String body = new String(decoder.decode(bodyEncoded));

                allURLS = extractUrls(body);

                HashMap<String, String> messageMap = new HashMap<String, String>();
                messageMap.put("body", body);
                messageMap.put("link", allURLS.toString());
                log.info("Gmail data retrieved successfully for query: " + query);
                return messageMap;
            }
        } catch (Exception e) {
            log.error("Error while getting Gmail data for query: " + query);
            throw new RuntimeException(e);
        }
    }


    /**
     * Get all the URLs from Email content
     */
    public static List<String> extractUrls(String emailBody) {
        log.info("Extracting URLs from email body.");
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(emailBody);

        while (urlMatcher.find()) {
            containedUrls.add(emailBody.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        log.info("URLs extracted successfully.");
        return containedUrls;
    }

    public static int getTotalCountOfMails() {
        log.info("Getting total count of mails.");
        int size;
        try {
            Gmail service = getService();
            List<Thread> threads = service.users().threads().list("me").execute().getThreads();
            size = threads.size();
            log.info("Total count of mails obtained: " + size);
        } catch (Exception e) {
            log.error("Exception while getting total count of mails.");
            size = -1;
        }
        return size;
    }

    public static boolean isMailExist(String messageTitle) {
        log.info("Checking if mail exists with title: " + messageTitle);
        try {
            Gmail service = getService();
            ListMessagesResponse response = service.
                    users().
                    messages().
                    list("me").
                    setQ("subject:" + messageTitle).
                    execute();
            List<Message> messages = getMessages(response);
            boolean exists = !messages.isEmpty();
            log.info("Mail exists: " + exists);
            return !messages.isEmpty();
        } catch (Exception e) {
            log.error("Exception while checking if mail exists.");
            return false;
        }
    }


    private static List<Message> getMessages(ListMessagesResponse response) {
        log.info("Getting messages.");
        List<Message> messages = new ArrayList<Message>();
        try {
            Gmail service = getService();
            while (response.getMessages() != null) {
                messages.addAll(response.getMessages());
                if (response.getNextPageToken() != null) {
                    String pageToken = response.getNextPageToken();
                    response = service.users().messages().list(USER_ID)
                            .setPageToken(pageToken).execute();
                } else {
                    break;
                }
            }
            return messages;
        } catch (Exception e) {
            log.info("Exception while getting messages: " + e.getMessage());
        }
        log.info("Messages obtained successfully.");
        return messages;
    }


    /**
     * Get the latest unread message of given to_user_email_id and Subject
     */
    public static String getEmailBody(String toEmail, String subject) {
        log.info("Getting email body for " + toEmail + " and title: " + subject);
        String query = "to:(" + toEmail + ") subject:(" + subject + ")";
        String body = null;

        try {
            body = getGmailData(query).get("body");
            log.info("Email body retrieved successfully.");
        } catch (Exception e) {
            log.error("Error while getting email body.");
        }
        return body;

    }

    public static String getEmailLink(String toEmail, String subject) {
        log.info("Getting email link for " + toEmail + " and title: " + subject);
        String query = "to:(" + toEmail + ") subject:(" + subject + ")";
        String link = null;

        try {
            link = getGmailData(query).get("link");
            log.info("Email link retrieved successfully.");
        } catch (Exception e) {
            log.error("Error while getting email body.");
        }
        return link;

    }

}