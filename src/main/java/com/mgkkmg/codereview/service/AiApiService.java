package com.mgkkmg.codereview.service;

import java.util.Map;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AiApiService {

	private final ChatModel chatModel;

	@Value("classpath:templates/prompt.st")
	private Resource promptResource;

	public String callAiApi(String message) {
		PromptTemplate promptTemplate = new PromptTemplate(promptResource);
		// promptTemplate.add("message", message);

		Prompt prompt = promptTemplate.create(Map.of("message", message));

		return chatModel.call(prompt).getResult().getOutput().getContent();
	}
}
