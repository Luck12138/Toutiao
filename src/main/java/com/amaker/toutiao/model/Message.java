package com.amaker.toutiao.model;

import java.util.Date;

/**
 * @program: toutiao
 * @Date: 2018/12/20 0020 21:02
 * @Author: GHH
 * @Description:
 */
public class Message {
//     `id` INT NOT NULL AUTO_INCREMENT,
//  `from_id` INT NULL,
//  `to_id` INT NULL,
//  `content` TEXT NULL,
//  `created_date` DATETIME NULL,
//  `has_read` INT NULL,
//  `conversation_id` VARCHAR(45) NOT NULL,

    private int id;
    private int fromId;
    private int toId;
    private String content;
    private Date createdDate;
    private int hasRead;
    private String conversationId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromId() {
        return fromId;
    }

    public void setFromId(int fromId) {
        this.fromId = fromId;
    }

    public int getToId() {
        return toId;
    }

    public void setToId(int toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getHasRead() {
        return hasRead;
    }

    public void setHasRead(int hasRead) {
        this.hasRead = hasRead;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }
}
