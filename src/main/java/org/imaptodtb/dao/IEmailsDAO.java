/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.imaptodtb.dao;

import org.imaptodtb.entity.Emails;


/**
 *
 * @author bcivel
 */
public interface IEmailsDAO {
    
    void insertEmails(Emails emails);
}
