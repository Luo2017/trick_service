package com.trick.trick_class.service.impl;

import com.trick.trick_class.exception.XDException;
import com.trick.trick_class.mapper.EpisodeMapper;
import com.trick.trick_class.mapper.PlayRecordMapper;
import com.trick.trick_class.mapper.VideoMapper;
import com.trick.trick_class.mapper.VideoOrderMapper;
import com.trick.trick_class.model.entity.Episode;
import com.trick.trick_class.model.entity.PlayRecord;
import com.trick.trick_class.model.entity.Video;
import com.trick.trick_class.model.entity.VideoOrder;
import com.trick.trick_class.service.VideoOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class VideoOrderServiceImpl implements VideoOrderService {

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private EpisodeMapper episodeMapper;

    @Autowired
    private PlayRecordMapper playRecordMapper;

    @Override
    @Transactional
    public int save(int userId, int videoId) {
        VideoOrder videoOrder = videoOrderMapper.findByUserIdAndVideoIdAndState(userId, videoId, 1);
        //当订单已经存在了，保存订单失败
        if(videoOrder != null) {
            return 0;
        }
        Video video = videoMapper.findById(videoId);

        VideoOrder newVideoOrder = new VideoOrder();
        newVideoOrder.setCreateTime(new Date());
        newVideoOrder.setOutTradeNo(UUID.randomUUID().toString());
        newVideoOrder.setState(1);
        newVideoOrder.setTotalFee(video.getPrice());
        newVideoOrder.setUserId(userId);
        newVideoOrder.setVideoImg(video.getCoverImg());
        newVideoOrder.setVideoTitle(video.getTitle());
        newVideoOrder.setVideoId(videoId);

        int rows = videoOrderMapper.saveOrder(newVideoOrder);

        //用来测试事务控制
//        int i = 1/0;

        //在生成订单时就生成一条播放记录，并且播放集数为第一集
        if(rows == 1) {
            Episode episode = episodeMapper.findFirstEpisodeByVideoId(videoId);
            if(episode == null) {
                 throw new XDException(-1, "集数为空");
            }
            PlayRecord newPlayRecord = new PlayRecord();
            newPlayRecord.setCreateTime(new Date());
            newPlayRecord.setCurrentNum(episode.getNum());//实际就是第一集
            newPlayRecord.setEpisodeId(episode.getId());
            newPlayRecord.setUserId(userId);
            newPlayRecord.setVideoId(videoId);
            //这里加上判断更好
            playRecordMapper.saveRecord(newPlayRecord);

        }

        return rows;
    }

    @Override
    public List<VideoOrder> listOrderByUserId(Integer userId) {
        return videoOrderMapper.listOrderByUserId(userId);
    }
}
