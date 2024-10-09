package oit.is.z2640.kaizi.janken.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2640.kaizi.janken.model.Janken;
import oit.is.z2640.kaizi.janken.model.Entry;

@Controller
@RequestMapping("/janken")
public class JankenController {

  @Autowired
  private Entry room;

  @GetMapping("step1")
  public String janken(ModelMap model, Principal prin) {
    String loginUser = prin.getName();

    model.addAttribute("loginUser", loginUser);
    return "janken.html";
  }

  @GetMapping("/jankengame")
  public String jankengame(@RequestParam String userhand, ModelMap model) {

    Janken game = new Janken(userhand);

    model.addAttribute("userhand", game.getUserHand());
    model.addAttribute("cpuHand", game.getCpuHand());
    model.addAttribute("result", game.getResult());

    return "janken.html";
  }

  @GetMapping("entry")
  public String Entry(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.room.addUser(loginUser);
    model.addAttribute("room", this.room);
    model.addAttribute("usersCount",this.room.count());
    return "janken.html";
  }
}
