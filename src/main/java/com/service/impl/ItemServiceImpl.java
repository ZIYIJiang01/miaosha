package com.service.impl;

import com.dao.ItemDOMapper;
import com.dao.ItemStockDOMapper;
import com.dataobject.ItemDO;
import com.dataobject.ItemStockDO;
import com.dataobject.UserDO;
import com.dataobject.UserPasswordDO;
import com.error.BusinessException;
import com.error.EmBusinessError;
import com.service.ItemService;
import com.service.model.ItemModel;
import com.service.model.UserModel;
import com.validator.ValidationResult;
import com.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ValidatorImpl validator;
    @Autowired
    private ItemDOMapper itemDOMapper;
    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
//        validation
        ValidationResult result = validator.validate(itemModel);
        if(result.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
//        from itemmodel to data object
        ItemDO itemDO = this.convertFromModel(itemModel);

//        write into database
        itemDOMapper.insertSelective(itemDO);
        itemModel.setId(itemDO.getId());
        ItemStockDO itemStockDO = this.convertStockFromModel(itemModel);
        itemStockDOMapper.insertSelective(itemStockDO);
//        return item that has been created
        return this.getItemById(itemModel.getId());
    }

    @Override
    public List<ItemModel> listItem() {
        List<ItemDO> itemDOList = itemDOMapper.listItem();
        List<ItemModel> itemModelList =  itemDOList.stream().map(itemDO -> {
                    ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
                    ItemModel itemModel = this.convertFromDataObject(itemDO, itemStockDO);
                    return itemModel;
                }).collect(Collectors.toList());
        return itemModelList;
    }


    @Override
    public ItemModel getItemById(Integer id) {
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(id);
        if(itemDO==null){
            return null;
        }
        ItemStockDO itemStockDO = itemStockDOMapper.selectByItemId(itemDO.getId());
        ItemModel itemModel = convertFromDataObject(itemDO, itemStockDO);
        return itemModel;
    }

    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount) {
        int affectedRow = itemStockDOMapper.decreaseStock(itemId, amount);
        if(affectedRow >0){
//            update success
            return true;
        }else{
//            update fail
            return false;
        }

    }

    @Override
    @Transactional
    public void increaseSales(Integer itemId, Integer amount) {
        itemDOMapper.increaseSales(itemId,amount);
    }

    //    convert data type
    private ItemDO convertFromModel(ItemModel itemModel){
        if (itemModel == null) {
            return null;
        }
        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel,itemDO);
        return itemDO;
    }
    private ItemStockDO convertStockFromModel( ItemModel itemModel){
        if (itemModel == null) {
            return null;
        }
        ItemStockDO itemStockDO = new  ItemStockDO();
        itemStockDO.setStock(itemModel.getStock());
        itemStockDO.setItemId(itemModel.getId());
        return itemStockDO;
    }
    private ItemModel convertFromDataObject(ItemDO itemDO, ItemStockDO itemStockDO){
        if(itemDO==null){
            return null;
        }
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO, itemModel);
        if(itemStockDO!=null) {
            itemModel.setStock(itemStockDO.getStock());
        }
        return itemModel;
    }
}
