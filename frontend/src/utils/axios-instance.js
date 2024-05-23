import axios from "axios";

const instance = axios.create({
    baseURL: 'http://192.168.56.11:8072/api/v1',
    timeout: 1000,
    headers: {'X-Custom-Header': 'foobar'}
});
export default instance;