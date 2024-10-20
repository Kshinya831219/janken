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
import oit.is.z2640.kaizi.janken.model.Match;

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

  @GetMapping("/fight")
  public String fight(@RequestParam String userhand, @RequestParam int opponentId, ModelMap model, Principal prin) {

    Janken game = new Janken(userhand);
    model.addAttribute("userhand", game.getUserHand());
    model.addAttribute("cpuHand", game.getCpuHand());
    model.addAttribute("result", game.getResult());
    String loginUser = prin.getName();
    User user = userMapper.findByName(loginUser);

    Match match = new Match();

    match.setUser1(user.getId());
    match.setUser2(opponentId);
    match.setUser1Hand(userhand);
    match.setUser2Hand(game.getCpuHand());
    matchMapper.insertMatch(match);
    User opponent = userMapper.selectById(opponentId);
    model.addAttribute("opponent", opponent);
    model.addAttribute("loginUser", loginUser);

    return "match.html";
  }
}
