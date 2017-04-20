package recognization;
import java.util.ArrayList;
/*keeps track of how and what should be displayed per store map*/

public class Slider {

	private ArrayList<String> report = new ArrayList<String>();
	private ArrayList<String> color = new ArrayList<String>();
	private ArrayList<String> nums = new ArrayList<String>();
	
	private ArrayList<String> ids = new ArrayList<String>();
	private ArrayList<String> coors = new ArrayList<String>();
	private ArrayList<String[]> whereArray = new ArrayList<String[]>();
	
	public void refreshCircles(){
		report = new ArrayList<String>();
		color = new ArrayList<String>();
		nums = new ArrayList<String>();
		ids = new ArrayList<String>();
		coors = new ArrayList<String>();
		whereArray = new ArrayList<String[]>();
	}

	public void addToWhere(String x, String y){
		String[] temp = {x,y};
		whereArray.add(temp);
	}

	public void addToReport(String s){
		report.add(s);
	}
	public void addDotArray(String s){
		color.add(s);
	}
	
	public void addNums(String i){
		nums.add(i);
	}
	
	public void addCameraID(int i){
		ids.add(i+"");
	}
	public ArrayList<String> getCameraID(){ return ids;}
	
	public void addXY(String i){
		coors.add(i);
	}
	public ArrayList<String> getXY(){ return coors;}
	
	public ArrayList<String> getColors(){return color;}
	public ArrayList<String> getReport(){return report;}
	public ArrayList<String> getNumbers(){return nums;}
	public ArrayList<String[]> getWhere(){return whereArray;}


	//splice for tab spaced lines
	public String splice(String[] slist){ return "t1";}
	public String splice(String s){return "t2";}
}
