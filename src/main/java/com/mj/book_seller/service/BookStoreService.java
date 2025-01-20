package com.mj.book_seller.service;

import com.mj.book_seller.model.BookEntity;
import com.mj.book_seller.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookStoreService implements BookService {

  private final BookRepository bookRepository;

  @Override
  public BookEntity saveBook(BookEntity book) {
    return bookRepository.save(book);
  }

  @Override
  public void deleteBook(Long bookId) {
    bookRepository.deleteById(bookId);
  }

  @Override
  public List<BookEntity> findAllBooks() {
    return bookRepository.findAll();
  }
}
