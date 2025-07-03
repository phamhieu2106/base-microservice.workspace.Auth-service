package com.base.function;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.base.func.BaseFunc;
import com.base.constant.UserRole;
import com.base.request.QueryUserRequest;
import com.base.response.UserResponse;
import com.base.util.ElasticsearchRepositoryUtil;
import com.base.util.ElasticsearchUtils;
import com.base.util.PageableUtils;
import com.base.view.UserView;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QueryUserFunc extends BaseFunc {

    private final ElasticsearchRepositoryUtil elasticsearchRepositoryUtil;

    public Page<UserResponse> exec(QueryUserRequest request) {
        Pageable pageable = PageableUtils.convertToPageable(request.getPageNumber(), request.getPageSize());
        NativeQueryBuilder queryBuilder = ElasticsearchUtils.getQueryBuildersSort(new NativeQueryBuilder(), request.getSorts());
        queryBuilder.withPageable(pageable);

        List<String> fieldKeywords = List.of("fullName");
        Query boolQuery = ElasticsearchUtils.buildQueryBuilderWithFields(fieldKeywords, request.getKeyword());

        List<Query> queryFilters = new ArrayList<>();

        if (StringUtils.isNotBlank(request.getPhoneNumber())) {
            queryFilters.add(ElasticsearchUtils.buildFilterQueryWithValue("phoneNumber", request.getPhoneNumber()));
        }

        if (StringUtils.isNotBlank(request.getEmail())) {
            queryFilters.add(ElasticsearchUtils.buildFilterQueryWithValue("email", request.getEmail()));
        }

        if (StringUtils.isNotBlank(request.getUsername())) {
            queryFilters.add(ElasticsearchUtils.buildFilterQueryWithValue("username", request.getUsername()));
        }

        if (CollectionUtils.isNotEmpty(request.getStatus())) {
            queryFilters.add(ElasticsearchUtils.buildFilterQueryWithValues("status", request.getStatus()));
        }

        if (ObjectUtils.isNotEmpty(request.getFromCreatedDate()) || ObjectUtils.isNotEmpty(request.getToCreatedDate())) {
            queryFilters.add(ElasticsearchUtils.buildFilterQueryWithRangeDate("createdDate", request.getFromCreatedDate(),
                    request.getToCreatedDate()));
        }

        if (ObjectUtils.isNotEmpty(request.getFromDateOfBirth()) || ObjectUtils.isNotEmpty(request.getToDateOfBirth())) {
            queryFilters.add(ElasticsearchUtils.buildFilterQueryWithRangeDate("dateOfBirth", request.getFromDateOfBirth(),
                    request.getToDateOfBirth()));
        }

        queryBuilder.withQuery(boolQuery);
        queryBuilder.withFilter(ElasticsearchUtils.combineFilters(queryFilters));

        logger.info("Executing QueryUserFunc filter {}", queryBuilder.getFilter());
        logger.info("Executing QueryUserFunc query {}", queryBuilder.getQuery());

        return elasticsearchRepositoryUtil.searchPage(queryBuilder.build(), UserView.class, UserResponse.class);
    }
}
