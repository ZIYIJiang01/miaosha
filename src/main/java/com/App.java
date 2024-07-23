package com;

import com.dao.UserDOMapper;
import com.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;

/**
 * Hello world!
 *
 */
@RestController
@SpringBootApplication(scanBasePackages = {"com"})
@MapperScan("com.dao")
public class App {

    @Autowired
    private UserDOMapper userDOMapper;


    @RequestMapping("/")
    public String home(){
        UserDO userDo= userDOMapper.selectByPrimaryKey(1);
        if(userDo == null){
            return "user does not exist";
        }else{
            return userDo.getName();
        }
    }

    public static void main( String[] args ){ 
        SpringApplication.run(App.class, args);
        System.out.println( "Hello World!" );
    }
}
