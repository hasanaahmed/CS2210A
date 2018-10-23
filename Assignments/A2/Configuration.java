/**
 * 
 * @author hasanahmed
 *
 */
public class Configuration {
	private String configuration;
	private int score; 
	
	/**
	 * 
	 * @param configuration
	 * @param score
	 */
	public Configuration(String configuration, int score) {
		this.configuration = configuration;
		this.score = score;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getStringConfiguration() {
		return this.configuration;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getScore() {
		return this.score;
	}
	
}
