package com.sample.restDocs.docs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;

/**
 * Spring REST Docs 환경 클래스
 */
@AutoConfigureRestDocs( // Spring REST Docs 를 사용하기 위해 MockMvc 빈 커스텀
		uriScheme = "https", uriHost = "sample.restDocs.com"
)
@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
public class RestDocsSupport
{
	protected MockMvc mockMvc;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation)
	{
		// @AutoConfigureMockMvc 를 통한 자동주입이 아니라 테스트 전에 미리 mockMvc 세팅하는 작업
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.apply(documentationConfiguration(restDocumentation))
				.build();
	}
	
	
}
