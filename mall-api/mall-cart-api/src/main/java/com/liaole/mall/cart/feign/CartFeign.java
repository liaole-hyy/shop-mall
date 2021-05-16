package com.liaole.mall.cart.feign;

import com.liaole.mall.cart.model.Cart;
import com.liaole.mall.util.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(value = "mall-cart")
public interface CartFeign {

    /*** * 购物车数据 */
    @PostMapping(value = "/cart/list")
    public RespResult<List<Cart>> list(@RequestBody List<String> ids);

    /*** * 删除指定购物车 */
    @DeleteMapping("/cart")
    public RespResult delete(@RequestBody List<String> ids);
}
