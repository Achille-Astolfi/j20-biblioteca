package com.example.library.model.book;

import com.example.library.model.author.AuthorCreateDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class BookCreateDto {
  private String title;
  private Long authorId;
  private AuthorCreateDto author;

}
