package raf.rs.blog;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import raf.rs.blog.repo.comment.CommentRepo;
import raf.rs.blog.repo.comment.MySqlCommentRepo;
import raf.rs.blog.repo.post.MySqlPostRepo;
import raf.rs.blog.repo.post.PostRepo;
import raf.rs.blog.repo.user.MySqlUserRepo;
import raf.rs.blog.repo.user.UserRepo;
import raf.rs.blog.services.CommentService;
import raf.rs.blog.services.PostService;
import raf.rs.blog.services.UserService;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class Application extends ResourceConfig {
    public Application(){
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlPostRepo.class).to(PostRepo.class).in(Singleton.class);
                this.bindAsContract(PostService.class);

                this.bind(MySqlCommentRepo.class).to(CommentRepo.class).in(Singleton.class);
                this.bindAsContract(CommentService.class);

                this.bind(MySqlUserRepo.class).to(UserRepo.class).in(Singleton.class);
                this.bindAsContract(UserService.class);
            }
        };

        register(binder);

        packages("raf.rs.blog");
    }
}
