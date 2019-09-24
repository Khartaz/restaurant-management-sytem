package com.restaurant.management.mapper.scrumboard;

import com.restaurant.management.domain.scrumboard.Activity;
import com.restaurant.management.domain.scrumboard.dto.ActivityDTO;
import org.springframework.stereotype.Component;

@Component
public final class ActivityMapper {

    public Activity mapToActivity(ActivityDTO activityDTO) {
        return new Activity(
                activityDTO.getId(),
                activityDTO.getType(),
                activityDTO.getMemberId(),
                activityDTO.getMessage(),
                activityDTO.getTime()
        );
    }

    public ActivityDTO mapToActivityDTO(Activity activity) {
        return new ActivityDTO(
                activity.getId(),
                activity.getType(),
                activity.getMemberId(),
                activity.getMessage(),
                activity.getTime()
        );
    }
}
