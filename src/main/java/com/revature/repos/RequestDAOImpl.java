package com.revature.repos;

import com.revature.models.Request;
import com.revature.models.RequestDTO;
import com.revature.models.ResolveDTO;
import com.revature.utils.ConnectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RequestDAOImpl implements RequestDAO {

    private Logger logger = LoggerFactory.getLogger("RequestDAO Logger");

    @Override
    public List<Request> showAllRequests() {
        try (Statement statement = ConnectionUtil.getConnection().createStatement()){
            String sqlStatement = "SELECT r.reimb_id, r.reimb_amount, r.reimb_submitted, r.reimb_resolved, " +
                    "r.reimb_description, r.reimb_author, r.reimb_resolver, t.reimb_type, s.reimb_status\n" +
                    "FROM ers_reimbursement AS r\n" +
                    "JOIN ers_reimbursement_type AS t ON r.reimb_type_id = t.reimb_type_ID\n" +
                    "JOIN ers_reimbursement_status AS s ON r.reimb_status_id = s.reimb_status_id;";
            List<Request> requestList = new ArrayList<>();
            ResultSet rs = statement.executeQuery(sqlStatement);
            logger.info("The connection was established and the query was run against the database");
            while (rs.next()) {
                int requestId = rs.getInt("reimb_id");
                double amount = rs.getDouble("reimb_amount");
                String submitted = rs.getTimestamp("reimb_submitted").toString();
                Timestamp resolvedDate = rs.getTimestamp("reimb_resolved");
                String resolved = "";
                if (resolvedDate != null) {
                    resolved = resolvedDate.toString();
                }else{
                    resolved = "N/A";
                }
                String description = rs.getString("reimb_description");
                int author = rs.getInt("reimb_author");
                int resolver = rs.getInt("reimb_resolver");
                String type = rs.getString("reimb_type");
                String status = rs.getString("reimb_status");
//int requestId, double amount, Date submitted, Date resolved, String description, int author, int resolver, String status, String type)
                Request a = new Request(requestId, amount, submitted, resolved, description, author, resolver, status, type);
                requestList.add(a);
            }
            if (!requestList.isEmpty()){
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
    public List<Request> showUserRequests(int userId) {
        try (Connection conn = ConnectionUtil.getConnection()){
            String sqlStatement = "SELECT r.reimb_id, r.reimb_amount, r.reimb_submitted, r.reimb_resolved, " +
                    "r.reimb_description, r.reimb_author, r.reimb_resolver, t.reimb_type, s.reimb_status\n" +
                    "FROM ers_reimbursement AS r\n" +
                    "JOIN ers_reimbursement_type AS t ON r.reimb_type_id = t.reimb_type_ID\n" +
                    "JOIN ers_reimbursement_status AS s ON r.reimb_status_id = s.reimb_status_id" +
                    "WHERE reimb_author = ?;";
            List<Request> requestList = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(sqlStatement);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            logger.info("The connection was established and the query was run against the database");
            while (rs.next()) {
                int requestId = rs.getInt("reimb_id");
                double amount = rs.getDouble("reimb_amount");
                String submitted = rs.getTimestamp("reimb_submitted").toString();
                Timestamp resolvedDate = rs.getTimestamp("reimb_resolved");
                String resolved = "";
                if (resolvedDate != null) {
                    resolved = resolvedDate.toString();
                }else{
                    resolved = "N/A";
                }
                String description = rs.getString("reimb_description");
                int author = rs.getInt("reimb_author");
                int resolver = rs.getInt("reimb_resolver");
                String type = rs.getString("reimb_type");
                String status = rs.getString("reimb_status");
//int requestId, double amount, Date submitted, Date resolved, String description, int author, int resolver, String status, String type)
                Request a = new Request(requestId, amount, submitted, resolved, description, author, resolver, status, type);
                requestList.add(a);
            }
            if (!requestList.isEmpty()){
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
            String sqlStatement = "SELECT r.reimb_id, r.reimb_amount, r.reimb_submitted, r.reimb_resolved, r.reimb_description,"+
                    "r.reimb_author, r.reimb_resolver, t.reimb_type_id, t.reimb_type, s.reimb_status_id, s.reimb_status "+
            "FROM ers_reimbursement AS r "+
            "JOIN ers_reimbursement_type AS t ON r.reimb_type_id = t.reimb_type_ID "+
            "JOIN ers_reimbursement_status AS s ON r.reimb_status_id = s.reimb_status_id "+
            "WHERE s.reimb_status = ?;";
            List<Request> requestList = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(sqlStatement);
            statement.setString(1, status);
            ResultSet rs = statement.executeQuery();
            logger.info("The connection was established and the query was run against the database");
            while (rs.next()) {
                int requestId = rs.getInt("reimb_id");
                double amount = rs.getDouble("reimb_amount");
                String submitted = rs.getTimestamp("reimb_submitted").toString();
                Timestamp resolvedDate = rs.getTimestamp("reimb_resolved");
                String resolved = "";
                if (resolvedDate != null) {
                    resolved = resolvedDate.toString();
                }
                String description = rs.getString("reimb_description");
                int author = rs.getInt("reimb_author");
                int resolver = rs.getInt("reimb_resolver");
                String type = rs.getString("reimb_type");
                String returnedStatus = rs.getString("reimb_status");
                Request a = new Request(requestId, amount, submitted, resolved, description, author, resolver, returnedStatus, type);
                requestList.add(a);
            }

            if (!requestList.isEmpty()){
                return requestList;
            }else{
                logger.debug("No entries were retrieved");
            }

            return requestList;
        }catch (SQLException e){
            e.printStackTrace();
            logger.error("The connection to the database failed.");
        }

        logger.info("Something went wrong and the query didn't run correctly.");
        return null;
    }

    @Override
    public List<Request> showUserRequestsByStatus(String status, int userId) {
        try (Connection conn = ConnectionUtil.getConnection()){
            String sqlStatement = "SELECT r.reimb_id, r.reimb_amount, r.reimb_submitted, r.reimb_resolved, r.reimb_description,"+
                    "r.reimb_author, r.reimb_resolver, t.reimb_type_id, t.reimb_type, s.reimb_status_id, s.reimb_status "+
                    "FROM ers_reimbursement AS r "+
                    "JOIN ers_reimbursement_type AS t ON r.reimb_type_id = t.reimb_type_ID "+
                    "JOIN ers_reimbursement_status AS s ON r.reimb_status_id = s.reimb_status_id "+
                    "WHERE s.reimb_status = ?, reimb_author = ?;";
            List<Request> requestList = new ArrayList<>();
            PreparedStatement statement = conn.prepareStatement(sqlStatement);
            statement.setString(1, status);
            statement.setInt(2, userId);
            ResultSet rs = statement.executeQuery();
            logger.info("The connection was established and the query was run against the database");
            while (rs.next()) {
                int requestId = rs.getInt("reimb_id");
                double amount = rs.getDouble("reimb_amount");
                String submitted = rs.getTimestamp("reimb_submitted").toString();
                Timestamp resolvedDate = rs.getTimestamp("reimb_resolved");
                String resolved = "";
                if (resolvedDate != null) {
                    resolved = resolvedDate.toString();
                }
                String description = rs.getString("reimb_description");
                int author = rs.getInt("reimb_author");
                int resolver = rs.getInt("reimb_resolver");
                String type = rs.getString("reimb_type");
                String returnedStatus = rs.getString("reimb_status");
                Request a = new Request(requestId, amount, submitted, resolved, description, author, resolver, returnedStatus, type);
                requestList.add(a);
            }

            if (!requestList.isEmpty()){
                return requestList;
            }else{
                logger.debug("No entries were retrieved");
            }

            return requestList;
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
                    "VALUES ('Pending');";

            PreparedStatement statement = conn.prepareStatement(insertQuery,Statement.RETURN_GENERATED_KEYS);
            statement.execute();
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                int requestStatusId = rs.getInt("reimb_status_id");
                return requestStatusId;
            }else{
                logger.debug("A ReimbStatus id was not generated");
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
    public boolean resolveRequest(ResolveDTO resolveDTO) {
        int statusId = getStatusId(resolveDTO.requestID);
        try (Connection conn = ConnectionUtil.getConnection()){
            logger.info("Before queries are run in RequestDAOImpl resolve request");
            String updateQuery = "UPDATE ers_reimbursement_status SET reimb_status = ? WHERE reimb_status_id = ?;";
            String addResolveDate = "UPDATE ers_reimbursement SET reimb_resolved = ?, reimb_resolver = ? WHERE reimb_id = ?;";

            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setString(1, resolveDTO.resolveChoice);
            statement.setInt(2, statusId);
            statement.execute();

            PreparedStatement statement2 = conn.prepareStatement(addResolveDate);
            statement2.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            statement2.setInt(2,resolveDTO.authorId);
            statement2.setInt(3, resolveDTO.requestID);
            statement2.execute();

            logger.info("The connection was established and the resolveRequest queries were run against the database");
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            logger.error("The connection to the database failed for resolveRequest.");
        }

        logger.info("Something went wrong and the resolveRequest didn't run correctly.");
        return false;
    }

    private int getStatusId(int requestID) {
        try (Connection conn = ConnectionUtil.getConnection()){
            logger.info("Before queries are run in RequestDAOImpl resolve request");
            String updateQuery = "SELECT reimb_status_id FROM ers_reimbursement WHERE reimb_id = ?;";

            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setInt(1, requestID);
            ResultSet rs = statement.executeQuery();
            if (rs.next()){
                logger.info("Got the status ID");
                return rs.getInt("reimb_status_id");
            }else {
                logger.info("Did not get the status ID");
            }
        }catch (SQLException e){
            e.printStackTrace();
            logger.error("The connection to the database failed for resolveRequest.");
        }
        return 0;
    }

}
