package example;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;

/**
 * Listening on an SNS event, and publish the event message to another SNS at fixed speed until timeout
 */
public class SNSEventProcessor implements RequestHandler<SNSEvent, Object> {
    public Object handleRequest(SNSEvent request, Context context) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(Calendar.getInstance().getTime());

        // Get sns topic arn
        String publishSNSTopicARN = getEnvOrDefault("PUBLISHING_SNS_TOPIC_ARN", "defaul_sns_topic");

        // Get publish rate
        String publishRateStr = getEnvOrDefault("PUBLISH_RATE", "5");
        int publishRate = Integer.parseInt(publishRateStr);

        // Get time for sleep. Will see timeout error if this is longer than lambda execution timeout
        String sleepTimeStr = getEnvOrDefault("SLEEP_TIME", "290");
        int sleepTimeInSec = Integer.parseInt(sleepTimeStr);

        AmazonSNS snsClient = AmazonSNSClient.builder().withRegion(Region.getRegion(Regions.US_EAST_1).toString()).build();

        // I think Lambda is not supposed to be used like this way, but that doesn't mean we cant do this XD
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            String msg =  request.getRecords().get(0).getSNS().getMessage() + " : " + timeStamp;
            snsClient.publish(publishSNSTopicARN, msg);
        }, 0, 1000 / publishRate, TimeUnit.MILLISECONDS);

        try {
            Thread.sleep(1000 * sleepTimeInSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getEnvOrDefault(String envVarName, String defaultValue){
        String envVar = System.getenv(envVarName);
        if (envVar == null || envVar.equals("")) {
            return defaultValue;
        } else {
            return envVar;
        }
    }
}

