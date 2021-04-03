package com.readlist.service.core.storage.repository;

import com.readlist.service.core.storage.schema.ArticleSchema;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface IArticleRepository extends ReactiveCrudRepository<ArticleSchema, Integer> {
    @Query("select * from articles where user_id = $1")
    Flux<ArticleSchema> findByUserId(Integer userId);
}