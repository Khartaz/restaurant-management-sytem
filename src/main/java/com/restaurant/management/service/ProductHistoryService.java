package com.restaurant.management.service;

import com.restaurant.management.domain.ecommerce.history.ProductHistory;
import com.restaurant.management.repository.history.ProductHistoryRepository;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;

import java.util.List;

public interface ProductHistoryService extends ProductHistoryRepository {

    List<ProductHistory> productRevisions(Long productId, @CurrentUser UserPrincipal currentUser);
}
