package devproblems.service.impl;

import devproblems.dao.AuthorDao;
import devproblems.entity.Author;
import devproblems.service.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    AuthorDao authorDao;

    @Override
    public Author findAllByAuthor_id(Integer id) {
        return authorDao.findAllByAuthor_id(id);
    }

    @Override
    public List<Author> findAll() {
        return authorDao.findAll();
    }
}
