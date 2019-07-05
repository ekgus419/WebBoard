package com.dh.webservice.domain;


import com.dh.webservice.config.WebBaseTimeConfig;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "BOARD")
@Data
public class Board extends WebBaseTimeConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "BNO")
    private int bNo;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "WRITER", updatable = false)
    private String writer;

    @Column(name = "DEL_FLAG")
    private String delFlag;

    @Column(name = "HIT", columnDefinition = "int default 0")
    private int hit;

    @Column(name = "REPLY_COUNT", columnDefinition = "int default 0")
    private int replyCount;

    @Column(name = "GROUP_NO")
    private int groupNo;

    @Column(name = "GROUP_SEQ")
    private double groupSeq;

    @Column(name = "PARENT_NO", insertable = true, updatable = false)
    private int parentNo;

    @Column(name = "DEPTH", insertable = true, updatable = false)
    private int depth;


}