package com.icesi.edu.taller.app;



import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.icesi.edu.taller.model.TsscAdmin;
import com.icesi.edu.taller.model.TsscGame;
import com.icesi.edu.taller.model.TsscStory;
import com.icesi.edu.taller.model.TsscTimecontrol;
import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.repository.AdminRepository;
import com.icesi.edu.taller.service.AdminService;
import com.icesi.edu.taller.service.GameService;
import com.icesi.edu.taller.service.StoryService;
import com.icesi.edu.taller.service.TopicService;

@SpringBootApplication
@EnableJpaRepositories("com.icesi.edu.taller.repository")
@EntityScan("com.icesi.edu.taller.model")
@ComponentScan({"com.icesi.edu.taller.service", "com.icesi.edu.taller.security", "com.icesi.edu.taller.controller"})
public class Taller2Application {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext c = SpringApplication.run(Taller2Application.class, args);
//		for (String string : c.getBeanDefinitionNames()) {
//		System.out.println(string);
//	}
		
		AdminService admServ = (AdminService) c.getBean("admServ");
		GameService gameServ = (GameService) c.getBean("gameServ");;
		StoryService stoServ = (StoryService) c.getBean("stoServ");;
		TopicService topServ = (TopicService) c.getBean("topServ");;
		
		//Admin
		TsscAdmin admin = new TsscAdmin();
		admin.setId(1); //
		admin.setSuperAdmin("NO");
		admin.setUser("el admin");
		admin.setPassword("{noop}eladmin1");
		admServ.save(admin).toString();
		//SuperAdmin
		TsscAdmin superadmin = new TsscAdmin();
		superadmin.setId(2);
		superadmin.setSuperAdmin("YES");
		superadmin.setUser("superadmin");
		superadmin.setPassword("{noop}superadmin1");
		admServ.save(superadmin).toString();
		//Topic
		TsscTopic topic= new TsscTopic();
		topic.setId(1);
		topic.setName("201-MGP-Full");
		topic.setDescription("Completa-PI2-20-1");
		topic.setDefaultGroups(4);
		topic.setDefaultSprints(4);
		topic.setGroupPrefix("100-Groups");
		//Game
		TsscGame game = new TsscGame();
		game.setName("residentEvil");
		game.setId(1);
		game.setNGroups(4);
		game.setNSprints(4);
		game.setAdminPassword("ferxxo");
		game.setGuestPassword("ferxxo");
		game.setScheduledTime(LocalTime.MIDNIGHT);
		game.setScheduledDate(LocalDate.of(2020, 10, 22));
		game.setStartTime(LocalTime.MIDNIGHT);
		game.setTsscTopic(topic);
		ArrayList<TsscGame> games = new ArrayList<>();
		games.add(game);
		//Story
		TsscStory story = new TsscStory();
		story.setId(1);
		story.setShortDescription("survival horror");
		story.setPriority(BigDecimal.TEN);
		story.setBusinessValue(BigDecimal.ONE);
		story.setInitialSprint(BigDecimal.ONE);
		story.setTsscGame(game);
		story.setTsscTopic(topic);
		ArrayList<TsscStory> stories = new ArrayList<>();
		stories.add(story);
		//TimeControl
		TsscTimecontrol timeControl = new TsscTimecontrol();
		timeControl.setId(1);
		timeControl.setIntervalRunning(BigDecimal.valueOf(10000));
		timeControl.setName("time1");
		ArrayList<TsscTimecontrol> timeControls = new ArrayList<>();
		timeControls.add(timeControl);
		//Saves
		topServ.save(topic);
		game.setTsscTopic(topic);
		gameServ.save(game);
		story.setTsscGame(game);
		stoServ.save(story);
	
	
	}

	

	
//
	

}

	

