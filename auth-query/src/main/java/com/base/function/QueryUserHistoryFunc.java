package com.base.function;

import com.base.func.BaseFunc;
import com.base.request.QueryHistoryRequest;
import com.base.response.HistoryResponse;
import com.base.constant.UserRole;
import com.base.domain.entity.HistoryEntity;
import com.base.repository.UserHistoryRepository;
import com.base.util.PageableUtils;
import com.base.util.PermissionUtils;
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
