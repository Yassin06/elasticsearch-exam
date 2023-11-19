package com.elastic.elasticsearchexam.model;

import lombok.Data;

@Data
public class Exam {
    private String id;
    private String examName;
    private String examinee;
    private String examineeMail;

    public Exam() {
    }

    public Exam(String id, String examName, String examinee, String examineeMail) {
        this.id = id;
        this.examName = examName;
        this.examinee = examinee;
        this.examineeMail = examineeMail;
    }
}
