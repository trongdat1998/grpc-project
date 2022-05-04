package com.devproblems.controller;

import com.devproblems.service.BookAuthorClientService;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.protobuf.Descriptors;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class BookAuthorController {
    BookAuthorClientService bookAuthorClientService;

    @GetMapping("/authors/{authorId}")
    public Map<Descriptors.FieldDescriptor, Object> getAuthorId(@PathVariable String authorId) {
        return bookAuthorClientService.getAuthorId(Integer.parseInt(authorId));
    }

    @GetMapping("/author/{authorId}")
    public Map<Descriptors.FieldDescriptor, Object> getAuthor(@PathVariable String authorId) {
    return bookAuthorClientService.getAuthor(Integer.parseInt(authorId));
    }

    @GetMapping("/book/{authorId}")
    public List<Map<Descriptors.FieldDescriptor,Object>> getBooksByAuthor(@PathVariable String authorId) throws InterruptedException {
        return bookAuthorClientService.getBooksByAuthor(Integer.parseInt(authorId));
    }

    @GetMapping("/book")
    public Map<String,Map<Descriptors.FieldDescriptor,Object>> getExpensiveBook() throws InterruptedException {
        return bookAuthorClientService.getExpesiveBook();
    }
    @GetMapping("/book/author/{gender}")
    public List<Map<Descriptors.FieldDescriptor,Object>> getBooksByAuthorGender(@PathVariable String gender) throws InterruptedException {
        return bookAuthorClientService.getBooksByAuthorGender(gender);
    }
}
