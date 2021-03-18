package com.trick.trick_class.controller;

import com.trick.trick_class.model.entity.Video;
import com.trick.trick_class.model.entity.VideoBanner;
import com.trick.trick_class.service.VideoService;
import com.trick.trick_class.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pub/video")
public class VideoController {

    //容器中的实例只有VideoServiceImpl
    @Autowired
    private VideoService videoService;

    /**
     * 视频列表
     * @return
     */
    @RequestMapping("/list")
    public Object listVideo() {
        List<Video> videos = videoService.listVideo();
        //返回的是一个JsonData类型的，code默认为0，msg为null，data为videos
        return JsonData.buildSuccess(videos);
    }

    /**
     * 轮播列表
     * @return
     */
    @RequestMapping("/list_banner")
    public Object listBanner() {
        List<VideoBanner> videoBanners = videoService.listBanner();
        //返回的是一个JsonData类型的，code默认为0，msg为null，data为videos
        return JsonData.buildSuccess(videoBanners);
    }

    /**
     * 根据id查找视频详情
     * @return
     */
    //Get方式的请求，请求信息放在url中，video_id=5这种形式，为了防止出现参数不一致，限定了从参数video_id中取参数
    @GetMapping("/find_detail_by_id")
    public JsonData findDetailById(@RequestParam(value = "video_id", required = true) int videoId) {
        Video video = videoService.findDetailById(videoId);
        return JsonData.buildSuccess(video);
    }

}
