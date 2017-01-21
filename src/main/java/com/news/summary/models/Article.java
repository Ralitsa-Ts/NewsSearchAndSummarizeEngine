package com.news.summary.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "articles")
public class Article {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Type(type = "text")
	private String articleStoryIntroduction;

	@NotNull
	@Type(type = "text")
	private String articleMainStory;

	private String articleSection;

	private String pageType;

	private String url;

	private String title;

	public Article() { }

	public Article(Long id) { 
		this.id = id;
	}

	public Article(String articleStoryIntroduction, String articleMainStory,
					String articleSection, String pageType, String url, String title) {
		this.articleStoryIntroduction = articleStoryIntroduction;
		this.articleMainStory = articleMainStory;
		this.articleSection = articleSection;
		this.pageType = pageType;
		this.url = url;
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArticleStoryIntroduction() {
		return articleStoryIntroduction;
	}

	public void setArticleStoryIntroduction(String articleStoryIntroduction) {
		this.articleStoryIntroduction = articleStoryIntroduction;
	}

	public String getArticleMainStory() {
		return articleMainStory;
	}

	public void setArticleMainStory(String articleMainStory) {
		this.articleMainStory = articleMainStory;
	}

	public String getArticleSection() {
		return articleSection;
	}

	public void setArticleSection(String articleSection) {
		this.articleSection = articleSection;
	} 

	public String getPageType() {
		return pageType;
	}

	public void setPageType(String pageType) {
		this.pageType = pageType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	} 

	public String getUrl() {
		return url;
	} 

	public void setUrl(String url) {
		this.url = url;
	}
}