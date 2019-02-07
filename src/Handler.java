import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Handler {
	String filepath;
	String nameFile;
	int TRESHOLD;
	ReadDat readDat;
	ArrayList<Node> graph;
	float alpha;
	ArrayList<Path> pathesFound;
	
	ArrayList<Node> coveredNodes;
	float[][] dangerous;
	 PrintWriter writer;
	
	 int[] occorrenzaFoglia;
	 
	 ArrayList<Path> pathChoiced;
	 
	public void start() {
		readDat= new ReadDat();
		graph= new ArrayList<Node>();		
		pathesFound= new ArrayList<Path>();
		coveredNodes= new ArrayList<Node>();
		
		try{
		    writer = new PrintWriter(nameFile, "UTF-8");
		} catch (IOException e) {
		}
		
		
		readDat.readFile(this.filepath, graph, null, null, null);
		alpha = readDat.getAlpha();
		dangerous=readDat.getDangerous();
		
		
		
		setCoefficient();
		
		Path initialPath= new Path();
		initialPath.addNode(graph.get(0));
		
		ArrayList<Node> initialReachable= new ArrayList<Node>();
		for(Node n: graph){
			if(n!=graph.get(0))
				initialReachable.add(n);
		}
		
		occorrenzaFoglia= new int[graph.size()];
		
		creaCammino(graph.get(0), initialPath, initialReachable);
		
		addDirPath();
		
		pathChoiced= new ArrayList<Path>();

		while(coveredNodes.size()<graph.size()){
			ArrayList<Path> pathMoreNode = connectMoreNode();
			/**
			 * rimuovere quelli che si intersecano con percorsi già scelti da pathMoreNode
			 */
			
			ArrayList<Path> tempPath= new ArrayList<Path>();
			
			
			for (int i =0; i < pathMoreNode.size(); i++) {
				for (int j = 0; j < pathChoiced.size(); j++) {
					if(!notIntersect(pathMoreNode.get(i), pathChoiced.get(j), graph.size())){
						tempPath.add(pathMoreNode.get(i));
						break;
					}
						
				}
			}
			
			for (int i = 0; i < tempPath.size(); i++) {
				pathMoreNode.remove(tempPath.get(i));
			}
			
						
			/**
			 * scegliamo il meno pericoloso, lo aggiungiamo ai percorsi scelti, e aggiorniamo i covered.
			 */
			Path lessDangerous=checkDangerous(pathMoreNode);
			
			pathChoiced.add(lessDangerous);
			
			
			for (int i = 0; i < lessDangerous.getNodes().size(); i++) {
				if(!coveredNodes.contains(lessDangerous.getNodes().get(i))){
					coveredNodes.add(lessDangerous.getNodes().get(i));
				}
			}
		}
		
		
		stampaSoluzione();
		
	    writer.close();
		
	}
	
	private void setCoefficient() {
		TRESHOLD= (int) (graph.size()*100/(2*alpha));
		
	}

	private void stampaSoluzione() {
		int[] risposta= new int[graph.size()];
		
		for (int i = 0; i < pathChoiced.size(); i++) {
			for (int j = 1; j < pathChoiced.get(i).getNodes().size(); j++) {
				risposta[pathChoiced.get(i).getNodes().get(j).getNumber()]=pathChoiced.get(i).getNodes().get(j-1).getNumber();
			}			
		}
		for (int i = 1; i < risposta.length; i++) {
			writer.println(i+" "+risposta[i]);
		}
	}

	private void addDirPath() {
		for (int i = 1; i < graph.size(); i++) {
			Path trivialTemp= new Path();
			trivialTemp.addNode(graph.get(0));
			trivialTemp.addNode(graph.get(i));
			pathesFound.add(trivialTemp);
		}
		
		
	}

	/**
	 * node 
	 * @param node e' il nodo sul quale è chiamato il metodo creacammino
	 * @param path
	 * @param reachable reachable contiene solo i nodi raggiungibili dal nodo corrente, e path contiene tutti i nodi percorsi fino al nodo corrente compreso.
	 */

	private void creaCammino(Node node, Path path,ArrayList<Node> reachable){
		if(isLeaf(reachable)){
			this.pathesFound.add(path);
			occorrenzaFoglia[node.getNumber()]++;
			
		return;
			
		}else{
			for (int i = 0; i < reachable.size(); i++) {
				Node successivo = reachable.get(i);
				Path pathContainingI= clonePath(path,successivo);
				ArrayList<Node> reachFromI=cloneReachable(reachable, successivo,pathContainingI);
				creaCammino(successivo, pathContainingI, reachFromI);
		
			}
			
			
			
		}	
		return;
		
	}
	
	
	private ArrayList<Node> cloneReachable(ArrayList<Node> reachable, Node successivo, Path pathContainingI) {
		ArrayList<Node> clonedReachable= new ArrayList<Node>();
		for (int i = 0; i < reachable.size(); i++) {
			if(reachNodeFromPath(pathContainingI, successivo, reachable.get(i))&&reachable.get(i).getNumber()!=successivo.getNumber()){
				if(occorrenzaFoglia[reachable.get(i).getNumber()]<=TRESHOLD){
					clonedReachable.add(reachable.get(i));
				}
			}
		}
		
		return clonedReachable;
	}

	private Path clonePath(Path path, Node successivo) {
		Path clonedPath= new Path();
		for (int i = 0; i < path.getNodes().size(); i++) {
			clonedPath.addNode(path.getNodes().get(i));
		}
		clonedPath.addNode(successivo);
		return clonedPath;
	}

	private boolean isLeaf(ArrayList<Node> reachable){
		return reachable.size()==0;
	}
	
	public boolean reachNodeFromPath(Path path, Node actualNode, Node nextNode){
		
		if(path.getDistance()+distance(actualNode, nextNode)<=this.alpha*distance(this.graph.get(0), nextNode)){
			return true;
		}
		return false;
	}
	
	public Float distance(Node n1, Node n2){
		return new Float((float) Math.sqrt((Math.pow((n1.getX()-n2.getX()), 2) + Math.pow(n1.getY()-n2.getY(), 2))));
	}
	
	public boolean notIntersect(Path p1, Path p2, int dimGraph){
		int[] occ1= new int[dimGraph];
		int[] occ2= new int[dimGraph];
		
		
		for (int i = 0; i < Math.min(p1.getNodes().size(), p2.getNodes().size()); i++) {
			if(p1.getNodes().get(i).getNumber()!=p2.getNodes().get(i).getNumber()){
				for (int j = i; j < p1.getNodes().size(); j++) {
					occ1[p1.getNodes().get(j).getNumber()]=1;
				}
				for (int j = i; j < p2.getNodes().size(); j++) {
					occ2[p2.getNodes().get(j).getNumber()]=1;
				}
				for (int t = 0; t < occ2.length; t++) {
					if(occ1[t]==occ2[t]&&occ1[t]==1){
						return false;
						}
				}
				return true;
			}
				
		}
		return true;
		
	}
	
	/*
	 * metodo che ritorna i cammini che aggiungono più nodi
	 */
	
	public ArrayList<Path> connectMoreNode(){
		ArrayList<Path> percorsiDaEliminare= new ArrayList<Path>();
		
		ArrayList<Path> result= new ArrayList<Path>();
		int maxCoveredNode=0;
		for (int i = 0; i < pathesFound.size(); i++) {
			int salta=0;
			int coveredNodePathI=0;
			for (int j = 0; j < pathChoiced.size(); j++) {
				if(!notIntersect(pathesFound.get(i), pathChoiced.get(j), graph.size())){
					salta=1;
					percorsiDaEliminare.add(pathesFound.get(i));
				}
					
			}
			if(salta==1){
				continue;
			}
			for (int j = 0; j < pathesFound.get(i).getNodes().size(); j++) {
				if(!coveredNodes.contains(pathesFound.get(i).getNodes().get(j)))
					coveredNodePathI++;
			}
			if(coveredNodePathI>maxCoveredNode){
				result= new ArrayList<Path>();
				maxCoveredNode=coveredNodePathI;
				result.add(pathesFound.get(i));
			}else if(coveredNodePathI==maxCoveredNode){
				result.add(pathesFound.get(i));
			}
		}
		
		if(percorsiDaEliminare.size()>0)
		pathesFound.removeAll(percorsiDaEliminare);
		
		return result;
	}
	
	
	public Path checkDangerous(ArrayList<Path> pathes){
		Path minDanPath= pathes.get(0);
		float minDangerous= getDangerous(pathes.get(0));
		for (int i = 0; i < pathes.size(); i++) {
			if(getDangerous(pathes.get(i))<minDangerous){
				minDangerous=getDangerous(pathes.get(i));
				minDanPath=pathes.get(i);
				for(int j=1;j<minDanPath.getNodes().size();j++){
					dangerous[minDanPath.getNodes().get(j-1).getNumber()][minDanPath.getNodes().get(j).getNumber()]=0;
					dangerous[minDanPath.getNodes().get(j).getNumber()][minDanPath.getNodes().get(j-1).getNumber()]=0;
					
				}
				
			}
		}		
		return pathes.get(0);
		
	}
	
	public float getDangerous(Path path){
		float result=(float) 0.0;
		for (int i =1; i < path.getNodes().size(); i++) {
			result+=dangerous[path.getNodes().get(i-1).getNumber()][path.getNodes().get(i).getNumber()];
		}
		return (Float) result;
	}
	
	public void setFilePath(String filepath){
		this.filepath=filepath;
	}
	
	public void setNameFile(String name){
	this.nameFile=name;
	this.nameFile+=".sol";
	}
}
