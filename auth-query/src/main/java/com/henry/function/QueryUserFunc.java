package com.henry.function;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.henry.base.func.BaseFunc;
import com.henry.request.user.QueryUserRequest;
import com.henry.response.UserResponse;
import com.henry.util.ElasticsearchRepository;
import com.henry.util.ElasticsearchUtils;
import com.henry.util.PageableUtils;
import com.henry.view.UserView;
import lombok.RequiredArgsConstructor;
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

    private final ElasticsearchRepository elasticsearchRepository;

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

        if (ObjectUtils.isNotEmpty(request.getStatus())) {
            queryFilters.add(ElasticsearchUtils.buildFilterQueryWithValues("status", request.getStatus()));
        }

        queryBuilder.withQuery(boolQuery);
        queryBuilder.withFilter(ElasticsearchUtils.combineFilters(queryFilters));

        logger.info("Executing QueryUserFunc filter {}", queryBuilder.getFilter());
        logger.info("Executing QueryUserFunc query {}", queryBuilder.getQuery());

        return elasticsearchRepository.searchPage(queryBuilder.build(), UserView.class, UserResponse.class);
    }
}
