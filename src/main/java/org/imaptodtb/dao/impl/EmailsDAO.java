/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.imaptodtb.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Level;
import org.imaptodtb.dao.IEmailsDAO;
import org.imaptodtb.database.DatabaseSpring;
import org.imaptodtb.entity.Emails;
import org.imaptodtb.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author bcivel
 */
@Repository
public class EmailsDAO implements IEmailsDAO {

    @Autowired
    private DatabaseSpring databaseSpring;
    
    @Override
    public void insertEmails(Emails emails) {
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO emails (`id`,`from`,`replyto`,`to`,`cc`, `bcc`, `subject`, `sendDate`, `message`, `receiveddate`, `uid`) ");
        query.append("VALUES (0,?,?,?,?,?,?,?,?,?,?)");

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query.toString());
            try {
                preStat.setString(1, emails.getFrom());
                preStat.setString(2, emails.getReplyTo());
                preStat.setString(3, emails.getTo());
                preStat.setString(4, emails.getCc());
                preStat.setString(5, emails.getBcc());
                preStat.setString(6, emails.getSubject());
                preStat.setString(7, emails.getSendDate());
                preStat.setString(8, emails.getMessage());
                preStat.setString(9, emails.getReceivedDate());
                preStat.setLong(10, emails.getUid());

                preStat.executeUpdate();

            } catch (SQLException exception) {
                Logger.log(EmailsDAO.class.getName(), Level.ERROR, exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            Logger.log(EmailsDAO.class.getName(), Level.ERROR, exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.log(EmailsDAO.class.getName(), Level.WARN, e.toString());
            }
        }}

    @Override
    public Emails getLastMessage() {
        boolean throwExcep = false;
        Emails result = null;
        final String query = "SELECT * FROM emails order by id desc limit 1";

        Connection connection = this.databaseSpring.connect();
        try {
            PreparedStatement preStat = connection.prepareStatement(query);
            try {
                
                ResultSet resultSet = preStat.executeQuery();
                try {
                    if (resultSet.first()) {
                        result = this.loadMessage(resultSet);
                    } 
                } catch (SQLException exception) {
                    Logger.log(EmailsDAO.class.getName(), Level.ERROR, exception.toString());
                } finally {
                    resultSet.close();
                }
            } catch (SQLException exception) {
                Logger.log(EmailsDAO.class.getName(), Level.ERROR, exception.toString());
            } finally {
                preStat.close();
            }
        } catch (SQLException exception) {
            Logger.log(EmailsDAO.class.getName(), Level.ERROR, exception.toString());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                Logger.log(EmailsDAO.class.getName(), Level.ERROR, e.toString());
            }
        }
        
        return result;
    }
    
    private Emails loadMessage(ResultSet resultSet) throws SQLException {
        Emails email = new Emails();
        email.setId(resultSet.getInt("id"));
        email.setFrom(resultSet.getString("from"));
        email.setReplyTo(resultSet.getString("replyto"));
        email.setTo(resultSet.getString("to"));
        email.setCc(resultSet.getString("cc"));
        email.setBcc(resultSet.getString("bcc"));
        email.setSubject(resultSet.getString("subject"));
        email.setSendDate(resultSet.getString("senddate"));
        email.setMessage(resultSet.getString("message"));
        email.setReceivedDate(resultSet.getString("receiveddate"));
        email.setUid(resultSet.getLong("uid"));

        return email;
    }
    
}
