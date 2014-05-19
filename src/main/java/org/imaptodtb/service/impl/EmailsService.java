/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.imaptodtb.service.impl;

import org.imaptodtb.dao.IEmailsDAO;
import org.imaptodtb.entity.Emails;
import org.imaptodtb.service.IEmailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bcivel
 */
@Service
public class EmailsService implements IEmailsService {

    @Autowired
    IEmailsDAO emailsDao;
    
    @Override
    public void insertEmails(Emails email) {
        emailsDao.insertEmails(email);
    }
    
}
