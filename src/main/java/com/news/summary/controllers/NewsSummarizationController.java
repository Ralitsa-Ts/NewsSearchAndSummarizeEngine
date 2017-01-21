package com.news.summary.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class NewsSummarizationController {
  
  @RequestMapping("/find-summarized-article")
  @ResponseBody
  public String findSummarizedArticle(String search) {
    try {
    }
    catch (Exception ex) {
      return "Could not retrieve summarized article." + ex.toString();
    }
    return "Summarized article successfully retrieved.";
  }
}