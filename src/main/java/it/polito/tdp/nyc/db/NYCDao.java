package it.polito.tdp.nyc.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.nyc.model.Adiacenza;
import it.polito.tdp.nyc.model.Hotspot;

public class NYCDao {
	
	public List<Hotspot> getAllHotspot(){
		String sql = "SELECT * FROM nyc_wifi_hotspot_locations";
		List<Hotspot> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Hotspot(res.getInt("OBJECTID"), res.getString("Borough"),
						res.getString("Type"), res.getString("Provider"), res.getString("Name"),
						res.getString("Location"),res.getDouble("Latitude"),res.getDouble("Longitude"),
						res.getString("Location_T"),res.getString("City"),res.getString("SSID"),
						res.getString("SourceID"),res.getInt("BoroCode"),res.getString("BoroName"),
						res.getString("NTACode"), res.getString("NTAName"), res.getInt("Postcode")));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<String> getProvider(){
		String sql = "SELECT DISTINCT Provider FROM nyc_wifi_hotspot_locations";
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("Provider"));
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;

	}

	public List<String> getVertici(String p) {
		String sql = "SELECT DISTINCT City FROM nyc_wifi_hotspot_locations"
				+ " WHERE Provider=?";
		List<String> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, p);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(res.getString("City"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
	
	public List<Adiacenza> getArchi(String p){
		String sql = "SELECT n1.City, n2.City, n1.Latitude AS lat1, n1.Longitude as lon1,"
				+ " n2.Latitude AS lat2, n2.Longitude as lon2"
				+ " FROM nyc_wifi_hotspot_locations n1,nyc_wifi_hotspot_locations n2"
				+ " WHERE n1.Provider=? AND n1.Provider=n2.Provider"
				+ " AND n1.City!=n2.City";
		List<Adiacenza> result = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, p);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				LatLng peso1=new LatLng(res.getDouble("lat1"),res.getDouble("lon1"));
				LatLng peso2=new LatLng(res.getDouble("lat2"),res.getDouble("lon2"));
				result.add(new Adiacenza(res.getString("n1.City"),res.getString("n2.City"), peso1, peso2));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return result;
	}
}
