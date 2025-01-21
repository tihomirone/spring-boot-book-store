package com.mj.book_seller.controller;


import com.mj.book_seller.model.PurchaseHistoryEntity;
import com.mj.book_seller.repository.projection.PurchaseItem;
import com.mj.book_seller.security.UserPrincipal;
import com.mj.book_seller.service.PurchaseHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/purchase-history")
@RequiredArgsConstructor
public class PurchaseHistoryController {

  private final PurchaseHistoryService purchaseHistoryService;

  @PostMapping
  public ResponseEntity<PurchaseHistoryEntity> savePurchaseHistory(@RequestBody PurchaseHistoryEntity purchaseHistory) {
    return new ResponseEntity<>(purchaseHistoryService.savePurchaseHistory(purchaseHistory), HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<PurchaseItem>> getAllPurchasesOfUser(
      @AuthenticationPrincipal UserPrincipal user) {
    return ResponseEntity.ok(purchaseHistoryService.findPurchasedItemsOfUser(user.getId()));
  }
}
