import java.util.Map;

import com.google.gson.annotations.SerializedName;

public class Event {
	private String id;
	private String type;
	private Actor actor;
	private Repo repo;
	private Map<String, Object> payload;
	@SerializedName("created_at")
	private String createdAt;

	public static class Actor {
		private String login;

		public String getLogin() {
			return login;
		}
	}
	public static class Repo {
		private String name;

		public String getName() {
			return name;
		}
	}

	public String getId() {
		return id;
	}
	public String getType() {
		return type;
	}
	public Actor getActor() {
		return actor;
	}
	public Repo getRepo() {
		return repo;
	}
	public Map<String, Object> getPayload() {
		return payload;
	}
	public String getCreatedAt() {
		return createdAt;
	}
}
