package com.readlist.service.web.controllers;

import com.readlist.service.core.interfaces.IReadListService;
import com.readlist.service.core.model.ReadList;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("read-lists")
class ReadListController {
    @Autowired
    private IReadListService readListService;

    private final Integer mockUserId = 0;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Flux<ReadList> getAll() {
        return readListService.getAllReadLists(mockUserId);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<ReadList>> getById(@PathVariable("id") Integer id) {
        return  readListService.findById(id)
                .map(readList -> ResponseEntity.ok(readList))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, examples = {
            @ExampleObject(value = "{\"title\":\"Test read list\"}")
    }))
    Mono<ResponseEntity<URI>> create(@RequestBody ReadList readList) {
        return readListService.createReadList(readList, mockUserId)
                .map(created -> ResponseEntity.created(URI.create(("/" + created.getId()))).build());
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200")
    Mono<ResponseEntity<ReadList>> updateById(@PathVariable Integer id, @RequestBody ReadList readList) {
        return readListService.updateReadList(id, readList)
                .map(updated -> ResponseEntity.ok(updated))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    //TODO today read-list endpoint
    //TODO add/remove article endpoints

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    Mono<ResponseEntity<Void>> deleteById(@PathVariable Integer id) {
        return readListService.deleteReadList(id)
                .map(deleted -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}