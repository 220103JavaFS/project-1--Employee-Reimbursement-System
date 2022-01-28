package com.revature.repos;

import com.revature.models.Request;
import com.revature.utils.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RequestDAOImpl implements RequestDAO {

    Logger logger = LoggerFactory.getLogger("RequestDAO Logger");

    @Override
    public List<Request> showAllRequests() {
        try (Connection conn = ConnectionUtil.getConnection()){
            String sqlStatement = "SELECT * FROM (SELECT request.reimb_id, request.reimb_amount, request.reimb_submitted, request.reimb_resolved, request.reimb_description, request.reimb_author, request.reimb_resolver, request_type.reimb_type, request.reimb_status_id\n" +
                    "FROM ers_reimbursement AS request JOIN ers_reimbursement_type AS request_type ON request.reimb_type_id = request_type.reimb_type_ID) AS q JOIN ers_reimbursement_status AS status ON q.reimb_status_id=status.reimb_status_id;";
            List<Request> requestList = new ArrayList<>();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sqlStatement);
            logger.info("The connection was established and the query was run against the database");
            while (rs.next()) {
                int requestId = rs.getInt("reimb_id");
                double amount = rs.getDouble("reimb_amount");
                Date submitted = rs.getDate("reimb_submitted");
                Date resolved = rs.getDate("reimb_resolved");
                String description = rs.getString("reimb_description");
                int author = rs.getInt("reimb_author");
                int resolver = rs.getInt("reimb_resolver");
                String type = rs.getString("reimb_type");
                String status = rs.getString("reimb_status");
                Request a = new Request(requestId, amount, submitted, resolved, description, author, resolver, status, type);
                requestList.add(a);
            }

            if (requestList.isEmpty()){
                return null;
            }else {
                return requestList;
            }
        }catch (SQLException e){
            e.printStackTrace();
            logger.error("The connection to the database failed.");
        }

        logger.info("Something went wrong and the query didn't run correctly.");
        return null;
    }

    @Override
    public List<Request> showByStatus(String status) {
        return null;
    }

    @Override
    public boolean addRequest(Request request) {

        return false;
    }

    @Override
    public boolean approveRequest(int reimbId) {
        return false;
    }

    @Override
    public boolean denyRequest(int reimbId) {
        return false;
    }
}
