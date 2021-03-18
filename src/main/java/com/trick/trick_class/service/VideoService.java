package com.trick.trick_class.service;

import com.trick.trick_class.model.entity.Video;
import com.trick.trick_class.model.entity.VideoBanner;

import java.util.List;

public interface VideoService {

    List<Video> listVideo();

    List<VideoBanner> listBanner();

    Video findDetailById(int videoId);
}
