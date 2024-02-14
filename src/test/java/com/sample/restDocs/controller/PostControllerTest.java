package com.sample.restDocs.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.restDocs.controller.request.PostCreateRequest;
import com.sample.restDocs.repository.PostRepository;
import com.sample.restDocs.service.PostService;
import com.sample.restDocs.service.response.PostResponse;
import com.sample.restDocs.vo.Post;

import static org.junit.jupiter.api.Assertions.*;
@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest
{
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private PostRepository postRepository;
	
	@BeforeEach
	void clean() {
		postRepository.deleteAll();
	}
	
	
	
	@DisplayName("글 하나를 작성하면 정상적으로 저장된다.")
	@Test
	void write() throws Exception
	{
	    // given
		PostCreateRequest request = PostCreateRequest.builder().title("테스트 제목")
				.content("테스트 내용").build();
		
		// when then
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/post").contentType(
						MediaType.APPLICATION_JSON).content(
						objectMapper.writeValueAsString(request)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("테스트 제목"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("테스트 내용"))
				.andDo(MockMvcResultHandlers.print());
	}
	
	
	@DisplayName("글 1개를 저장하고 조회하는 테스트")
	@Test
	void get() throws Exception
	{
	    // given
		Post post = Post.builder().title("테스트 제목").content("테스트 내용").build();
		Post response = postRepository.save(post);
		
		// when then
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/{postId}", response.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("테스트 제목"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("테스트 내용"))
				.andDo(MockMvcResultHandlers.print());
	   
	}
	
}