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
import oit.is.z2640.kaizi.janken.model.MatchMapper;
import oit.is.z2640.kaizi.janken.model.User;
import oit.is.z2640.kaizi.janken.model.UserMapper;

@Controller
@RequestMapping("/match")
public class MatchController {

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @GetMapping("/{id}")
  public String match(@PathVariable int id, Principal prin, ModelMap model) {

    String loginUser = prin.getName();

    model.addAttribute("loginUser", loginUser);

    User opponent = userMapper.selectById(id);
    model.addAttribute("opponent", opponent);

    return "match.html";
  }

  @PostMapping("/jankengame")
  public String jankengame(@RequestParam String userhand, ModelMap model) {

    Janken game = new Janken(userhand);

    model.addAttribute("userhand", game.getUserHand());
    model.addAttribute("cpuHand", game.getCpuHand());
    model.addAttribute("result", game.getResult());

    return "match.html";
  }
}
