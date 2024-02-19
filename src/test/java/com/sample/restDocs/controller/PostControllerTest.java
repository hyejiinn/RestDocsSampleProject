package com.sample.restDocs.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.restDocs.controller.request.PostCreateRequest;
import com.sample.restDocs.repository.PostRepository;
import com.sample.restDocs.vo.Post;

@AutoConfigureMockMvc // @SpringBootTest 를 사용하는 테스트에서 MockMvc를 사용하는 경우에 사용
@SpringBootTest
class PostControllerTest
{
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private PostRepository postRepository;
	
	@BeforeEach
	void clean() {
		postRepository.deleteAll(); // 테스트 수행 후 데이터 클렌징
	}
	
	@DisplayName("글 작성 테스트")
	@Test
	void write() throws Exception
	{
	    // given
		PostCreateRequest request = PostCreateRequest.builder().title("테스트 제목")
				.content("테스트 내용").build();
		
		// when
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/post")
													   .contentType(MediaType.APPLICATION_JSON)
													   .content(objectMapper.writeValueAsString(request)));
		
		// then
		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("테스트 제목"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("테스트 내용"))
				.andDo(MockMvcResultHandlers.print());
	}
	
	
	@DisplayName("글 1개를 저장 후 해당 글 조회 테스트")
	@Test
	void get() throws Exception
	{
	    // given
		Post post = Post.builder().title("테스트 제목").content("테스트 내용").build();
		Post response = postRepository.save(post);
		
		// when
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/{postId}", response.getId()));
		
		// then
		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("테스트 제목"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.content").value("테스트 내용"))
				.andDo(MockMvcResultHandlers.print());
	   
	}
	
}