import com.google.gson.annotations.SerializedName;

public class Compare {
	@SerializedName("total_commits")
	private int totalCommits;

	public int getTotalCommits() {
		return totalCommits;
	}
}

