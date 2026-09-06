package com.hostel.dao;

import com.hostel.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FeeDAO {

    public double getMonthlyRevenue() {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM fee_payments WHERE status = 'COMPLETED' AND " +
                     "DATE_PART('month', payment_date) = DATE_PART('month', CURRENT_DATE) AND " +
                     "DATE_PART('year', payment_date) = DATE_PART('year', CURRENT_DATE)";

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0.0;
    }
}
