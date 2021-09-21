package com.galvanize.tmo.paspringstarter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class LibraryController {
    List<Book> library = new ArrayList<>();

    @GetMapping("/health")
    public void health() {

    }

    @PostMapping
    public ResponseEntity addBook(@RequestBody Book book) throws JsonProcessingException {
        System.out.println("Adding book " + book.getTitle() + " to library.");
        book.setId(library.size()+1);
        library.add(book);
        String s = new ObjectMapper().writeValueAsString(book);
        return new ResponseEntity<>(s , HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getLibrary() {
        System.out.println("Fetching sorted library.");
        HashMap<String, List<Book>> response = new HashMap<>();
        sortLibrary(library);
        response.put("books",library);
        return new ResponseEntity<>(response , HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity clearLibrary() {
        System.out.println("Clearing library.");
        library.clear();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public void sortLibrary(List<Book> library) {
        library.sort(Comparator.comparing(Book::getTitle));
    }
}
