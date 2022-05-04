package com.devproblems;

import java.util.ArrayList;
import java.util.List;

public class TempDb {
    public static List<Author> getAuthorsFromTempDb(){
        return new ArrayList<Author>(){
            {
                add(Author.newBuilder().setAuthorId(1).setBookId(1).setFirstName("JK").setLastName("AA").setGender("male").build());
                add(Author.newBuilder().setAuthorId(2).setBookId(2).setFirstName("JK2").setLastName("AA2").setGender("male2").build());
                add(Author.newBuilder().setAuthorId(2).setBookId(2).setFirstName("JK3").setLastName("AA3").setGender("male3").build());
            }
        };
    }
    public static List<Book> getBooksFromTempDb(){
        return new ArrayList<Book>(){
            {
                add(Book.newBuilder().setBookId(1).setAuthorId(1).setTitle("book1").setPrice(123.3f).setPages(100).build());
                add(Book.newBuilder().setBookId(2).setAuthorId(1).setTitle("book2").setPrice(321.3f).setPages(100).build());
                add(Book.newBuilder().setBookId(3).setAuthorId(2).setTitle("book3").setPrice(1234.3f).setPages(100).build());
            }
        };
    }
}
