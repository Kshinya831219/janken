package oit.is.z2640.kaizi.janken.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z2640.kaizi.janken.model.Match;
import oit.is.z2640.kaizi.janken.model.MatchInfo;
import oit.is.z2640.kaizi.janken.model.MatchInfoMapper;
import oit.is.z2640.kaizi.janken.model.MatchMapper;

@Service
public class AsyncKekka {
  boolean enamy = false;
  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchInfoMapper miMapper;

  @Autowired
  MatchMapper mMapper;

  @Transactional
  public MatchInfo syncBuyMatchInfo(MatchInfo matchInfo) {
    MatchInfo existingMatch = miMapper.findActiveMatchesByUserId(matchInfo.getUser1(), matchInfo.getUser2());
    if (existingMatch == null) {
      miMapper.insertMatchInfo(matchInfo);
    } else {
      Match thisMatch = new Match();
      thisMatch.setUser1(matchInfo.getUser1());
      thisMatch.setUser2(existingMatch.getUser1());
      thisMatch.setUser1Hand(matchInfo.getUser1Hand());
      thisMatch.setUser2Hand(existingMatch.getUser1Hand());
      existingMatch.setIsActive(false);
      miMapper.updateMatchInfo(existingMatch);
      thisMatch.setIsActive(true);
      mMapper.insertMatch(thisMatch);
      enamy = true;
    }

    return matchInfo;
  }

  public void syncShowmatch(Match match, MatchInfo matchInfo1, MatchInfo matchInfo2) {
    matchInfo1.setIsActive(false);
    matchInfo2.setIsActive(false);
    match.setIsActive(false);
    miMapper.updateMatchInfo(matchInfo1);
    miMapper.updateMatchInfo(matchInfo2);
    mMapper.insertMatch(match);
  }

  @Async
  public void connect(SseEmitter emitter, int Id) throws IOException {
    logger.info("connect start" + Id);
    enamy = true;
    try {
      while (true) {// 無限ループ
        // DBが更新されていなければ0.5s休み

        Match thisMatch = mMapper.findActiveMatch();

        if (thisMatch == null) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }

        emitter.send(thisMatch);
        logger.info("Match result sent to " + Id);
        TimeUnit.MILLISECONDS.sleep(1000);
        enamy = false;
        mMapper.updateMatchStatus(thisMatch.getId());
      }
    } catch (InterruptedException e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    }
  }
}
