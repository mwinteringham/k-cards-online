const axios = require('axios');

const API = {

    createWorkshop : async (payload) => {
        try {
            const res = await axios({
                method: 'put',
                url: '/workshop',
                data: payload
            });
            
            return res;
        } catch(err) {
            return err.response;
        }
    },

    joinWorkshop : async (workshopCode, payload) => {
        try{
            const res = await axios({
                method: 'post',
                url: '/workshop/' + workshopCode + '/join',
                data: payload
            });
    
            return res;
        } catch (err) {
            return err.response;
        }
    },
    
    sendCard : async (workshopCode, payload) => {
        try {
            const res = await axios({
                method: 'post',
                url: '/workshop/' + workshopCode + '/card',
                data: payload
            });
    
            return res;
        } catch(err){
            return err.response;
        }
    },

    leaveWorkshopAsAttendee : async (workshopCode, payload) => {
        try {
            const res = await axios({
                method: 'delete',
                url: '/workshop/' + workshopCode + '/leave',
                data: payload
            });
    
            return res;
        } catch(err) {
            return err.response;
        }
    },

    getActivity : async (workshopCode) => {
        try{
            const res = await axios({
                method: 'get',
                url: '/workshop/' + workshopCode + '/activity'
            });
    
            return res;
        } catch(err) {
            return err.response;
        }
    },

    deleteCard : async (workshopCode, cardCode) => {
        try {
            const res = await axios({
                method: 'delete',
                url: '/workshop/' + workshopCode + '/card/' + cardCode
            });

            return res;
        } catch (err) {
            return err.response;
        }
    },

    deleteWorkshop : async (workshopCode) => {
        try {
            const res = await axios({
                method: 'delete',
                url: '/workshop/' + workshopCode
            });
    
            return res;
        } catch (err) {
            return err.response;
        }
    }

}

export default API;