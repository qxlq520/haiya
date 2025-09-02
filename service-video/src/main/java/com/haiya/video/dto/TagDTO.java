package com.haiya.video.dto;

public class TagDTO {
    private Long id;
    private String name;
    private Long videoCount;

    // Constructors
    public TagDTO() {}

    public TagDTO(Long id, String name, Long videoCount) {
        this.id = id;
        this.name = name;
        this.videoCount = videoCount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(Long videoCount) {
        this.videoCount = videoCount;
    }
}