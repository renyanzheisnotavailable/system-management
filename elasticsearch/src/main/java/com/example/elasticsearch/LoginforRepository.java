package com.example.elasticsearch;

import com.example.api.domain.Logininfor;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;


@Component
public interface LoginforRepository extends ElasticsearchRepository<Logininfor, Long> {

}
