import axios from "axios"

const deploymentURL = 'https://i10a504.p.ssafy.io/'

// local vue api axios instance
function localAxios() {
  const instance = axios.create({
    baseURL: deploymentURL,
    headers: {
      "Content-Type": "application/json;charset=utf-8",
    },
  });
  return instance;
}

export { localAxios }