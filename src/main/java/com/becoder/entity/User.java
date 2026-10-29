package com.becoder.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	private String mobNo;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Role> role;
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="status_id")
	private AccountStatus accountStatus;
	
	//cascade = ALL	What happens to the status when I save or delete the user? It follows the user

	
	//fetch = FetchType.EAGER
    //Becoder’s point: when we load a user, the account status should come along.
	
	//EAGER: the status is loaded together with the user, in the same query (a join).
	//LAZY: the status is loaded only when you call getAccountStatus(), and fails with LazyInitializationException
	//if the session is closed.

	//For @OneToOne the default is already EAGER, so fetch = FetchType.EAGER is written only to make it clear.
	
	
	
	
	
	
	
	//=========================================================================================================
	//First, understand a foreign key
	
	//       account_status                 user
	//       ---------------                ----------------------------
	//       id | is_active                 id | email    | status_id
	//       10 | true                      1  | a@g.com  | 10
	//       11 | false                     2  | b@g.com  | 11
	
	//user.status_id stores the id of a row in account_status. This column is the foreign key. 
	//It is how the database knows which status belongs to which user.

	//In Java you do not write Integer statusId. You write the whole object:	
	
	//==========================================================================================================
	//@JoinColumn(name = "status_id")

    //It tells Hibernate: “the foreign key column in the user table is called status_id.”
	
	//Q1. What is @JoinColumn?
	//It specifies the foreign key column used to join two entity tables, with its name and constraints.
	
	//Q3. Which side holds the foreign key?
	//	The owning side, where @JoinColumn is written. In @ManyToOne / @OneToMany it is always the “many” side.
	
	//Q4. Difference between @JoinColumn and @JoinTable?
	//@JoinColumn stores the foreign key as a column in one of the two tables. @JoinTable uses a separate middle
	//table, used mainly for @ManyToMany.
}
