import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nl.yurimeiburg.ondertekenen.gson.ActivityCodeDeserializer;
import nl.yurimeiburg.ondertekenen.gson.TransactionStatusDeserializer;
import nl.yurimeiburg.ondertekenen.objects.ActivityCode;
import nl.yurimeiburg.ondertekenen.objects.Transaction;
import nl.yurimeiburg.ondertekenen.objects.TransactionStatus;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Yuri Meiburg on 28-1-2015.
 */
public class TransactionTest {
    private Gson gson;

    @Before
    public void init() {
        gson = new GsonBuilder()
                .registerTypeAdapter(ActivityCode.class, new ActivityCodeDeserializer())
                .registerTypeAdapter(TransactionStatus.class, new TransactionStatusDeserializer())
                .create();
    }

    /**
     * Take complete sample from https://api.signhost.com/Help/Api/GET-api-transaction-transactionId
     * and use that as reference.
     */
    @Test
    public void testIfJSonConversionWorks() {

        String jsonSample = "{\n" +
                "  \"Id\": \"179a843c-8fdd-4f68-a38a-9604f8169549\",\n" +
                "  \"Status\": 20,\n" +
                "  \"File\": {\n" +
                "    \"Id\": \"083ce426-8bf5-4147-9abd-2eea977fae22\",\n" +
                "    \"Name\": \"contract.pdf\"\n" +
                "  },\n" +
                "  \"Seal\": true,\n" +
                "  \"Signers\": [\n" +
                "    {\n" +
                "      \"Id\": \"3c5feb2e-f4ca-4701-96ce-8f3fc1e56cd5\",\n" +
                "      \"Email\": \"user@example.com\",\n" +
                "      \"Mobile\": \"+31612345678\",\n" +
                "      \"RequireScribble\": true,\n" +
                "      \"RequireEmailVerification\": true,\n" +
                "      \"RequireSmsVerification\": true,\n" +
                "      \"SendSignRequest\": true,\n" +
                "      \"SendSignConfirmation\": null,\n" +
                "      \"SignRequestMessage\": \"Hello, could you please sign this document? Best regards, John Doe\",\n" +
                "      \"DaysToRemind\": 15,\n" +
                "      \"Language\": \"en-US\",\n" +
                "      \"ScribbleName\": \"John Doe\",\n" +
                "      \"ScribbleNameFixed\": false,\n" +
                "      \"Reference\": \"Client #123\",\n" +
                "      \"ReturnUrl\": \"http://signhost.com\",\n" +
                "      \"Activities\": [\n" +
                "        {\n" +
                "          \"Id\": \"a4569cb4-d780-4586-b259-2650dd646b4c\",\n" +
                "          \"Code\": 103,\n" +
                "          \"Activity\": \"Opened\",\n" +
                "          \"CreatedDateTime\": \"2015-02-05T05:04:37.6227457+01:00\"\n" +
                "        },\n" +
                "        {\n" +
                "          \"Id\": \"1b36093d-2c68-4c3a-b2ad-0ad7aca42937\",\n" +
                "          \"Code\": 203,\n" +
                "          \"Activity\": \"Signed\",\n" +
                "          \"CreatedDateTime\": \"2015-02-05T05:09:37.6227457+01:00\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"RejectReason\": null,\n" +
                "      \"SignUrl\": \"http://ui.signhost.com/sign/bd3ef567-0563-4f51-95dc-48a416c246d6\",\n" +
                "      \"SignedDateTime\": null,\n" +
                "      \"RejectDateTime\": null,\n" +
                "      \"CreatedDateTime\": \"2015-02-05T05:04:37.6227457+01:00\",\n" +
                "      \"ModifiedDateTime\": \"2015-02-05T05:04:37.6227457+01:00\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"Reference\": \"Contract #123\",\n" +
                "  \"PostbackUrl\": \"http://example.com/postback.php\",\n" +
                "  \"SignRequestMode\": 2,\n" +
                "  \"DaysToExpire\": 30,\n" +
                "  \"SendEmailNotifications\": true,\n" +
                "  \"CreatedDateTime\": \"2015-02-05T05:04:37.6227457+01:00\",\n" +
                "  \"ModifiedDateTime\": \"2015-02-05T05:04:37.6227457+01:00\",\n" +
                "  \"CanceledDateTime\": null\n" +
                "}";
        Transaction transaction = gson.fromJson(jsonSample, Transaction.class);

        assertEquals("179a843c-8fdd-4f68-a38a-9604f8169549", transaction.getId());
        assertEquals(TransactionStatus.IN_PROGRESS, transaction.getStatus());
        assertEquals(true, transaction.isSeal());
        assertEquals("Contract #123", transaction.getReference());
        assertEquals("http://example.com/postback.php", transaction.getPostbackUrl());
        assertEquals(new Integer(2), transaction.getSignRequestMode());
        assertEquals(new Integer(30), transaction.getDaysToExpire());
        assertTrue(transaction.getSendEmailNotifications());

        // Superficially check if signer is loaded. Actual test is in SignerTest
        assertEquals(1, transaction.getSigners().length);
    }

}
