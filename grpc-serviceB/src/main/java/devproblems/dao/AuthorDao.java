package devproblems.dao;

import devproblems.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorDao extends JpaRepository<Author, Integer> {


    @Query("select a from Author a where a.author_id = ?1")
    Author findAllByAuthor_id(Integer id);
}
