package com.liaole.mall.goods.feign;

import com.liaole.mall.cart.model.Cart;
import com.liaole.mall.goods.model.Sku;
import com.liaole.mall.util.RespResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "mall-goods")
public interface SkuFeign {

    /**** * 指定分类下的推广产品列表 */
    @GetMapping(value = "/sku/aditems/type")
    public List<Sku> typeItems(@RequestParam(value = "id") Integer id);

    /**** * 删除指定分类下的推广产品列表 */
    @DeleteMapping(value = "/sku/aditems/type/{id}")
    public RespResult deleteTypeItems(@PathVariable(value = "id")Integer id);

    /**** * 修改指定分类下的推广产品列表 */
    @PutMapping(value = "/sku/aditems/type/{id}")
    public RespResult updateTypeItems(@PathVariable(value = "id")Integer id);

    /**** * 根据ID获取Sku */
    @GetMapping(value = "/sku/{id}")
    public RespResult<Sku> one(@PathVariable(value = "id") String id);

    @PostMapping(value = "/sku/dcount")
    public RespResult decount(@RequestBody List<Cart> carts);

}
