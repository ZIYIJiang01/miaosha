package com.service;

import com.service.model.PromoModel;

public interface PromoService {
//    according to item id, get current or coming promotions
    PromoModel getPromoByItemId(Integer itemId);

    void publishPromo(Integer promoId);

//    generate token
    String generateSecondKillToken(Integer promoId, Integer itemId, Integer userId);

}
