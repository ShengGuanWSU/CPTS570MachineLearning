package homework3;

public class Feature {
	private String fName;
	private int idx;
	private String[] fOptions;
	
	public int getIdx() {
		return idx;
	}
	
	public void setIdx(int idx) {
		this.idx = idx;
	}
	
	public String getfName() {
		return fName;
	}
	
	public void setfName(String fName) {
		this.fName = fName;
	}
	
	public String[] getfOptions() {
		return fOptions;
	}
	
	public void setfOptions(String[] fOptions) {
		this.fOptions = fOptions;
	}
	
	public String toString() {
		return this.fName;
	}

}
