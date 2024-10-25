package oit.is.z2640.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import oit.is.z2640.kaizi.janken.model.Entry;
import oit.is.z2640.kaizi.janken.model.Match;
import oit.is.z2640.kaizi.janken.model.MatchInfo;
import oit.is.z2640.kaizi.janken.model.MatchInfoMapper;


@Controller
@RequestMapping("/janken")
public class JankenController {

  @Autowired
  Entry room;

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  @GetMapping("step1")
  public String janken(ModelMap model, Principal prin) {
    String loginUser = prin.getName();
    model.addAttribute("loginUser", loginUser);

    ArrayList<User> users = userMapper.AllUsers();
    model.addAttribute("users", users);

    ArrayList<Match> matches = matchMapper.selectAllMatches();
    model.addAttribute("matches", matches);

 ArrayList<MatchInfo> activeMatches = matchInfoMapper.selectAllActiveMatches();
 model.addAttribute("activeMatches", activeMatches);

    return "janken.html";
  }

  @PostMapping("/jankengame")
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
    model.addAttribute("usersCount", this.room.count());
    return "janken.html";
  }

}
