package ttv.poltoraha.pivka.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

// Как правило все имеющиеся метрики создаются в отдельном классе.
@Component
public class CustomMetrics {
    private final Counter counter;
    private final Timer timer;

    @Autowired
    private MeterRegistry meterRegistry;

    @Autowired
    public CustomMetrics(MeterRegistry meterRegistry) {
        this.counter = Counter
                .builder("counter-pisun")
                .description("schitaet")
                .register(meterRegistry);

        this.timer = meterRegistry.timer("timer-zalupka", List.of(Tag.of("description", "zasekaet")));
    }

    public void recordCounter() {
        counter.increment();
    }

    public void recordTimer(Long durationMs) {
        timer.record(Duration.ofMillis(durationMs));
    }
}
