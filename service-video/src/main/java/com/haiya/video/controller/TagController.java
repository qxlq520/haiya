package com.haiya.video.controller;

import com.haiya.video.entity.Tag;
import com.haiya.video.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping("/{id}")
    public Optional<Tag> getTagById(@PathVariable Long id) {
        return tagService.getTagById(id);
    }

    @GetMapping("/name/{name}")
    public Optional<Tag> getTagByName(@PathVariable String name) {
        return tagService.getTagByName(name);
    }

    @PostMapping
    public Tag createTag(@RequestBody String name) {
        return tagService.createTag(name);
    }

    @GetMapping("/video/{videoId}")
    public List<Tag> getTagsByVideoId(@PathVariable Long videoId) {
        return tagService.getTagsByVideoId(videoId);
    }

    @GetMapping("/{tagId}/videos")
    public List<Long> getVideoIdsByTagId(@PathVariable Long tagId) {
        return tagService.getVideoIdsByTagId(tagId);
    }

    @PostMapping("/video/{videoId}/tag/{tagId}")
    public void addTagToVideo(@PathVariable Long videoId, @PathVariable Long tagId) {
        tagService.addTagToVideo(videoId, tagId);
    }

    @DeleteMapping("/video/{videoId}/tag/{tagId}")
    public void removeTagFromVideo(@PathVariable Long videoId, @PathVariable Long tagId) {
        tagService.removeTagFromVideo(videoId, tagId);
    }
}