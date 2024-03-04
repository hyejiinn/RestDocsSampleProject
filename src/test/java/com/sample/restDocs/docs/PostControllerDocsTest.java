package com.sample.restDocs.docs;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.sample.restDocs.controller.request.PostCreateRequest;
import com.sample.restDocs.controller.request.PostEditRequest;
import com.sample.restDocs.repository.PostRepository;
import com.sample.restDocs.service.PostService;
import com.sample.restDocs.service.response.PostResponse;
import com.sample.restDocs.vo.Post;

public class PostControllerDocsTest extends RestDocsSupport
{
	@Autowired
	private PostRepository postRepository;
	
	@MockBean
	private PostService postService;
	
	protected List<FieldDescriptor> defaultResponseFieldDescriptors = new java.util.ArrayList<>(
			List.of(
					PayloadDocumentation.fieldWithPath("code").type(JsonFieldType.NUMBER).description("응답 코드"),
					PayloadDocumentation.fieldWithPath("status").type(JsonFieldType.STRING).description("응답 상태"),
					PayloadDocumentation.fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
					PayloadDocumentation.fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터")
			));
	
	@DisplayName("글 하나를 작성하면 정상적으로 저장된다.")
	@Test
	void write() throws Exception
	{
		// given
		PostCreateRequest request = PostCreateRequest.builder().title("테스트 제목")
				.content("테스트 내용").build();
		
		defaultResponseFieldDescriptors.add(PayloadDocumentation.fieldWithPath("data.title").description("post 제목"));
		defaultResponseFieldDescriptors.add(PayloadDocumentation.fieldWithPath("data.content").description("post 내용"));
		
		BDDMockito.given(
						postService.writePost(BDDMockito.any(PostCreateRequest.class)))
				.willReturn(
						PostResponse.builder()
								.title("테스트 제목")
								.content("테스트 내용")
								.build()
				);
		
		// when
		ResultActions result = mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/post")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(request)))
						.andDo(MockMvcResultHandlers.print());

		// then
		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcRestDocumentation.document("post-write", // REST Docs
														 RestDocsUtils.getDocumentRequest(),
														 RestDocsUtils.getDocumentResponse(),
														 PayloadDocumentation.requestFields( // 요청 데이터 스펙
																PayloadDocumentation.fieldWithPath("title").description("post 제목").optional(),
																PayloadDocumentation.fieldWithPath("content").description("post 내용")
														 ),
														 PayloadDocumentation.responseFields( // 응답 데이터 스펙
																 defaultResponseFieldDescriptors
														 )
				));
		;
	}
	
	@DisplayName("글 1개를 저장하고 조회하는 테스트")
	@Test
	void get() throws Exception
	{
		// given
		defaultResponseFieldDescriptors.add(PayloadDocumentation.fieldWithPath("data.title").description("post 제목"));
		defaultResponseFieldDescriptors.add(PayloadDocumentation.fieldWithPath("data.content").description("post 내용"));
		
		BDDMockito.given(postService.getPost(BDDMockito.anyLong())).willReturn(
				PostResponse.builder()
						.title("테스트 제목222")
						.content("테스트 내용")
						.build()
		);
		
		// when
		ResultActions result = mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/post/{postId}", BDDMockito.anyLong()))
										.andDo(MockMvcResultHandlers.print());
		
		// then
		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcRestDocumentation.document("post-get", // REST docs
														 RestDocsUtils.getDocumentResponse(),
														 RequestDocumentation.pathParameters(
																 RequestDocumentation.parameterWithName("postId").description("post Id")
														 ),
														 PayloadDocumentation.responseFields(
																 defaultResponseFieldDescriptors
														 )
				));
		
	}
	
	@DisplayName("글 1개를 수정 후 해당 글 조회 테스트")
	@Test
	void update() throws Exception
	{
		// given
		PostEditRequest editRequest = PostEditRequest.builder().title("테스트 제목 수정!").content("테스트 내용 수정!").build();
		
		defaultResponseFieldDescriptors.add(PayloadDocumentation.fieldWithPath("data.title").description("post 제목"));
		defaultResponseFieldDescriptors.add(PayloadDocumentation.fieldWithPath("data.content").description("post 내용"));
		long anyLong = BDDMockito.anyLong();
		BDDMockito.given(postService.editPost(anyLong, BDDMockito.any(PostEditRequest.class)))
				.willReturn(
						PostResponse.builder()
								.title("테스트 제목 수정!")
								.content("테스트 내용 수정!")
								.build()
				);
		
		// when
		ResultActions result = mockMvc.perform(
						RestDocumentationRequestBuilders.post("/api/v1/post/{postId}", anyLong)
								.contentType(MediaType.APPLICATION_JSON)
								.content(objectMapper.writeValueAsString(editRequest)))
				.andDo(MockMvcResultHandlers.print());
		
		// then
		result.andExpect(MockMvcResultMatchers.status().isOk())
				.andDo(MockMvcRestDocumentation.document("post-update", // REST Docs
														 RestDocsUtils.getDocumentRequest(),
														 RestDocsUtils.getDocumentResponse(),
														 RequestDocumentation.pathParameters(
																 RequestDocumentation.parameterWithName("postId").description("post Id")
														 ),
														 PayloadDocumentation.requestFields(
															PayloadDocumentation.fieldWithPath("title").type(JsonFieldType.STRING).description("post 제목").optional(),
															PayloadDocumentation.fieldWithPath("content").type(JsonFieldType.STRING).description("post 내용")
														 ),
														 PayloadDocumentation.responseFields(
																 defaultResponseFieldDescriptors
														 )
														 ));
	}
}
