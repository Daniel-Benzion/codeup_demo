package com.codeup.codeup_demo.models;

import javax.persistence.*;

@Entity
@Table(name="posts")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "title", nullable = false, length = 100)
	private String title;

	@Column(name = "body", nullable = false, columnDefinition = "TEXT")
	private String body;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User owner;

	public Post(){};

	public Post(String title, String body, User owner) {
		this.title = title;
		this.body = body;
		this.owner = owner;
	}

	public Post(long id, String title, String body, User owner) {
		this.id = id;
		this.title = title;
		this.body = body;
		this.owner = owner;
	}

	public String getBody() {
		return body;
	}

	public String getTitle() {
		return title;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
}
