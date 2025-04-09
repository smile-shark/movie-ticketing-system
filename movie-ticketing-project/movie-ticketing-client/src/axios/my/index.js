import axios from "axios";
import { myPath } from "./path";
import CryptoJS from "crypto-js";

const key = "movie-ticketing-project";

function exportAxios(password){
  let aesStr=CryptoJS.AES.encrypt(password,key).toString()
  return aesStr
}


const instance = axios.create({
  baseURL: 'http://localhost:8080',
});

instance.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    return Promise.reject(error);
  }
);

const api={
    sendMailVerifyCode(email){
        return instance.post(myPath.sendMailVerifyCode,{
            email:email
        })
    },
    customerRegister(email,password,code){
      return instance.post(myPath.customerRegister,{
        email:email,
        emailVerifyCode:code,
        password:exportAxios(password)
      })
    }
}

export default api;