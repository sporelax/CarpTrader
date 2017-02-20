import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JPanel;

public class CarpTrader {
	
	int length = 2950;
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
		    }
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
	    }
	    	
	  void drawGraph(){	    
		  
		  GraphingData g2 = new GraphingData();
		  g2.setPaint(Color.black);
	  
          for(int i = 201; i < 1930; i++) {
              double x = PAD + (i-200)*xInc;
              double y = h - PAD - scale*data[i];
              g2.draw(new Line2D.Double(x-xInc,h - PAD - scale*data[i-1],x,y));
          }

            
          JFrame f = new JFrame("OMXS30 from 060320 to 120425");
          f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          f.add(new GraphingData());
          f.setSize(1400,800);
          f.setLocation(100,20);    
          GraphingData.setData(Close,ma50,ma200,plot5,plot4);
          f.setVisible(true);

	}
	  
	  class GraphingData extends JPanel {
		  final int PAD = 20;

		  protected void paintComponent(Graphics g) {
			  super.paintComponent(g);
			  Graphics2D g2 = (Graphics2D)g;
			  g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					  RenderingHints.VALUE_ANTIALIAS_ON);
			  int w = getWidth();
			  int h = getHeight();
			  double xInc = (double)(w - 2*PAD)/1950;
			  double scale = (double)(h - 2*PAD)/2000;
			  //Mark data points
			  g2.setPaint(Color.red);
			  for(int i = 0; i < data.length; i++) {
				  double x = PAD + i*xInc;
				  double y = h - PAD - scale*data[i];
				  g2.fill(new Ellipse2D.Double(x-2, y-2, 4, 4));
			  }
		  }
	  }

}
