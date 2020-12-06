const axios = require('axios');

const API = {

    createWorkshop : async (payload) => {
        try {
            const res = await axios({
                method: 'post',
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
        const res = await axios({
            method: 'delete',
            url: '/workshop/' + workshopCode + '/leave',
            data: payload
        });

        return res;
    },

    getActivity : async (workshopCode) => {
        const res = await axios({
            method: 'get',
            url: '/workshop/' + workshopCode + '/activity'
        });

        return res;
    },

    deleteCard : async (workshopCode, cardCode) => {
        const res = await axios({
            method: 'delete',
            url: '/workshop/' + workshopCode + '/card/' + cardCode
        });

        return res;
    },

    deleteWorkshop : async (workshopCode) => {
        const res = await axios({
            method: 'delete',
            url: '/workshop/' + workshopCode
        });

        return res;
    }

}

export default API;