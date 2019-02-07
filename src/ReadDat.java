import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadDat {

	private float alpha;
	private float[][] dangerous;
	
	
	
	public  void readFile(String filePath, ArrayList<Node> nodes, Integer nNodes, Float alpha,
			Float[][] dangerous) {
		String[] split = null;
		try (
				BufferedReader reader = new BufferedReader(new FileReader(filePath));

				)
				{
			String line = null;
			while ((line = reader.readLine()) != null) {
				if (line.contains(":=")) {
					split = line.split(" ");
					if (split[1] != null && split[0].equals("param")
							&& (split[2].equals(":=") || split[3].equals(":="))) {
						if (split[1].equals("n")) {
							nNodes = new Integer(split[3]);
							nNodes+=1;
							for (int i = 0; i < nNodes; i++) {
								Node tempNode= new Node();
								nodes.add(tempNode);
							}
						}
						if (split[1].equals("alpha")) {
							setAlpha(Float.parseFloat(split[3]));
							this.alpha = Float.parseFloat(split[3]);
						}
						if (split[1].equals("coordX")) {
							int column = 1;
							int row = 0;
							row = (nNodes)/ column;
							String[] split2;

							for (int i = 0; i < row+1 && (line = reader.readLine()) != null; i++) {
								split2 = line.split("\\s+");
								String[] split3 =null;
								if (!split2[0].matches("[0-9]+"))
								split3 = Arrays.copyOfRange(split2, 1, split2.length);
								else
									split3=split2;
								column = split3.length / 2;
								if (column!=0&&i==0) {
									row = nNodes / column;
								}

								for (int j = 0; j < split3.length; j += 2) {
									Node node = nodes.get(Integer.parseInt(split3[j]));
									node.setX(Integer.parseInt(split3[j + 1]));
									node.setNumber(Integer.parseInt(split3[j]));
								}
								if(line.length()<2){
									break;
								}
							}
						}

						if (split[1].equals("coordY")) {
							int column = 1;
							int row = 0;
							row = (nNodes)/ column;
							String[] split2;
							for (int i = 0; i < row+1 && (line = reader.readLine()) != null; i++) {
								split2 = line.split("\\s+");
								String[] split3 =null;
								if (!split2[0].matches("[0-9]+"))
								split3 = Arrays.copyOfRange(split2, 1, split2.length);
								else
									split3=split2;
								
								column = split3.length / 2;
								if (column!=0&&i==0) {
									row = nNodes / column;
								}
								for (int j = 0; j < split3.length; j += 2) {
									Node node = nodes.get(Integer.parseInt(split3[j]));
									node.setY(Integer.parseInt(split3[j + 1]));

								}
								if(line.length()<2){
									break;
								}
							}
						}

					}
				}

				if (line.contains("\t")) {
					if (this.dangerous == null)
						this.dangerous = new float[nNodes][nNodes];
					split = line.split("\t");

					for (int i = 0; i < nNodes && (line = reader.readLine()) != null; i++) {
						split = line.split("\t");
						for (int j = 1; j < nNodes + 1; j++) {
							this.dangerous[i][j - 1] = Float.parseFloat(split[j]);
						}
					}

				}

			}

		} catch (Exception e) {
			System.out.println("Error!\n");
			e.printStackTrace();
		}

	}
	
	public void setAlpha(float alpha){
		this.alpha=alpha;
	}
	public float getAlpha(){
		return this.alpha;
	}
	
	public float[][] getDangerous(){
		return this.dangerous;
	}
	
	
}
