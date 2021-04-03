package com.readlist.service.core.model;

import com.readlist.service.core.storage.schema.ReadListSchema;
import org.springframework.stereotype.Service;

@Service
public class ReadListMapper {

    public ReadList toDto(ReadListSchema dbReadList) {
        return ReadList.builder()
                .id(dbReadList.getId())
                .title(dbReadList.getTitle())
                .timestamp(dbReadList.getTs().toEpochMilli())
                .build();
    }

    public ReadListSchema toSchema(ReadList readList) {
        return ReadListSchema.builder()
                .title(readList.getTitle())
                .build();
    }
}
