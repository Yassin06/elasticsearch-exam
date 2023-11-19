package com.elastic.elasticsearchexam.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetRequest;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.indices.*;
import com.elastic.elasticsearchexam.model.Exam;
import com.elastic.elasticsearchexam.service.ElasticClient;
import com.elastic.elasticsearchexam.service.ElasticsearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Log4j2
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Autowired
    ElasticClient elasticClient;

    ElasticsearchIndicesClient indices;
    ElasticsearchClient client;

    @Override
    public void createIndex(String indexName) throws IOException {
        client=elasticClient.getElasticsearchClient();
        CreateIndexRequest.Builder createIndexBuilder = new CreateIndexRequest.Builder();
        createIndexBuilder.index(indexName);
        CreateIndexRequest createIndexRequest = createIndexBuilder.build();

        indices = client.indices();

        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest);


    }

    @Override
        public IndexResponse saveDocument(String indexName, Exam exam) throws IOException {
        client=elasticClient.getElasticsearchClient();
        IndexRequest.Builder<Exam> indexReqBuilder = new IndexRequest.Builder<>();

        indexReqBuilder.index(indexName);
        indexReqBuilder.id(exam.getId());
        indexReqBuilder.document(exam);
        IndexRequest<Exam> indexRequest = indexReqBuilder.build();

        IndexResponse response = client.index(indexRequest);

        return response;
    }

    @Override
    public  Exam getDocument(String indexName,String id) throws IOException {
        Exam exam=null;
        client=elasticClient.getElasticsearchClient();

        GetRequest.Builder getRequestBuilder = new GetRequest.Builder();
        getRequestBuilder.index(indexName);
        getRequestBuilder.id(id);

        GetRequest getRequest = getRequestBuilder.build();
        GetResponse<Exam> getResponse = client.get(getRequest, Exam.class);;
        exam = getResponse.source();

        return exam;
    }

    @Override
    public void deleteIndex(String indexName) throws IOException {
        client=elasticClient.getElasticsearchClient();
        DeleteIndexRequest.Builder delete=new DeleteIndexRequest.Builder();
        delete.index(indexName);
        DeleteIndexRequest deleteIndexResponse= delete.build();
        indices = client.indices();

            DeleteIndexResponse deleteIndexResponse1=indices.delete(deleteIndexResponse);

    }


}
