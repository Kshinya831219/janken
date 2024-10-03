package oit.is.z2640.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2640.kaizi.janken.model.Janken;

@Controller
public class JankenController {

  @GetMapping("/janken")
  public String janken(@RequestParam(name = "name", required = false) String userName, ModelMap model) {

    model.addAttribute("name", userName);
    return "janken.html";
  }

  @GetMapping("/jankengame")
  public String jankengame(@RequestParam String userhand, ModelMap model) {
    Janken game = new Janken(userhand);
    model.addAttribute("hand",game);
    model.addAttribute("userhand", game.getUserHand());
    model.addAttribute("cpuHand", game.getCpuHand());
    model.addAttribute("result", game.getResult());

    return "janken.html";
  }

}
