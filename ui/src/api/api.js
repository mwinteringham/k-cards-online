const axios = require('axios');

const API = {

    createWorkshop : async (payload) => {
        const res = await axios({
            method: 'post',
            url: '/workshop',
            data: payload
        });
        
        return res;
    },

    joinWorkshop : async (workshopCode, payload) => {
        const res = await axios({
            method: 'post',
            url: '/workshop/' + workshopCode + '/join',
            data: payload
        });

        return res;
    }

}

export default API;