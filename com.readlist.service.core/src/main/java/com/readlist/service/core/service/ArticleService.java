package com.readlist.service.core.service;

import com.readlist.service.core.interfaces.IArticleService;
import com.readlist.service.core.model.Article;
import com.readlist.service.core.model.ArticleMapper;
import com.readlist.service.core.storage.repository.IArticleRepository;
import com.readlist.service.core.storage.schema.ArticleSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
public class ArticleService implements IArticleService {

    @Autowired private IArticleRepository articleRepository;
    @Autowired private ArticleMapper articleMapper;

    @Override
    public Mono<Article> createArticle(Article article, Integer userId) {
        ArticleSchema dtArticle = articleMapper.toSchema(article);
        dtArticle.setTs(Instant.now());
        dtArticle.setUserId(userId);
        return articleRepository.save(dtArticle)
                .map(articleMapper::toDto);
    }

    @Override
    public Flux<Article> getAllArticles(Integer userId) {
        return articleRepository.findByUserId(userId)
                .map(articleMapper::toDto);
    }

    @Override
    public Mono<Article> findById(Integer articleId) {
        return articleRepository.findById(articleId)
                .map(articleMapper::toDto);
    }

    @Override
    public Mono<Article> updateArticle(Integer articleId, Article article) {
        return articleRepository.findById(articleId)
                .flatMap(dbArticle -> {
                    dbArticle.setTitle(article.getTitle());
                    dbArticle.setUrl(article.getUrl().toString());
                    return articleRepository.save(dbArticle);
                })
                .map(articleMapper::toDto);
    }

    @Override
    public Mono<Article> deleteArticle(Integer articleId) {
        // TODO delete relations
        return articleRepository.findById(articleId)
                .flatMap(existingArticle -> articleRepository.delete(existingArticle)
                        .then(Mono.just(articleMapper.toDto(existingArticle))));
    }
}
