package com.ps.oss.cqrs.demo.repositories;

import com.ps.oss.cqrs.demo.models.write.EventSource;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface EventSourceRepository extends MongoRepository<EventSource, String> {
}
