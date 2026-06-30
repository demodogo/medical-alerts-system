package com.dsy2206.summaries.producer.service;
import com.dsy2206.summaries.producer.dto.SummaryPublishRequest;
import com.dsy2206.summaries.producer.dto.SummaryPublishResponse;
import com.dsy2206.summaries.producer.dto.VitalSignsSummaryMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SummaryProducerService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.summaries}")
    private String summariesExchange;

    @Value("${rabbitmq.routing-key.summaries}")
    private String summariesRoutingKey;

    public SummaryPublishResponse publishSummary(SummaryPublishRequest request) {
        VitalSignsSummaryMessage message = VitalSignsSummaryMessage.fromRequest(request);

        rabbitTemplate.convertAndSend(
                summariesExchange,
                summariesRoutingKey,
                message
        );

        log.info("Resumen de signos vitales publicado. eventId={}, patientId={}",
                message.getEventId(),
                message.getPatientId());

        return SummaryPublishResponse.builder()
                .published(true)
                .eventId(message.getEventId())
                .message("Resumen de signos vitales publicado en RabbitMQ.")
                .build();
    }
}