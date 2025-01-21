package com.mj.book_seller.controller;

import com.mj.book_seller.model.BookEntity;
import com.mj.book_seller.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/book")
@RequiredArgsConstructor
public class BookController {

  private final BookService bookService;

  @PostMapping
  public ResponseEntity<BookEntity> saveBook(@RequestBody BookEntity book) {
    return new ResponseEntity<>(bookService.saveBook(book), HttpStatus.CREATED);
  }

  @DeleteMapping("{bookId}")
  public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
    bookService.deleteBook(bookId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<BookEntity>> getAllBooks() {
    return new ResponseEntity<>(bookService.findAllBooks(), HttpStatus.OK);
  }
}
