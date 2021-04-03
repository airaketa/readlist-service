package com.readlist.service.core.interfaces;

import com.readlist.service.core.model.ReadList;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IReadListService {
    Mono<ReadList> createReadList(ReadList readList, Integer userId);

    Flux<ReadList> getAllReadLists(Integer userId);

    Mono<ReadList> findById(Integer readListId);

    Mono<ReadList> updateReadList(Integer readListId, ReadList readList);

    Mono<Void> addArticleToReadList(Integer readListId, Integer articleId);

    Mono<Void> removeArticleFromReadList(Integer readListId, Integer articleId);

    Mono<ReadList> deleteReadList(Integer articleId);
}
