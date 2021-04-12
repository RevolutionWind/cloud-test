package com.decent.order.dao;

import com.decent.common.enity.ProductOrder;
import com.decent.common.enity.UserOrderProduct;
import com.decent.order.vo.ProductOrderVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jiangy
 * @date 2021/1/2
 */
@Mapper
@Repository
public interface OrderDao {

    /**
     * 订单查询
     *
     * @param userCode 会员编号
     * @return 订单列表
     */
    @Select("select o.`id`, `user_code`, o.`order_no`, `total_price`, `product_id`, `product_name`, o.`icon_url`, `count`, " +
            "`pay_price`, `user_coupon_id`, `coupon_price`, `integral`, `order_status`, `success_time`, `end_valid_time`, " +
            "o.`create_time`, o.`update_time`, `brand_name`, `card_number`, `card_pwd`, `use_status`, `buy_after_effective_time`, " +
            "`use_notice` from product_order o, user_order_product op where o.order_no = op.order_no and o.user_code = #{userCode}")
    List<ProductOrderVO> listOrder(String userCode);

    /**
     * 查询订单卡券
     *
     * @param userCode 用户编号
     * @param orderNo  订单编号
     * @return 卡券
     */
    @Select("select o.`id`, `user_code`, o.`order_no`, `total_price`, `product_id`, `product_name`, o.`icon_url`, `count`, " +
            " `pay_price`, `user_coupon_id`, `coupon_price`, `integral`, `order_status`, `success_time`, `end_valid_time`,  " +
            " o.`create_time`, o.`update_time`, `brand_name`, `card_number`, `card_pwd`, `use_status`, `buy_after_effective_time`, " +
            " `use_notice` from product_order o, user_order_product op where o.order_no = op.order_no and o.user_code = #{userCode}" +
            "and o.order_no = #{orderNo}")
    UserOrderProduct queryOrderCoupon(String userCode, String orderNo);

    /**
     * 创建订单
     *
     * @param productOrder 订单
     */
    @Insert("INSERT INTO `product_order`" +
            "(`user_code`, `order_no`, `total_price`, `product_id`, `product_name`, " +
            "`icon_url`, `count`, `pay_price`, `user_coupon_id`, `coupon_price`, `integral`, " +
            "`order_status`, `end_valid_time`, `create_time`, `update_time`) " +
            "VALUES " +
            "(#{order.userCode}, #{order.orderNo}, #{order.totalPrice},  #{order.productId},  #{order.productName}, " +
            " #{order.iconUrl},#{order.count},#{order.payPrice},#{order.userCouponId},#{order.couponPrice},#{order.integral}," +
            "#{order.orderStatus},#{order.endValidTime}, now(), now())")
    void insertOrder(@Param("order") ProductOrder productOrder);
}
