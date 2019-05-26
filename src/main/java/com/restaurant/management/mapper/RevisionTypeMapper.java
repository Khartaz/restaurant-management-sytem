package com.restaurant.management.mapper;

import com.restaurant.management.domain.dto.RevisionTypeDto;
import org.hibernate.envers.RevisionType;
import org.springframework.stereotype.Component;

@Component
public final class RevisionTypeMapper {

    public RevisionTypeDto mapToRevisionTypeDto(RevisionType revisionType) {

        if (revisionType == null) {
            return null;
        }

        switch (revisionType) {
            case ADD:
                return RevisionTypeDto.ADD;
            case MOD:
                return RevisionTypeDto.MOD;
            case DEL:
                return RevisionTypeDto.DEL;
            default:
                throw new IllegalArgumentException("revisionType");
        }
    }

}
