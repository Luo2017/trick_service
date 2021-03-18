package com.trick.trick_class.controller;

import com.trick.trick_class.model.entity.VideoOrder;
import com.trick.trick_class.model.request.VideoOrderRequest;
import com.trick.trick_class.service.VideoOrderService;
import com.trick.trick_class.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pri/order")
public class VideoOrderController {

    @Autowired
    private VideoOrderService videoOrderService;

    /**
     * 经过拦截器request获得了user_id和name等基本信息
     * @param videoOrderRequest
     * @param request
     * @return
     */
    @RequestMapping("save")
    public JsonData saveOrder(@RequestBody VideoOrderRequest videoOrderRequest, HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("user_id");
        int rows = videoOrderService.save(userId, videoOrderRequest.getVideoId());
        //build成功是不用加信息的
        return rows == 0 ? JsonData.buildError("下单失败") : JsonData.buildSuccess();
    }

    /**
     * 订单列表
     * @param request
     * @return
     */
    @RequestMapping("list")
    public JsonData listOrder(HttpServletRequest request) {
        Integer userId = (Integer) request.getAttribute("user_id");
        List<VideoOrder> videoOrderList = videoOrderService.listOrderByUserId(userId);
        return JsonData.buildSuccess(videoOrderList);
    }
}
