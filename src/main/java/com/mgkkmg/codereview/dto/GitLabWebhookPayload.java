package com.mgkkmg.codereview.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GitLabWebhookPayload {

	@JsonProperty("object_kind")
	private String objectKind;

	@JsonProperty("event_type")
	private String eventType;

	private User user;
	private Project project;

	@JsonProperty("object_attributes")
	private MergeRequestAttributes mergeRequestAttributes;

	@Setter
	@Getter
	public static class User {
		private Long id;
		private String name;
		private String username;
	}

	@Setter
	@Getter
	public static class Project {
		private Long id;
		private String name;
		private String description;
		@JsonProperty("web_url")
		private String webUrl;
	}

	@Setter
	@Getter
	public static class MergeRequestAttributes {
		private Long id;
		private Long iid;
		private String title;
		private String description;
		private String state;
		@JsonProperty("created_at")
		private Date createdAt;
		@JsonProperty("updated_at")
		private Date updatedAt;
		@JsonProperty("target_branch")
		private String targetBranch;
		@JsonProperty("source_branch")
		private String sourceBranch;
		@JsonProperty("source_project_id")
		private Long sourceProjectId;
		@JsonProperty("target_project_id")
		private Long targetProjectId;
	}

	// Convenience methods
	public Long getProjectId() {
		return project != null ? project.getId() : null;
	}

	public Long getMergeRequestIid() {
		return mergeRequestAttributes != null ? mergeRequestAttributes.getIid() : null;
	}
}
