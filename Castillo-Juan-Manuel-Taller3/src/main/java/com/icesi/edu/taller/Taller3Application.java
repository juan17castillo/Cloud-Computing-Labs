package com.icesi.edu.taller;



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

import com.icesi.edu.taller.repository.GameDAO;
import com.icesi.edu.taller.model.TsscAdmin;
import com.icesi.edu.taller.model.TsscGame;
import com.icesi.edu.taller.model.TsscStory;
import com.icesi.edu.taller.model.TsscTimecontrol;
import com.icesi.edu.taller.model.TsscTopic;
import com.icesi.edu.taller.service.AdminService;
import com.icesi.edu.taller.service.GameService;
import com.icesi.edu.taller.service.StoryService;
import com.icesi.edu.taller.service.TopicService;

@SpringBootApplication
@EnableJpaRepositories("com.icesi.edu.taller.repository")
@EntityScan("com.icesi.edu.taller.model")
@ComponentScan({"com.icesi.edu.taller"})
public class Taller3Application {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext c = SpringApplication.run(Taller3Application.class, args);}
//		for (String string : c.getBeanDefinitionNames()) {
//		System.out.println(string);
//	}
		
//		AdminService admServ = (AdminService) c.getBean("admServ");
//		GameService gameServ = (GameService) c.getBean("gameServ");;
//		StoryService stoServ = (StoryService) c.getBean("stoServ");;
//		TopicService topServ = (TopicService) c.getBean("topServ");;
		
	@Bean
	public CommandLineRunner runner(AdminService admServ, TopicService topServ, GameService gameServ,StoryService stoServ, GameDAO gameDAO) {
			
		return args -> {	
		
		
		
		//Admin
		TsscAdmin admin = new TsscAdmin();
		admin.setSuperAdmin("NO");
		admin.setUser("eladmin");
		admin.setPassword("{noop}eladmin1");
		admServ.save(admin);
		//SuperAdmin
		TsscAdmin superadmin = new TsscAdmin();
		superadmin.setSuperAdmin("YES");
		superadmin.setUser("superadmin");
		superadmin.setPassword("{noop}superadmin1");
		admServ.save(superadmin);
		//Topic
		TsscTopic topic= new TsscTopic();
		topic.setName("201-MGP-Full");
		topic.setDescription("Completa-PI2-20-1");
		topic.setDefaultGroups(4);
		topic.setDefaultSprints(4);
		topic.setGroupPrefix("100-Groups");
		//Game
		TsscGame game = new TsscGame();
		game.setName("residentEvil");
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
		story.setShortDescription("survival horror");
		story.setPriority(BigDecimal.TEN);
		story.setBusinessValue(BigDecimal.ONE);
		story.setInitialSprint(BigDecimal.ONE);
		story.setTsscTopic(topic);
		//TimeControl
//		TsscTimecontrol timeControl = new TsscTimecontrol();
//		timeControl.setId(1);
//		timeControl.setIntervalRunning(BigDecimal.valueOf(10000));
//		timeControl.setName("time1");
//		ArrayList<TsscTimecontrol> timeControls = new ArrayList<>();
//		timeControls.add(timeControl);
	

		//Saves
		topServ.save(topic);
		game.setTsscTopic(topic);
		gameServ.save(game);
		story.setTsscGame(game);
		story.setTsscTopic(topic);
		stoServ.save(story);
		
	
	
	};

	

	
//
	
	
	}
}

	

