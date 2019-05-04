package com.restaurant.management.repository.history;

import com.restaurant.management.domain.history.ProductHistory;

import java.util.List;

public interface ProductHistoryRepository {

    List<ProductHistory> productRevisions(Long productId);

}
