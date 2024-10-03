package oit.is.z2640.kaizi.janken.model;

import java.util.Random;

public class Janken {

  String userHand;
  String cpuHand;
  String result;
  static final String[] HANDS = { "グー", "チョキ", "パー" };

  public Janken(String user) {
    this.userHand = user;
    this.cpuHand = generateCpuHand();
    this.result = determineResult();
  }

  private String generateCpuHand() {
    Random random = new Random();
    return HANDS[random.nextInt(HANDS.length)];
  }

  private String determineResult() {
    if (userHand.equals(cpuHand)) {
      return "引き分け";
    } else if ((userHand.equals("グー") && cpuHand.equals("チョキ")) ||
        (userHand.equals("チョキ") && cpuHand.equals("パー")) ||
        (userHand.equals("パー") && cpuHand.equals("グー"))) {
      return "あなたの勝ち";
    } else {
      return "あなたの負け";
    }
  }

  public String getUserHand() {
    return userHand;
  }

  public String getCpuHand() {
    return cpuHand;
  }

  public String getResult() {
    return result;
  }
}
