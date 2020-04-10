package com.example.demo;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

@Component
class UserCommandLineRunner implements CommandLineRunner{

	@Override
	public void run(String... args) throws Exception {
		for(User b : this.userRepository.findAll()) {
			System.out.println(b.toString());
		}		
	}
	
	@Autowired UserRepository userRepository;
	

}
interface UserRepository extends JpaRepository<User, Long>{
	
	Collection<User> findByUsername(String userName);
}

@RestController
class UserRestController {
	

	@RequestMapping("/User")
	Collection<User> users (){
		return this.userRepository.findAll();
	}
	
	@Autowired UserRepository userRepository;
}

@Entity
class User{
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String userName;
	private String password;
	private String email;
	
	
	public User(String userName, String password, String email) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
	}


	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public String getUserName() {
		return userName;
	}


	public String getPassword() {
		return password;
	}


	public String getEmail() {
		return email;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", email=" + email + "]";
	}

	
}
