package com.trick.trick_class.mapper;

import com.trick.trick_class.model.entity.VideoOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoOrderMapper {

    VideoOrder findByUserIdAndVideoIdAndState(@Param("user_id") int user_id, @Param("video_id") int video_id, @Param("state") int state);

    int saveOrder(VideoOrder videoOrder);

    List<VideoOrder> listOrderByUserId(@Param("user_id") Integer userId);
}
