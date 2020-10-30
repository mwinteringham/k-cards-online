package com.automationintesting.service;

import com.automationintesting.db.KCardDB;
import com.automationintesting.db.service.WorkshopResult;
import com.automationintesting.model.Workshop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class WorkshopService {

    @Autowired
    private KCardDB kCardDB;

    private WorkshopCodeGenerator workshopCodeGenerator;

    @Autowired
    public WorkshopService() {
        workshopCodeGenerator = new WorkshopCodeGenerator();
    }

    public WorkshopResult createWorkshop(String workshopName) throws SQLException {
        String code = workshopCodeGenerator.createCode();

        if(kCardDB.addCode(code, workshopName)){
            return new WorkshopResult(HttpStatus.CREATED, new Workshop(code, workshopName));
        } else {
            return new WorkshopResult(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
