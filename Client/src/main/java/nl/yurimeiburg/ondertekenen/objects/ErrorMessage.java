package nl.yurimeiburg.ondertekenen.objects;

import com.google.gson.annotations.SerializedName;

/**
 * Data class containing Error messages
 *
 * @author Yuri Meiburg
 */
public class ErrorMessage extends JSONModelObject {
    @SerializedName("Message")
    private String message = null;

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}