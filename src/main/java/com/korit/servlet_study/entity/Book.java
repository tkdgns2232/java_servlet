package com.korit.servlet_study.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {
    private int bookId;
    private String bookName;
    private String isbn;
    private int authorId;
    private int publisherId;
    private int categoryId;
    private String bookImgUrl;

    private Author author;
    private Publisher publisher;
    private BookCategory bookCategory;

<<<<<<< HEAD

=======
    private String authorName;
    private String publisherName;
    private String categoryName;
    private String imgUrl;
>>>>>>> 2eec1839f7f5145b44306a17ea443187a9360713
}