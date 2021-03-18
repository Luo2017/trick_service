package com.trick.trick_class.mapper;

import com.trick.trick_class.model.entity.Video;
import com.trick.trick_class.model.entity.VideoBanner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoMapper {

    List<Video> listVideo();

    List<VideoBanner> listVideoBanner();

    Video findDetailById(@Param("video_id") int videoId);

    Video findById(@Param("video_id") int videoId);
}
