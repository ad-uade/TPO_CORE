/**
 * 
 */
package util.inicializadores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.hibernate.SQLQuery;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;

import util.HibernateUtil;
/**
 * @author huicha
 *
 */
public class InitSQL {

	public void execute() {
		String aSQLScriptFilePath = "src/main/resources/script.sql";

		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(aSQLScriptFilePath));

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
				Session currentSession = HibernateUtil.getSessionFactory().openSession();
				Transaction currentTransaction = currentSession.beginTransaction();
				SQLQuery query = currentSession.createSQLQuery(sCurrentLine);
				query.executeUpdate();
				currentTransaction.commit();
				currentSession.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
