package com.sample.restDocs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.restDocs.controller.request.PostCreateRequest;
import com.sample.restDocs.controller.response.ApiResponse;
import com.sample.restDocs.service.response.PostResponse;
import com.sample.restDocs.service.PostService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/api/v1")
@RequiredArgsConstructor
@RestController
public class PostController
{
	private final PostService postService;
	
	@PostMapping("/post")
	public ApiResponse<PostResponse> writePost(@RequestBody PostCreateRequest request)
	{
		return ApiResponse.ok(postService.writePost(request));
	}
	
	@GetMapping("/post/{postId}")
	public ApiResponse<PostResponse> getPost(@PathVariable(value = "postId") Long postId)
	{
		return ApiResponse.ok(postService.getPost(postId));
	}
	
}
