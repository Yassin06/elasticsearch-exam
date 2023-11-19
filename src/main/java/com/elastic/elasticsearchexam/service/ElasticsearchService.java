package com.elastic.elasticsearchexam.service;

import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.elastic.elasticsearchexam.model.Exam;

import java.io.IOException;

public interface ElasticsearchService {

    void createIndex(String indexName) throws IOException;
    IndexResponse saveDocument(String indexName, Exam exam) throws IOException;
    Exam getDocument(String indexName, String id) throws IOException;
    void deleteIndex(String indexName) throws IOException;

}
