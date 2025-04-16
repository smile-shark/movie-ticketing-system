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

export const myApi={
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
    },
    customerLogin(email,password){
      return instance.post(myPath.customerLogin,{
        userEmail:email,
        userPassword:exportAxios(password)
      })
    },
    platformLogin(account,password){
      return instance.post(myPath.platformLogin,{
        platformManagementAccount:account,
        platformManagementPassword:exportAxios(password)
      })
    },
    selectAllMovieType(){
      return instance.get(myPath.selectAllMovieType)
    },
    fileUpload(file){
      return instance.post(myPath.fileUpload,file,{
        headers:{
          'Content-Type':'multipart/form-data'
        }
      })
    },
    fileDelete(fileName){
      console.log(fileName)
      return instance.get(myPath.fileDelete,{
        params:{
          fileName:fileName
        }
      })
    }
}
