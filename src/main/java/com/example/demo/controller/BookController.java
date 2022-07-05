package com.example.demo.controller;

import com.example.demo.models.Book;
import com.example.demo.models.ResponseObject;
import com.example.demo.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "book")
public class BookController {
   @Autowired
   private BookRepository repository;

    @GetMapping("")
    List<Book> getAllList() {
//        List<Book> list = new ArrayList<>();
//        list.add(new Book(1L,"book1",1L,1200D,"aaaa"));
//        list.add(new Book(2L,"book1",2L,1200D,"bbbb"));
        return repository.findAll();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id){
        Optional<Book> foundBook = repository.findById(id);
        if(foundBook.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","find book successfully",foundBook)
            );
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("fail","Cannot find book ID with id = "+ id,"")
            );
        }
    }

    //Insert new Book
    @PostMapping("/insert")
    ResponseEntity<ResponseObject> insertBook(@RequestBody Book newBook){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Insert successfully",repository.save(newBook))
        );
    }
    //Update
    @PutMapping("/{id}")
    ResponseEntity<ResponseObject> updateBook(@RequestBody Book newBook,@PathVariable Long id){
        Book updateBook =  repository.findById(id)
                .map(book -> {
                    book.setName(newBook.getName());
                    book.setType(newBook.getType());
                    book.setPrice(newBook.getPrice());
                    book.setDescription(newBook.getDescription());
                    return repository.save(book);
                }).orElseGet(() ->{
                    newBook.setId(id);
                    return repository.save(newBook);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok","Update successfully",updateBook)
        );
     }

     //Delete
    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteBook(@PathVariable Long id){
        boolean exists = repository.existsById(id);
        if(exists){
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok","Delete successfully","")
            );
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed","Cannot find id = " + id,"")
            );
        }
    }
}
