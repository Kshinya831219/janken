package oit.is.z2640.kaizi.janken.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import oit.is.z2640.kaizi.janken.model.MatchInfo;

@Mapper
public interface MatchInfoMapper {

  @Insert("INSERT INTO matchInfo (user1, user2, user1Hand, isActive) VALUES (#{user1}, #{user2}, #{user1Hand}, #{isActive});")
  @Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
  void insertMatchInfo(MatchInfo matchInfo);

  @Select("SELECT * FROM matchInfo WHERE isActive = true")
  ArrayList<MatchInfo> selectAllActiveMatches();

  @Select("SELECT * FROM matchInfo WHERE (isActive = true AND user2 = #{userId} AND user1 =#{user2Id})")
  MatchInfo findActiveMatchesByUserId(int userId, int user2Id);

  @Select("SELECT * FROM matchInfo WHERE (isActive = true AND user1 =#{userId})")
  MatchInfo findActiveMatchesByUserId2(int userId);

  @Select("SELECT * FROM matchInfo WHERE id=#{Id}")
  MatchInfo findMatchInfoByUserId(int Id);

  @Update("UPDATE matchInfo SET isActive = #{isActive} WHERE id = #{id}")
  void updateMatchInfo(MatchInfo matchInfo);

}
