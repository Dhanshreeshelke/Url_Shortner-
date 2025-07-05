package com.blogs.service;
public interface UrlService {
    String shortenUrl(String longUrl);
    String shortenUrlWithCustomCode(String longUrl, String customCode, int userId) throws Exception;
    String getLongUrl(String shortCode);
}



