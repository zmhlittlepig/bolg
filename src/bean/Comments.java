package bean;


import com.alibaba.fastjson.annotation.JSONField;

public class Comments {

  private long commentId;
  private long commentPostId;
  private String commentAuthor;
  private String commentAuthorEmail;
  @JSONField(format="yyyy-MM-dd HH:mm:ss")
  private java.sql.Timestamp commentDate;
  private String commentContent;
  private String commentApproved;
  private String commentType;
  private long commentParent;
  private long userId;
  public Comments(){
      commentType="";
  }

  public long getCommentId() {
    return commentId;
  }

  public void setCommentId(long commentId) {
    this.commentId = commentId;
  }


  public long getCommentPostId() {
    return commentPostId;
  }

  public void setCommentPostId(long commentPostId) {
    this.commentPostId = commentPostId;
  }


  public String getCommentAuthor() {
    return commentAuthor;
  }

  public void setCommentAuthor(String commentAuthor) {
    this.commentAuthor = commentAuthor;
  }


  public String getCommentAuthorEmail() {
    return commentAuthorEmail;
  }

  public void setCommentAuthorEmail(String commentAuthorEmail) {
    this.commentAuthorEmail = commentAuthorEmail;
  }


  public java.sql.Timestamp getCommentDate() {
    return commentDate;
  }

  public void setCommentDate(java.util.Date commentDate) {
    this.commentDate = new java.sql.Timestamp(commentDate.getTime());
  }


  public String getCommentContent() {
    return commentContent;
  }

  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
  }


  public String getCommentApproved() {
    return commentApproved;
  }

  public void setCommentApproved(String commentApproved) {
    this.commentApproved = commentApproved;
  }


  public String getCommentType() {
    return commentType;
  }

  public void setCommentType(String commentType) {
    this.commentType = commentType;
  }


  public long getCommentParent() {
    return commentParent;
  }

  public void setCommentParent(long commentParent) {
    this.commentParent = commentParent;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "Comments{" +
            "commentId=" + commentId +
            ", commentPostId=" + commentPostId +
            ", commentAuthor='" + commentAuthor + '\'' +
            ", commentAuthorEmail='" + commentAuthorEmail + '\'' +
            ", commentDate=" + commentDate +
            ", commentContent='" + commentContent + '\'' +
            ", commentApproved='" + commentApproved + '\'' +
            ", commentType='" + commentType + '\'' +
            ", commentParent=" + commentParent +
            ", userId=" + userId +
            '}';
  }
}
