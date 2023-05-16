package raf.rs.blog.repo.comment;

import raf.rs.blog.entities.Comment;

import java.util.List;

public interface CommentRepo {
    public Comment addComment(Comment comment);
    public List<Comment> getCommentsByPostId(int id);
}
