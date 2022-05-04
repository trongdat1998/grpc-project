package devproblems.service.service;


import devproblems.entity.Author;

import java.util.List;

public interface AuthorService {
    Author findAllByAuthor_id(Integer id);

    List<Author> findAll();
}
