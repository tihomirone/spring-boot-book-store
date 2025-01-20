package com.mj.book_seller.service;

import com.mj.book_seller.model.PurchaseHistoryEntity;
import com.mj.book_seller.repository.PurchaseHistoryRepository;
import com.mj.book_seller.repository.projection.PurchaseItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookPurchaseHistoryService implements PurchaseHistoryService {

  private final PurchaseHistoryRepository purchaseHistoryRepository;

  @Override
  public PurchaseHistoryEntity savePurchaseHistory(PurchaseHistoryEntity purchaseHistory) {
    purchaseHistory.setPurchaseTime(LocalDateTime.now());
    return purchaseHistoryRepository.save(purchaseHistory);
  }

  @Override
  public List<PurchaseItem> findPurchasedItemsOfUser(Long userId) {
    return purchaseHistoryRepository.findAllPurchasesOfUser(userId);
  }
}
