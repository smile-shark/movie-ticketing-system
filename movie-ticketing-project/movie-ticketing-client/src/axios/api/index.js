import axios from 'axios';
import { apiPath } from './path';

const api = axios.create();

const apis={
    getVerifyCode:function(url,code){
        return api.get(url,{
                    params:{
                        verifycode:code,
                        qskey:'redis-sk-MATKmIISApWiByAc'
                    }
                })
    },
    getKey(){
        return api.get(apiPath.getKey)
    }
}
export default apis;