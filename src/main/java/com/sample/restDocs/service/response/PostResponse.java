package com.sample.restDocs.service.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostResponse
{
	private String title;
	private String content;
	
	@Builder
	public PostResponse(String title, String content)
	{
		this.title = title;
		this.content = content;
	}
}
