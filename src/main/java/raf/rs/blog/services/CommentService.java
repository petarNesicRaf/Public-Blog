package raf.rs.blog.services;

import raf.rs.blog.entities.Comment;
import raf.rs.blog.repo.comment.CommentRepo;

import javax.inject.Inject;
import java.util.List;

public class CommentService {

    @Inject
    private CommentRepo commentRepo;

    public Comment addComment(Comment comment)
    {
        return this.commentRepo.addComment(comment);
    }

    public List<Comment> getCommentsByPostId(Integer id)
    {
        return this.commentRepo.getCommentsByPostId(id);
    }

}
