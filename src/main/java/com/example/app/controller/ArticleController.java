package com.example.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.app.entity.Article;
import com.example.app.service.ArticleService;

@Controller
@RequestMapping("/user")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	@GetMapping("/article/{id}")
	public ResponseEntity<Article> getArticleById(@PathVariable("id") Integer id) {
		Article article = articleService.getArticleById(id);
		return new ResponseEntity<>(article, HttpStatus.OK);
	}
	
	@GetMapping("/articles")
	public ResponseEntity<List<Article>> getAllArticles() {
		List<Article> list = articleService.getAllArticles();
		return new ResponseEntity<List<Article>>(list, HttpStatus.OK);
	}
	
	@PostMapping("/article")
	public ResponseEntity<Void> addArticle(@Valid @RequestBody Article article, UriComponentsBuilder builder) {
		boolean flag = articleService.addArticle(article);
		if (flag == false) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(builder.path("/article/{id}").buildAndExpand(article.getArticleId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}
	
	@PutMapping("/article/{id}")
	public ResponseEntity<Article> updateArticle(@PathVariable("id") Integer id, @Valid @RequestBody Article article) {
		Article obj = articleService.getArticleById(id);
		if (obj == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			article.setArticleId(obj.getArticleId());
			articleService.updateArticle(article);
			return new ResponseEntity<>(article, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/article/{id}")
	public ResponseEntity<Void> deleteArticle(@PathVariable("id") Integer id) {
		articleService.deleteArticle(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
