package com.readlist.service.core.service;

import com.readlist.service.core.interfaces.IArticleService;
import com.readlist.service.core.interfaces.IReadListService;
import com.readlist.service.core.model.ReadList;
import com.readlist.service.core.model.ReadListMapper;
import com.readlist.service.core.storage.repository.IReadListArticleRelationRepository;
import com.readlist.service.core.storage.repository.IReadListRepository;
import com.readlist.service.core.storage.schema.ReadListArticleRelationSchema;
import com.readlist.service.core.storage.schema.ReadListSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class ReadListService implements IReadListService {

    @Autowired private IReadListRepository readListRepository;
    @Autowired private IReadListArticleRelationRepository readListArticleRelationRepository;

    @Autowired private IArticleService articleService;
    @Autowired private ReadListMapper readListMapper;

    @Override
    public Mono<ReadList> createReadList(ReadList readList, Integer userId) {
        ReadListSchema dtArticle = readListMapper.toSchema(readList);
        dtArticle.setTs(Instant.now());
        dtArticle.setUserId(userId);
        return readListRepository.save(dtArticle)
                .map(readListMapper::toDto);
    }

    @Override
    public Flux<ReadList> getAllReadLists(Integer userId) {
        return readListRepository.findByUserId(userId)
                .flatMap(this::convertToFullDto);
    }

    @Override
    public Mono<ReadList> findById(Integer readListId) {
        return readListRepository.findById(readListId)
                .flatMap(this::convertToFullDto);
    }

    @Override
    public Mono<ReadList> updateReadList(Integer readListId, ReadList readList) {
        return readListRepository.findById(readListId)
                .flatMap(dbReadList -> {
                    dbReadList.setTitle(readList.getTitle());
                    return readListRepository.save(dbReadList);
                })
                .map(readListMapper::toDto);
    }

    @Override
    public Mono<Void> addArticleToReadList(Integer readListId, Integer articleId) {
        return readListArticleRelationRepository.save(new ReadListArticleRelationSchema(readListId, articleId)).then();
    }

    @Override
    public Mono<Void> removeArticleFromReadList(Integer readListId, Integer articleId) {
        return readListArticleRelationRepository.deleteByReadListArticleIds(readListId, articleId).then();
    }

    @Override
    public Mono<ReadList> deleteReadList(Integer readListId) {
        // TODO delete relations
        return readListRepository.findById(readListId)
                .flatMap(existingReadList -> readListRepository.delete(existingReadList)
                        .then(Mono.just(readListMapper.toDto(existingReadList))));
    }

    private Mono<ReadList> convertToFullDto(ReadListSchema dbReadList) {
        return readListArticleRelationRepository.findByReadListId(dbReadList.getId())
                .flatMap(relation -> articleService.findById(relation.getArticleId()))
                .collectList()
                .map(articles -> {
                    ReadList readList = readListMapper.toDto(dbReadList);
                    readList.setArticles(articles);
                    return readList;
                });
    }
}
