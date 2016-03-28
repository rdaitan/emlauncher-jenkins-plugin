package slack;

import java.util.List;
import java.util.Map;

public class MessageFormatter {
    /**
     * Use the result to format a message.
     * @param result
     * @return
     */
    public static String format(List<Map> result) {

        String message = formatTitle(result);
        message += formatDescription(result);
        message += generateQrLink(result);

        return message;
    }

    /**
     * Format the package title.
     * @param result
     * @return
     */
    private static String formatTitle(List<Map> result) {
        return "<" + result.get(0).get("package_url") + "|*" + result.get(0).get("title") + "*>\n";
    }

    /**
     * Format the package description.
     * @param result
     * @return
     */
    private static String formatDescription(List<Map> result) {
        return result.get(0).get("description") + "\n";
    }

    /**
     * Generate a URL for the QR code
     * @param result
     * @return
     */
    private static String generateQrLink(List<Map> result) {
        return "http://" + result.get(0).get("eml_host") + "/qr/code?q=%2Fpackage%3Fid%3D" + result.get(0).get("id") + "\n";
    }
}
