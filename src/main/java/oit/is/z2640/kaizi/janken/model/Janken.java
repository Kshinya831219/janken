package oit.is.z2640.kaizi.janken.model;

public class Janken {
  String userName;

  public Janken() {
    this.userName = "";
  }

  public Janken(String userName) {
    this.userName = userName;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
