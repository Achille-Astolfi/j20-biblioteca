package com.example.library.model.book;

import java.util.Optional;

public interface BookService {
    // il service deve trovare BookResource partendo dall'id
    // si consiglia di usare java.util.Optional
    Optional<BookResource> readBookById(Long bookId);
}
