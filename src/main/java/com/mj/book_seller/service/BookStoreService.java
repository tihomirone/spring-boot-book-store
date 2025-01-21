package com.mj.book_seller.service;

import com.mj.book_seller.model.BookEntity;
import com.mj.book_seller.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookStoreService implements BookService {

  private final BookRepository bookRepository;

  @Override
  public BookEntity saveBook(BookEntity book) {
    return bookRepository.save(book);
  }

  @Override
  public void deleteBook(Long bookId) {
    if (bookId < 1) {
      log.error("Invalid Book Id!");
      throw new RuntimeException("Invalid Book Id!");
    }
    bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Invalid Book Id!"));
    bookRepository.deleteById(bookId);
  }

  @Override
  public List<BookEntity> findAllBooks() {
    return bookRepository.findAll();
  }
}
