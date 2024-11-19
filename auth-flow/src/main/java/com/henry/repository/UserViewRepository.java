package com.henry.repository;

import com.henry.view.UserView;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserViewRepository extends ElasticsearchRepository<UserView, String> {
}
