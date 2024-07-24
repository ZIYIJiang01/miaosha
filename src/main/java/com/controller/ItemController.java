package com.controller;

import com.controller.viewobject.ItemVO;
import com.controller.viewobject.UserVO;
import com.dataobject.ItemStockDO;
import com.error.BusinessException;
import com.response.CommonReturnType;
import com.service.CacheService;
import com.service.ItemService;
import com.service.UserService;
import com.service.model.ItemModel;
import com.service.model.UserModel;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller("item")
@RequestMapping("/item")
@CrossOrigin(origins = "http:localhost:8080", allowCredentials = "true", allowedHeaders = "*")
public class ItemController extends BaseController{

    @Autowired
    private ItemService itemService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private CacheService cacheService;

    /**
     * create item
     * */
    @RequestMapping(value="/create", method= {RequestMethod.POST}, consumes={CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createItem(@RequestParam(name="title")String title,
                                       @RequestParam(name="description")String description,
                                       @RequestParam(name="price")BigDecimal price,
                                       @RequestParam(name="stock")Integer stock,
                                       @RequestParam(name="imgUrl")String imgUrl) throws BusinessException {
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setStock(stock);
        itemModel.setImgUrl(imgUrl);

        ItemModel item = itemService.createItem(itemModel);
        ItemVO itemVO = convertFromModel(itemModel);
        return CommonReturnType.create(null);
    }

    /**
     * browse item list
     * */
    @RequestMapping(value="/list", method= {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType listItem(){
        List<ItemModel> itemModelList = itemService.listItem();
        List<ItemVO> itemVOList =  itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = this.convertFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());
        return CommonReturnType.create(itemVOList);
    }


    /**
     * get item detail
     * */
    @RequestMapping(value="/get", method= {RequestMethod.GET})
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(name="id")Integer id){

        ItemModel itemModel = null;
//        get from local cache
        itemModel = (ItemModel)cacheService.getFromCommonCache("item_"+id);
        if(itemModel == null){
            //        base on id, try to find it in redis
            itemModel = (ItemModel) redisTemplate.opsForValue().get("item_" + id);

//        if redis doesn't have, then access service
            if(itemModel == null){
                itemModel = itemService.getItemById(id);
//            set it into redis
                redisTemplate.opsForValue().set("item_" + id, itemModel);
                redisTemplate.expire("item_"+id, 10, TimeUnit.MINUTES);
            }
//          set it into cache
            cacheService.setCommonCache("item_"+id, itemModel);

        }



        ItemVO itemVO = convertFromModel(itemModel);
        return CommonReturnType.create(itemVO);
    }


    private ItemVO convertFromModel(ItemModel itemModel){
        if(itemModel == null){
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel,itemVO);
        if(itemModel.getPromoModel()!= null){
//            there is a promotion
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setStartDate(itemModel.getPromoModel().getStartDate().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
        }else{
            itemVO.setPromoStatus(0);
        }
        return itemVO;
    }
}
