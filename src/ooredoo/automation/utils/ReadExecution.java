package ooredoo.automation.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import ooredoo.automation.config.ExecuteEntity;

public class ReadExecution {
	 Workbook workbook;
	 ExecuteEntity exeEntity;
	 List<ExecuteEntity> executeList;
	 Row row = null;
	 Sheet internalsheet;
	public ArrayList<ExecuteEntity> getExecuteList(String FILE_NAME,String Fn_Name) {
		
		
		 try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			if(new File(FILE_NAME).getName().contains(".xlsx")) {
				 workbook = new XSSFWorkbook(excelFile);
			}
			
			else {
				 workbook = new HSSFWorkbook(excelFile);
			}
			
			 internalsheet = workbook.getSheet(Fn_Name);
			
			
			executeList=new ArrayList<ExecuteEntity>();
			
			for(int i=1; i<internalsheet.getLastRowNum()+1; i++) {
				row=internalsheet.getRow(i);
				exeEntity=new ExecuteEntity();
				exeEntity.setTS_ID(row.getCell(0).toString());
				exeEntity.setTS_Desc(row.getCell(1).toString());
				exeEntity.setTS_Fun_Name(row.getCell(2).toString());
				exeEntity.setTS_Exe_Flag(row.getCell(3).toString());
				
				executeList.add(exeEntity);
			}
				
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return (ArrayList<ExecuteEntity>) executeList;
	}
}
