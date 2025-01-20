package com.korit.servlet_study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
<<<<<<< HEAD
import com.korit.servlet_study.entity.*;
=======
import com.korit.servlet_study.entity.Author;
import com.korit.servlet_study.entity.Book;
import com.korit.servlet_study.entity.User;
>>>>>>> 2eec1839f7f5145b44306a17ea443187a9360713

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/book")
public class BookRestServlet extends HttpServlet {



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
<<<<<<< HEAD
        Author author = new Author(1, "테스트저자");
        Publisher publisher = new Publisher(10, "테스트출판사");
        BookCategory bookCategory = new BookCategory(100, "테스트카테고리");


        Book book = Book.builder()
                .bookId(1234)
                .bookName("테스트도서명")
                .isbn("abcd1234")
                .bookImgUrl("http://")
                .authorId(author.getAuthorId())
                .publisherId(publisher.getPublisherId())
                .categoryId(bookCategory.getCategoryId())
                .author(author)
                .publisher(publisher)
                .bookCategory(bookCategory)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
=======


        ObjectMapper objectMapper = new ObjectMapper();
        Book book = Book.builder()
                .bookId(1234)
                .bookName("가가가")
                .authorName("나나나")
                .publisherName("다다다")
                .categoryName("라라라")
                .imgUrl("http:/")
                .build();

>>>>>>> 2eec1839f7f5145b44306a17ea443187a9360713
        String jsonUser = objectMapper.writeValueAsString(book);
        System.out.println(jsonUser);

        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setHeader("Access-Control-Allow-Credentials", "ture");

        resp.setContentType("application/json");
        resp.getWriter().println(jsonUser);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
