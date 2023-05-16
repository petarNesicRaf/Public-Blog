<%--
  Created by IntelliJ IDEA.
  User: T450s
  Date: 5/15/2023
  Time: 5:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css" integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn" crossorigin="anonymous">

</head>
<body>
<div id="posts" class="container">
    <h1>
        Public blog
    </h1>

    <ul id="postList" class="list-group">

    </ul>

    <button class="btn btn-primary" id="new-post-button">New Post</button>
</div>
<div class="container d-none" id="new-post">
    <div class="form-group">
        <label for="author">Author</label>
        <input type="text" class="form-control" id="author"/>
    </div>
    <div class="form-group">
        <label for="title">Title</label>
        <input type="text" class="form-control" id="title"/>
    </div>
    <div class="form-group">
        <label for="content">Content</label>
        <textarea  class="form-control" id="content" rows="3"></textarea>
    </div>
    <button id="save-post" class="btn btn-primary">Save post</button>
</div>
<div class="container d-none" id="post">
    <h2 id="postID"></h2>
    <p id="postDate"></p>
    <p id="authorP"></p>
    <p id="contentField"></p>
    <br>
    <h3> Comments </h3>
    <ul id="commentList">

    </ul>
    <div class="form-group">
        <label for="name">Name:</label>
        <input type="text" class="form-control" id="name"/>
    </div>
    <div class="form-group">
        <label for="comment">Comment</label>
        <textarea type="text" class="form-control" id="comment"></textarea>
    </div>
    <button class="btn btn-primary" id="commentBtn">comment</button>
</div>
<script>
    console.log("test1");
    const getPosts = () => {
        document.getElementById('postList').innerHTML = "";
        fetch('/blog_war_exploded/api/posts', {
            method: 'GET'
        }).then(response => {
            return response.json();
        }).then(posts => {
            for (const post of posts) {
                addSubjectElements(post)
            }
        })
    }

    const addSubjectElements = (post) => {
        const postsList = document.getElementById('postList');

        const li = document.createElement('li');
        li.className = "list-group-item"
        const postLink = document.createElement('h3');
        postLink.className = "h3"
        postLink.innerText = post.title;

        const h2author = document.createElement('p');
        h2author.innerText = "author: " + post.author;
        h2author.className = "small"
        const content = document.createElement('p');
        content.innerText = post.content

        const button = document.createElement('button');
        button.className = "btn btn-primary"
        button.innerText = "read more"
        button.addEventListener('click', () => getPostById(post.id))

        li.appendChild(postLink);
        li.appendChild(content);
        li.appendChild(h2author);
        li.appendChild(button);

        postsList.appendChild(li);
    }

    const getPostById = (id) => {
        console.log("klik")
        document.getElementById("posts").className = "d-none";
        document.getElementById("post").className = "container d-block";

        fetch('/blog_war_exploded/api/posts/' + id, {
            method: 'GET'
        }).then(response => {
            return response.json();
        }).then(post => {
            document.getElementById("postID").innerText = post.title
            document.getElementById("postDate").innerText = post.uploadDate
            document.getElementById("authorP").innerText = post.author
            document.getElementById("contentField").innerText = post.content
            document.getElementById("commentBtn").addEventListener("click", () => {
                const name = document.getElementById("name").value
                const comment = document.getElementById("comment").value
                createComment(name, comment, id);
            })

            getCommentsByPostId(id);
        })
    }

    const createComment = (name, data, postId) => {
        fetch('/blog_war_exploded/api/comments', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                name: name,
                comment: data,
                postId: postId,
            })
        }).then(()=>{
            document.getElementById("name").value = ""
            document.getElementById("comment").value = ""
            getCommentsByPostId(postId);
        })
    }
    const getCommentsByPostId = (id) => {
        const list = document.getElementById("commentList")
        list.innerHTML = "";
        fetch('/blog_war_exploded/api/comments/' + id, {
            method: 'GET'
        }).then(response => {
            return response.json();
        }).then(comments => {
            for (const comment of comments) {
                const name = document.createElement("h6");
                name.innerText = comment.name
                const commentText = document.createElement("p");
                commentText.innerText = comment.comment;
                list.appendChild(name)
                list.appendChild(commentText)
            }

        })
    }
    console.log("test")
    getPosts()
    document.getElementById("save-post").addEventListener("click", () => {
        console.log("kliknut")
        const author = document.getElementById("author");
        const authorText = author.value;

        const title = document.getElementById("title");
        const titleText = title.value;

        const content = document.getElementById("content");
        const contentText = content.value;
        console.log(title, author, content)
        console.log(authorText, "   ", titleText, "      ", contentText)
        fetch('/blog_war_exploded/api/posts', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                author: authorText,
                title: titleText,
                content: contentText,
            })
        })
            .then(response => {
                getPosts()
                author.value = "";
                content.value = "";
                title.value = "";
                document.getElementById("posts").className = "container d-block";
                document.getElementById("new-post").className = "d-none";
            })
    })
    document.getElementById("new-post-button").addEventListener("click", () => {
        document.getElementById("posts").className = "d-none";
        document.getElementById("new-post").className = "container d-block";
    })
</script>

</body>
</html>
