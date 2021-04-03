package com.readlist.service.core.storage.schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table("read_list_article_relations")
public class ReadListArticleRelationSchema {
    @Id
    private Integer id;
    @Column("read_list_id")
    private Integer readListId;
    @Column("article_id")
    private Integer articleId;

    public ReadListArticleRelationSchema(Integer readListId, Integer articleId) {
        this.readListId = readListId;
        this.articleId = articleId;
    }
}
