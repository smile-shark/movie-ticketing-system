import axios from "axios";
import { myPath } from "./path";
import CryptoJS from "crypto-js";
import { Loading } from "element-ui";
import router from "@/router";
import { utils } from "@/utils/globalUtils";

const key = "movie-ticketing-project";

function exportAxios(password){
  let aesStr=CryptoJS.AES.encrypt(password,key).toString()
  return aesStr
}
let loadingInstance

const instance = axios.create({
  baseURL: '/server8080',
});
instance.interceptors.request.use(
  (config) => {
    loadingInstance = Loading.service()
    // 验证请求路径为customer加上token
    if (config.url.includes('customer')){
      const token = localStorage.getItem("customerToken");
      if (token) {
        config.headers.Authorization = token;
      }
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
)
instance.interceptors.response.use(
  (response) => {
    if(response.data.code==401){
      router.push('/')
    }
    loadingInstance.close()
    return response;
  },
  (error) => {
    loadingInstance.close()
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
    },
    insertMovie(movie){
      return instance.post(myPath.insertMovie,movie)
    },
    selectAllMarket(){
      return instance.get(myPath.globalSelectAllMarket)
    },
    updateUserMarket(userId,marketId){
      return instance.put(myPath.updateUserByUserId,{
        userId:userId,
        marketId:marketId
      })
    },
    selectAllAllowedMobileDisplays(){
      return instance.get(myPath.selectAllAllowedMobileDisplays)
    },
    selectMovieList({movieId=null,movieName=null,director=null,star=null,issuingRegion=null,random=null,already=null,size=10,page=1}){
      return instance.get(myPath.selectMovieListCustomer,{
        params:{
          movieId,
          movieName,
          random,
          director,
          star,
          issuingRegion,
          already,// 0已上映的,1未上映的,null全部
          size,
          page
        }
      })
    },
    selectMovieListPlatform({movieId=null,movieName=null,director=null,star=null,issuingRegion=null,already=null,size=10,page=1}){
      return instance.get(myPath.selectMovieListPlatform,{
        params:{
          movieId,
          movieName,
          director,
          star,
          issuingRegion,
          already,
          size,
          page
        }
      })
    },
    updateMovie(movie){
      return instance.put(myPath.updateMovie,movie)
    },
    selectOneAdvertisementByRandom(){
      return instance.get(myPath.selectOneAdvertisementByRandom)
    },
    selectMovieTypesByName(movieTypeName){
      return instance.get(myPath.selectMovieTypesByName,{
        params:{
          movieTypeName
        }
      })
    },
    insertMovieType(movieTypeName){
      return instance.post(myPath.insertMovieType,null,{
        params:{
          movieTypeName
        }
      })
    },
    deleteMovieType(movieTypeId){
      return instance.delete(myPath.deleteMovieType,{
        params:{
          movieTypeId
        }
      })
    },
    selectUsersByUser(page=1,size=1,user=null){
      return instance.post(myPath.selectUsersByUser,user,{
        params:{
          page,
          size
        }
      })
    },
    platformUpdateUserByUserId(user){
      return instance.put(myPath.platformUpdateUserByUserId,user)
    },
    getMobleDisplay(){
      return instance.get(myPath.mobileDisplay)
    },
    insertMoblieDidpslay(mobileDisplay){
      return instance.post(myPath.mobileDisplay,mobileDisplay)
    },
    updateMobileDisplay(mobileDisplay){
      return instance.put(myPath.mobileDisplay,mobileDisplay)
    },
    deleteMobileDisplay(mobileDisplayImage){
      return instance.delete(myPath.mobileDisplay,{
        data: {
          mobileDisplayImage: mobileDisplayImage
        }
      })
    },
    selectSimpleMovieList(){
      return instance.get(myPath.selectSimpleMovieList)
    },
    cinemaManagementRegister(email,password,code){
      return instance.post(myPath.cinemaManagementRegister,{
        email:email,
        emailVerifyCode:code,
        password:exportAxios(password)
      })
    },
    cinemaManagementLogin(cinemaManagementEmail,cinemaManagementPassword){
      return instance.post(myPath.cinemaManagementLogin,{
        cinemaManagementEmail:cinemaManagementEmail,
        cinemaManagementPassword:exportAxios(cinemaManagementPassword)
      })
    },
    selectCinemaByCinemaManagementId(cinemaManagementId){
      return instance.get(myPath.selectCinemaByCinemaManagementId,{
        params:{
          cinemaManagementId
        }
      })
    },
    selectAllCinemaBrand(){
      return instance.get(myPath.selectAllCinemaBrand)
    },
    selectAllEconomize(){
      return instance.get(myPath.selectAllEconomize)
    },
    selectMarketByEconomizeId(economizeId){
      return instance.get(myPath.selectMarketByEconomizeId,{
        params:{
          economizeId
        }
      })
    },
    selectCountyByMarketId(marketId){
      return instance.get(myPath.selectCountyByMarketId,{
        params:{
          marketId
        }
      })
    },
    insertCinema(cinema){
      return instance.post(myPath.insertCinema,cinema)
    },
    insertScreeningRoom(screeningRoom){
      return instance.post(myPath.insertScreeningRoom,screeningRoom)
    },
    selectScreeningRoomByCinemaId(cinemaId){
      return instance.get(myPath.selectScreeningRoomByCinemaId,{
        params:{
          cinemaId
        }
      })  
    },
    updateScreeningRoom(screeningRoom){
      return instance.put(myPath.updateScreeningRoom,screeningRoom)
    },
    cinemaSelectSimpleMovieList(){
      return instance.get(myPath.cinemaSelectSimpleMovieList)
    },
    cinemaSelectMovieList({movieId=null,movieName=null,director=null,star=null,issuingRegion=null,already=null,size=10,page=1}){
      return instance.get(myPath.selectMovieListPlatform,{
        params:{
          movieId,
          movieName,
          director,
          star,
          issuingRegion,
          already,
          size,
          page
        }
      })
    },
    selectSimpleScreeningRoom(){
      return instance.get(myPath.selectSimpleScreeningRoom)
    },
    insertSliceArrangement(sliceArrangement){
      sliceArrangement.sliceArrangementStartTime=utils.urbanConversion(sliceArrangement.sliceArrangementStartTime)
      sliceArrangement.sliceArrangementEndTime=utils.urbanConversion(sliceArrangement.sliceArrangementEndTime)
      return instance.post(myPath.insertSliceArrangement,sliceArrangement)
    },
    selectScreeningRoomByScreeningRoomId(screeningRoomId){
      return instance.get(myPath.selectScreeningRoomByScreeningRoomId,{
        params:{
          screeningRoomId
        }
      })
    },
    selectSliceArrangementBySliceArrangement({sliceArrangementId=null,cinemaId=null,movieId=null,screeningRoomId=null,startTime=null,endTime=null,page=1,size=1}){
      if(sliceArrangementId==''){
        sliceArrangementId=null
      }
      if(cinemaId==''){
        cinemaId=null
      }
      if(movieId==''){
        movieId=null
      }
      if(screeningRoomId==''){
        screeningRoomId=null
      }
      if(startTime){
        startTime=utils.urbanConversion(startTime)
      }
      if(endTime){
        endTime=utils.urbanConversion(endTime)
      }
      return instance.get(myPath.selectSliceArrangementBySliceArrangement,{ 
        params:{
          sliceArrangementId,
          cinemaId,
          movieId,
          screeningRoomId,
          startTime,
          endTime,
          page,
          size
        }
    })
  },
  customerSelectAllCinemaBrand(){
    return instance.get(myPath.customerSelectAllCinemaBrand)
  },
  customerSelectCountyByMarketId(marketId){
    return instance.get(myPath.customerSelectCountyByMarketId,{
      params:{
        marketId
      }
    })
  },
  customerSelectLowPriceCinema(countyId,cinemaBrandId,movieId,page,size){
    return instance.get(myPath.customerSelectLowPriceCinema,{
      params:{
        countyId,
        cinemaBrandId,
        movieId,
        page,
        size
      }
    })
  },
  selectCinemaByCinemaId(cinemaId){
    return instance.get(myPath.selectCinemaByCinemaId,{
      params:{
        cinemaId
      }
    })
  },
  customerSelectSliceArrangementBySliceArrangement({sliceArrangementId=null,cinemaId=null,movieId=null,screeningRoomId=null,startTime=null,endTime=null,page=1,size=1}){
    if(sliceArrangementId==''){
      sliceArrangementId=null
    }
    if(cinemaId==''){
      cinemaId=null
    }
    if(movieId==''){
      movieId=null
    }
    if(screeningRoomId==''){
      screeningRoomId=null
    }
    if(startTime){
      startTime=utils.urbanConversion(startTime)
    }
    if(endTime){
      endTime=utils.urbanConversion(endTime)
    }
    return instance.get(myPath.selectSliceArrangementBySliceArrangement,{ 
      params:{
        sliceArrangementId,
        cinemaId,
        movieId,
        screeningRoomId,
        startTime,
        endTime,
        page,
        size
      }
  })
  },
  insertOrder(order){
    return instance.post(myPath.insertOrder,order)
  },
  selectOrderByOrderId(orderId){
    return instance.get(myPath.selectOrderByOrderId,{
      params:{
        orderId
      }
    })
  },
  updateOrderStatus(orderId,state){
    return instance.put(myPath.updateOrderStatus,null,{
      params:{
        orderId,
        state
      }
    })
  },
  selectOrderBySliceArrangementId(sliceArrangementId,page=1,size=1){
    return instance.get(myPath.selectOrderBySliceArrangementId,{
      params:{
        sliceArrangementId,
        page,
        size
      }
    })
  },
  selectOrderDetailByOrderId(orderId){
    return instance.get(myPath.selectOrderDetailByOrderId,{
      params:{
        orderId
      }
    })
  },
  selectOrderDetailListByOrder({
    orderId=null,
    userId=null,
    sliceArrangementId=null,
    votePayState=votePayState?votePayState:null,
    createTime=null,
    page=1,
    size=1
  }){
    return instance.get(myPath.selectOrderDetailListByOrder,{
      params:{
        orderId,
        userId,
        sliceArrangementId,
        votePayState,
        createTime,
        page,
        size
      }
    })
  },
  selectMovieListOrderByScore(){
    return instance.get(myPath.selectMovieListOrderByScore)
  },
  selectOrderNumByTime(cinemaId,startTime,endTime){
    return instance.get(myPath.selectOrderNumByTime,{
      params:{
        cinemaId,
        startTime:utils.urbanConversion(startTime),
        endTime:utils.urbanConversion(endTime)
      }
    })
  },
  selectSliceArrangementNumByTime(cinemaId,startTime,endTime){
    return instance.get(myPath.selectSliceArrangementNumByTime,{
      params:{
        cinemaId,
        startTime:utils.urbanConversion(startTime),
        endTime:utils.urbanConversion(endTime)
      }
    })
  },
  selectOrderVoteAllPriceByTime(cinemaId,startTime,endTime){
    return instance.get(myPath.selectOrderVoteAllPriceByTime,{
      params:{
        cinemaId,
        startTime:utils.urbanConversion(startTime),
        endTime:utils.urbanConversion(endTime)
      }
    })
  },
  selectAllOrderPrice(cinemaId){
    return instance.get(myPath.selectAllOrderPrice,{
      params:{
        cinemaId
      }
    })
  },
  selectChartValueByTimeAndMovie(cinemaId,startTime,endTime){
    return instance.get(myPath.selectChartValueByTimeAndMovie,{
      params:{
        cinemaId,
        startTime:utils.urbanConversion(startTime),
        endTime:utils.urbanConversion(endTime)
      }
    })
  },
  selectChartValueByTimeAndScreeningRoom(cinemaId,startTime,endTime){
    return instance.get(myPath.selectChartValueByTimeAndScreeningRoom,{
      params:{
        cinemaId,
        startTime:utils.urbanConversion(startTime),
        endTime:utils.urbanConversion(endTime)
      }
    })
  },
}
