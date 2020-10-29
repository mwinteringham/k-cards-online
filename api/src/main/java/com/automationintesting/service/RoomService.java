package com.automationintesting.service;

import com.automationintesting.db.KCardDB;
import com.automationintesting.db.service.RoomResult;
import com.automationintesting.model.Code;
import com.automationintesting.service.RoomCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class RoomService {

    @Autowired
    private KCardDB kCardDB;

    private RoomCodeGenerator roomCodeGenerator;

    @Autowired
    public RoomService() throws SQLException {
        roomCodeGenerator = new RoomCodeGenerator();
    }

    public RoomResult createRoom() throws SQLException {
        String code = roomCodeGenerator.createCode();

        if(kCardDB.addCode(code)){
            return new RoomResult(HttpStatus.CREATED, new Code(code));
        } else {
            return new RoomResult(HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
