package com.sample.restDocs.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sample.restDocs.controller.request.PostCreateRequest;
import com.sample.restDocs.controller.request.PostEditRequest;
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
//		return new PostResponse();
	}
	
	@Transactional
	public PostResponse editPost(Long postId, PostEditRequest request)
	{
		Post post = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("해당 Post는 존재하지 않습니다."));
		
		post.change(request.getTitle(), request.getContent());
		
		return PostResponse.builder().title(post.getTitle())
				.content(post.getContent()).build();
	}
}
