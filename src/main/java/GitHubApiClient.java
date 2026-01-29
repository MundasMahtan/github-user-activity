import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.google.gson.Gson;
import java.io.IOException;

public class GitHubApiClient {
	public static final String GITHUB_API_BASE = "https://api.github.com";
	private OkHttpClient client;
	private Gson gson;

	public GitHubApiClient() {
		this.client = new OkHttpClient();
		this.gson = new Gson();
	}

	// Fetch events for a user
	public Event[] getUserEvents(String username) throws IOException {
		// Step 1: Build URL
		String url = GITHUB_API_BASE + "/users/" + username + "/events";

		// Step 2: Create request
		Request request = new Request.Builder().url(url).build();

		// Step 3: Execute request
		Response response = client.newCall(request).execute();

		// Step 4: Check if HTTP response succeeded
		if (!response.isSuccessful()) {
			System.out.println("Error HTTP is" + response.code());
			response.close();
			return null;
		}
		if (response.body() == null) {
			System.out.println("The response body is null");
			response.close();
			return null;
		}
		// Step 5: Get response body as string
		String jsonResponse = response.body().string();

		// Step 6: Parse to events array
		Event[] events = gson.fromJson(jsonResponse, Event[].class);
		response.close();
		return events;
	}
	public int getCommitCount(String repoName, String beforeSha, String afterSha) throws IOException {
		// Step 1: Build URL
		String url = GitHubApiClient.GITHUB_API_BASE + "/repos/" + repoName + "/compare/" + beforeSha + "..." + afterSha;
		// Step 2: Create request
		Request request = new Request.Builder().url(url).build();

		// Step 3: Execute request
		Response response = client.newCall(request).execute();

		// Step 4: Check if HTTP response succeeded
		if (!response.isSuccessful()) {
			System.out.println("Error HTTP is" + response.code());
			response.close();
			return 0;
		}
		if (response.body() == null) {
			System.out.println("The response body is null");
			response.close();
			return 0;
		}
		// Step 5: Get response body as string
		String jsonResponse = response.body().string();

		// Step 6: Parse to events array
		Compare compare = gson.fromJson(jsonResponse, Compare.class);
		int commits = compare.getTotalCommits();

		// Call GitHub API to compare commits
		// https://api.github.com/repos/{owner}/{repo}/compare/{before}...{after}
		response.close();
		return commits;
	}
}