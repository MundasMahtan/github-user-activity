import java.io.IOException;

public class GitHubActivity {
	public static void main(String[] args) {
		// Check arguments
		if (args.length == 0) {
			System.out.println("Please provide a username");
			return;
		}

		String username = args[0];

		// Create API client
		GitHubApiClient apiClient = new GitHubApiClient();

		// Fetch events
		try {
			Event[] githubEvents = apiClient.getUserEvents(username);
			if (githubEvents == null || githubEvents.length == 0) {
				System.out.println("No events found for user " + username);
				return;
			}
			for (Event event : githubEvents) {
				System.out.println(displayEvent(event, apiClient));
			}
		} catch (IOException e) {
			System.out.println("Error connecting to " + username);
		}
	}

	public static String displayEvent(Event event, GitHubApiClient apiClient) throws IOException {
		String result = "- ";
		switch (event.getType()) {
		case "CommitCommentEvent":
			result += "Commited comment in ";
			break;
		case "CreateEvent":
			result += "Created in ";
			break;
		case "DeleteEvent":
			result += "Deleted ";
			break;
		case "DiscussionEvent":
			result += "Discussed in ";
			break;
		case "ForkEvent":
			result += "Forked ";
			break;
		case "GollumEvent":
			result += "Created or updated wiki page for ";
			break;
		case "IssueCommentEvent":
			result += "Issued comment in ";
			break;
		case "IssuesEvent":
			result += "Issued in ";
			break;
		case "MemberEvent":
			result += "Added ro removed collaborator in ";
			break;
		case "PublicEvent":
			result += "Made public the ";
			break;
		case "PullRequestEvent":
			result += "Opened a pull request in ";
			break;
		case "PullRequestReviewEvent":
			result += "Requested pull review in ";
			break;
		case "PullRequestReviewCommentEvent":
			result += "Requested pull comment review in ";
			break;
		case "PushEvent":
			String repoName = event.getRepo().getName();
			String beforeSha = (String) event.getPayload().get("before");
			String afterSha = (String) event.getPayload().get("head");
			int commitCount = 0;
			try {
				commitCount = apiClient.getCommitCount(repoName, beforeSha, afterSha);
			} catch (IOException e) {
				System.out.println("Error connecting to " + repoName);
			}
			switch (commitCount) {
				case 1:
					result += "Pushed " + commitCount + " commit to ";
					break;
				default:
					result += "Pushed " + commitCount + " commits to ";
					break;
			}
			break;
		case "ReleaseEvent":
			result += "Released in ";
			break;
		case "WatchEvent":
			result += "Starred ";
			break;
		}
		result += event.getRepo().getName();
		return result;
	}
}