package com.restaurant.management.mapper.scrumboard;

import com.restaurant.management.domain.scrumboard.Member;
import com.restaurant.management.domain.scrumboard.dto.MemberDTO;
import org.springframework.stereotype.Component;

@Component
public final class MemberMapper {

    public Member mapToMember(MemberDTO memberDTO) {
        return new Member(
                memberDTO.getId(),
                memberDTO.getName(),
                memberDTO.getAvatar()
        );
    }

    public MemberDTO mapToMemberDTO(Member member) {
        return new MemberDTO(
                member.getId(),
                member.getName(),
                member.getAvatar()
        );
    }
}
