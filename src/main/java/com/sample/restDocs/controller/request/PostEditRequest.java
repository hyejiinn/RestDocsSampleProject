package com.sample.restDocs.controller.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostEditRequest
{
	private String title;
	private String content;
	
	@Builder
	public PostEditRequest(String title, String content)
	{
		this.title = title;
		this.content = content;
	}
}
