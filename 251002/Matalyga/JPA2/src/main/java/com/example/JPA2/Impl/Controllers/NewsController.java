package com.example.JPA2.Impl.Controllers;

import com.example.JPA2.Impl.DTO.NewsRequestTo;
import com.example.JPA2.Impl.DTO.NewsResponseTo;
import com.example.JPA2.Impl.DTO.UserResponseTo;
import com.example.JPA2.Impl.Services.NewsService;
import org.hibernate.sql.ast.tree.expression.Collation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/v1.0")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping("/news")
    public Collection<NewsResponseTo> getAllNews() {
        return newsService.getAll();
    }

    @GetMapping("/news/{id}")
    public ResponseEntity<NewsResponseTo> getNews(@PathVariable long id) {
        try{
            NewsResponseTo news = newsService.get(id);
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/news")
    public ResponseEntity<NewsResponseTo> createNews(@Valid @RequestBody NewsRequestTo NewsTo) {
        NewsResponseTo addedNews = newsService.add(NewsTo);
        return new ResponseEntity<>(addedNews, HttpStatus.CREATED);
    }

    @DeleteMapping("/news/{id}")
    public ResponseEntity<NewsResponseTo> deleteNews(@PathVariable long id) {
        NewsResponseTo deletedNews = newsService.delete(id);
        return new ResponseEntity<>(deletedNews, deletedNews == null ? HttpStatus.NOT_FOUND : HttpStatus.NO_CONTENT);
    }

    @PutMapping("/news")
    public ResponseEntity<NewsResponseTo> updateNews(@Valid @RequestBody NewsRequestTo newsRequestTo) {
        NewsResponseTo newsResponseTo = newsService.update(newsRequestTo);
        return new ResponseEntity<>(newsResponseTo, newsResponseTo.getContent() == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }
}
