package bean;


import com.alibaba.fastjson.annotation.JSONField;

import java.sql.Timestamp;

public class Users {

  private long ID;
  private String userLogin;
  private String userPass;
  private String userNicename;
  private String userEmail;
  @JSONField(format="yyyy-MM-dd HH:mm:ss")
  private java.sql.Timestamp userRegistered;
  private long userStatus;
  private String displayName;
  private String pic;



  public Users(){
    userEmail = "example@email.com";
    displayName = "";
  }

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public long getId() {
    return ID;
  }

  public void setId(long id) {
    this.ID = id;
  }


  public String getUserLogin() {
    return userLogin;
  }

  public void setUserLogin(String userLogin) {
    this.userLogin = userLogin;
  }


  public String getUserPass() {
    return userPass;
  }

  public void setUserPass(String userPass) {
    this.userPass = userPass;
  }


  public String getUserNicename() {
    return userNicename;
  }

  public void setUserNicename(String userNicename) {
    this.userNicename = userNicename;
  }


  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }


  public java.sql.Timestamp getUserRegistered() {
    return userRegistered;
  }


  public void setUserRegistered(java.util.Date userRegistered) {
    this.userRegistered = new Timestamp(userRegistered.getTime());
  }


  public long getUserStatus() {
    return userStatus;
  }

  public void setUserStatus(long userStatus) {
    this.userStatus = userStatus;
  }


  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return "Users{" +
            "ID=" + ID +
            ", userLogin='" + userLogin + '\'' +
            ", userPass='" + userPass + '\'' +
            ", userNicename='" + userNicename + '\'' +
            ", userEmail='" + userEmail + '\'' +
            ", userRegistered=" + userRegistered +
            ", userStatus=" + userStatus +
            ", displayName='" + displayName + '\'' +
            '}';
  }
}
