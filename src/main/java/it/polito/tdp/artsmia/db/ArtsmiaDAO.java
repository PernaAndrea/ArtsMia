package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.ArtObject;

public class ArtsmiaDAO {

	public void listObjects(Map<Integer,ArtObject> map) {
		
		String sql = "SELECT * from objects";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				if(!map.containsKey(res.getInt("object_id"))) {
				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				map.put(artObj.getId(),artObj);
				}
			}
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
	}

	public int getPeso(ArtObject a1, ArtObject a2) { //PER APPROCCIO 1
		// TODO Auto-generated method stub
		String sql = "SELECT COUNT(*) AS peso "
				+ "FROM exhibition_objects e1,exhibition_objects e2 "
				+ "WHERE e1.exhibition_id=e2.exhibition_id AND e1.object_id=? AND e2.object_id=?";
				
				Connection conn = DBConnect.getConnection();
		
				try {
					PreparedStatement st = conn.prepareStatement(sql);
					st.setInt(1, a1.getId());
					st.setInt(2, a2.getId());
					ResultSet res = st.executeQuery();
					int peso = 0;
					
					if(res.next()) {
						peso = res.getInt("peso");
					}
					
					conn.close();
					return peso;
					
				} catch (SQLException e) {
					e.printStackTrace();
					
				}
		return 0;
	}
	
	
	public List<Adiacenza> getAdiacenze() { // APPROCCIO 3 ----classe di supporto alla classe sopra per approccio 3 
		//creo anche la classe adiacenza
		String sql = "SELECT e2.object_id , e1.object_id, COUNT(*) AS peso "
				+ "FROM exhibition_objects e1,exhibition_objects e2 "
				+ "WHERE e1.exhibition_id=e2.exhibition_id AND e1.object_id > e2.object_id "
				+ "GROUP BY e2.object_id , e1.object_id";
		
				Connection conn = DBConnect.getConnection();
				List<Adiacenza> result = new ArrayList<Adiacenza>();
				
				try {
					PreparedStatement st = conn.prepareStatement(sql);
					ResultSet res = st.executeQuery();
					
					
					while(res.next()) {
						result.add(new Adiacenza(res.getInt("id1"),res.getInt("id2"),res.getInt("peso")));
					}
					
					conn.close();
					return result;
					
				} catch (SQLException e) {
					e.printStackTrace();
					
				}
		return null;
	}
}
