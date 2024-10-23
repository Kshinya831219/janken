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
import oit.is.z2640.kaizi.janken.model.MatchInfoMapper;
import oit.is.z2640.kaizi.janken.model.MatchInfo;

@Controller
@RequestMapping("/match")
public class MatchController {

  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

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

    String loginUser = prin.getName();
    User user = userMapper.findByName(loginUser);
  model.addAttribute("loginUser", loginUser);
    MatchInfo matchInfo = new MatchInfo();
    matchInfo.setUser1(user.getId());
    matchInfo.setUser2(opponentId);
    matchInfo.setUser1Hand(userhand);
    matchInfo.setIsActive(true);

    matchInfoMapper.insertMatchInfo(matchInfo);

    model.addAttribute("matchInfo", matchInfo);

    return "wait.html";
  }
}
