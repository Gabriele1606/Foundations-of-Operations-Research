
public class Node {
int coordX;
int coordY;
int number;

public Node(){
	
}

public Node(int x, int y){
	this.coordX=x;
	this.coordY=y;
}

public void setX(int coordX){
	this.coordX=coordX;
}

public void setY(int coordY){
	this.coordY=coordY;
}

public int getX(){
	return this.coordX;
}

public int getY(){
	return this.coordY;
}

public void setNumber(int number){
	this.number=number;
}

public Node copyNode(Node node){
	return new Node(node.getX(),node.getY());
}

public int getNumber(){
	return this.number;
}

}
