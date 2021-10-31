package com.example.library.model.book;

import com.example.library.model.author.AuthorCreateDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookCreateDto {
  private String title;
  private Long authorId;
  private AuthorCreateDto author;

}
