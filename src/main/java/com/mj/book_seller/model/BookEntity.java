package com.mj.book_seller.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Books")
public class BookEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false, length = 100)
  private String title;

  @Column(name = "description", nullable = false, length = 1000)
  private String description;

  @Column(name = "author", nullable = false, length = 100)
  private String author;

  @Column(name = "price", nullable = false)
  private Double price;

  @Column(name = "create_time", nullable = false)
  private LocalDateTime createTime;
}
