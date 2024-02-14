package com.sample.restDocs.service;

import org.springframework.stereotype.Service;

import com.sample.restDocs.controller.request.PostCreateRequest;
import com.sample.restDocs.repository.PostRepository;
import com.sample.restDocs.service.response.PostResponse;
import com.sample.restDocs.vo.Post;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostService
{
	private final PostRepository postRepository;
	
	public PostResponse getPost(Long postId)
	{
		Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("해당 Post는 존재하지 않습니다."));
		
		return PostResponse.builder().title(post.getTitle())
				.content(post.getContent()).build();
	}
	
	public PostResponse writePost(PostCreateRequest request)
	{
		Post requestPost = request.toEntity();
		
		Post save = postRepository.save(requestPost);
		
		return PostResponse.builder().title(save.getTitle())
				.content(save.getContent()).build();
	}
}
