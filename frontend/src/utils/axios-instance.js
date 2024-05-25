import axios from "axios";

const instance = axios.create({
    // baseURL: `${process.env.REACT_APP_BASE_URL}:${process.env.REACT_APP_PORT}/api/${process.env.REACT_APP_API_VERSION}`,
    // baseURL: 'http://localhost:8072/api/v1',
    baseURL: 'http://192.168.56.11:8072/api/v1',
    timeout: 10000,
    headers: {'X-Custom-Header': 'foobar'}
});
export default instance;