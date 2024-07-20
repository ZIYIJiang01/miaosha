package com.service;

import com.error.BusinessException;
import com.service.model.ItemModel;

import java.util.List;

public interface ItemService {

//    create item
    ItemModel createItem(ItemModel itemModel) throws BusinessException;
//    item list browse
    List<ItemModel> listItem();
//    item detail browse
    ItemModel getItemById(Integer id);
}
