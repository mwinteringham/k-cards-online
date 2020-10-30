package com.automationintesting.api;

import com.automationintesting.db.service.RoomResult;
import com.automationintesting.model.RoomRequest;
import com.automationintesting.service.RoomService;
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
    private RoomService roomService;

    @RequestMapping(value = "/room", method = RequestMethod.POST)
    public ResponseEntity createRoom(@RequestBody RoomRequest roomRequest) throws SQLException {
        RoomResult roomResult = roomService.createRoom(roomRequest.getWorkshopName());

        return ResponseEntity.status(roomResult.getHttpStatus()).body(roomResult.getCode());
    }

}
