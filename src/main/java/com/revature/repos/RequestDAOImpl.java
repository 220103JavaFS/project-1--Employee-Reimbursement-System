package com.revature.repos;

import com.revature.models.Request;
import com.revature.models.RequestDTO;
import com.revature.utils.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestDAOImpl implements RequestDAO {

    Logger logger = LoggerFactory.getLogger("RequestDAO Logger");

    @Override
    public List<Request> showAllRequests() {
        try (Connection conn = ConnectionUtil.getConnection()){
            String sqlStatement = "SELECT r.reimb_id, r.reimb_amount, r.reimb_submitted, r.reimb_resolved, r.reimb_description, r.reimb_author, r.reimb_resolver, t.reimb_type, s.reimb_status\n" +
                    "FROM ers_reimbursement AS r\n" +
                    "JOIN ers_reimbursement_type AS t ON r.reimb_type_id = t.reimb_type_ID\n" +
                    "JOIN ers_reimbursement_status AS s ON r.reimb_status_id = s.reimb_status_id;";
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
        try (Connection conn = ConnectionUtil.getConnection()){
            String sqlStatement = "SELECT r.reimb_id, r.reimb_amount, r.reimb_submitted, r.reimb_resolved, r.reimb_description, " +
                    "r.reimb_author, r.reimb_resolver, t.reimb_type_id, t.reimb_type, s.reimb_status_id s.reimb_status " +
                    "FROM ers_reimbursement AS r " +
                    "JOIN ers_reimbursement_type AS t ON r.reimb_type_id = t.reimb_type_ID " +
                    "JOIN ers_reimbursement_status AS s ON r.reimb_status_id = s.reimb_status_id " +
                    "WHERE s.status = ?";
            List<Request> requestList = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(sqlStatement);
            statement.setString(1, status);
            ResultSet rs = statement.executeQuery();
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
                String returnedStatus = rs.getString("reimb_status");
                Request a = new Request(requestId, amount, submitted, resolved, description, author, resolver, returnedStatus, type);
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
    public int addReimbStatus() {
        try (Connection conn = ConnectionUtil.getConnection()){
            String insertQuery = "INSERT INTO ers_reimbursement_status (reimb_status)\n" +
                    "VALUES ('pending');";

            PreparedStatement statement = conn.prepareStatement(insertQuery,Statement.RETURN_GENERATED_KEYS);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int requestStatusId = rs.getInt("reimb_status_id");
                return requestStatusId;
            }else{
                logger.debug("A ReimbStatus id was not generated");
                return 0;
            }



        }catch (SQLException e){
            e.printStackTrace();
            logger.error("The connection to the database failed.");
        }

        logger.info("Something went wrong and the query didn't run correctly.");
        return 0;

    }

    @Override
    public int addReimbType(String type) {
        try (Connection conn = ConnectionUtil.getConnection()){
            String insertQuery = "INSERT INTO ers_reimbursement_type (reimb_type)\n" +
                    "VALUES (?);";

            PreparedStatement statement = conn.prepareStatement(insertQuery,Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, type);
            statement.execute();
            logger.info("The connection was established and the query was run against the database");
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int requestTypeId = rs.getInt("reimb_type_id");
                return requestTypeId;
            }else{
                logger.debug("A ReimbType id was not generated");
                return 0;
            }
        }catch (SQLException e){
            e.printStackTrace();
            logger.error("The connection to the database failed.");
        }

        logger.info("Something went wrong and the query didn't run correctly.");
        return 0;
    }


    @Override
    public boolean addRequest(RequestDTO request) {
        int statusId = addReimbStatus();
        int typeId = addReimbType(request.type);

        if (statusId != 0 && typeId != 0) {
            try (Connection conn = ConnectionUtil.getConnection()) {
                String sqlStatement = "INSERT INTO ers_reimbursement (reimb_amount, reimb_description, reimb_author, "+
                "reimb_status_id, reimb_type_id, reimb_submitted)\n" +
                        "VALUES(?, ?, ?, ?, ?, ?);";

                PreparedStatement statement = conn.prepareStatement(sqlStatement);
                statement.setDouble(1, request.amount);
                statement.setString(2, request.description);
                statement.setInt(3, request.authorId);
                statement.setInt(4, statusId);
                statement.setInt(5, typeId);
                statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));

                statement.execute();
                logger.info("The connection was established and the query was run against the database");

                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                logger.error("The connection to the database failed.");
            }

            logger.info("Something went wrong and the query didn't run correctly.");
        }else{
            logger.debug("Either the status or the type were not inserted correctly");
        }
        return false;
    }

    @Override
    public boolean approveRequest(int reimbId) {
        try (Connection conn = ConnectionUtil.getConnection()){
            String updateQuery = "UPDATE ers_reimbursement_status SET reimb_status = 'approved' WHERE reimb_status_id = ?;";

            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setInt(1, reimbId);
            statement.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            logger.error("The connection to the database failed.");
        }

        logger.info("Something went wrong and the query didn't run correctly.");
        return false;
    }

    @Override
    public boolean denyRequest(int reimbId) {
        try (Connection conn = ConnectionUtil.getConnection()){
            String updateQuery = "UPDATE ers_reimbursement_status SET reimb_status = 'denied' WHERE reimb_status_id = ?;";

            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setInt(1, reimbId);
            statement.execute();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            logger.error("The connection to the database failed.");
        }

        logger.info("Something went wrong and the query didn't run correctly.");
        return false;
    }
}
