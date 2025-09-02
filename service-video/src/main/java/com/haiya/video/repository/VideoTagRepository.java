package com.haiya.video.repository;

import com.haiya.video.entity.VideoTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoTagRepository extends JpaRepository<VideoTag, Long> {
    List<VideoTag> findByVideoId(Long videoId);
    List<VideoTag> findByTagId(Long tagId);
    boolean existsByVideoIdAndTagId(Long videoId, Long tagId);
}