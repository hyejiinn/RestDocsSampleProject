package com.sample.restDocs.docs;

import java.util.List;

import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.request.RequestDocumentation;

public class RestDocsUtils
{
	/**
	 * 문서의 request 를 보기좋게 출력하기 위해 사용
	 * @return
	 */
	public static OperationRequestPreprocessor getDocumentRequest()
	{
		return Preprocessors.preprocessRequest(Preprocessors.prettyPrint());
	}
	
	/**
	 * 문서의 response 를 보기좋게 출력하기 위해 사용
	 * @return
	 */
	public static OperationResponsePreprocessor getDocumentResponse()
	{
		return Preprocessors.preprocessResponse(Preprocessors.prettyPrint());
	}
	
	public static RestDocumentationResultHandler getMethodDocument(String identifier, List<ParameterDescriptor> queryParameters,List<FieldDescriptor> requestFields, List<FieldDescriptor> responseFields)
	{
		return MockMvcRestDocumentation.document(identifier,
												 RestDocsUtils.getDocumentRequest(),
												 RestDocsUtils.getDocumentResponse(),
												 RequestDocumentation.queryParameters(queryParameters),
												 PayloadDocumentation.requestFields(requestFields),
												 PayloadDocumentation.responseFields(responseFields)
		);
	}
	
	public static RestDocumentationResultHandler getMethodDocument(String identifier, List<FieldDescriptor> requestFields, List<FieldDescriptor> responseFields)
	{
		return MockMvcRestDocumentation.document(identifier,
												 RestDocsUtils.getDocumentRequest(),
												 RestDocsUtils.getDocumentResponse(),
												 PayloadDocumentation.requestFields(requestFields),
												 PayloadDocumentation.responseFields(responseFields)
		);
	}
	
	public static RestDocumentationResultHandler getMethodDocument(String identifier, List<FieldDescriptor> responseFields)
	{
		return MockMvcRestDocumentation.document(identifier,
												 RestDocsUtils.getDocumentRequest(),
												 RestDocsUtils.getDocumentResponse(),
												 PayloadDocumentation.responseFields(responseFields)
		);
	}
	
	public static ParameterDescriptor param(String name, String description)
	{
		return RequestDocumentation.parameterWithName(name).description(description);
	}
	
	public static FieldDescriptor field(String jsonPath, JsonFieldType type, String description)
	{
		return PayloadDocumentation.fieldWithPath(jsonPath).type(type).description(description);
	}
	

}
