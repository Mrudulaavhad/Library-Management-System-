import axios from 'axios'

const instance = axios.create({
  baseURL: "http://localhost:8080"
})

instance.interceptors.request.use(
  async (config) => {
    config.headers['Content-Type'] = 'application/json';
    config.headers['Access-Control-Allow-Origin'] = '*';
    return config;
  },
  (error) => {
    Promise.reject(error);
  }
);

instance.interceptors.response.use((response) => {
  return response;
}, error => {
    if (error.response.status === 401 || error.response.status === 500) {
      console.log(error.response.status)
      localStorage.clear();    }
    return Promise.reject(error);
  });


export default instance;

