package com.example.demo.database;

import com.example.demo.models.Book;
import com.example.demo.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
    @Bean
    CommandLineRunner initDataBase(BookRepository bookRepository){

        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Book book1 = new Book("book1",1L,1100D,"aaaaa");
                Book book2 = new Book("book2",2L,1500D,"bbbbb");
                System.out.println("insert data: " + bookRepository.save(book1));
                System.out.println("insert data: " + bookRepository.save(book2));
            }
        };
    }
}
