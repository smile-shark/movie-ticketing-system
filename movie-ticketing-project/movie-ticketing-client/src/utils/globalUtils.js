import dayjs from "dayjs"
export const utils ={
    formatTimestampToYYYMMDDHHMMSS(timestamp) {
        return dayjs(timestamp).format('YYYY-MM-DD HH:mm:ss');
    },
    formatTimestampToYYYMMDD(timestamp) {
        return dayjs(timestamp).format('YYYY-MM-DD');
    }
}