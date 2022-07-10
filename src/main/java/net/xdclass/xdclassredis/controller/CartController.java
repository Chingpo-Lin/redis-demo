package net.xdclass.xdclassredis.controller;

import net.xdclass.xdclassredis.dao.VideoDao;
import net.xdclass.xdclassredis.model.VideoDO;
import net.xdclass.xdclassredis.util.JsonData;
import net.xdclass.xdclassredis.util.JsonUtil;
import net.xdclass.xdclassredis.vo.CartItemVO;
import net.xdclass.xdclassredis.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private VideoDao videoDao;

    @RequestMapping("add")
    public JsonData addCart(int videoId, int buyNum) {

        BoundHashOperations<String,Object,Object> myCart = getMyCartOps();
        Object cacheObj = myCart.get(videoId + "");

        String result = "";
        if (cacheObj != null) {
            result = (String) cacheObj;
        }

        if (cacheObj == null) {
            // items not in cart
            CartItemVO cartItem = new CartItemVO();
            VideoDO videoDO = videoDao.findDetailById(videoId);

            cartItem.setBuyNum(buyNum);
            cartItem.setPrice(videoDO.getPrice());
            cartItem.setProductId(videoDO.getId());
            cartItem.setProductImg(videoDO.getImg());
            cartItem.setProductTitle(videoDO.getTitle());

            myCart.put(videoId + "", JsonUtil.objectToJson(cartItem));
        } else {
            // items already in cart
            CartItemVO cartItemVO = JsonUtil.jsonToPojo(result, CartItemVO.class);
            cartItemVO.setBuyNum(cartItemVO.getBuyNum() + buyNum);

            myCart.put(videoId + "", JsonUtil.objectToJson(cartItemVO));
        }
        return JsonData.buildSuccess();
    }

    /**
     * check my cart
     * @return
     */
    @RequestMapping("mycart")
    public JsonData getMyCart() {
        BoundHashOperations<String,Object,Object> myCart = getMyCartOps();
        List<Object> itemList = myCart.values();
        List<CartItemVO> cartItemVOList = new ArrayList<>();

        for (Object item: itemList) {
            CartItemVO cartItemVO = JsonUtil.jsonToPojo((String) item, CartItemVO.class);
            cartItemVOList.add(cartItemVO);
        }

        CartVO cartVO = new CartVO();
        cartVO.setCartItems(cartItemVOList);

        return JsonData.buildSuccess(cartVO);
    }

    @RequestMapping("clear")
    public JsonData clear() {
        String key = getCartKey();
        redisTemplate.delete(key);

        return JsonData.buildSuccess();
    }

    private BoundHashOperations<String, Object, Object> getMyCartOps() {
        String key = getCartKey();
        return redisTemplate.boundHashOps(key);
    }

    private String getCartKey() {

        // id, get from interceptor
        int userId = 88;

        String cartKey = String.format("video:cart:%s", userId);
        return cartKey;
    }
}
