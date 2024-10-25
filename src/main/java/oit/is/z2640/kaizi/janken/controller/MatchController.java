package oit.is.z2640.kaizi.janken.controller;

import java.io.IOException;
import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z2640.kaizi.janken.model.Janken;
import oit.is.z2640.kaizi.janken.model.MatchMapper;
import oit.is.z2640.kaizi.janken.model.User;
import oit.is.z2640.kaizi.janken.model.UserMapper;
import oit.is.z2640.kaizi.janken.service.AsyncKekka;
import oit.is.z2640.kaizi.janken.model.Match;
import oit.is.z2640.kaizi.janken.model.MatchInfoMapper;
import oit.is.z2640.kaizi.janken.model.MatchInfo;

@Controller
@RequestMapping("/match")
public class MatchController {

  private final Logger logger = LoggerFactory.getLogger(MatchController.class);
  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  @Autowired
  AsyncKekka kekka;

  @GetMapping("/{id}")
  public String match(@PathVariable int id, Principal prin, ModelMap model) {

    String loginUser = prin.getName();

    model.addAttribute("loginUser", loginUser);

    User opponent = userMapper.selectById(id);
    model.addAttribute("opponent", opponent);

    return "match.html";
  }

  @GetMapping("/fight")
  public String fight(@RequestParam String userhand, @RequestParam int opponentId, Principal prin, ModelMap model) {

    String loginUser = prin.getName();
    User user = userMapper.findByName(loginUser);
    model.addAttribute("loginUser", loginUser);
    MatchInfo matchInfo = new MatchInfo();
    matchInfo.setUser1(user.getId());
    matchInfo.setUser2(opponentId);
    matchInfo.setUser1Hand(userhand);
    matchInfo.setIsActive(true);
    final MatchInfo matchInfo2 = kekka.syncBuyMatchInfo(matchInfo);
    model.addAttribute("matchInfoId", matchInfo2.getId());
    return "wait.html";
  }

  @GetMapping("/fight/step9")
  public SseEmitter sample59(Principal prin) {
    logger.info("接続前");
    final SseEmitter sseEmitter = new SseEmitter();
//Long.MAX_VALUE
    String loginUser = prin.getName();
    try {
      User user = userMapper.findByName(loginUser);
      this.kekka.connect(sseEmitter, user.getId());
    } catch (IOException e) {
      sseEmitter.complete();
    }
    return sseEmitter;
  }
}
