package devproblems.entity;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;
@Data
@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer author_id;
    private String first_name;
    private String last_name;
    private String gender;
    @JsonIgnore
    @OneToMany(mappedBy = "author")
    List<Book> book;

}
