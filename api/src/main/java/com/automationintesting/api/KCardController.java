package com.automationintesting.api;

import com.automationintesting.db.service.WorkshopResult;
import com.automationintesting.model.Workshop;
import com.automationintesting.service.WorkshopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class KCardController {

    @Autowired
    private WorkshopService workshopService;

    @RequestMapping(value = "/workshop", method = RequestMethod.POST)
    public ResponseEntity createWorkshop(@RequestBody Workshop workshop) throws SQLException {
        WorkshopResult workshopResult = workshopService.createWorkshop(workshop.getName());

        return ResponseEntity.status(workshopResult.getHttpStatus()).body(workshopResult.getCode());
    }

}
