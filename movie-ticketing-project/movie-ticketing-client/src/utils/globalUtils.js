import dayjs from "dayjs"
export const utils ={
    formatTimestampToYYYMMDDHHMMSS(timestamp) {
        return dayjs(timestamp).format('YYYY-MM-DD HH:mm:ss');
    },
    formatTimestampToYYYMMDD(timestamp) {
        return dayjs(timestamp).format('YYYY-MM-DD');
    },
    verifyEmailFormat(email){
        if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email)){
            return false
        }
        return true
    }
}