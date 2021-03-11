package com.readlist.service.core.storage.repository;

import com.readlist.service.core.storage.schema.ArticleSchema;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IArticleRepository extends ReactiveCrudRepository<ArticleSchema, Integer> {
}
