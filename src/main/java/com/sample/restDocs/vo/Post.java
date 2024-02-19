package com.sample.restDocs.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Post
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private String content;
	
	@Builder
	public Post(Long id, String title, String content)
	{
		this.id = id;
		this.title = title;
		this.content = content;
	}
	
	public void change(String title, String content)
	{
		this.title = title;
		this.content = content;
	}
}
