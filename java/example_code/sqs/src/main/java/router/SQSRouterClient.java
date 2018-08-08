package router;
import com.amazonaws.auth.*;
import com.amazonaws.services.sqs.*;
import com.amazonaws.services.sqs.model.*;
import java.util.*;

public class SQSRouterClient implements RouterClient, AutoCloseable {
    final String queueUrl;
    final AmazonSQS sqs;
    
    public SQSRouterClient(String accessKey, String secretKey, String awsAccount, String queueName) {
        this.sqs = AmazonSQSClientBuilder.standard().withCredentials(
            new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)) 
            ).build();
        // sqs.listQueues().getQueueUrls().stream().forEach(System.out::println);
        // queueUrl = sqs.getQueueUrl(
            // new GetQueueUrlRequest().withQueueOwnerAWSAccountId(awsAccount).withQueueName(queueName)
        // ).getQueueUrl();
        queueUrl = "https://sqs.us-east-2.amazonaws.com/" + awsAccount + "/" + queueName;
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