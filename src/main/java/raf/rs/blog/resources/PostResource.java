package raf.rs.blog.resources;

import raf.rs.blog.entities.Post;
import raf.rs.blog.services.PostService;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/posts")
public class PostResource {
    @Inject
    private PostService postService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Post> allPosts(){
        return this.postService.allPosts();
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Post create(@Valid Post post)
    {
        return this.postService.addPost(post);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Post find(@PathParam("id") Integer id)
    {
        return  this.postService.findPost(id);
    }
}
