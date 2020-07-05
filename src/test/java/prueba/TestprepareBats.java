package prueba;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import com.mycompany.prueba.model.Model;
/**
 * Test class for {@link com.mycompany.prueba.model.Model#preparedBats(java.lang.String)} method.
 * 
 * @author SERGI
 * @version 1.0
 * @since   2020-07-4
 */
class TestprepareBats {
	/**
	 * Test 1 Check if path had been written properly in create.bat
	 * 
	 * @param args Unused.
     * @return Nothing.
     * @exception FileNotFoundException .
     * @see FileNotFoundException
	 */
	@Test
	void prepareBatsTest1() {
		String pathPath = "";
		Model model = new Model();
		File dir = new File (".");
		String rootPath = dir.getAbsolutePath().split("prueba")[0] + "prueba\\";
		
		//this class auto-generate resourcesPath on src\\main, but here we are in test dir, so we change it
		//I know, i should use getters but..
		model.resourcesPath = rootPath + "src\\test\\resources\\";
		
		model.prepareBats(pathPath);
		File file = new File (rootPath + "src\\test\\resources\\Bats\\create.bat");
		
		Scanner sc;
		try {
			sc = new Scanner(file);
			assertEquals("@PATH=%PATH%;"+pathPath+";"+pathPath+"Scripts\\;",sc.nextLine());
		} catch (FileNotFoundException e) {
			//Auto-generated catch block
			fail("no file in: "+rootPath + "src\\test\\resources\\Bats\\create.bat" );
			e.printStackTrace();
		}	
	}
	
	/**
	 * Test 2  Check if python-command-line was written in prueba.bat,flatPlot.bat
	 * and 3dPlot.bat also no instance of model object is needed to run this method.
	 * 
	 * @param args Unused.
     * @return Nothing.
     * @exception FileNotFoundException .
     * @see FileNotFoundException
     * 
	 */
	@Test
	void prepareBatsTest2() {
		File dir = new File (".");
		String rootPath = dir.getAbsolutePath().split("prueba")[0] + "prueba\\";
		//LIST FILES¿?¿? Nope, there are some files that we dont want.. maybe doing some filtering on file.list could be better than that
		File file0 = new File (rootPath + "src\\test\\resources\\Bats\\read.bat");
		File file1 = new File (rootPath + "src\\test\\resources\\Bats\\flatPlot.bat"); 
		File file2 = new File (rootPath + "src\\test\\resources\\Bats\\3dPlot.bat");
		File file3 = new File (rootPath + "src\\test\\resources\\Bats\\write.bat");
		File [] files = {file0,file1,file2,file3};
		int [] lines_where_callPython_is = {17,14,14,14};
		int index = 0;
		String test = "";
		Scanner sc;
		for (File file: files) {
			try {
				sc = new Scanner(file);
				//TODO find other way, could be to look for " call xxx\python.exe " coincidence at line 10 and up..
				for (int i = 0; i < lines_where_callPython_is[index];i++) {
					sc.nextLine();				
				}
				String line = sc.nextLine();
				test += line.substring(0, 6);
				
			} catch (FileNotFoundException e) {
				//Auto-generated catch block
				fail("no file in: "+rootPath + "src\\test\\resources\\Bats\\" );
				e.printStackTrace();
			}
			index++;
		}
		assertEquals(" Call "+" Call "+" Call ",test);
	}
	
}
