package com.elastic.elasticsearchexam.controller;

import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.elastic.elasticsearchexam.model.ResponseModel;
import com.elastic.elasticsearchexam.model.Exam;
import com.elastic.elasticsearchexam.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
public class ElasticsearchController {

    @Autowired
    ElasticsearchService elasticsearchService;

    @PostMapping("/new/{indexName}")
    public ResponseEntity<ResponseModel> newIndex(@PathVariable String indexName){
        try {
            elasticsearchService.createIndex(indexName);
         return   ResponseEntity.status(200).body(new ResponseModel("index created "));
        } catch (IOException e) {
            return    ResponseEntity.status(500).body( new ResponseModel(" Error while creqting the index: "+indexName));
        }
    }

    @PostMapping("/delete/{indexName}")
    public ResponseEntity<ResponseModel> deleteIndex(@PathVariable String indexName){
        try {
            elasticsearchService.deleteIndex(indexName);
            return   ResponseEntity.status(200).body(new ResponseModel("index delete "));
        } catch (IOException e) {
            return    ResponseEntity.status(500).body( new ResponseModel(" Error while deleting the index: "+indexName));
        }
    }

    @PostMapping("/newDocument/{indexName}")
    public ResponseEntity<ResponseModel>  newDocument(@PathVariable String indexName, @RequestBody Exam exam){
        IndexResponse res;
        try {
              res= elasticsearchService.saveDocument(indexName,exam);
            if (res.version()!=1){
                return   ResponseEntity.status(200).body(new ResponseModel("Document updated successfully"));
            }
            return   ResponseEntity.status(200).body(new ResponseModel("Document successfully created") );
        } catch (IOException e) {
            return    ResponseEntity.status(500).body(new ResponseModel(" ResponseModel while creating the document"));
        }
    }

    @GetMapping("/document/{indexName}")
    public ResponseEntity getDocument(@PathVariable String indexName, @RequestParam  String examId){
        Exam exam;
        try {
            exam= elasticsearchService.getDocument(indexName, examId);
            if(exam== null){
                return    ResponseEntity.status(404).body(new ResponseModel("Exam does not exist"));
            }
            return   ResponseEntity.status(200).body(exam);
        } catch (IOException e) {
            return    ResponseEntity.status(500).body(new ResponseModel("Error while creating the document"));
        }
    }

}
