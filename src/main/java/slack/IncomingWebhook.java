package slack;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * An Incoming Webhook object to post messages to a Slack channel.
 */
public class IncomingWebhook {
    private String url;
    private String channel;
    private String message;
    private String username;
    private String iconEmoji;

    private int retries = 20;

    /**
     * Create a new IncomingWebhook object and set its url.
     * @param url
     */
    public IncomingWebhook(String url) {
        this.url = url;
    }

    /**
     * Set the channel to send the message.
     * @param channel
     * @return
     */
    public IncomingWebhook channel(String channel) {
        this.channel = channel;
        return this;
    }

    /**
     * Set the message to send.
     * @param message
     * @return
     */
    public IncomingWebhook message(String message) {
        this.message = message;
        return this;
    }

    /**
     * Set the username to display.
     * @param username
     * @return
     */
    public IncomingWebhook username(String username) {
        this.username = username;
        return this;
    }

    /**
     * Set the icon emoji to use.
     * @param iconEmoji
     * @return
     */
    public IncomingWebhook iconEmoji(String iconEmoji) {
        this.iconEmoji = ":" + iconEmoji.toLowerCase() + ":";
        return this;
    }

    /**
     * Send the message to the Webhook URL.
     */
    public void send() throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();

        int status_code = 0;

        while (status_code != HttpStatus.SC_OK && retries-- > 0) {
            try {
                HttpPost request = new HttpPost(url);
                request.addHeader("Content-type", "application/json");
                request.setEntity(this.toJsonStringEntity());

                HttpResponse response = httpClient.execute(request);
                status_code = response.getStatusLine().getStatusCode();

                // Consume to allow reuse of connection
                response.getEntity().consumeContent();
            } catch (Exception e) {
                httpClient.getConnectionManager().shutdown();
                throw e;
            }
        }

        httpClient.getConnectionManager().shutdown();
    }

    /**
     * Convert this object to a StringEntity to be passed in an HttpPost entity.
     * @return
     * @throws UnsupportedEncodingException
     */
    private StringEntity toJsonStringEntity() throws UnsupportedEncodingException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("text", message);

        if (channel != null && !channel.isEmpty()) {
            jsonObject.put("channel", channel);
        }

        if (username != null && !username.isEmpty()) {
            jsonObject.put("username", username);
        }

        if (iconEmoji != null && !iconEmoji.isEmpty()) {
            jsonObject.put("icon_emoji", iconEmoji);
        }

        return new StringEntity(jsonObject.toString());
    }
}
