package org.egovframe.cloud.portalservice.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * org.egovframe.cloud.userservice.domain.user.Role
 * <p>
 * 사용자 권한
 *
 * @author 표준프레임워크센터 jaeyeolkim
 * @version 1.0
 * @since 2021/06/30
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------    ---------------------------
 *  2021/06/30    jaeyeolkim  최초 생성
 * </pre>
 */
@Getter
@RequiredArgsConstructor
public enum Role {

    // 스프링 시큐리티에서는 권한 코드에 항상 ROLE_ 이 앞에 있어야 한다.
	
    GUEST("ROLE_GUEST", "손님"),
    TEACHER("ROLE_TEACHER", "선생"),
    OFFICER("ROLE_OFFICER", "서울시교육청 관리자"),
    ADMIN("ROLE_ADMIN", "시스템 관리자"),
    STUDENT("ROLE_STUDENT", "학생");

    private final String key;
    private final String title;
}
