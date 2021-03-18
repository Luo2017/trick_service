package com.trick.trick_class.service.impl;

import com.trick.trick_class.config.CacheKeyManager;
import com.trick.trick_class.model.entity.Video;
import com.trick.trick_class.model.entity.VideoBanner;
import com.trick.trick_class.mapper.VideoMapper;
import com.trick.trick_class.service.VideoService;
import com.trick.trick_class.utils.BaseCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {

    //属性中有关联对象时需要依赖注入，报红不是错误
    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private BaseCache baseCache;

    @Override
    public List<Video> listVideo() {
        try{


            Object cacheObj =  baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_VIDEO_LIST, ()->{

                List<Video> videoList =  videoMapper.listVideo();

                System.out.println("从数据库里面找视频列表");

                return videoList;

            });
//            System.out.println(baseCache.getTenMinuteCache().getIfPresent(CacheKeyManager.INDEX_BANNER_KEY));
            //当获取轮播图列表成功时执行下面的
            if(cacheObj instanceof List){
                List<Video> videoList = (List<Video>)cacheObj;
                return videoList;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<VideoBanner> listBanner() {
        try{
            //这里的key好像并没有作用，get方法默认获取key对应的value即key所对应的缓存
            //显然是没有相应缓存的，那就执行后面的方法并缓存，对应给前面的key，即查询数据库获得轮播图列表
            //下次再查询该key对应的value值时就可以查到对应的结果了
//            System.out.println(baseCache.getTenMinuteCache().getIfPresent(CacheKeyManager.INDEX_BANNER_KEY));

            Object cacheObj =  baseCache.getTenMinuteCache().get(CacheKeyManager.INDEX_BANNER_KEY, ()->{

                List<VideoBanner> bannerList =  videoMapper.listVideoBanner();

                System.out.println("从数据库里面找轮播图列表");

                return bannerList;

            });
//            System.out.println(baseCache.getTenMinuteCache().getIfPresent(CacheKeyManager.INDEX_BANNER_KEY));
            //当获取轮播图列表成功时执行下面的
            if(cacheObj instanceof List){
                List<VideoBanner> bannerList = (List<VideoBanner>)cacheObj;
                return bannerList;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Video findDetailById(int videoId) {
        //下面是待办的意思
        //TODO 需要mybatis复杂查询
        String videoCacheKey = String.format(CacheKeyManager.VIDEO_DETAIL,videoId);
//        System.out.println(videoCacheKey);
        try{
            Object cacheObject = baseCache.getTenMinuteCache().get(
                    videoCacheKey, ()->{
            // 需要使⽤用mybaits关联复杂查询
                        Video video = videoMapper.findDetailById(videoId);
                        return video;
                    });
            if(cacheObject instanceof Video){
                Video video = (Video)cacheObject;
                return video;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
