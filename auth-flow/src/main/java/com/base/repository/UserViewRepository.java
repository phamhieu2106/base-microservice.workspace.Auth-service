package com.base.repository;

import com.base.view.UserView;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserViewRepository extends ElasticsearchRepository<UserView, String> {
}
