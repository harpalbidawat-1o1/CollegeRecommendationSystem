
package com.college.dao;

import com.college.db.DBConnection;
import com.college.model.College;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CollegeDAO {

    public List<College> getAllColleges() {
        List<College> colleges = new ArrayList<>();

        String sql = "SELECT * FROM colleges";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                colleges.add(new College(
                    rs.getInt("college_id"),
                    rs.getString("college_name"),
                    rs.getString("city"),
                    rs.getString("university"),
                    rs.getString("address")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return colleges;
    }

    public boolean addCollege(College college) {
        String sql = "INSERT INTO colleges " +
                "(college_name, city, university, address) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, college.getCollegeName());
            ps.setString(2, college.getCity());
            ps.setString(3, college.getUniversity());
            ps.setString(4, college.getAddress());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCollege(College college) {
        String sql = "UPDATE colleges SET college_name=?, city=?, university=?, address=? WHERE college_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, college.getCollegeName());
            ps.setString(2, college.getCity());
            ps.setString(3, college.getUniversity());
            ps.setString(4, college.getAddress());
            ps.setInt(5, college.getCollegeId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCollege(int collegeId) {
        String sql = "DELETE FROM colleges WHERE college_id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, collegeId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
