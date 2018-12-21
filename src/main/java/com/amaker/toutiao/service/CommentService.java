package com.amaker.toutiao.service;

import com.amaker.toutiao.dao.CommentDao;
import com.amaker.toutiao.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: toutiao
 * @Date: 2018/12/19 0019 21:00
 * @Author: GHH
 * @Description:
 */
@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    public void addComment(Comment comment){
        commentDao.addComment(comment);
    }

    public List<Comment> selectComment(int entityId,int entityType){
        return commentDao.selectCommentByIdAndType(entityId,entityType);
    }


    public int commentCount(int entityId, int entityType){
        return commentDao.commentCount(entityId,entityType);
    }

    public void deleteComment(int status,int id){
        commentDao.updateStatus(status,id);
    }
}
