//刷新按钮监听函数
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int inputYear = Integer.parseInt(yearField.getText().trim());
		String monthInfo = monthField.getSelectedItem().toString();
		int inputMonth = Integer.parseInt(monthInfo.substring(0,monthInfo.indexOf("月")));
		int monthDayNum=0;//本月总共的天数
		int hangNum=0;//行标记
		int dayNum=1;//当前使用的日期
		int weekNum=0;//本月第一天的星期值
		int dayNum_old=0;//当前使用的日期
		int dayNum_chose=0;//当前使用的日期
		

		
		monthDayNum=this.getLastDay(inputYear,inputMonth);
		weekNum=this.getWeekNum(inputYear,inputMonth);
		
        switch(weekNum)
        {
	        case 0: 
	        	weekNum=8;
	    		field1.setText("    1"+"                   2" +"                      3"+"                     4" +"                      5" +"                     6" +"                    7");
	    		dayNum=7;
	    		break;
	        case 1: 
	        	field1.setText("      "+"                   1" +"                      2"+"                     3" +"                      4" +"                     5" +"                    6");
	    		dayNum=6;
	    		break;
	        case 2: 
	        	field1.setText("        "+"                    " +"                      1"+"                     2" +"                      3" +"                     4" +"                    5");
	    		dayNum=5;
	    		break;
	        case 3: 
	        	field1.setText("         "+"                    " +"                       "+"                     1" +"                      2" +"                     3" +"                    4");
	    		dayNum=4;
	    		break;
	        case 4: 
	        	field1.setText("          "+"                    " +"                       "+"                      " +"                      1" +"                     2" +"                    3");
	    		dayNum=3;
	    		break;
	        case 5: 
	        	field1.setText("            "+"                    " +"                       "+"                      " +"                       " +"                     1" +"                    2");
	    		dayNum=2;
	    		break;
	        case 6: 
	        	field1.setText("             "+"                    " +"                       "+"                      " +"                       " +"                      " +"                    1");
	    		dayNum=1;
	    		break;
        }
        weekNum=dayNum+1;
		if (e.getSource() == refresh)
		{ 
	        //打印本月份的日期
	    	for(dayNum=weekNum;dayNum<=monthDayNum;dayNum+=7)
	    	{
	            switch(hangNum)
	            {
	    	        case 0: 
	    	        	field2.setText("    "+(dayNum)+"                   " +(dayNum+1)+"                      " +(dayNum+2)+"                     " +(dayNum+3)+"                      " +(dayNum+4)+"                     " +(dayNum+5)+"                    " +(dayNum+6));
	    	        	break;
	    	        case 1: 
	    	        	field3.setText("   "+(dayNum)+"                 " +(dayNum+1)+"                   " +(dayNum+2)+"                   " +(dayNum+3)+"                    " +(dayNum+4)+"                   " +(dayNum+5)+"                  " +(dayNum+6));
	    	        	break;
	    	        case 2: 
	    	        	field4.setText("   "+(dayNum)+"                 " +(dayNum+1)+"                   " +(dayNum+2)+"                   " +(dayNum+3)+"                    " +(dayNum+4)+"                   " +(dayNum+5)+"                  " +(dayNum+6));
	    	        	dayNum_old=(dayNum+6);
	    	        	break;
	    	        case 3: 
	    	        	dayNum_chose=monthDayNum-dayNum_old;
	    	            switch(dayNum_chose)
	    	            {
	    	    	        case 0: 
	    	    	        	weekNum=8;
	    	    	        	field5.setText("   ");
	    	    	        	field6.setText("   ");
	    	    	        	break;
	    	    	        case 1: 
	    	    	        	field5.setText("   "+(dayNum_old+1));
	    	    	        	field6.setText("   ");
	    	    	        	break;
	    	    	        case 2: 
	    	    	        	field5.setText("   "+(dayNum_old+1)+"                 " +(dayNum_old+2));
	    	    	        	field6.setText("   ");
	    	    	        	break;
	    	    	        case 3: 
	    	    	        	field5.setText("   "+(dayNum_old+1)+"                 " +(dayNum_old+2)+"                   " +(dayNum_old+3));
	    	    	        	field6.setText("   ");
	    	    	        	break;
	    	    	        case 4: 
	    	    	        	field5.setText("   "+(dayNum_old+1)+"                 " +(dayNum_old+2)+"                   " +(dayNum_old+3)+"                   " +(dayNum_old+4));
	    	    	        	field6.setText("   ");
	    	    	        	break;
	    	    	        case 5: 
	    	    	        	field5.setText("   "+(dayNum_old+1)+"                 " +(dayNum_old+2)+"                   " +(dayNum_old+3)+"                   " +(dayNum_old+4)+"                    " +(dayNum_old+5));
	    	    	        	field6.setText("   ");
	    	    	        	break;
	    	    	        case 6: 
	    	    	        	field5.setText("   "+(dayNum_old+1)+"                 " +(dayNum_old+2)+"                   " +(dayNum_old+3)+"                   " +(dayNum_old+4)+"                    " +(dayNum_old+5)+"                   " +(dayNum_old+6));
	    	    	        	field6.setText("   ");
	    	    	        	break;
	    	    	        case 7: 
	    	    	        	field5.setText("   "+(dayNum_old+1)+"                 " +(dayNum_old+2)+"                   " +(dayNum_old+3)+"                   " +(dayNum_old+4)+"                    " +(dayNum_old+5)+"                   " +(dayNum_old+6)+"                  " +(dayNum_old+7));
	    	    	        	field6.setText("   ");
	    	    	        	break;
	    	    	        case 8: 
	    	    	        	field5.setText("   "+(dayNum_old+1)+"                 " +(dayNum_old+2)+"                   " +(dayNum_old+3)+"                   " +(dayNum_old+4)+"                    " +(dayNum_old+5)+"                   " +(dayNum_old+6)+"                  " +(dayNum_old+7));
	    	    	        	field6.setText("   "+(dayNum_old+8));
	    	    	        	break;
	    	    	        case 9: 
	    	    	        	field5.setText("   "+(dayNum_old+1)+"                 " +(dayNum_old+2)+"                   " +(dayNum_old+3)+"                   " +(dayNum_old+4)+"                    " +(dayNum_old+5)+"                   " +(dayNum_old+6)+"                  " +(dayNum_old+7));
	    	    	        	field6.setText("   "+(dayNum_old+8)+"                 "+(dayNum_old+9));
	    	    	        	break;
	    	            }
	    	        	break;
	            }
	        	hangNum++;
			}
		}

		
	}