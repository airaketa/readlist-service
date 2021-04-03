package com.readlist.service.core.storage.repository;

import com.readlist.service.core.storage.schema.ReadListArticleRelationSchema;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IReadListArticleRelationRepository
        extends ReactiveCrudRepository<ReadListArticleRelationSchema, Integer> {
    @Query("select * from read_list_article_relations where read_list_id = $1")
    Flux<ReadListArticleRelationSchema> findByReadListId(Integer readListId);

    @Query("delete * from read_list_article_relations where read_list_id = $1 and article_id = $2")
    Mono<ReadListArticleRelationSchema> deleteByReadListArticleIds(Integer readListId, Integer articleId);
}
