package bean;


import com.alibaba.fastjson.annotation.JSONField;

public class Posts {

    private long ID;
    private long postAuthor;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private java.sql.Timestamp postDate;
    private String postContent;
    private String postTitle;
    private String postExcerpt;
    private String postStatus;
    private String commentStatus;
    private String postPic;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private java.sql.Timestamp postModified;
    private String postType;
    private long commentCount;

    public Posts(){
        postStatus = "publish";
        commentStatus = "open";
        postPic = "/image/default.png";
        postType = "post";
        commentCount = 0;
    }

    public long getID() {
        return ID;
    }

    public void setID(long id) {
        this.ID = id;
    }


    public long getPostAuthor() {
        return postAuthor;
    }

    public void setPostAuthor(long postAuthor) {
        this.postAuthor = postAuthor;
    }


    public java.sql.Timestamp getPostDate() {
        return postDate;
    }

    public void setPostDate(java.util.Date postDate) {
        this.postDate = new java.sql.Timestamp(postDate.getTime());
    }


    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }


    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }


    public String getPostExcerpt() {
        return postExcerpt;
    }

    public void setPostExcerpt(String postExcerpt) {
        this.postExcerpt = postExcerpt;
    }


    public String getPostStatus() {
        return postStatus;
    }

    public void setPostStatus(String postStatus) {
        this.postStatus = postStatus;
    }


    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }


    public String getPostPic() {
        return postPic;
    }

    public void setPostPic(String postPic) {
        this.postPic = postPic;
    }


    public java.sql.Timestamp getPostModified() {
        return postModified;
    }

    public void setPostModified(java.util.Date postModified) {
        this.postModified = new java.sql.Timestamp(postModified.getTime());
    }


    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }


    public long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    @Override
    public String toString() {
        return "Posts{" +
                "ID=" + ID +
                ", postAuthor=" + postAuthor +
                ", postDate=" + postDate +
                ", postContent='" + postContent + '\'' +
                ", postTitle='" + postTitle + '\'' +
                ", postExcerpt='" + postExcerpt + '\'' +
                ", postStatus='" + postStatus + '\'' +
                ", commentStatus='" + commentStatus + '\'' +
                ", postPic='" + postPic + '\'' +
                ", postModified=" + postModified +
                ", postType='" + postType + '\'' +
                ", commentCount=" + commentCount +
                '}';
    }
}
