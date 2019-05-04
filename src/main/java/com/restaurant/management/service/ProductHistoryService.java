package com.restaurant.management.service;

import com.restaurant.management.audit.query.AuditQueryResult;
import com.restaurant.management.audit.query.AuditQueryUtils;
import com.restaurant.management.domain.Product;
import com.restaurant.management.domain.history.ProductHistory;
import com.restaurant.management.repository.history.ProductHistoryRepository;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductHistoryService implements ProductHistoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<ProductHistory> productRevisions(Long productId) {

        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        AuditQuery auditQuery = auditReader.createQuery()
                .forRevisionsOfEntity(Product.class, false, true)
                .add(AuditEntity.id().eq(productId));

        return AuditQueryUtils.getAuditQueryResult(auditQuery, Product.class).stream()
                .map(ProductHistoryService::getProductHistory)
                .collect(Collectors.toList());
    }

    private static ProductHistory getProductHistory(AuditQueryResult<Product> auditQueryResult) {
        return new ProductHistory(
                auditQueryResult.getEntity(),
                auditQueryResult.getRevision().getRevisionNumber(),
                auditQueryResult.getRevisionType(),
                auditQueryResult.getEntity().getCreatedAt(),
                auditQueryResult.getEntity().getUpdatedAt(),
                auditQueryResult.getEntity().getCreatedBy(),
                auditQueryResult.getEntity().getUpdatedBy()
        );
    }
}
