package com.news.summary.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "searches")
public class Search {

    @Id
    private String id;

    @Type(type = "text")
    private String keywords;

    private String status;

    public Search() {
    }

    public Search(String id, String keywords, String status) {
        this.id = id;
        this.keywords = keywords;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
