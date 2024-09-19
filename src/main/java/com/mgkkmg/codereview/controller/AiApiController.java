package com.mgkkmg.codereview.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mgkkmg.codereview.service.AiApiService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AiApiController {

	private final AiApiService aiApiService;

	@GetMapping("/ai/generate")
	public Map<String, String> generate(@RequestParam(value = "message") String message) {
		return Map.of("generation", aiApiService.callAiApi(message));
	}

	// @PostMapping("/ai/generate2")
	// public Map<String, String> generate2(@RequestBody Map<String, String> param) {
	// 	String message = "Tell me a joke";
	// 	if (StringUtils.hasText(param.get("message"))) {
	// 		message = param.get("message");
	// 	}
	//
	// 	PromptTemplate promptTemplate = new PromptTemplate("""
    //         {message}
    //         """);
	// 	promptTemplate.add("message", message);
	//
	// 	String promptMessage = promptTemplate.render();
	//
	// 	Message userMessage = new UserMessage(promptMessage);
	// 	Message systemMessage = new SystemMessage("""
	// 		translate to korean
	// 		""");
	//
	// 	// Prompt prompt = new Prompt(List.of(userMessage, systemMessage));
	//
	// 	return Map.of("generation", chatModel.call(userMessage, systemMessage));
	// }
}
