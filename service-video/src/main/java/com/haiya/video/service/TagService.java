package com.haiya.video.service;

import com.haiya.video.entity.Tag;
import com.haiya.video.entity.VideoTag;
import com.haiya.video.repository.TagRepository;
import com.haiya.video.repository.VideoTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;
    
    @Autowired
    private VideoTagRepository videoTagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    public Optional<Tag> getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    public Tag createTag(String name) {
        // 检查标签是否已存在
        Optional<Tag> existingTag = tagRepository.findByName(name);
        if (existingTag.isPresent()) {
            return existingTag.get();
        }
        
        Tag tag = new Tag(name);
        return tagRepository.save(tag);
    }

    @Transactional
    public List<Tag> getTagsByVideoId(Long videoId) {
        List<VideoTag> videoTags = videoTagRepository.findByVideoId(videoId);
        List<Long> tagIds = videoTags.stream()
                .map(VideoTag::getTagId)
                .collect(Collectors.toList());
        
        return tagRepository.findAllById(tagIds);
    }

    @Transactional
    public List<Long> getVideoIdsByTagId(Long tagId) {
        List<VideoTag> videoTags = videoTagRepository.findByTagId(tagId);
        return videoTags.stream()
                .map(VideoTag::getVideoId)
                .collect(Collectors.toList());
    }

    @Transactional
    public void addTagToVideo(Long videoId, Long tagId) {
        // 检查关联是否已存在
        if (!videoTagRepository.existsByVideoIdAndTagId(videoId, tagId)) {
            VideoTag videoTag = new VideoTag(videoId, tagId);
            videoTagRepository.save(videoTag);
            
            // 更新标签的视频计数
            tagRepository.findById(tagId).ifPresent(tag -> {
                tag.setVideoCount(tag.getVideoCount() + 1);
                tagRepository.save(tag);
            });
        }
    }

    @Transactional
    public void removeTagFromVideo(Long videoId, Long tagId) {
        List<VideoTag> videoTags = videoTagRepository.findByVideoId(videoId);
        for (VideoTag videoTag : videoTags) {
            if (videoTag.getTagId().equals(tagId)) {
                videoTagRepository.delete(videoTag);
                
                // 更新标签的视频计数
                tagRepository.findById(tagId).ifPresent(tag -> {
                    if (tag.getVideoCount() > 0) {
                        tag.setVideoCount(tag.getVideoCount() - 1);
                        tagRepository.save(tag);
                    }
                });
                break;
            }
        }
    }
}