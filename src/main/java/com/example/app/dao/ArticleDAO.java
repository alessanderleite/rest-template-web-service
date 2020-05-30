package com.example.app.dao;

import java.util.List;

import com.example.app.entity.Article;

public interface ArticleDAO {
	List<Article> getAllArticles();
	Article getArticleById(int articleId);
	void addArticle(Article article);
	void updateArticle(Article article);
	void deleteArticle(int articleId);
	boolean articleExists(String title, String category);
}
