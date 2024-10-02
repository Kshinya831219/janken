package oit.is.z2640.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class JankenController {

  @GetMapping("/janken")
  public String janken(@RequestParam(name = "name", required = false) String userName, ModelMap model) {

    model.addAttribute("name", userName);
    return "janken.html";
  }

  @GetMapping("/jankengame")
  public String jankengame(@RequestParam String userhand, ModelMap model) {
    String cpuHand = "グー";
    String result;

    if (userhand.equals(cpuHand)) {
      result = "引き分け";
    } else if ((userhand.equals("グー") && cpuHand.equals("チョキ")) ||
        (userhand.equals("チョキ") && cpuHand.equals("パー")) ||
        (userhand.equals("パー") && cpuHand.equals("グー"))) {
      result = "あなたの勝ち";
    } else {
      result = "あなたの負け";
    }

    model.addAttribute("userhand", userhand);
    model.addAttribute("cpuHand", cpuHand);
    model.addAttribute("result", result);

    return "janken.html";
  }

}
