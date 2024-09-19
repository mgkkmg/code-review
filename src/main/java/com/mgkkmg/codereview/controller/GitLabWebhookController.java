package com.mgkkmg.codereview.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mgkkmg.codereview.dto.GitLabWebhookPayload;
import com.mgkkmg.codereview.service.GitLabCommentService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class GitLabWebhookController {

	private final GitLabCommentService gitLabCommentService;

	@PostMapping("/webhook/comment")
	public void handleGitLabWebhook(@RequestBody GitLabWebhookPayload payload) {
		String aiGeneratedComment = gitLabCommentService.generateAICodeReview(payload);
		gitLabCommentService.addCommentToMergeRequest(
			payload.getProjectId(),
			payload.getMergeRequestIid(),
			aiGeneratedComment
		);
	}
}
