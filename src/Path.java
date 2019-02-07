import java.util.ArrayList;

public class Path {
ArrayList<Node> pathNode= new ArrayList<Node>();
float currentDistance;

public void addNode(Node node) {
	// TODO Auto-generated method stub
	pathNode.add(node);
}
public ArrayList<Node> getNodes(){
	
	return this.pathNode;
}

public float getDistance(){
	if(pathNode.size()<=1)
		return (float) 0.0;
	float distParz=(float) 0.0;
	for (int i = 0; i < pathNode.size()-1; i++) {
		distParz+=distance(pathNode.get(i), pathNode.get(i+1));
	}
	return distParz;
}


private Float distance(Node n1, Node n2){
	return new Float((float) Math.sqrt((Math.pow((n1.getX()-n2.getX()), 2) + Math.pow(n1.getY()-n2.getY(), 2))));
}

public String toString(){
	if(pathNode.size()==0)
		return new String("");
	String cammino= new String("");
	for (int i = 0; i < pathNode.size(); i++) {
		cammino+=pathNode.get(i).getNumber();
		cammino+="-";
	}
	return cammino;
}

}
