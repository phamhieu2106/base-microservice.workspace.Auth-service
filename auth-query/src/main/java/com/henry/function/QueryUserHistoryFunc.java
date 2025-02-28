package com.henry.function;

import com.henry.base.func.BaseFunc;
import com.henry.base.request.QueryHistoryRequest;
import com.henry.base.response.HistoryResponse;
import com.henry.constant.UserRole;
import com.henry.domain.entity.HistoryEntity;
import com.henry.repository.UserHistoryRepository;
import com.henry.util.PageableUtils;
import com.henry.util.PermissionUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueryUserHistoryFunc extends BaseFunc {

    private final UserHistoryRepository userHistoryRepository;

    public Page<HistoryResponse> exec(QueryHistoryRequest request) {
        PermissionUtils.hasRole(UserRole.ALL_ROLE);

        Pageable pageable = PageableUtils.convertToPageable(request.getPageNumber(), request.getPageSize(), request.getSorts());
        Page<HistoryEntity> pageHistories = userHistoryRepository.findAllByEntityId(request.getEntityId(), pageable);
        return PageableUtils.convertPageToPageResponse(pageHistories, HistoryResponse.class);
    }
}
