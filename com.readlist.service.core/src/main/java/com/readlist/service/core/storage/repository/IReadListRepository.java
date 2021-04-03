package com.readlist.service.core.storage.repository;

import com.readlist.service.core.storage.schema.ReadListSchema;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IReadListRepository extends ReactiveCrudRepository<ReadListSchema, Integer> {
    @Query("select * from read_lists where user_id = $1")
    Flux<ReadListSchema> findByUserId(Integer userId);
}
