package com.restaurant.management.repository.history;

import com.restaurant.management.domain.history.ProductHistory;
import com.restaurant.management.security.CurrentUser;
import com.restaurant.management.security.UserPrincipal;

import java.util.List;

public interface ProductHistoryRepository {

    List<ProductHistory> productRevisions(Long productId, @CurrentUser UserPrincipal currentUser);
}
