/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.imaptodtb.service;

import org.imaptodtb.entity.Emails;

/**
 *
 * @author bcivel
 */
public interface IEmailsService {
    
    void insertEmails(Emails email);
}
