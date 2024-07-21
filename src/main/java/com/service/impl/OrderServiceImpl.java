package com.service.impl;

import com.dao.OrderDOMapper;
import com.dao.SequenceDOMapper;
import com.dataobject.OrderDO;
import com.dataobject.SequenceDO;
import com.error.BusinessException;
import com.error.EmBusinessError;
import com.service.ItemService;
import com.service.OrderService;
import com.service.UserService;
import com.service.model.ItemModel;
import com.service.model.OrderModel;
import com.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceDOMapper;

    @Override
    @Transactional
    public OrderModel createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException {
//        check if valid to order: item exist, user status legal, order amount is correct
        ItemModel itemModel = itemService.getItemById(itemId);
        if(itemModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "item does not exist");
        }
        UserModel userModel = userService.getUserById(userId);
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "user does not exist");
        }
        if(amount <= 0 || amount >99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "amount does not correct");
        }
//        if order correct, stock decrement (order lock) (not after pay
        boolean result = itemService.decreaseStock(itemId, amount);
        if(!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }
//        take order to database
        OrderModel orderModel = new OrderModel();
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        BigDecimal price = itemModel.getPrice();
        orderModel.setItemPrice(price);
        orderModel.setOrderPrice(price.multiply(new BigDecimal(amount)));

//        create order id
        orderModel.setId(generateOrderNo());
        OrderDO orderDO = convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);
//       add sales
        itemService.increaseSales(itemId,amount);
        //        return to front end
        return orderModel;
    }

//    generate order id
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected String generateOrderNo(){
//        16 digit order id ->decide by ourselves
        StringBuilder stringBuilder = new StringBuilder();
//        8 is time stamp yy-mm-dd ->easy to archive by time
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);
//        6 is auto-increment sequence -> make sure order id don't repeat
//        2 is database and table sharding -> use that to split into different database and table,
//                                            disperse the pressure for database to operate

//        get current sequence
        int sequence = 0;
        SequenceDO sequenceDO = sequenceDOMapper.getSequenceByName("order_info");
        sequence = sequenceDO.getCurrentValue();
        sequenceDO.setCurrentValue(sequenceDO.getCurrentValue()+sequenceDO.getStep());
        sequenceDOMapper.updateByPrimaryKeySelective(sequenceDO);
        String sequenceStr = String.valueOf(sequence);
        for(int i=0; i< 6-sequenceStr.length(); i++){
            stringBuilder.append(0);
        }
        stringBuilder.append(sequenceStr);
//    just for temporary
        stringBuilder.append("00");
        return stringBuilder.toString();
    }

//    convert data type
    private OrderDO convertFromOrderModel(OrderModel orderModel){
        if(orderModel == null){
            return null;
        }
        OrderDO orderDO = new OrderDO();
        BeanUtils.copyProperties(orderModel, orderDO);
        return orderDO;
    }

}
