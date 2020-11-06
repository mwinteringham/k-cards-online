// const axios = require('axios');

const API = {

    createWorkshop : async (payload) => {
        // const res = await axios({
        //     method: 'post',
        //     url: '/workshop',
        //     data: payload
        // });

        const res = {
            status : 201
        }
        
        return res;
    },

    joinWorkshop : () => {
        const res = {
            status : 200
        }

        return res;
    }

}

export default API;