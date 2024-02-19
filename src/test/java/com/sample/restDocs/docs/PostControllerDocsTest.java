package com.sample.restDocs.docs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.restDocs.controller.request.PostCreateRequest;
import com.sample.restDocs.repository.PostRepository;
import com.sample.restDocs.vo.Post;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

@AutoConfigureMockMvc
public class PostControllerDocsTest extends RestDocsSupport
{
	@Autowired
	private PostRepository postRepository;
	
	@DisplayName("글 하나를 작성하면 정상적으로 저장된다.")
	@Test
	void write() throws Exception
	{
		// given
		PostCreateRequest request = PostCreateRequest.builder().title("테스트 제목")
				.content("테스트 내용").build();
		
		// when then
		mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/post")
								.contentType(
										MediaType.APPLICATION_JSON).content(
								objectMapper.writeValueAsString(request)))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcRestDocumentation.document("post-write",
														 PayloadDocumentation.responseFields(
																 PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
																 PayloadDocumentation.fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
																 PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
																 PayloadDocumentation.fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
																 PayloadDocumentation.fieldWithPath("data.title").description("post 제목"),
																 PayloadDocumentation.fieldWithPath("data.content").description("post 내용")
														 )
				));
		;
	}
	
	@DisplayName("글 1개를 저장하고 조회하는 테스트")
	@Test
	void get() throws Exception
	{
		// given
		Post post = Post.builder().title("테스트 제목").content("테스트 내용").build();
		Post response = postRepository.save(post);
		
		// when then
		mockMvc.perform(
						RestDocumentationRequestBuilders.get("/api/v1/post/{postId}",
															 response.getId()))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcRestDocumentation.document("post-get",
														 RequestDocumentation.pathParameters(
																 RequestDocumentation.parameterWithName("postId").description("post Id")
														 ),
														 PayloadDocumentation.responseFields(PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
																 PayloadDocumentation.fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
																 PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
																
																 PayloadDocumentation.fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
																 PayloadDocumentation.fieldWithPath("data.title").description("게시글 제목"),
																 PayloadDocumentation.fieldWithPath("data.content").description("게시글 내용")
														 )
				));
		
	}
}
