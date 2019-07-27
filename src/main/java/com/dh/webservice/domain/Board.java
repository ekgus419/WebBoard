/**
 * @author cdh
 * @since 2019-07-01
 * @copyright  Copyright dh-0419(https://github.com/ekgus419/WebBoard)
 *
 */
package com.dh.webservice.domain;


import com.dh.webservice.config.WebBaseTimeConfig;
import lombok.Data;

import javax.persistence.*;

/**
 * @title Board Entity를 정의한다.
 * @author cdh
 * @FileName Board
 *
 */
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
    private int groupSeq;

    @Column(name = "PARENT_NO", insertable = true, updatable = false)
    private int parentNo;

    @Column(name = "DEPTH", insertable = true, updatable = false)
    private int depth;


}