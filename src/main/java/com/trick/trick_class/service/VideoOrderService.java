package com.trick.trick_class.service;

import com.trick.trick_class.model.entity.VideoOrder;

import java.util.List;

public interface VideoOrderService {

    int save(int userId, int videoId);

    List<VideoOrder> listOrderByUserId(Integer userId);
}
