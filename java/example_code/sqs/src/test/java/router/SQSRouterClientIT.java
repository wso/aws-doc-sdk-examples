package router;

import org.junit.Test;
import com.amazonaws.services.sqs.*;
import com.amazonaws.services.sqs.model.*;
import java.util.*;

import static org.junit.Assert.*;

public class SQSRouterClientIT {
    String QUEUE_NAME = "demo.fifo";
    
    @Test
    public void whenRouterClientSendsMessage_ThenQueueSizeShouldIncrease() {
        String input = "{ \"sql\":\"UPSERT INTO TEST values (?,?,?)\",\"params\":[\"p1\",\"p2\",\"p3\"]}";
        
        try (SQSRouterClient testObject = new SQSRouterClient(QUEUE_NAME) ) {
            testObject.routeData(input);
        }
        

        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
        String queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();

        // receive messages from the queue
        List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();

        assertEquals( 1, messages.size());
        // delete messages from the queue
        for (Message m : messages) {
            System.out.println( m.getReceiptHandle());
            assertEquals( input, m.getBody());
        }
        
    }
}