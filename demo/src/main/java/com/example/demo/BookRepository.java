package com.example.demo;

import java.util.List;

public class BookRepository {
  public List<String> findBookTitle(){
    return List.of("Sapiens", "Harry Potter");
  }
}
