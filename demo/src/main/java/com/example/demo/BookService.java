package com.example.demo;

public class BookService {

  public String findAuthor(final String bookName){
    if(bookName.equals("Harry Potter")){
      return "J.K.Rowling";
    }

    if(bookName.equals("Sapiens")){
      return "Harrari";
    }
    return "";
  }
}
