package com.sample.restDocs.docs;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;

public class RestDocsUtils
{
	public static OperationRequestPreprocessor getDocumentRequest()
	{
		return Preprocessors.preprocessRequest(Preprocessors.prettyPrint()); // 문서의 request 를 보기좋게 출력하기 위해 사용
	}
	
	public static OperationResponsePreprocessor getDocumentResponse()
	{
		return Preprocessors.preprocessResponse(Preprocessors.prettyPrint()); // 문서의 response 를 보기좋게 출력하기 위해 사용
	}
}
