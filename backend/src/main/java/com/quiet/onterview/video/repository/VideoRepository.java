package com.quiet.onterview.video.repository;

import com.quiet.onterview.video.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long> {
}
