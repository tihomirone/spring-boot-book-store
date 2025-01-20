package com.mj.book_seller.service;

import com.mj.book_seller.model.PurchaseHistoryEntity;
import com.mj.book_seller.repository.projection.PurchaseItem;

import java.util.List;

public interface PurchaseHistoryService {

  PurchaseHistoryEntity savePurchaseHistory(PurchaseHistoryEntity purchaseHistory);

  List<PurchaseItem> findPurchasedItemsOfUser(Long userId);
}
