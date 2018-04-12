package homework3;


public class TreeNode {
	private int label;
    private TreeNode leftNode;
    private TreeNode rightNode;
    private Feature feature;
    private String log;

    TreeNode() {
   }
    
    public TreeNode getLeftNode(){
    	return leftNode;
    }
    
    public void setLeftNode(TreeNode leftNode) {
		this.leftNode = leftNode;
	}
	
	public TreeNode getRightNode() {
		return rightNode;
	}
	
	public void setRightNode(TreeNode rightNode) {
		this.rightNode = rightNode;
	}
	
	public Feature getFeature() {
		return feature;
	}
	
	public void setFeature(Feature feature){
		this.feature = feature;
	}
	
	public int getOutputLabel() {
		return label;
	}
	
	public void setOutputLabel(int outputLabel) {
		this.label = outputLabel;
	}
	
	public String getLog() {
		return log;
	}
	
	public void setLog(String log) {
		this.log = log;
	}
	
	public StringBuilder toString(StringBuilder prefix, boolean isTail, StringBuilder sb) {
	    if(getRightNode() != null) {
	    	getRightNode().toString(new StringBuilder().append(prefix).append(isTail ? "©¦   " : "    "), false, sb);
	    }
	    
	    String result = "";
	    if(getFeature() != null) {
	    	result = getFeature().toString();
	    }
	    sb.append(prefix).append(isTail ? "©¸©¤©¤ " : "©°©¤©¤ ").append(result+getLog()).append("\n");
	    if(getLeftNode() != null) {
	    	getLeftNode().toString(new StringBuilder().append(prefix).append(isTail ? "    " : "©¦   "), true, sb);
	    }
	    return sb;
	}

	@Override
	public String toString() {
	    return this.toString(new StringBuilder(), true, new StringBuilder()).toString();
	}
	
}
