package ttv.poltoraha.pivka.consumer;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


// Пример Kafka consumer`a через Spring Kafka.
@Service
public class KafkaConsumer {

    private final RateLimiter rateLimiter = RateLimiter.create(15.0);

    @KafkaListener(topics = "your-topic", groupId = "my-group")
    public void listen(String message) {
        rateLimiter.acquire();
        saveToDatabase(message);
    }

    private void saveToDatabase(String message) {
        try {
            Thread.sleep(1000);
            System.out.println("Saved to DB: " + message + " at " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
