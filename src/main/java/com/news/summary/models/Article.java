package com.news.summary.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "articles")
public class Article {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  
  @NotNull
  private String articleStoryIntroduction;
  
  @NotNull
  private String articleMainStory;
  
  private String articleSection;
  
  private String pageType;
  
  public Article() { }

  public Article(Long id) { 
    this.id = id;
  }
  
  public Article(String articleStoryIntroduction, String articleMainStory, String articleSection, String pageType) {
    this.articleStoryIntroduction = articleStoryIntroduction;
    this.articleMainStory = articleMainStory;
    this.articleSection = articleSection;
    this.pageType = pageType;
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
	
}