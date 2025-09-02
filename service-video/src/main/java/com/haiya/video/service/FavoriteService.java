package com.haiya.video.service;

import com.haiya.video.entity.Favorite;
import com.haiya.video.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public List<Favorite> getFavoritesByUserId(Long userId) {
        return favoriteRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public Long getFavoriteCountByVideoId(Long videoId) {
        return favoriteRepository.countByVideoId(videoId);
    }

    public boolean isVideoFavoritedByUser(Long userId, Long videoId) {
        return favoriteRepository.existsByUserIdAndVideoId(userId, videoId);
    }

    @Transactional
    public Favorite favoriteVideo(Long userId, Long videoId) {
        // 检查是否已经收藏
        if (favoriteRepository.existsByUserIdAndVideoId(userId, videoId)) {
            throw new RuntimeException("Video already favorited by user");
        }
        
        Favorite favorite = new Favorite(userId, videoId);
        return favoriteRepository.save(favorite);
    }

    @Transactional
    public boolean unfavoriteVideo(Long userId, Long videoId) {
        Optional<Favorite> favorite = favoriteRepository.findByUserIdAndVideoId(userId, videoId);
        if (favorite.isPresent()) {
            favoriteRepository.delete(favorite.get());
            return true;
        }
        return false;
    }
}