package raf.rs.blog.services;

import raf.rs.blog.entities.Post;
import raf.rs.blog.repo.post.PostRepo;

import javax.inject.Inject;
import java.util.List;

public class PostService {

    @Inject
    private PostRepo postRepo;

    public Post addPost(Post post){
        return this.postRepo.addPost(post);
    }

    public List<Post> allPosts(){
        return  this.postRepo.allPosts();
    }

    public Post findPost(Integer id){
        return this.postRepo.findPostById(id);
    }

}
