package raf.rs.blog.repo.post;

import raf.rs.blog.entities.Post;

import java.util.List;

public interface PostRepo {

    public Post addPost(Post post);
    public List<Post> allPosts();
    public Post findPostById(int id);
}
