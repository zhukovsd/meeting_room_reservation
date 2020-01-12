package com.mokhovav.meeting_room_reservation.services;

import com.mokhovav.meeting_room_reservation.datatables.MeetingRoom;
import com.mokhovav.meeting_room_reservation.datatables.Role;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeetingRoomService {
    final DAOService<MeetingRoom> daoService;

    public MeetingRoomService(DAOService<MeetingRoom> daoService) {
        this.daoService = daoService;
    }

    public boolean isExist(String name) {
        return name.isEmpty() ? false : (MeetingRoom)daoService.findObject("from MeetingRoom where roomName='"+name+"'") != null;
    }

    public boolean isExist(MeetingRoom meetingRoom) {
        return meetingRoom!=null ? isExist(meetingRoom.getRoomName()) : false;
    }

    public boolean isExist(Long id) {
        return getById(id) != null;
    }

    public MeetingRoom getById(Long id){
        return id > 0 ? (MeetingRoom)daoService.findObject("from MeetingRoom where id="+id) : null;
    }

    public List<MeetingRoom> getAll(){
        return (List<MeetingRoom>)daoService.findAll(MeetingRoom.class);
    }

    public boolean save(MeetingRoom meetingRoom){
        if(!isExist(meetingRoom) && !meetingRoom.getRoomName().isEmpty()) {
            daoService.save(meetingRoom);
            return true;
        }
        return false;
    }

    public boolean update (MeetingRoom meetingRoom) {
        if( meetingRoom!=null && getById(meetingRoom.getId()) != null  && !meetingRoom.getRoomName().isEmpty()) {
            daoService.update(meetingRoom);
            return true;
        }
        return false;
    }

    public boolean delete(Long id) {
        MeetingRoom meetingRoom = getById(id);
        if(meetingRoom == null ) return false;
        daoService.delete(meetingRoom);
        return true;
    }

    public MeetingRoom getByRoomName(String name){
        return name.isEmpty()? null : (MeetingRoom)daoService.findObject("from MeetingRoom where roomName='"+name+"'");
    }
}
