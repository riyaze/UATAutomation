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
import ooredoo.automation.config.TestCaseEntity;

public class ReadTestCase {
	
	 Workbook workbook;
	 TestCaseEntity caseEntity;
	 List<TestCaseEntity> testcaselist;
	 Row row = null;
	 Sheet internalsheet ;
	public   ArrayList<TestCaseEntity> getTestCases(String FILE_NAME,String Fn_Name) {
		
		
		 try {
			FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));
			if(new File(FILE_NAME).getName().contains(".xlsx")) {
				 workbook = new XSSFWorkbook(excelFile);
			}
			
			else {
				 workbook = new HSSFWorkbook(excelFile);
			}
			
			 internalsheet = workbook.getSheet(Fn_Name);
			
			
			testcaselist=new ArrayList<TestCaseEntity>();
			
			for(int i=1; i<internalsheet.getLastRowNum()+1; i++) {
				row=internalsheet.getRow(i);
				caseEntity=new TestCaseEntity();
				caseEntity.setTS_ID(row.getCell(0).toString());
				caseEntity.setTC_ID(row.getCell(1).toString());
				caseEntity.setTC_Desc(row.getCell(2).toString());
				caseEntity.setTC_KeyWord(row.getCell(3).toString());
				caseEntity.setTC_Object(row.getCell(4).toString());
				caseEntity.setTC_Data(row.getCell(5).toString());
				
				testcaselist.add(caseEntity);
			}
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 return (ArrayList<TestCaseEntity>) testcaselist;
	}
}
