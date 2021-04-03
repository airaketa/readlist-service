package com.readlist.service.core.model;

import com.readlist.service.core.storage.schema.ArticleSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

@Service
public class ArticleMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleMapper.class);

    public Article toDto(ArticleSchema dbArticle) {
        Article.ArticleBuilder dtoBuilder = Article.builder()
                .id(dbArticle.getId())
                .title(dbArticle.getTitle())
                .timestamp(dbArticle.getTs().toEpochMilli());
        try {
            dtoBuilder.url(new URL(dbArticle.getUrl()));
        }
        catch (MalformedURLException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return dtoBuilder.build();
    }

    public ArticleSchema toSchema(Article article) {
        return ArticleSchema.builder()
                .title(article.getTitle())
                .url(article.getUrl().toExternalForm())
                .build();
    }
}
