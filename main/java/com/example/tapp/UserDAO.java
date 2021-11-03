package com.example.tapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Data Access Object
 */
@Dao
public interface UserDAO {

    @Insert // 삽입
    void setInsertUser(User user);

    @Update // 수정
    void setUpdateUser(User user);

    @Delete  // 삭제
    void setDeleteUser(User user);

    //조회쿼리
    @Query("SELECT * FROM User") // 쿼리 : 데이터베이스에 요청하는 명령문
    List<User> getUserAll();

   /* @Query("UPDATE User SET order_temperature1 = :temperature1 WHERE order_id = :id")
    void update_t1(String temperature1, int id);

    @Query("UPDATE User SET order_temperature2 = :temperature2 WHERE order_id = :id")
    void update_t2(String temperature2, int id);

    @Query("UPDATE User SET order_temperature3 = :temperature3 WHERE order_id = :id")
    void update_t3(String temperature3, int id);

    @Query("UPDATE User SET order_temperature4 = :temperature4 WHERE order_id = :id")
    void update_t4(String temperature4, int id);

    @Query("UPDATE User SET order_count = :count WHERE order_id = :id")
    void update_c(String count, int id);*/

    /*@Query("UPDATE User SET count = :count, price = :price WHERE order_id =:id")
    void update(Float amount, Float price, int id);
*/
  /*  @Query("SELECT * FROM User WHERE name")
    User findByName(String name);*/



}
