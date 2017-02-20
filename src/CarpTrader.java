import java.awt.*;
import java.awt.geom.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

public class CarpTrader {

	static int length = 2943;
	Double[] hi = new Double[length];
	Double[] lo = new Double[length];
	Double[] cl = new Double[length];
	Double[] ma50 = new Double[length];
	Double[] ma200 = new Double[length];
	String[] date = new String[length];


	public static void main(String[] args) throws FileNotFoundException, IOException {

		System.out.println("CarpTrader version 1.0");

		CarpTrader ct = new CarpTrader();
		ct.readFile();
		ct.ma200();
		ct.ma50();
		ct.drawGraph();
	}


	void readFile() throws FileNotFoundException, IOException{
		try (BufferedReader br = new BufferedReader(new FileReader("050608-170217.csv"))) {
			String line;

			//Skip first line, it's just a description of the fields
			line = br.readLine();
			int i = 0;
			//Read all lines and place in variables
			while ((line = br.readLine()) != null) {
				String[] splitLine = line.split(";");
				hi[i]=Double.parseDouble(splitLine[1]);
				lo[i]=Double.parseDouble(splitLine[2]);
				cl[i]=Double.parseDouble(splitLine[3]);
				date[i]=splitLine[0];
				i++;
			}
		}
	}

	void ma50(){
		//Ma50
		for (int k=50;k<length;k++){
			double temp = 0;
			for (int j=k-50;j<k;j++){
				temp = temp + cl[j];
			}
			ma200[k]=temp/50;
		}
	}
	
	void ma200(){
		//Ma200
		for (int k=200;k<length;k++){
			double temp = 0;
			for (int j=k-200;j<k;j++){
				temp = temp + cl[j];
			}
			ma200[k]=temp/200;
		}
	}

	void calcMF(double startMoney){

		/*
	    double dblCurMoney=startMoney;
	    int intBuyTiming=1;
	    int intSellTiming=0;

	    for (int k=200;k<i;k++){
	    	if(ma200[k]>=ma50[k]&&intSellTiming==1){
	                    dblCurMoney=dblStocks*Close[k];
	                    dblStocks=0;
	                    intBuyTiming=1;
	                    intSellTiming=0;
	                    dblLastMoney=dblCurMoney;
	           } else if(ma200[k]<=ma50[k]&&intBuyTiming==1){
	                    dblStocks = dblCurMoney/Close[k];
	                    dblCurMoney=dblStocks*Close[k];
	                    dblLastMoney=dblCurMoney;
	                    dblCurMoney=0;
	                    intBuyTiming=0;
	                    intSellTiming=1;
	          }
	    }




	    dblCurMoney=startMoney;
	    intBuyTiming=1;
	    intSellTiming=0;
	    for (int k=200;k<i;k++){
	      	if(ma200[k]>=ma50[k]&&intSellTiming==1){
	                dblCurMoney=dblStocks*Close[k];
	                temp=Close[k];
	                 intBuyTiming=1;
	                 intSellTiming=0;
	                 dblLastMoney=dblCurMoney;
	            } else if(ma200[k]<=ma50[k]&&intBuyTiming==1){
	                 dblCurMoney=(2*temp-Close[k])*dblStocks;
	                 dblLastMoney=dblCurMoney;
	    		 dblStocks = dblCurMoney/Close[k];
	                 dblCurMoney=0;
	                 intBuyTiming=0;
	                 intSellTiming=1;   
	             }
	    }


	    for(int j=3;j<9;j++){
	        dblCurMoney=startMoney;
	        for (int k=201;k<i;k++){
	             if(ma200[k]>=ma50[k]){
	                 dblCurMoney=dblCurMoney*(((Close[k]/Close[k-1]-1)*-0.3*j)+1);
	             } else {
	                 dblCurMoney=dblCurMoney*(((Close[k]/Close[k-1]-1)*0.3*j)+1);
	             }
	   	}
	   }


	    for (int j=4;j<20;j+=1){
	        int intDiscount = 25;
	        dblCurMoney=startMoney;
	        intBuyTiming=1;
	        intSellTiming=0;
	    }
	    for (int k=200;k<i;k++){
	    	if(dblStocks<0||dblCurMoney<0||(Close[k]-temp+intDiscount*j<0&&intSellTiming==1)||(temp-Close[k]+intDiscount*j<0&&intBuyTiming==1&&temp!=0)){
	                                System.out.print("ERROR: negative value!!!!!!!!");
	                                break;
	                            }


	    	if(ma200[k]>=ma50[k]&&intSellTiming==1){
	            dblCurMoney=dblStocks*(Close[k]-temp+intDiscount*j)
	            dblStocks = dblCurMoney/(intDiscount*j);
	            temp=Close[k];
	            intBuyTiming=1;
	            intSellTiming=0;
	            dblLastMoney=dblCurMoney;
	        } else if(ma200[k]<=ma50[k]&&intBuyTiming==1){
	             dblCurMoney=dblStocks*(temp-Close[k]+intDiscount*j);
	             dblLastMoney=dblCurMoney;
	          	dblStocks = dblCurMoney/(intDiscount*j);
	             temp=Close[k];
	             dblCurMoney=0;
	             intBuyTiming=0;
	              intSellTiming=1;
	        }
		 */
	}


	public void drawGraph(){	    

		JFrame f = new JFrame("OMXS30 from 060320 to 120425");
		GraphingData g = new GraphingData();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(g);
		f.setSize(1400,800);
		f.setLocation(100,20);    
		g.setData(cl,ma50,ma200);
		f.setVisible(true);

	}

	static class GraphingData extends JPanel {
		Double[] cl = new Double[length];
		Double[] ma50 = new Double[length];
		Double[] ma200 = new Double[length];
		final int PAD = 20;

		void setData(Double[] d1, Double[] d2, Double[] d3){
			cl = d1;
			ma50 = d2;
			ma200 = d3;
		}
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			int w = getWidth();
			int h = getHeight();
			double xInc = (double)(w - 2*PAD)/1950;
			double scale = (double)(h - 2*PAD)/2000;
			
			g2.setPaint(Color.black);
			System.out.println("Length cl: "+cl.length+", ma50: "+ma50.length+", ma200: "+ma200.length);
			for(int i = 201; i < 1930; i++) {
				double x = PAD + (i-200)*xInc;
				double y = h - PAD - scale*cl[i];
				g2.draw(new Line2D.Double(x-xInc,h - PAD - scale*cl[i-1],x,y));
			}
			/*
			g2.setPaint(Color.blue);
			for(int i = 201; i < 1930; i++) {
				double x = PAD + (i-200)*xInc;
				double y = h - PAD - scale*ma50[i];
				g2.draw(new Line2D.Double(x-xInc,h - PAD - scale*ma50[i-1],x,y));
			}

			g2.setPaint(Color.red);
			for(int i = 201; i < 1930; i++) {
				double x = PAD + (i-200)*xInc;
				double y = h - PAD - scale*ma200[i];
				g2.draw(new Line2D.Double(x-xInc,h - PAD - scale*ma200[i-1],x,y));
			}
			*/
		} 
	}

}
