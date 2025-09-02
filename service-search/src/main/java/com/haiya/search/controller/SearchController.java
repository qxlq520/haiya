package com.haiya.search.controller;

import com.haiya.search.entity.SearchDocument;
import com.haiya.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 全局搜索
     */
    @GetMapping
    public List<SearchDocument> searchAll(@RequestParam String keyword) {
        return searchService.searchAll(keyword);
    }

    /**
     * 按类型搜索
     */
    @GetMapping("/type/{type}")
    public List<SearchDocument> searchByType(@PathVariable String type, @RequestParam String keyword) {
        return searchService.searchByType(type, keyword);
    }

    /**
     * 按类型搜索（无关键词）
     */
    @GetMapping("/type-only/{type}")
    public List<SearchDocument> searchByTypeOnly(@PathVariable String type) {
        return searchService.searchByTypeOnly(type);
    }

    /**
     * 保存文档
     */
    @PostMapping
    public SearchDocument saveDocument(@RequestBody SearchDocument document) {
        return searchService.saveDocument(document);
    }

    /**
     * 删除文档
     */
    @DeleteMapping("/{id}")
    public void deleteDocument(@PathVariable String id) {
        searchService.deleteDocument(id);
    }

    /**
     * 获取所有文档
     */
    @GetMapping("/all")
    public List<SearchDocument> getAllDocuments() {
        return searchService.getAllDocuments();
    }
    
    /**
     * 搜索用户
     */
    @GetMapping("/users")
    public List<SearchDocument> searchUsers(@RequestParam String keyword) {
        return searchService.searchByType("user", keyword);
    }
    
    /**
     * 搜索视频
     */
    @GetMapping("/videos")
    public List<SearchDocument> searchVideos(@RequestParam String keyword) {
        return searchService.searchByType("video", keyword);
    }
}