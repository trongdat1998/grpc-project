package devproblems.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer book_id;
    private String title;
    private Float price;
    private Integer pages;
    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

}
