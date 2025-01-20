package com.mj.book_seller.service;

import com.mj.book_seller.model.BookEntity;

import java.util.List;

public interface BookService {

  BookEntity saveBook(BookEntity book);

  void deleteBook(Long bookId);

  List<BookEntity> findAllBooks();
}
