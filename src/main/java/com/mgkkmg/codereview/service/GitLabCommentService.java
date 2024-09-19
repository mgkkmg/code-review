package com.mgkkmg.codereview.service;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Diff;
import org.gitlab4j.api.models.MergeRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mgkkmg.codereview.dto.GitLabWebhookPayload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class GitLabCommentService {

	private final AiApiService aiApiService;

	@Value("${gitlab.host-url}")
	private String hostUrl;

	@Value("${gitlab.access-token}")
	private String personalAccessToken;

	public String generateAICodeReview(GitLabWebhookPayload payload) {
		if (!"merge_request".equals(payload.getObjectKind())) {
			return "Invalid request type.";
		}

		try (GitLabApi gitLabApi = new GitLabApi(hostUrl, personalAccessToken)) {

			MergeRequest mergeRequest = gitLabApi.getMergeRequestApi().getMergeRequestChanges(
				payload.getProjectId(),
				payload.getMergeRequestIid()
			);

			StringBuilder diffContent = new StringBuilder();
			for (Diff diff : mergeRequest.getChanges()) {
				diffContent.append("File: ").append(diff.getNewPath()).append("\n");
				diffContent.append("Old Path: ").append(diff.getOldPath()).append("\n");
				diffContent.append("New File: ").append(diff.getNewFile()).append("\n");
				diffContent.append("Renamed File: ").append(diff.getRenamedFile()).append("\n");
				diffContent.append("Deleted File: ").append(diff.getDeletedFile()).append("\n");
				diffContent.append(diff.getDiff()).append("\n\n");
			}

			return aiApiService.callAiApi(diffContent.toString());
		} catch (GitLabApiException e) {
			log.error("Failed to get merge request diffs: {}", e.getMessage());
			return "Failed to generate AI code review due to an error.";
		}
	}

	public void addCommentToMergeRequest(Long projectId, Long mergeRequestIid, String comment) {
		try (GitLabApi gitLabApi = new GitLabApi(hostUrl, personalAccessToken)) {
			MergeRequest mergeRequest = gitLabApi.getMergeRequestApi().getMergeRequest(projectId, mergeRequestIid);
			String fullComment = String.format("Review for MR '%s':\n%s", mergeRequest.getTitle(), comment);
			gitLabApi.getNotesApi().createMergeRequestNote(projectId, mergeRequestIid, fullComment);

			log.info("Comment added successfully to MR #{}: {}", mergeRequestIid, mergeRequest.getTitle());
		} catch (GitLabApiException e) {
			log.error("Failed to add comment: {}", e.getMessage());
		}
	}
}
