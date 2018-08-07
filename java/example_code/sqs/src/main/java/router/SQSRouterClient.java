package router;
import com.amazonaws.services.sqs.*;
import com.amazonaws.services.sqs.model.*;
import java.util.*;

public class SQSRouterClient implements RouterClient, AutoCloseable {
    final String queueUrl;
    final AmazonSQS sqs;
    
    public SQSRouterClient(String queueName) {
        this.sqs = AmazonSQSClientBuilder.defaultClient();
        queueUrl = sqs.getQueueUrl(queueName).getQueueUrl();
    }
    
    public void routeData(String clientData) {
        SendMessageRequest request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody(clientData);
                
        request.setMessageGroupId("messageGroup1");
        
        sqs.sendMessage(request);
    }
    
    public void close() {
        
    }
}