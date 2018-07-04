package com.zl.way.sensitive.api;

import com.zl.way.sensitive.api.model.SensitiveWordsRequest;
import com.zl.way.sensitive.api.model.SensitiveWordsResponse;
import com.zl.way.util.ResponseResult;
import com.zl.way.util.ResponseResultUtil;
import com.zl.way.util.SensiWordsUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sensiwords")
public class SensitiveWordsApi {

	@RequestMapping(value = "/valid", method = RequestMethod.POST)
	public ResponseResult<SensitiveWordsResponse> isSensitiveWords(
			@RequestBody SensitiveWordsRequest sensitiveWordsRequest) {

		String sentence = sensitiveWordsRequest.getSentence();

		SensitiveWordsResponse sensitiveWordsResponse = new SensitiveWordsResponse();
		sensitiveWordsResponse.setSensiWordsPassed(!SensiWordsUtil.isSensiWords(sentence));

		return ResponseResultUtil.wrapSuccessResponseResult(sensitiveWordsResponse);
	}
}
