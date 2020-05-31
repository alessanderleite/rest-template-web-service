package com.example.app.client;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.app.entity.Article;

@Component
public class RestClientUtil implements CommandLineRunner {
	
	private static final String GET_BY_ID_URL = "http://localhost:8080/user/article/{id}";
	private static final String GET_ALL_URL = "http://localhost:8080/user/articles";
	private static final String CREATE_URL = "http://localhost:8080/user/article";
	private static final String UPDATE_URL = "http://localhost:8080/user/article/{id}";
	private static final String DELETE_BY_ID_URL = "http://localhost:8080/user/article/{id}";
	private static RestTemplate restTemplate = new RestTemplate();

	public void getById() {
		Map<String, String> params = new HashMap<>();
		params.put("id", "2");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> request = new HttpEntity<>(headers);
		ResponseEntity<Article> result = restTemplate.exchange(GET_BY_ID_URL, HttpMethod.GET, request, Article.class, 2);
		Article article = result.getBody();
		System.out.println("Id: " + article.getArticleId() + ", Title: " + article.getTitle() + ", Category: " + article.getCategory());
	}
	
	public void getAll() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> request = new HttpEntity<>("parameters", headers);
		ResponseEntity<String> result = restTemplate.exchange(GET_ALL_URL, HttpMethod.GET, request, String.class);
		System.out.println(result);
	}
	
	public void create() {
	   	HttpHeaders headers = new HttpHeaders();
    	headers.setContentType(MediaType.APPLICATION_JSON);
		Article objArticle = new Article("SQL Server", "Database");
        HttpEntity<Article> requestEntity = new HttpEntity<Article>(objArticle, headers);
        URI uri = restTemplate.postForLocation(CREATE_URL, requestEntity);
        System.out.println(uri.getPath());    	
	}
	
	public void update() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Article obj = new Article("Spring Security and JWT", "Spring Framework");
		HttpEntity<Article> request = new HttpEntity<Article>(obj, headers);
		restTemplate.exchange(UPDATE_URL, HttpMethod.PUT, request, Article.class, 8).getBody();
	}
	
	public void deleteById() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", "7");
		restTemplate.delete(DELETE_BY_ID_URL, params);
		
	}

	@Override
	public void run(String... args) throws Exception {
		getById();
		getAll();
		create();
		update();
		deleteById();
	}
}
