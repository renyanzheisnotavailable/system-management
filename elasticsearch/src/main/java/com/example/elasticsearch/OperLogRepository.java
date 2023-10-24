package com.example.elasticsearch;


import com.example.api.domain.OperLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;

@Service
public interface OperLogRepository extends ElasticsearchRepository<OperLog, Long> {

}
