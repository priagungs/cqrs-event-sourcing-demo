package com.ps.oss.cqrs.demo.services;

import com.ps.oss.cqrs.demo.enums.EventSourceId;
import com.ps.oss.cqrs.demo.models.write.Event;
import com.ps.oss.cqrs.demo.models.write.EventSource;
import com.ps.oss.cqrs.demo.models.write.domainevent.CreateAccountEvent;
import com.ps.oss.cqrs.demo.models.write.domainevent.TopUpEvent;
import com.ps.oss.cqrs.demo.models.write.domainevent.WithdrawEvent;
import com.ps.oss.cqrs.demo.repositories.EventRepository;
import com.ps.oss.cqrs.demo.repositories.EventSourceRepository;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class EventService {

  private final EventSourceRepository sourceRepository;

  private final EventRepository eventRepository;

  private final ProjectionService projectionService;

  public EventService(EventSourceRepository sourceRepository, EventRepository eventRepository, ProjectionService projectionService) {
    this.sourceRepository = sourceRepository;
    this.eventRepository = eventRepository;
    this.projectionService = projectionService;
  }

  public void topUp(Event<TopUpEvent> topUpEvent) {
    EventSource eventSource = sourceRepository.findById(EventSourceId.TOP_UP)
        .orElseThrow(() -> new RuntimeException("Event source not found"));

    validateVersion(topUpEvent, eventSource);
    eventSource.setVersion(eventSource.getVersion() + 1);
    sourceRepository.save(eventSource);

    eventRepository.save(topUpEvent);
    projectionService.apply(topUpEvent.getDomainEvent());
  }

  public void withDraw(Event<WithdrawEvent> withdrawEvent) {

    EventSource eventSource = sourceRepository.findById(EventSourceId.WITHDRAW)
        .orElseThrow(() -> new RuntimeException("Event source not found"));

    validateVersion(withdrawEvent, eventSource);
    eventSource.setVersion(eventSource.getVersion() + 1);
    sourceRepository.save(eventSource);

    eventRepository.save(withdrawEvent);
    projectionService.apply(withdrawEvent.getDomainEvent());

  }

  public void createAccount(Event<CreateAccountEvent> createAccountEvent) {

    EventSource eventSource = sourceRepository.findById(EventSourceId.CREATE_ACCOUNT)
        .orElseThrow(() -> new RuntimeException("Event source not found"));

    validateVersion(createAccountEvent, eventSource);
    eventSource.setVersion(eventSource.getVersion() + 1);
    sourceRepository.save(eventSource);

    eventRepository.save(createAccountEvent);
    projectionService.apply(createAccountEvent.getDomainEvent());
  }

  private void validateVersion(Event<?> event, EventSource eventSource) {
    if (event.getVersion() != eventSource.getVersion()) {
      throw new ConcurrencyFailureException("Version mismatch");
    }
  }
}
